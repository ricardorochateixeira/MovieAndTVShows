package com.ricardoteixeira.movietvshowsexplorer.app.presentation.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.main.MainActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.login.LoginActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.login.PasswordDialog
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterActivity: AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.register_activity)

        supportActionBar?.hide()

        val view = findViewById<View>(R.id.register_activity)

        val intentMovies = Intent(this, MainActivity::class.java)
        val intentWelcome = Intent(this, WelcomeActivity::class.java)
        val intentLogin = Intent(this, LoginActivity::class.java)

        image_information.setOnClickListener {
            showDialog()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.registerEvent.collect { event ->
                when (event) {
                    is RegisterViewModel.RegisterEvent.NavigateToLoginActivity -> {
                        startActivity(intentLogin)
                    }

                    is RegisterViewModel.RegisterEvent.EnterAsAGuest -> {
                        startActivity(intentMovies)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is RegisterViewModel.RegisterEvent.NavigateToWelcomeActivity -> {
                        startActivity(intentWelcome)
                    }

                    is RegisterViewModel.RegisterEvent.ErrorRegistering -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is RegisterViewModel.RegisterEvent.InsertPasswordOrEmail -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is RegisterViewModel.RegisterEvent.NavigateToMoviesFragment -> {
                        startActivity(intentMovies)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is RegisterViewModel.RegisterEvent.PasswordNotEqualConfirmPassword -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                        clearText()
                    }

                    is RegisterViewModel.RegisterEvent.PasswordLength -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                        clearText()
                    }

                    is RegisterViewModel.RegisterEvent.ShowCheckName -> {
                        check_name.visibility = View.VISIBLE
                    }

                    is RegisterViewModel.RegisterEvent.HideCheckName -> {
                        check_name.visibility = View.INVISIBLE
                    }

                    is RegisterViewModel.RegisterEvent.ShowCheckPassword -> {
                        check_password.visibility = View.VISIBLE
                    }

                    is RegisterViewModel.RegisterEvent.HideCheckPassword -> {
                        check_password.visibility = View.INVISIBLE
                    }

                    is RegisterViewModel.RegisterEvent.ShowCheckConfirmPassword -> {
                        check_confirm_password.visibility = View.VISIBLE
                    }

                    is RegisterViewModel.RegisterEvent.HideCheckConfirmPassword -> {
                        check_confirm_password.visibility = View.INVISIBLE
                    }
                }
            }

            button_go_back_register_activity.setOnClickListener {
                navigateToWelcomeActivity()
            }
        }

        button_go_back_register_activity.setOnClickListener {
            navigateToWelcomeActivity()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        button_register_register_activity.setOnClickListener {
            register()
        }

        text_login_register_activity.setOnClickListener {
            navigateToLoginActivity()
        }

        text_enter_as_guest_register_activity.setOnClickListener {
            enterAsAGuest()
        }

        observeNameTextChange()
        observePasswordTextChange()
        observeConfirmPasswordTextChange()
        viewModel.nameTextAsLiveData.observe(this, {})
        viewModel.passwordTextAsLiveData.observe(this, {})
        viewModel.passwordConfirmTextAsLiveData.observe(this, {})
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun clearText() {
        edit_password_register_activity.text.clear()
        edit_confirm_password_register_activity.text.clear()
    }

    private fun register() {
        val email = edit_email_register_activity.text.toString()
        val password = edit_password_register_activity.text.toString()
        val confirmPassword = edit_confirm_password_register_activity.text.toString()
        val name = edit_name_register_activity.text.toString()
        viewModel.registerUser(email, password, confirmPassword, name)
    }

    private fun navigateToLoginActivity() {
        viewModel.navigateToLoginActivity()
    }

    private fun enterAsAGuest() {
        viewModel.enterAsAGuest()
    }

    private fun navigateToWelcomeActivity() {
        viewModel.navigateToWelcomeActivity()
    }

    private fun showDialog() {
        PasswordDialog(this).show()
    }

    private fun observeNameTextChange() {
        edit_name_register_activity.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.nameText.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observePasswordTextChange() {
        edit_password_register_activity.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.passwordText.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeConfirmPasswordTextChange() {
        edit_confirm_password_register_activity.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.passwordConfirmText.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

}