package com.therishideveloper.notekeeperkotlin.viewmodel

import android.text.TextUtils
import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.therishideveloper.notekeeperkotlin.model.UserRequest
import com.therishideveloper.notekeeperkotlin.model.UserResponse
import com.therishideveloper.notekeeperkotlin.repository.UserRepository
import com.therishideveloper.notekeeperkotlin.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun signUpUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.signUpUser(userRequest)
        }
    }

    fun signInUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.signInUser(userRequest)
        }
    }

    fun validateCredentials(userRequest: UserRequest): Pair<Boolean, String> {
        var result = Pair(true, "")

        if (TextUtils.isEmpty(userRequest.email) || TextUtils.isEmpty(userRequest.password)) {
            result = Pair(false, "Please provide credentials...")
        } else if (!EMAIL_ADDRESS.matcher(userRequest.email).matches()) {
            result = Pair(false, "Please provide valid email...")
        } else if (userRequest.password.length < 6) {
            result = Pair(false, "Password should be at least 6 character...")
        }
        return result
    }
}