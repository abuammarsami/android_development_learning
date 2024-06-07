package com.ammar.planetsapp;

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

public class MyCustomAdapter extends ArrayAdapter<Planet> {

    // Using Custom Layouts --> MyCustomAdapter
    // Using Custom Object/Model --> extends ArrayAdapter<Planet>
    private ArrayList<Planet> planetsArrayList;
    // we are using array list as its dynamically grow and shrink as it needed for our planet model class
    Context context;

    //Constructor
    public MyCustomAdapter(Context context, ArrayList<Planet> planetsArrayList) {
        super(context, R.layout.item_list_layout, planetsArrayList);
        this.planetsArrayList = planetsArrayList;
        this.context = context;
    }

    // Viw Holder Class: used to cache references to the views within
    //                   an item layout, so that they don't need to be
    //                   repeatedly looked up during scrolling

    private static class MyViewHolder {

        // our views will be referenced here
        TextView planetName;
        TextView moonCount;
        ImageView planetImage;
    }

    // getView(): USed to create and return a view for a specific item in the list


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1- Get the data item for this position from the list of planets (ArrayList)
        // Or we can say Get the planet object for the current position

        Planet planet = getItem(position); // getItem() is a method of ArrayAdapter class that returns the item at the specified position in the list

        // 2- Inflate Layout- Check if an existing view is being reused, otherwise inflate the view
        MyViewHolder myViewHolder; // view lookup cache stored in tag
        final View result; // View for the row item

        //Convert View is used to reuse the old view
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            // Inflate the custom layout, inflate means to render or show the layout
            /***
             * The inflate() method takes three arguments:
             * 1- The resource ID of the layout you want to inflate
             * 2- The view group to be the parent of the inflated layout
             * 3- A boolean that tells the system whether to attach the inflated layout to the view group
             *    (passing false means you have to add it yourself)
             *    Indicates whether the inflated view should be attached to the parent immediately.
             *    Passing false means that the view will not be attached to the parent right away, which is usually the
             *    desired behavior when you are providing the view to an adapter.
             *    Since we are using a custom adapter and passing it the view to handle it, we use false.
             */
            convertView = inflater.inflate(
                    R.layout.item_list_layout,
                    parent,
                    false);

           // Finding Views
            myViewHolder.planetName = (TextView) convertView.findViewById(R.id.planetName);
            myViewHolder.moonCount = (TextView) convertView.findViewById(R.id.planetDescription);
            myViewHolder.planetImage = (ImageView) convertView.findViewById(R.id.imageView);

            result = convertView;

            /***
             * The set tag method is used to attach an arbitrary object to the view object method.
             * By doing this, you are associating the Viewholder with the specific view that it
             * represents and the primary reason for attaching Viewholder to the view using set tag
             * method is to ensure that you later can retrieve it from the view When you need to
             * update the View's content.
             */

            convertView.setTag(myViewHolder);
        } else {
            // The view is recycled

            /**
             * When the view is recycled, you can retrieve the associated Viewholder using get tag
             * method and use it to efficiently populate the view with the data for the current
             * starting by the my Viewholder equal viewholder convert the view dot get tag result
             * equal to convert view so the get tag method is used to retrieve an object that was
             * previously set as a tag using the set tag method on the view object.
             *
             */
            myViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }
        // getting the data from model class(Planet)
        myViewHolder.planetName.setText(planet.getPlanetName());
        myViewHolder.moonCount.setText(planet.getMoonCount());
        myViewHolder.planetImage.setImageResource(planet.getPlanetImage());

        return result;
    }
}
