package com.example.smartcook.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcook.R;
import com.example.smartcook.classes.Post;
import com.example.smartcook.home.post.PostViewModel;
import com.example.smartcook.home.post.PostsScreen;
import com.example.smartcook.home.post.PostsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;


public class ManagePostsScreen extends Fragment {

    RecyclerView recyclerView;
    Observer<ArrayList<Post>> observer;

    Observer<PostsViewModel.DeleteResult> deletePostResponseObserver;


    PostsViewModel postsViewModel;


    ArrayList<Post> posts;
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<ManagePostsScreen.MyRecyclerViewAdapter.ViewHolder> {

        private List<Post> mData;
        private LayoutInflater mInflater;
        private PostsScreen.ItemClickListener mClickListener;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<Post> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ManagePostsScreen.MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.post, parent, false);
            return new ManagePostsScreen.MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ManagePostsScreen.MyRecyclerViewAdapter.ViewHolder holder, int position) {
            Post post = mData.get(position);
            Log.println(Log.ASSERT , "1", post.post_text);
            holder.myTextView.setText(post.post_text);
            holder.numOfReplies.setText(String.valueOf(post.replies.size()));
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;
            TextView numOfReplies;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.post);
                numOfReplies = itemView.findViewById(R.id.numOfReplies);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        Post getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught
        public void setClickListener(PostsScreen.ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events

    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public ManagePostsScreen() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_posts_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deletePostResponseObserver = new Observer<PostsViewModel.DeleteResult>() {
            @Override
            public void onChanged(PostsViewModel.DeleteResult deleteResult) {
                switch (deleteResult){
                    case Success:
                        postsViewModel.getPosts();
                        break;
                    case Failure:
                        Toast toast = new Toast(getContext());
                        toast.setText("Connection Problem");
                        toast.show();
                        break;
                }
            }
        };
        postsViewModel = new ViewModelProvider(getActivity()).get(PostsViewModel.class);
        postsViewModel.getDeleteResultMutableLiveData().observe(getActivity(), deletePostResponseObserver);
        recyclerView = view.findViewById(R.id.posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        posts = new ArrayList<>() ;
        recyclerView.setAdapter(new ManagePostsScreen.MyRecyclerViewAdapter(getContext(), posts));
        postsViewModel.getPosts();
        observer = list -> {
            if(!list.isEmpty()){
                posts = list;
                recyclerView.setAdapter(new ManagePostsScreen.MyRecyclerViewAdapter(getContext(), posts));
                ((ManagePostsScreen.MyRecyclerViewAdapter) recyclerView.getAdapter()).setClickListener((view1, position) -> {
                    Post post = ((ManagePostsScreen.MyRecyclerViewAdapter) recyclerView.getAdapter()).getItem(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Delete entry");
                    alertDialog.setMessage("Are you sure you want to delete this entry?");
                    alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Continue with delete operation
                        postsViewModel.deletePost(post.post_id);
                    });
                    alertDialog.setNegativeButton(android.R.string.no, null);
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.show();

                });
            }
        };

        postsViewModel.getPostsArray().observe(getActivity(), observer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postsViewModel.getPostsArray().removeObserver(observer);
        postsViewModel.getDeleteResultMutableLiveData().removeObserver(deletePostResponseObserver);
    }
}