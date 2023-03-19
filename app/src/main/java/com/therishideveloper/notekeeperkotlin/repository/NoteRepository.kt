package com.therishideveloper.notekeeperkotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.therishideveloper.notekeeperkotlin.api.NoteApi
import com.therishideveloper.notekeeperkotlin.model.NoteResponse
import com.therishideveloper.notekeeperkotlin.model.UserResponse
import com.therishideveloper.notekeeperkotlin.utils.Constants
import com.therishideveloper.notekeeperkotlin.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class NoteRepository @Inject constructor(private val noteApi: NoteApi) {

    private val _noteLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val noteLiveData : LiveData<NetworkResult<List<NoteResponse>>>
    get() = _noteLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>>
    get() = _statusLiveData

    suspend fun getNotes(){
        _noteLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.getNotes()
        if (response.isSuccessful && response.body() != null) {
            Log.d(Constants.TAG, "signUpUser: " + response.body().toString())
            _noteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorBody = JSONObject(response.errorBody()!!.charStream().readText())
            _noteLiveData.postValue(NetworkResult.Error(errorBody.getString("message")))
        } else {
            _noteLiveData.postValue(NetworkResult.Error("Something went wrong..."))
        }
    }

    suspend fun createNote(noteResponse: NoteResponse){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.createNote(noteResponse)
        handleResponse(response,"Note Created")
    }

    suspend fun updateNote(noteId:String,noteResponse: NoteResponse){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.updateNote(noteId,noteResponse)
        handleResponse(response,"Note Deleted")
    }

    suspend fun deleteNote(noteId:String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.deleteNote(noteId)
        handleResponse(response,"Note Deleted")
    }

    private fun handleResponse(response: Response<NoteResponse>, message:String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong..."))
        }
    }

}