package com.ricardoteixeira.movietvshowsexplorer.app.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.register.RegisterActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity() : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        supportActionBar?.hide()

        val view = findViewById<View>(R.id.loginActivity)

        val intentMovies = Intent(this, MainActivity::class.java)
        val intentWelcome = Intent(this, WelcomeActivity::class.java)
        val intentRegister = Intent(this, RegisterActivity::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LoginEvent.NavigateToRegisterActivity -> {
                        startActivity(intentRegister)
                    }

                    is LoginViewModel.LoginEvent.NavigateToMoviesFragment -> {
                        startActivity(intentMovies)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is LoginViewModel.LoginEvent.EnterAsAGuest -> {
                        startActivity(intentMovies)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is LoginViewModel.LoginEvent.ErrorLoggingIn -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is LoginViewModel.LoginEvent.InsertPasswordOrEmail -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is LoginViewModel.LoginEvent.NavigateToWelcomeActivity -> {
                        startActivity(intentWelcome)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    }

                    is LoginViewModel.LoginEvent.ResetPassword -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }

                    is LoginViewModel.LoginEvent.InsertEmail -> {
                        Snackbar.make(view, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        button_login_login_activity.setOnClickListener {
            login()
        }

        text_register_login_activity.setOnClickListener {
            navigateToRegisterActivity()
        }

        text_enter_as_guest_login_activity.setOnClickListener {
            enterAsAGuest()
        }

        button_go_back_login_activity.setOnClickListener {
            navigateToWelcomeActivity()
        }

        text_reset_password.setOnClickListener {
            sendResetPasswordEmail()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun login() {
        val email = edit_email_login_activity.text.toString()
        val password = edit_password_login_activity.text.toString()
        viewModel.login(email, password)
    }

    private fun navigateToRegisterActivity() {
        viewModel.navigateToRegisterActivity()
    }

    private fun enterAsAGuest() {
        viewModel.enterAsAGuest()
    }

    private fun navigateToWelcomeActivity() {
        viewModel.navigateToWelcomeActivity()
    }

    private fun navigateToMoviesFragment() {
        viewModel.navigateToMoviesFragment()
    }

    private fun sendResetPasswordEmail() {
        val email = edit_email_login_activity.text.toString()
        viewModel.sendResetPasswordEmail(email)
    }
}