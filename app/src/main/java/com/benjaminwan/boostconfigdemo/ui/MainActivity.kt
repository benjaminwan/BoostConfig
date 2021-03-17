package com.benjaminwan.boostconfigdemo.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.benjaminwan.boostconfigdemo.R
import com.benjaminwan.boostconfigdemo.databinding.ActivityMainBinding
import com.benjaminwan.boostconfigdemo.utils.viewBinding
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()

    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.labelVisibilityMode =
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (isRootFragment(destination.id)) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.toolbar.navigationIcon = null
            } else {
                binding.bottomNavigationView.visibility = View.GONE
                binding.toolbar.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }

    private fun isRootFragment(@IdRes id: Int): Boolean =
        when (id) {
            R.id.page1Fragment,
            R.id.page2Fragment,
            R.id.page3Fragment,
            R.id.page4Fragment -> {
                true
            }
            else -> {
                false
            }
        }
}