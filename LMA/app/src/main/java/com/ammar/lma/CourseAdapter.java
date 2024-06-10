package com.ammar.lma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.lma.databinding.CourseListItemBinding;
import com.ammar.lma.model.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    private onItemClickListener listener;
    private ArrayList<Course> courses = new ArrayList<>();


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseListItemBinding courseListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.course_list_item,
                parent,
                false);
        return new CourseViewHolder(courseListItemBinding.getRoot(), courseListItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.courseListItemBinding.setCourse(currentCourse);

    }


    @Override
    public int getItemCount() {
        return null != courses ? courses.size() : 0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
       private CourseListItemBinding courseListItemBinding;

        public CourseViewHolder(@NonNull View itemView, CourseListItemBinding courseListItemBinding) {
            super(itemView);
            this.courseListItemBinding = courseListItemBinding;

            courseListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setCourses(ArrayList<Course> newCourses) {
//        this.courses = courses;
//        notifyDataSetChanged(); // this has a performance cost so we will use DiffUtil later

        // courses variable is old courses here
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CourseDiffCallback(courses, newCourses), false);
        courses = newCourses;
        result.dispatchUpdatesTo(CourseAdapter.this);

    }
}
