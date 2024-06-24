package com.ammar.movieapppaginglibrary.util;

import androidx.recyclerview.widget.DiffUtil;

import com.ammar.movieapppaginglibrary.model.Movie;

public class MovieComparator extends DiffUtil.ItemCallback<Movie> {

        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
//            return oldItem.equals(newItem);
            return oldItem.getId() == newItem.getId();
        }
}
