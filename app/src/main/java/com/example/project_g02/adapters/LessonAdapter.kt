package com.example.project_g02.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g02.databinding.LessonLayoutBinding
import com.example.project_g02.models.Lesson

class LessonAdapter(
    private val lessons: List<Lesson>,
    private val complete: BooleanArray,
    private val onItemClick: (Int) -> Unit //Click listener lambda used to notify host activity about clicks
) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LessonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //When the ViewHolder is first initialized, set a click listener on each item's root view
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position) //Send back clicked position to host
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LessonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("Lessons Adapter Pos: $position", "Called")
        val lesson: Lesson = this.lessons[position]

        holder.binding.lessonNum.text = "${position + 1}"

        holder.binding.lessonTitle.text = lesson.name
        holder.binding.lessonDuration.text = lesson.getTime()

        if (complete[position]) {
            holder.binding.lessonCheck.visibility = android.view.View.VISIBLE
        }

    }
}