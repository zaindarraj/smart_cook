package com.example.smartcook.home.post;

import android.content.Context;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcook.R;
import com.example.smartcook.classes.Reply;
import com.example.smartcook.classes.UserInfo;
import com.example.smartcook.registeration.login.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


public class PostScreen extends Fragment {

    TextView post;

    LoginViewModel loginViewModel;
    Toast toast;

    TextInputEditText reply;
    RecyclerView replies;
    List<Reply> list;

    ImageButton sendReply;

    PostViewModel postViewModel;

    Observer<PostViewModel.ReplySubmitResult> observer;

    MaterialButton signOut;

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<PostScreen.MyRecyclerViewAdapter.ViewHolder> {

        private List<Reply> mData;
        private LayoutInflater mInflater;
        private PostsScreen.ItemClickListener mClickListener;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<Reply> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public PostScreen.MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.reply, parent, false);
            return new PostScreen.MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(PostScreen.MyRecyclerViewAdapter.ViewHolder holder, int position) {
            Reply reply = mData.get(position);
            Log.println(Log.ASSERT, "!", reply.getReply_text());
            holder.reply.setText(reply.getReply_text());
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView reply;

            ViewHolder(View itemView) {
                super(itemView);
                reply = itemView.findViewById(R.id.reply);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        Reply getItem(int id) {
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



    public PostScreen() {
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_post_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        toast = new Toast(getContext());
        reply = view.findViewById(R.id.replyText);
        sendReply = view.findViewById(R.id.sendReply);
        postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
        list= postViewModel.getPost().replies;
        post = view.findViewById(R.id.post);
        replies = view.findViewById(R.id.replies);
        replies.setLayoutManager(new LinearLayoutManager(getActivity()));
        replies.setAdapter(new PostScreen.MyRecyclerViewAdapter(getContext(), list));
        post.setText(postViewModel.getPost().post_text);
        observer = new Observer<PostViewModel.ReplySubmitResult>() {
            @Override
            public void onChanged(PostViewModel.ReplySubmitResult replySubmitResult) {
                        switch (replySubmitResult){
                            case Success:
                                Reply reply1 = new Reply();
                                reply1.setReply_text(reply.getText().toString());
                                list.add(reply1);
                                replies.getAdapter().notifyItemInserted(list.size()-1);
                                break;
                            case Failure:
                                Toast.makeText(getContext(),"Connection Problem" , Toast.LENGTH_SHORT);
                                break;
                        }
            }
        };
        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reply.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Please write reply fist" , Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Replied" , Toast.LENGTH_SHORT).show();

                    postViewModel.reply(UserInfo.getInstance().getUserID(), postViewModel.getPost().post_id,
                            reply.getText().toString());
                    postViewModel.replySubmitResult.observe(getActivity(), observer);
                }
            }
        });


    }
}