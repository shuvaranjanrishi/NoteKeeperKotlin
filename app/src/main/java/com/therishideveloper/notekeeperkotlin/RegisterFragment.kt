package com.therishideveloper.notekeeperkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.therishideveloper.notekeeperkotlin.databinding.FragmentRegisterBinding
import com.therishideveloper.notekeeperkotlin.model.UserRequest
import com.therishideveloper.notekeeperkotlin.session.TokenManager
import com.therishideveloper.notekeeperkotlin.utils.NetworkResult
import com.therishideveloper.notekeeperkotlin.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            var validationResult = validateUserInput()
            if(validationResult.first){
                authViewModel.signUpUser(getUserRequest())
            }else{
                binding.errorTv.text = validationResult.second
            }
        }

        binding.loginNowTv.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        bindObservers()

    }

    private fun getUserRequest(): UserRequest {
        var email = binding.emailEt.text.toString().trim()
        var password = binding.passwordEt.text.toString().trim()
        return UserRequest(email, password)
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        return authViewModel.validateCredentials(getUserRequest())
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken("token")
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.errorTv.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}