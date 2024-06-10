package com.ammar.lma;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.ammar.lma.model.Course;

import java.util.ArrayList;

public class CourseDiffCallback extends DiffUtil.Callback{

    ArrayList<Course> oldCourseList;
    ArrayList<Course> newCourseList;

    public CourseDiffCallback(ArrayList<Course> oldCourseList, ArrayList<Course> newCourseList) {
        this.oldCourseList = oldCourseList;
        this.newCourseList = newCourseList;
    }

    @Override
    public int getOldListSize() {
        return oldCourseList == null ? 0 : oldCourseList.size();
    }

    @Override
    public int getNewListSize() {
        return newCourseList == null ? 0 : newCourseList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // here will be triggered by delete and insert
        return oldCourseList.get(oldItemPosition).getCourseId() ==
                newCourseList.get(newItemPosition).getCourseId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // here will be triggered by update
        // By default, it compares the position of the memory in the memory of the two objects.
        // So we if we check an older one and the newly created one, as are two objects,
        // even their content are the same. It will tell you they are not equal.
        // That is not the behavior that we need to our recycler view.
        // We want this method to return false only if there are changes in the content.
        // They are not comparing the content of this item, so we have to override the equals method
        // of the course class.
        return oldCourseList.get(oldItemPosition).equals(newCourseList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
