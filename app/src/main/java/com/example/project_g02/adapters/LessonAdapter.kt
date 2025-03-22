package com.example.project_g02.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g02.databinding.LessonLayoutBinding
import com.example.project_g02.models.Lesson
import com.example.project_g02.singletons.Lessons

class LessonAdapter(private val complete: BooleanArray) :
    RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    val lessons = Lessons

    inner class ViewHolder(val binding: LessonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LessonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lessons.lessonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson: Lesson = this.lessons.lessonList[position]
        holder.binding.lessonNum.text = "${position + 1}"

        holder.binding.lessonTitle.text = lesson.name
        holder.binding.lessonDuration.text = lesson.getTime()

        if (complete[position]) {
            holder.binding.lessonCheck.visibility = android.view.View.VISIBLE
        }
    }
}