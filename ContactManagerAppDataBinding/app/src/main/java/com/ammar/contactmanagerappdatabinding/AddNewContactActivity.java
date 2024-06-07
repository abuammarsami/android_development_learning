package com.ammar.contactmanagerappdatabinding;

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

import com.ammar.contactmanagerappdatabinding.databinding.ActivityAddNewContactBinding;

public class AddNewContactActivity extends AppCompatActivity {

    private ActivityAddNewContactBinding activityAddNewContactBinding;
    Contact contact;
    private AddNewContactActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contact();
        activityAddNewContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);
        activityAddNewContactBinding.setContact(contact);

        handlers = new AddNewContactActivityClickHandlers(this);
        activityAddNewContactBinding.setClickHandler(handlers);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public class AddNewContactActivityClickHandlers {
        Context context;

        public AddNewContactActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSaveContactButtonClicked(View view){

            if (contact.getName() == null){
                Toast.makeText(AddNewContactActivity.this, "Name field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent();
                i.putExtra("NAME", contact.getName());
                i.putExtra("EMAIL", contact.getEmail());
                setResult(RESULT_OK, i);
                finish();
            }

        }
    }
}