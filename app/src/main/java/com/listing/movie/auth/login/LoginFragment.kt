package com.listing.movie.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.listing.movie.R
import com.listing.movie.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLoginBinding.inflate(inflater,container,false)

        with(binding){
            tvRegister.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                login()
            }
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (it) {
                navigateToHome()
            } else {
                Toast.makeText(requireContext(),"Email atau Password salah",Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it) {
                navigateToHome()
            }
        }


        return binding.root
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun login() {
        with(binding){
            btnLogin.isVisible = false
            pbLoading.isVisible = true

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.login(email, password)

            btnLogin.isVisible = true
            pbLoading.isVisible = false
        }
    }
}