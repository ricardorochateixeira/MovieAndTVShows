package com.ricardoteixeira.movietvshowsexplorer.app.presentation.userprofile

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.edituserprofile.EditUserProfileActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_user_profile_activity.*
import kotlinx.android.synthetic.main.user_profile_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserProfileActivity: AppCompatActivity() {

    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val editUserProfileActivity = Intent(this, EditUserProfileActivity::class.java)

        setContentView(R.layout.user_profile_activity)

        val mainActivity = Intent(this, MainActivity::class.java)

        supportActionBar?.hide()

        val view = findViewById<View>(R.id.user_profile_activity)

        viewModel.userState.observe(this@UserProfileActivity, Observer { state ->
            when (state.isLoading) {
                true -> handleLoading()
                false -> stopIsLoading()
            }
        })

        viewModel.userState.observe(this@UserProfileActivity, Observer {
            text_user_name.text = getString(R.string.user_profile_name, it.data?.name)
            image_user_photo.setImageURI(it.data?.imageUri?.toUri())
        })

        lifecycleScope.launchWhenStarted {
            viewModel.userProfileEvent.collect { event ->
                when(event) {
                    is UserProfileViewModel.UserProfileEvent.NavigateToEditUserProfileActivity -> startActivity(editUserProfileActivity)

                    is UserProfileViewModel.UserProfileEvent.ShowYouAreNotLoggedSnackBar -> Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()

                    is UserProfileViewModel.UserProfileEvent.NavigateToMainActivity -> startActivity(mainActivity)
                }
            }
        }

        initProfile()

        button_edit_user_profile_activity.setOnClickListener {
            handleClickEditButton()
        }

        button_back_user_profile_activity.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun handleLoading() {
        user_profile_activity.alpha = 0.5F
        text_user_name.visibility = View.INVISIBLE
        image_user_photo.visibility = View.INVISIBLE
        button_edit_user_profile_activity.visibility = View.INVISIBLE
        progress_bar_user_profile_activity.alpha = 1F
        progress_bar_user_profile_activity.visibility = View.VISIBLE
    }

    private fun stopIsLoading() {
        user_profile_activity.alpha = 1F
        text_user_name.visibility = View.VISIBLE
        image_user_photo.visibility = View.VISIBLE
        button_edit_user_profile_activity.visibility = View.VISIBLE
        progress_bar_user_profile_activity.visibility = View.INVISIBLE
    }

    private fun handleClickEditButton() {
        viewModel.handleClickEditButton()
    }

    override fun onRestart() { initProfile()
        viewModel.userState.observe(this@UserProfileActivity, Observer {
            text_user_name.text = getString(R.string.user_profile_name, it.data?.name)
            image_user_photo.setImageURI(it.data?.imageUri?.toUri())
        })
        super.onRestart()
    }

    private fun initProfile() {
        viewModel.initProfile()
    }

    private fun navigateToMainActivity() {
        viewModel.navigateToMainActivity()
    }
}