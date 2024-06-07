package com.ammar.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
    * This class is used to create a custom adapter for the GridView.
    * It extends the ArrayAdapter class.
    * It takes the CourseModel class as a parameter.
    * It is used to bind the data to the GridView.
    * It is used to create the view for each item in the GridView.
    * It is used to set the data to the view for each item in the GridView.
 */
public class MyAdapter extends ArrayAdapter<CourseModel> {

    public MyAdapter(@NonNull Context context, ArrayList<CourseModel> courseModels) {
        super(context, 0, courseModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItemView = inflater.inflate(R.layout.card_item, parent, false);
        }

        CourseModel currentCourse = getItem(position);
        TextView courseTitle = listItemView.findViewById(R.id.gridTextView);
        ImageView courseImage = listItemView.findViewById(R.id.gridImageView);

        courseTitle.setText(currentCourse.getCourseName());
        courseImage.setImageResource(currentCourse.getCourseImage());

        return listItemView;
    }
}
