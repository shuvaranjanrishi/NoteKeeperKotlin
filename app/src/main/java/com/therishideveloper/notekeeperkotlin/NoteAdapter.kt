package com.therishideveloper.notekeeperkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.therishideveloper.notekeeperkotlin.databinding.ItemNoteBinding
import com.therishideveloper.notekeeperkotlin.model.NoteResponse

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class NoteAdapter(private val onNoteClicked:(NoteResponse)->Unit) : ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteResponse: NoteResponse) {
            binding.titleTv.text = noteResponse.title
            binding.descriptionTv.text = noteResponse.body
            binding.root.setOnClickListener{
                onNoteClicked(noteResponse)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }
    }
}