package com.ricardoteixeira.movietvshowsexplorer.app.presentation.loginregister

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_register_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginRegisterActivity: AppCompatActivity() {

    private val registerViewModel: LoginRegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_register_activity)

        val view = findViewById<View>(R.id.loginActivity)

        val intent = Intent(this, MainActivity::class.java)

        button_login.setOnClickListener {
            login()

            lifecycleScope.launchWhenStarted {
                registerViewModel.loginRegisterEvent.collect { event ->
                    when (event) {
                        is LoginRegisterViewModel.LoginRegisterEvent.NavigateToMoviesScreen -> {
                            startActivity(intent)
                        }

                        is LoginRegisterViewModel.LoginRegisterEvent.InsertPasswordOrEmail -> {
                            Snackbar.make(view, event.message, Snackbar.LENGTH_SHORT).show()
                        }

                        is LoginRegisterViewModel.LoginRegisterEvent.ErrorLoggingIn -> {
                            Snackbar.make(view, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }

        button_register.setOnClickListener {
            register()
            lifecycleScope.launchWhenStarted {
                registerViewModel.loginRegisterEvent.collect { event ->
                    when (event) {
                        is LoginRegisterViewModel.LoginRegisterEvent.NavigateToMoviesScreen -> {
                            startActivity(intent)
                        }

                        is LoginRegisterViewModel.LoginRegisterEvent.InsertPasswordOrEmail -> {
                            Snackbar.make(view, event.message, Snackbar.LENGTH_SHORT).show()
                        }

                        is LoginRegisterViewModel.LoginRegisterEvent.ErrorRegistering -> {
                            Snackbar.make(view, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun login() {
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()
        registerViewModel.login(email, password)
    }

    private fun register() {
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()
        val name = edit_username.text.toString()
        registerViewModel.registerUser(email, password, name)
    }

}