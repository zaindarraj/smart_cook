package com.example.smartcook.home.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.smartcook.MainActivity;
import com.example.smartcook.R;
import com.example.smartcook.classes.Post;
import com.example.smartcook.classes.UserInfo;
import com.example.smartcook.registeration.login.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class PostsScreen extends Fragment {

    RecyclerView recyclerView;

    TextInputEditText postText;

    MaterialButton submitPost;


    Observer<ArrayList<Post>> observer;
    LoginViewModel loginViewModel;

    Observer<PostViewModel.PostSubmitResult> postSubmitResultObserver;


    PostsViewModel postsViewModel;

    PostViewModel postViewModel;

    ArrayList<Post> posts;

    MaterialButton signOut;

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<PostsScreen.MyRecyclerViewAdapter.ViewHolder> {

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
        public PostsScreen.MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.post, parent, false);
            return new PostsScreen.MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(PostsScreen.MyRecyclerViewAdapter.ViewHolder holder, int position) {
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




    public PostsScreen() {
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
        return inflater.inflate(R.layout.fragment_posts_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        loginViewModel.context = getContext();
        signOut = view.findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.logOut();

            }
        });
        submitPost = getView().findViewById(R.id.submit_post);
        postText = view.findViewById(R.id.post_text);
        postViewModel  = new ViewModelProvider(getActivity()).get(PostViewModel.class);
        postsViewModel = new ViewModelProvider(getActivity()).get(PostsViewModel.class);
        recyclerView = view.findViewById(R.id.posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        posts = new ArrayList<>() ;
        recyclerView.setAdapter(new PostsScreen.MyRecyclerViewAdapter(getContext(), posts));


        //submitting a post
        submitPost.setOnClickListener(view12 -> {
            if(postText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }else{
                Log.println(Log.ASSERT,"1", String.valueOf(UserInfo.getInstance().getUserID()));
            postViewModel.submitPost(UserInfo.getInstance().getUserID(), postText.getText().toString());
            postSubmitResultObserver = postSubmitResult -> {
                switch(postSubmitResult){

                    case Success:

                        Toast.makeText(getContext(),"Added Post" , Toast.LENGTH_SHORT);

                        postsViewModel.getPosts();
                        break;
                    case Failure:

                        Toast.makeText(getContext(),"Connection Problem" , Toast.LENGTH_SHORT);

                        break;
                }
            };
                postViewModel.postSubmitResult.observe(getActivity(),postSubmitResultObserver);
            }
        });
        postsViewModel.getPosts();
        observer = list -> {
                    if(!list.isEmpty()){
                        posts = list;
                        recyclerView.setAdapter(new PostsScreen.MyRecyclerViewAdapter(getContext(), posts));
                        ((MyRecyclerViewAdapter) recyclerView.getAdapter()).setClickListener((view1, position) -> {
                            postViewModel.setPost(((MyRecyclerViewAdapter) recyclerView.getAdapter()).getItem(position));
                            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_postScreen);
                        });
                    }
        };

        postsViewModel.postsArray.observe(getActivity(), observer);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        postsViewModel.postsArray.removeObserver(observer);
    }
}