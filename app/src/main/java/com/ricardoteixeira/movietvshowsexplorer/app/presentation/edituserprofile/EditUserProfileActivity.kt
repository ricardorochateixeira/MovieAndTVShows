package com.ricardoteixeira.movietvshowsexplorer.app.presentation.edituserprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.userprofile.UserProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_user_profile_activity.*
import kotlinx.android.synthetic.main.edit_user_profile_activity.view.*
import kotlinx.android.synthetic.main.user_profile_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditUserProfileActivity : AppCompatActivity() {

    companion object {
        private val IMAGE_CHOOSE = 1000
        private val PERMISSION_CODE = 1001
    }

    private val viewModel: EditUserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_user_profile_activity)

        supportActionBar?.hide()

        val userProfileIntent = Intent(this, UserProfileActivity::class.java)

        viewModel.userEditState.observe(this, Observer {
            edit_username_edit_user_profile_activity.setText(it.data?.name)
            text_email_edit_user_profile_Activity.text = it.data?.email
            image_edit_user_photo.setImageURI(it.data?.imageUri?.toUri())

        })

        viewModel.userEditState.observe(this, Observer { state ->
            when (state.isLoading) {
                true -> handleLoading()
                false -> stopIsLoading()
            }
        })

        lifecycleScope.launchWhenStarted {
            viewModel.userProfileEvent.collect { event ->
                when (event) {
                    is EditUserProfileViewModel.EditUserProfileEvent.AreYouSureYouWantToCancel ->
                        AlertDialog.Builder(this@EditUserProfileActivity)
                            .setTitle(event.message)
                            .setPositiveButton("Yes") { _, _ -> navigateToUserProfileActivityWithoutSaving() }
                            .setNegativeButton("No") { _, _ -> }
                            .show()

                    is EditUserProfileViewModel.EditUserProfileEvent.DoYouWantToSaveChanges ->
                        AlertDialog.Builder(this@EditUserProfileActivity)
                            .setTitle(event.message)
                            .setPositiveButton("Yes") { _, _ -> navigateToUserProfileActivitySaving() }
                            .setNegativeButton("No") { _, _ -> }
                            .show()

                    is EditUserProfileViewModel.EditUserProfileEvent.NavigateToUserProfileActivity -> {
                        userProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(userProfileIntent)
                        this@EditUserProfileActivity.finish()
                    }
                }
            }
        }

        image_edit_user_photo.setOnClickListener {
            checkCameraPermission()
        }

        button_save_changes_edit_user_profile_activity.setOnClickListener {
            saveChanges()
        }

        button_cancel_changes_edit_user_profile_activity.setOnClickListener {
            cancelChanges()
        }

        button_back_edit_user_profile_activity.setOnClickListener {
            cancelChanges()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchGallery()
                } else {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            image_edit_user_photo.setImageURI(imageUri)
            viewModel.setImageUri(imageUri.toString())
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_CHOOSE
        )
    }

    @SuppressLint("WrongConstant")
    private fun checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    application,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                launchGallery()
            }
        } else {
            launchGallery()
        }
    }

    private fun saveChanges() {
        viewModel.saveChanges()
    }

    private fun cancelChanges() {
        viewModel.cancelChanges()
    }

    private fun navigateToUserProfileActivityWithoutSaving() {
        viewModel.navigateToUserProfileActivityWithoutSaving()
    }

    private fun navigateToUserProfileActivitySaving() {
        val userName = edit_username_edit_user_profile_activity.text.toString()
        viewModel.saveUserProfile(userName)
        viewModel.navigateToUserProfileActivitySaving()
    }

    private fun handleLoading() {
        progress_bar_Edit_user_profile_activity.visibility = View.VISIBLE
        edit_user_profile_activity.visibility = View.INVISIBLE
    }

    private fun stopIsLoading() {
        progress_bar_Edit_user_profile_activity.visibility = View.INVISIBLE
        edit_user_profile_activity.visibility = View.VISIBLE
    }
}

