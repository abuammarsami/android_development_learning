package com.ammar.gridview;

public class CourseModel {
    private String courseName;
    private int courseImage;

    public CourseModel(String courseName, int courseImage) {
        this.courseName = courseName;
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseImage() {
        return courseImage;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseImage(int courseImage) {
        this.courseImage = courseImage;
    }
}
