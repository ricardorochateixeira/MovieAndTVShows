package com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.MainActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.login.LoginActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.register.RegisterActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.utils.blink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash_activity.*
import kotlinx.android.synthetic.main.welcome_activity.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.welcome_activity)

        supportActionBar?.hide()

        val intentMoviesFragment = Intent(this, MainActivity::class.java)
        val intentLogin = Intent(this, LoginActivity::class.java)
        val intentRegister = Intent(this, RegisterActivity::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.welcomeEvent.collect { event ->
                when (event) {
                    is WelcomeViewModel.WelcomeEvent.NavigateToLoginActivity -> {
                        startActivity(intentLogin)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is WelcomeViewModel.WelcomeEvent.NavigateToRegisterActivity -> {
                        startActivity(intentRegister)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is WelcomeViewModel.WelcomeEvent.EnterAsAGuest -> {
                        startActivity(intentMoviesFragment)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is WelcomeViewModel.WelcomeEvent.NavigateToMoviesFragmentWithLogin -> {
                        startActivity(intentMoviesFragment)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            }
        }


        button_login_welcome_activity.setOnClickListener {
            login()
        }

        button_register_welcome_activity.setOnClickListener {
            register()
        }

        text_enter_as_guest.setOnClickListener {
            enterAsAGuest()
        }
    }

    private fun login() {
        viewModel.navigateToLoginActivity()
    }

    private fun register() {
        viewModel.navigateToRegisterActivity()
    }

    private fun enterAsAGuest() {
        viewModel.enterASAGuest()
    }

}