package com.ammar.volumecalculatorapp;

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

public class MyCustomAdapter extends ArrayAdapter<Shape> {
    private ArrayList<Shape> shapeArrayList;
    Context context;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     */
    public MyCustomAdapter(ArrayList<Shape> shapeArrayList, Context context) {
        super(context, R.layout.grid_item_layout, shapeArrayList);
        this.shapeArrayList = shapeArrayList;
        this.context = context;
    }

    // View Holder: Used to cache references to the views within an item layout
    private static class MyViewHolder{
        ImageView shapeImage;
        TextView shapeName;

    }

    // GetView(): Used to create and return a view for a specific item in Grid.

    /*
    This method will check if a recycledView view that went off the screen is available.

    If it is not, you inflate a new layout and create a Viewholder instance.

    If a recycled view is available, you simply update the data in the existing view holder and reuse it.

    So this method is responsible for creating and returning a view that represents an item within
    the adapters data set.

    This method is called by the framework whenever a new item needs to be displayed on the screen, such

    as when you are scrolling through a list, view or grid view or similar list based UI component.
     */

    /**
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1- Get the Shape object for current position
        Shape shapes = getItem(position);

        // 2- Inflating Layout:
        MyViewHolder viewHolder;

        if (convertView == null) {
            // no view went off-screen --> create a new view
            viewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(
                    R.layout.grid_item_layout,
                    parent,
                    false);

            // Then need to find the views and manipulate the view within the inflated layout
            viewHolder.shapeImage = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.shapeName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);

        }else{
            // a view went off-screen --> reuse it
            viewHolder = (MyViewHolder) convertView.getTag();

        }

        // last step getting the data from the model class (Shape)
        viewHolder.shapeImage.setImageResource(shapes.getShapeImage());
        viewHolder.shapeName.setText(shapes.getShapeName());

        return convertView;

    }
}
