package com.example.smartcook.home.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.admin.DeletePostResponse;
import com.example.smartcook.api.APITools;
import com.example.smartcook.classes.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsViewModel extends ViewModel {

   public enum DeleteResult{
        Success,
        Failure
    }
    MutableLiveData<DeleteResult>deleteResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DeleteResult> getDeleteResultMutableLiveData() {
        return deleteResultMutableLiveData;
    }

    MutableLiveData<ArrayList<Post>> postsArray = new MutableLiveData<ArrayList<Post>>();

    public MutableLiveData<ArrayList<Post>> getPostsArray() {
        return postsArray;
    }

    public void deletePost(int postID){
        Call<DeletePostResponse> call = APITools.getInstance().getService().deletePost(postID,"deletePost");
        call.enqueue(new Callback<DeletePostResponse>() {
            @Override
            public void onResponse(Call<DeletePostResponse> call, Response<DeletePostResponse> response) {
                if(response.isSuccessful()){
                    DeletePostResponse body = response.body();
                    if(body.code == 1){
                       deleteResultMutableLiveData.setValue(DeleteResult.Success);
                    }else{
                        deleteResultMutableLiveData.setValue(DeleteResult.Failure);

                    }
                }else{
                    deleteResultMutableLiveData.setValue(DeleteResult.Failure);

                }
            }

            @Override
            public void onFailure(Call<DeletePostResponse> call, Throwable t) {
                deleteResultMutableLiveData.setValue(DeleteResult.Failure);

            }
        });
    }

    public void getPosts(){
        Call<PostsResponse> call = APITools.getInstance().getService().getPosts("getPosts");
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if(response.isSuccessful()){
                    PostsResponse body = response.body();
                    if(body.code == 1){
                        if(!body.posts.isEmpty()){
                            postsArray.setValue(body.posts);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });
    }

}
