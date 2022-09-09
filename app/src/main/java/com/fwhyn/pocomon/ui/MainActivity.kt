package com.fwhyn.pocomon.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.databinding.ActivityMainBinding
import com.fwhyn.pocomon.ui.utils.UiConstant
import org.koin.androidx.viewmodel.ext.android.viewModel

lateinit var launcher: ActivityResultLauncher<Intent>

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val bottomNavigation = viewBinding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // set views
        setTheme(R.style.Theme_Pocomon)
        setContentView(viewBinding.root)
        bottomNavigation.setupWithNavController(navHostFragment.navController)

        // set activity result receiver
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == UiConstant.INFO_ACTIVITY_CODE){
                viewBinding.bottomNavigationView.selectedItemId = R.id.caught_fragment
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.exit) {
            finish()
        } else {
            Toast.makeText(this, getString(R.string.exit_message), Toast.LENGTH_SHORT).show()
            viewModel.setExitTimer()
        }
    }
}