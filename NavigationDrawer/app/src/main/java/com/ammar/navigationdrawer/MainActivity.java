package com.ammar.navigationdrawer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // Step 1: Declare DrawerLayout
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Step 2: setting up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // now display up an app icon, like displaying three dashes
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Step 3: Find our drawer view
        mDrawer = findViewById(R.id.myDrawerLayout);

        // Drawer navigation
        nvDrawer = findViewById(R.id.navigationView);
        setUpDrawerContent(nvDrawer);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myDrawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /* Now, since we have used the menu and we have learned how to create and deal with menus
        in the menu section, we need to override that and a method called onOptionsItemSelected,
        which is a method that is called when the user selects an item from the menu.
        So, we need to override that method and we need to call the toggle method of the drawer layout
        object, which is the object that we have created here. So, we need to call the toggle method
        of this object, and this will toggle the drawer. So, let's do that.
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void setUpDrawerContent(NavigationView nvDrawer){
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;

        Class fragmentClass;
        if (item.getItemId() == R.id.nav_first_fragment) {
            fragmentClass = FirstFragment.class;
        } else if (item.getItemId() == R.id.nav_second_fragment) {
            fragmentClass = SecondFragment.class;
        } else if (item.getItemId() == R.id.nav_third_fragment) {
            fragmentClass = ThirdFragment.class;
        } else {
            fragmentClass = FirstFragment.class;
        }

        // Now we need to make to start creating the new instance of the fragments.
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        // Now we need to replace the fragment that is displayed in the content view.
        // So, we need to get the fragment manager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}