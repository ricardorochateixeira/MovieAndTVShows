package com.ricardoteixeira.movietvshowsexplorer.app.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.main.MainActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.utils.blink
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_activity)

        supportActionBar?.hide()

        please_wait.blink()

        checkIfUserIsLogged()

        val intentMoviesFragment = Intent(this, MainActivity::class.java)
        val intentWelcome = Intent(this, WelcomeActivity::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.splashEvent.collect { event ->
                when (event) {
                    is SplashViewModel.SplashEvent.NavigateToMoviesFragmentWithLogin -> {
                        startActivity(intentMoviesFragment)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }

                    is SplashViewModel.SplashEvent.NavigateToWelcomeActivity -> {
                        startActivity(intentWelcome)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            }
        }
    }

    private fun checkIfUserIsLogged() {
        val logged = viewModel.checkIfLoggedIn()

        if (logged) {
            navigateToMoviesFragmentWithLogin()
        } else {
            navigateToWelcomeActivity()
        }
    }

    private fun navigateToMoviesFragmentWithLogin() {
        viewModel.navigateToMoviesFragmentWithLogin()
    }

    private fun navigateToWelcomeActivity() {
        viewModel.navigateToWelcomeActivity()
    }
}