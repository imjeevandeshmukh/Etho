package com.bytelogs.etho.view

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.BaseActivity
import com.bytelogs.etho.R
import com.bytelogs.etho.model.User
import com.bytelogs.etho.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import android.content.Intent
import android.app.Activity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class ProfileActivity : BaseActivity(),View.OnClickListener {

    private lateinit var profileViewModel: ProfileViewModel
    private var  profileUrl:String= ""
    private var  resultUri:Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btSave.setOnClickListener(this)
        ivProfile.setOnClickListener(this)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        getUserDetail()





    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btSave -> uploadImage()
            R.id.ivProfile -> openImageIntent()
        }
    }
    private fun getUserDetail(){
        profileViewModel.getUserDetail().observe(this, Observer {
              if(it !=null){
                  etAge.setText(it.age)
                  etMobile.setText(it.mobile)
                  etName.setText(it.name)
                  etOccupation.setText(it.occupation)
                  Glide.with(this).load(it.profileImgUrl).into(ivProfile)

              }
        })
    }
    private fun uploadImage(){
        if(!resultUri.toString().isNullOrEmpty()) {
            progressBar2.visibility = View.VISIBLE
            profileViewModel.uploadFile(resultUri).observe(this, Observer {

                when (it) {

                    AppConstants.SUCCESS -> {
                        toast("Upload Successful")
                        saveUser()
                    }

                    AppConstants.FAILURE -> {
                        progressBar2.visibility = View.GONE
                        toast("Upload failed")
                    }
                }

            })
        }else{
            toast("Please select image by tapping on profile picture")
        }
    }
    private fun saveUser(){
        val user=  User(etName.text.toString(),etMobile.text.toString(),etAge.text.toString(),etOccupation.text.toString(),profileUrl)
        profileViewModel.onClickSave(user).observe(this, Observer {
            progressBar2.visibility = View.GONE
            when(it){
                AppConstants.FAILURE -> toast("Please fill in all fields")

                AppConstants.SUCCESS -> toast("Details saved")
            }

        })
    }
    private fun openImageIntent(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)
            }else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
        }else {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode === Activity.RESULT_OK) {
                resultUri = result.uri
                ivProfile.setImageURI(resultUri)

            } else if (resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this)
                }
                return
            }
        }
    }
}
