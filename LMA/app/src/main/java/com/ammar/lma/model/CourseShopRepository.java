package com.ammar.lma.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Best practice is to use a Repository for each Component (Category, Course). Here for time-saving, I have used a single Repository for both.
public class CourseShopRepository {

    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;
    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;

    public CourseShopRepository(Application application) {
        CourseDatabase database = CourseDatabase.getInstance(application);
        categoryDAO = database.categoryDAO();
        courseDAO = database.courseDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCourses() {
        return courseDAO.getAllCourses();
    }

    public LiveData<List<Course>> getCoursesByCategory(int categoryId) {
        return courseDAO.getCourseByCategory(categoryId);
    }

    public void insertCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                categoryDAO.insert(category);
            }
        });
    }

    public void insertCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                courseDAO.insert(course);
            }
        });
    }

    public void updateCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                categoryDAO.update(category);
            }
        });
    }

    public void updateCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                courseDAO.update(course);
            }
        });
    }

    public void deleteCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                categoryDAO.delete(category);
            }
        });
    }

    public void deleteCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                courseDAO.delete(course);
            }
        });
    }
}
