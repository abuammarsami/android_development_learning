package com.ammar.customlistview;

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

public class CustomAdapter extends ArrayAdapter<CountryModelClass> {
    private ArrayList<CountryModelClass> countryArrayList;
    Context context;

    public CustomAdapter(ArrayList<CountryModelClass> countryArrayList, Context context) {
        super(context, R.layout.item_list_layout, countryArrayList);
        this.countryArrayList = countryArrayList;
        this.context = context;
    }

    /*
        View Holder: Used to cache references to the views within an item layout,
        simplifying the process of recycling views as you scroll through a list.

        This is a design pattern that is used to cache expensive operations and reduce the number of times
        you have to inflate a view. This is done by storing the inflated layout in a view holder object.
        This object is then attached to the view as a tag via the setTag() method.
        This allows you to reuse the view later on when the list is scrolled, instead of having to inflate
        the layout again.

        Simply ViewHolder means a view holder. It holds the reference to the views in the layout.
     */

    public static class ViewHolder {
        // Declare the views
        ImageView countryFlagImg;
        TextView countryName;
        TextView worldCupWinCount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        CountryModelClass dataModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_layout, parent, false);
            viewHolder.countryFlagImg = convertView.findViewById(R.id.imageView);
            viewHolder.countryName = convertView.findViewById(R.id.countryName);
            viewHolder.worldCupWinCount = convertView.findViewById(R.id.cup_win_desc);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        // Set the data to the views
        viewHolder.countryFlagImg.setImageResource(dataModel.getCountryFlagImage());
        viewHolder.countryName.setText(dataModel.getCountryName());
        viewHolder.worldCupWinCount.setText(dataModel.getWorldCupWinCount());

        // Return the completed view to render on screen
        return convertView;
    }
}
