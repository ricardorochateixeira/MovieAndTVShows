package com.ricardoteixeira.movietvshowsexplorer.app.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.login.LoginActivity
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.userprofile.UserProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val loginIntent = Intent(this, LoginActivity::class.java)
        val userProfileIntent = Intent(this, UserProfileActivity::class.java)

        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController: NavController = navHostFragment.navController

        val navInflater: NavInflater = navController.navInflater

        val graph: NavGraph = navInflater.inflate(R.navigation.navigation)

        navController.graph = graph

        bottom_nav.setupWithNavController(navController)

        setupActionBarWithNavController(navController)

        lifecycleScope.launchWhenStarted {
            viewModel.mainEvent.collect { event ->
                when (event) {
                    is MainViewModel.MainEvent.ShowDoYouWantToLogOutDialog -> {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(event.message)
                            .setPositiveButton("Yes") {_, _ -> logOut(loginIntent)}
                            .setNegativeButton("No") {_,_ -> }
                            .show()
                    }

                    is MainViewModel.MainEvent.ShowDoYouWQWantToNavigateToLoginPage -> {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(event.message)
                            .setPositiveButton("Yes") { _, _ -> navigateToLogin(loginIntent)  }
                            .setNegativeButton("No") { _, _ -> }
                            .show()
                    }

                    is MainViewModel.MainEvent.NavigateToUserFragment -> {
                        startActivity(userProfileIntent)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu, menu)

        viewModel.authenticationState.observe(this, { authenticationState ->
            when (authenticationState) {
                MainViewModel.AuthenticationState.AUTHENTICATED -> {
                    menu?.findItem(R.id.logged)?.title = "Log Out"
                }
                MainViewModel.AuthenticationState.UNAUTHENTICATED  -> {
                    menu?.findItem(R.id.logged)?.title = "Log In"
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logged -> {
                if (item.title == "Log Out") {
                    showDoYouWantToLogOutDialog()
                } else {
                    showDoYouWQWantToNavigateToLoginPage()
                }
            }
            R.id.button_account -> navigateToUserActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToUserActivity() {
        viewModel.navigateToUserFragment()
    }

    private fun navigateToLogin (intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun showDoYouWantToLogOutDialog() {
        viewModel.showDoYouWantToLogOutDialog()
    }

    private fun logOut(intent: Intent) {
        viewModel.logOut()
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun showDoYouWQWantToNavigateToLoginPage() {
        viewModel.showDoYouWQWantToNavigateToLoginPage()
    }
}
