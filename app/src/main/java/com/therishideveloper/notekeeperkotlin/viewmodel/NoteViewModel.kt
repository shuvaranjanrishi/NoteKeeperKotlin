package com.therishideveloper.notekeeperkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.therishideveloper.notekeeperkotlin.model.NoteResponse
import com.therishideveloper.notekeeperkotlin.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

@HiltViewModel
class NoteViewModel@Inject constructor(private val noteRepository: NoteRepository) :ViewModel() {

    val noteLiveData get() = noteRepository.noteLiveData
    val statusLiveData get() = noteRepository.statusLiveData

    fun getNotes(){
        viewModelScope.launch {
            noteRepository.getNotes()
        }
    }

    fun createNote(noteResponse: NoteResponse){
        viewModelScope.launch {
            noteRepository.createNote(noteResponse)
        }
    }

    fun updateNote(noteId:String,noteResponse:NoteResponse){
        viewModelScope.launch {
            noteRepository.updateNote(noteId,noteResponse)
        }
    }

    fun deleteNote(noteId: String){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }
}