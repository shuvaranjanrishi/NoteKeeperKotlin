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
import com.google.gson.Gson
import com.therishideveloper.notekeeperkotlin.databinding.FragmentNoteBinding
import com.therishideveloper.notekeeperkotlin.model.NoteResponse
import com.therishideveloper.notekeeperkotlin.utils.NetworkResult
import com.therishideveloper.notekeeperkotlin.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var note: NoteResponse? = null
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindHandlers() {
        binding.deleteBtn.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it.id.toString())
            }
        }
        binding.submitBtn.setOnClickListener {
            val title = binding.titleEt.text.toString().trim()
            val description = binding.descriptionEt.text.toString().trim()
            val noteResponse = NoteResponse(1, 1, title, description)
            if (note == null) {
                noteViewModel.createNote(noteResponse)
            } else {
                noteViewModel.updateNote("" + note!!.id, noteResponse)
            }
        }
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success->{
                    findNavController().popBackStack()
                }
                is NetworkResult.Error->{}
                is NetworkResult.Loading->{
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("Note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.titleEt.setText(it.title)
                binding.descriptionEt.setText(it.body)
            }
        } else {
            binding.noteTv.text = "Add Note"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}