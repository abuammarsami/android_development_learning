package com.ammar.lma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.lma.databinding.ActivityMainBinding;
import com.ammar.lma.model.Category;
import com.ammar.lma.model.Course;
import com.ammar.lma.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoryList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers clickHandlers;
    private Category selectedCategory;

    // RecyclerView
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private ArrayList<Course> coursesList;

    private static final int ADD_COURSE_REQUEST_CODE = 1;
    private static final int EDIT_COURSE_REQUEST_CODE = 2;
    public int selectedCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandler(clickHandlers);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                // Update the UI
                categoryList = (ArrayList<Category>) categories;
                for (Category category : categories) {
                    Log.i("TAG", category.getCategoryName());
                }

                showOnSpinner();
            }
        });

        mainActivityViewModel.getCoursesOfSelectedCategory(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                // Update the UI
                for (Course course : courses) {
                    Log.i("TAG", course.getCourseName());
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this,
                R.layout.spinner_item,
                categoryList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    // Added for RecyclerView
    public void LoadCoursesArrayList(int categoryId) {
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                coursesList = (ArrayList<Course>) courses;
                // Update the UI
                LoadRecyclerView();
            }
        });
    }

    // Added for RecyclerView
    private void LoadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerView;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);
        courseAdapter.setCourses(coursesList);

        // Edit the Course
        courseAdapter.setOnItemClickListener(new CourseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                selectedCourseId = course.getCourseId();
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
                intent.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
                intent.putExtra(AddEditActivity.UNIT_PRICE, course.getUnitPrice());
                startActivityForResult(intent, EDIT_COURSE_REQUEST_CODE);
            }
        });

        // Delete A Course
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Course courseToDelete = coursesList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(courseToDelete);
                Toast.makeText(MainActivity.this, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(courseRecyclerView);
    }

    public class MainActivityClickHandlers{
        public void onFABClicked(View view) {
            // Handle FAB click
            // Create the Course
//            Toast.makeText(MainActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_COURSE_REQUEST_CODE);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
            // Handle item selection
            // getting item at position when user select the spinner data
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is " + selectedCategory.getId() +
                    " \n name is " + selectedCategory.getCategoryName();
            Toast.makeText(parent.getContext(), " "+message, Toast.LENGTH_SHORT).show();

            // Load courses (RecyclerView)
            LoadCoursesArrayList(selectedCategory.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getId();

        if (requestCode == ADD_COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = new Course();
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
            course.setCategoryId(selectedCategoryId);
            mainActivityViewModel.addNewCourse(course);
            Log.v("TAG", "Inserted" + course.getCourseName());
            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = new Course();
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
            course.setCategoryId(selectedCategoryId);
            course.setCourseId(selectedCourseId);
            mainActivityViewModel.updateCourse(course);
            Toast.makeText(this, "Course Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }
    }
}