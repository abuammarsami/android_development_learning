package com.ammar.lma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.ammar.lma.databinding.ActivityAddEditBinding;
import com.ammar.lma.model.Course;

public class AddEditActivity extends AppCompatActivity {

    private Course course;
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String UNIT_PRICE = "unitPrice";

    private ActivityAddEditBinding activityAddEditBinding;
    private AddAndEditActivityClickHandlers addAndEditActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit);

        course = new Course();
        activityAddEditBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_edit);
        activityAddEditBinding.setCourse(course);

        addAndEditActivityClickHandlers = new AddAndEditActivityClickHandlers(this);
        activityAddEditBinding.setClickHandler(addAndEditActivityClickHandlers);

        Intent intent = getIntent();
        if (intent.hasExtra(COURSE_ID)) {
            // This will activate when the user clicks on RecyclerView item
            setTitle("Edit Course");
            course.setCourseName(intent.getStringExtra(COURSE_NAME));
            course.setUnitPrice(intent.getStringExtra(UNIT_PRICE));
        } else {
            // This will activate when the user clicks on the FAB to add a new course
            setTitle("Add New Course");
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public class AddAndEditActivityClickHandlers {
        Context context;

        public AddAndEditActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view){
            if (course.getCourseName() == null) {
                Toast.makeText(context, "Course name cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(COURSE_NAME, course.getCourseName());
                intent.putExtra(UNIT_PRICE, course.getUnitPrice());
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}