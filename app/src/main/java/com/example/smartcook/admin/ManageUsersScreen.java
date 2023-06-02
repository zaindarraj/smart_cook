package com.example.smartcook.admin;

import android.content.Context;
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
import com.example.smartcook.classes.User;
import com.example.smartcook.home.post.PostsScreen;
import com.example.smartcook.home.post.PostsViewModel;

import java.util.ArrayList;
import java.util.List;


public class ManageUsersScreen extends Fragment {

    RecyclerView recyclerView;
    Observer<ArrayList<User>> observer;

    Observer<ManageUsersViewModel.DeleteUserResult> deleteUserResult;


    ManageUsersViewModel usersViewModel;


    ArrayList<User> users;
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<ManageUsersScreen.MyRecyclerViewAdapter.ViewHolder> {

        private List<User> mData;
        private LayoutInflater mInflater;
        private ManageUsersScreen.ItemClickListener mClickListener;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<User> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ManageUsersScreen.MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.user, parent, false);
            return new ManageUsersScreen.MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ManageUsersScreen.MyRecyclerViewAdapter.ViewHolder holder, int position) {
            User user = mData.get(position);
            holder.myTextView.setText(user.getName());
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.userName);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        User getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught
        public void setClickListener(ManageUsersScreen.ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events

    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    public ManageUsersScreen() {
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
        return inflater.inflate(R.layout.fragment_manage_users_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.users);
        usersViewModel = new ViewModelProvider(getActivity()).get(ManageUsersViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        deleteUserResult = (Observer<ManageUsersViewModel.DeleteUserResult>) deleteResult -> {
            switch (deleteResult){
                case Success:
                    usersViewModel.getUsers();
                    break;
                case Failed:
                    Toast toast = new Toast(getContext());
                    toast.setText("Connection Problem");
                    toast.show();
                    break;
            }
        };

        observer = list -> {
            if(!list.isEmpty()){
                Log.println(Log.ASSERT,"1", list.toString());

                users = list;
                recyclerView.setAdapter(new ManageUsersScreen.MyRecyclerViewAdapter(getContext(), users));
                ((ManageUsersScreen.MyRecyclerViewAdapter) recyclerView.getAdapter()).setClickListener((view1, position) -> {
                    User user = ((ManageUsersScreen.MyRecyclerViewAdapter) recyclerView.getAdapter()).getItem(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Delete entry");
                    alertDialog.setMessage("Are you sure you want to delete this entry?");
                    alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Continue with delete operation
                        usersViewModel.deleteUser(user.getUserID());
                    });
                    alertDialog.setNegativeButton(android.R.string.no, null);
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.show();

                });
            }
        };

        usersViewModel.getDeleteResultMutableLiveData().observe(getActivity(),deleteUserResult);
        usersViewModel.getUsersArray().observe(getActivity(),observer);
        usersViewModel.getUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usersViewModel.getDeleteResultMutableLiveData().removeObserver(deleteUserResult);
        usersViewModel.getUsersArray().removeObserver(observer);
    }
}