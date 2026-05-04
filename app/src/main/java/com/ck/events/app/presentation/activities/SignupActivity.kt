package com.ck.events.app.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ck.events.app.databinding.ActivitySignupBinding
import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.presentation.viewmodels.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnNavigateToLogin.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            viewModel.signup(email, password, confirmPassword)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.authState.collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        binding.tvError.visibility = View.GONE
                    }
                    is AuthResult.Success -> {
                        startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                        finishAffinity()
                    }
                    is AuthResult.Error -> {
                        binding.tvError.text = result.message
                        binding.tvError.visibility = View.VISIBLE
                    }
                    null -> {}
                }
            }
        }
    }
}
