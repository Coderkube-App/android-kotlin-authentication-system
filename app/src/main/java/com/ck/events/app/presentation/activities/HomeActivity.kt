package com.ck.events.app.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ck.events.app.databinding.ActivityHomeBinding
import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        observeViewModel()
    }

    private fun setupUI() {
        val user = viewModel.currentUser
        if (user != null) {
            binding.tvUserEmail.text = user.email
            binding.tvProviderInfo.text = "Logged in via: ${user.provider}"
        } else {
            navigateToLogin()
        }
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Log Out") { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.logoutState.collect { result ->
                if (result is AuthResult.Success) {
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}
