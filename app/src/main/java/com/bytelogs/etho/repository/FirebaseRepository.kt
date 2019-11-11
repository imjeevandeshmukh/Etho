package com.bytelogs.etho.repository

import androidx.lifecycle.MutableLiveData
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener




public class FirebaseRepository {

    val auth: FirebaseAuth
    private val database :FirebaseDatabase
    private val storage:FirebaseStorage
    private  val signInStatus = MutableLiveData<Int>()
    private  val createUserStatus = MutableLiveData<Int>()
    private  val uploadStatus = MutableLiveData<Int>()
    private  val readUserDetail = MutableLiveData<User>()
    private lateinit var  profileImageUrl :String
    init {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    fun signInEmailPassword(email:String,password:String):MutableLiveData<Int>{
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                 signInStatus.postValue(AppConstants.SUCCESS)

            } else {
                signInStatus.postValue(AppConstants.FAILURE)
            }

        })
        return signInStatus
    }
    fun getUser(): FirebaseUser? {
        return auth?.currentUser
    }
    fun createUserWithEmailPassword(email: String,password: String):MutableLiveData<Int>{
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(OnCompleteListener {
            if(it.isSuccessful){
                createUserStatus.value = AppConstants.SUCCESS
            } else{
                createUserStatus.value = AppConstants.FAILURE
            }
        })
        return createUserStatus
    }
    fun getUserDetail():MutableLiveData<User>{
        val uid =  getUser()?.uid
        database.getReference("Users").child(uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val  user  = dataSnapshot.getValue(User::class.java)
                readUserDetail.value = user
            }

            override fun onCancelled(databaseError: DatabaseError) {
                readUserDetail.value = null

            }
        })
        return readUserDetail


    }
    fun saveUserProfileInfo(user: User){
        val uid =  getUser()?.uid
        val usersRef = database.getReference("Users")
        user.profileImgUrl = profileImageUrl
        val users = mutableMapOf<String,User>()
        users.put(uid.toString(), user)
        usersRef.setValue(users)

    }
    fun uploadFile(uri:Uri):LiveData<Int>{
        val uid =  getUser()?.uid
        val imapath = storage.getReference("profile_images").child(uid + ".jpg")
        val uploadTask = imapath.putFile(uri)
        uploadTask.continueWithTask(object :
            Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
            @Throws(Exception::class)
            override fun then(task: Task<UploadTask.TaskSnapshot>): Task<Uri> {
                if (!task.isSuccessful) {
                    throw task.getException()!!
                }
                return imapath.getDownloadUrl()
            }
        }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
            if (task.isSuccessful) {
                profileImageUrl = task.result.toString()
                uploadStatus.value  = AppConstants.SUCCESS
            } else {
                uploadStatus.value  = AppConstants.FAILURE
            }
        })
        return uploadStatus


    }
}