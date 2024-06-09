package com.ammar.lma.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Course.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    private static CourseDatabase instance;

    public abstract CategoryDAO categoryDAO();
    public abstract CourseDAO courseDAO();

    // Singleton pattern to get the database instance.
    public static synchronized CourseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CourseDatabase.class, "course_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Callback to populate the database
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Inserting data when the database is created
            InitializeData();
        }
    };

    private static void InitializeData() {
        // Inserting data when the database is created
        // This is just for testing purposes. In a real app, you should not insert data like this.
        // You should provide a way for the user to insert data.
        // You can use the repository to insert data.
        // You can also use the RoomDatabase.Callback to insert data when the database is created.

        CourseDAO courseDAO = instance.courseDAO();
        CategoryDAO categoryDAO = instance.categoryDAO();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Category category1 = new Category(1,
                        "Frontend",
                        "Web development Interface");
                Category category2 = new Category(2,
                        "Backend",
                        "Web development Server-side");

                categoryDAO.insert(category1);
                categoryDAO.insert(category2);

                Course course1 = new Course(1,
                        "HTML",
                        "100$",
                        1);
                Course course2 = new Course(2,
                        "CSS",
                        "150$",
                        1);
                Course course3 = new Course(3,
                        "JavaScript",
                        "200$",
                        1);

                Course course4 = new Course(4,
                        "NodeJS",
                        "300$",
                        2);

                Course course5 = new Course(5,
                        "ExpressJS",
                        "400$",
                        2);

                Course course6 = new Course(6,
                        "MongoDB",
                        "500$",
                        2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);
                courseDAO.insert(course4);
                courseDAO.insert(course5);
                courseDAO.insert(course6);
            }
        });


    }


}
