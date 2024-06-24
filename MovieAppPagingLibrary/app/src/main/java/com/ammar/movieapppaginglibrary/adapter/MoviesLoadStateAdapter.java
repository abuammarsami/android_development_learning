package com.ammar.movieapppaginglibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.movieapppaginglibrary.R;
import com.ammar.movieapppaginglibrary.databinding.LoadStateItemBinding;

// LoadStateAdapter is a special adapter provided by the Paging 3 library to handle the loading state of the PagingData.
// It is used to show the loading state of the PagingData in the RecyclerView.
// It is a RecyclerView.Adapter that listens to the LoadState of the PagingData and displays the loading state in the RecyclerView.
// 

public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder> {

    private final View.OnClickListener mRetryCallback;

    public MoviesLoadStateAdapter(View.OnClickListener retryCallback) {
        mRetryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(parent, mRetryCallback);
    }

    public class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private Button mRetryButton;
        private TextView mErrorMessage;

        public LoadStateViewHolder(@NonNull ViewGroup parent,
                                   @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.load_state_item, parent, false));

            // In this context, itemView is the view for each individual item in the RecyclerView.
            // By calling LoadStateItemBinding.bind(itemView), the code is creating a binding object that can
            // be used to more easily interact with the views defined in the load_state_item.xml layout file.
            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            mProgressBar = binding.progressBar;
            mRetryButton = binding.retryButton;
            mErrorMessage = binding.errorMsg;
            mRetryButton.setOnClickListener(retryCallback);

        }

        /**
         * This method is used to bind the LoadState to the ViewHolder.
         * It updates the UI based on the current loading state.
         *
         * @param loadState The current loading state of the PagingData.
         */
        public void bind(LoadState loadState) {
            // Check if the current state is an error state.
            if (loadState instanceof LoadState.Error) {
                // Cast the loadState to LoadState.Error to access the error details.
                LoadState.Error loadStateError = (LoadState.Error) loadState;

                // Set the error message text view with the localized error message.
                // getLocalizedMessage() is designed to provide locale-specific error messages,
                // i.e., error messages in the language of the user. If not overridden,
                // it behaves exactly like getMessage().
                mErrorMessage.setText(loadStateError.getError().getLocalizedMessage());
            }

            // Set the visibility of the progress bar based on whether the current state is a loading state.
            mProgressBar.setVisibility(
                    loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);

            // Set the visibility of the retry button based on whether the current state is an error state.
            mRetryButton.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);

            // Set the visibility of the error message text view based on whether the current state is an error state.
            mErrorMessage.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }

    }

}
