package com.listing.movie.auth.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.listing.movie.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }
        }
        return binding.root
    }

    private fun register() {
        isValid {
            if (it) {
                with(binding) {
                    btnRegister.isVisible = false
                    pbLoading.isVisible = true
                    val username = etUsername.text.toString()
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    viewModel.register(email, username, password)
                    reset()
                    Toast.makeText(requireContext(),"Registrasi berhasil",Toast.LENGTH_LONG).show()
                    btnRegister.isVisible = true
                    pbLoading.isVisible = false
                }
            }
        }
    }

    private fun reset() {
        with(binding){
            etUsername.text = null
            etEmail.text = null
            etPassword.text = null
            etPasswordConfirmation.text = null
        }
    }

    private fun isValid(onValidationComplete: (Boolean) -> Unit) {
        var valid = true

        with(binding) {
            // Synchronous validation
            if (etUsername.text.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            } else {
                tilUsername.isErrorEnabled = false
            }

            if (etEmail.text.isEmpty()) {
                tilEmail.error = "Email tidak boleh kosong"
                valid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()) {
                tilEmail.error = "Email tidak valid"
                valid = false
            } else {
                tilEmail.isErrorEnabled = false
            }

            if (etPassword.text.isEmpty()) {
                tilPassword.error = "Password tidak boleh kosong"
                valid = false
            } else {
                tilPassword.isErrorEnabled = false
            }

            if (etPasswordConfirmation.text.toString() != etPassword.text.toString()) {
                tilPasswordConfirmation.error = "Konfirmasi password tidak sama"
                valid = false
            } else {
                tilPasswordConfirmation.isErrorEnabled = false
            }

            if (!valid) {
                // If any synchronous validation fails, return immediately
                onValidationComplete(false)
                return
            }

            // Asynchronous validation
            val email = etEmail.text.toString()
            viewModel.isUserRegistered(email)
            viewModel.isRegistered.observe(viewLifecycleOwner) { isRegistered ->
                if (isRegistered) {
                    tilEmail.error = "Email telah terpakai"
                    onValidationComplete(false)
                } else {
                    tilEmail.isErrorEnabled = false
                    onValidationComplete(true)
                }
            }
        }
    }

}