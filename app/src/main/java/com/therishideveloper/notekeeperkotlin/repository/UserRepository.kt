package com.therishideveloper.notekeeperkotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.therishideveloper.notekeeperkotlin.api.UserApi
import com.therishideveloper.notekeeperkotlin.model.UserRequest
import com.therishideveloper.notekeeperkotlin.model.UserResponse
import com.therishideveloper.notekeeperkotlin.utils.Constants.TAG
import com.therishideveloper.notekeeperkotlin.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    suspend fun signUpUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.singUp(userRequest)
        handleResponse(response)
    }

    suspend fun signInUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.singIn(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            Log.d(TAG, "signUpUser: " + response.body().toString())
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorBody.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong..."))
        }
    }
}