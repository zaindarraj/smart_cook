package com.example.smartcook.home.post;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.api.APITools;
import com.example.smartcook.classes.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    enum PostSubmitResult{
        Success,
        Failure
    }

    enum ReplySubmitResult{
        Success,
        Failure
    }

    MutableLiveData<PostSubmitResult> postSubmitResult = new MutableLiveData<PostSubmitResult>();
    MutableLiveData<ReplySubmitResult> replySubmitResult = new MutableLiveData<ReplySubmitResult>();

    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    void submitPost(
            int userID,
            String text
    ){
        Call<PostResponse> call = APITools.getInstance().getService().addPost(
                userID, text, "addPost"
        );

        Log.println(Log.ASSERT, "dfd", String.valueOf(userID));
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()) {
                    PostResponse body = response.body();
                    if (body.code == 1) {
                        postSubmitResult.setValue(PostSubmitResult.Success);

                    } else {
                        postSubmitResult.setValue(PostSubmitResult.Failure);
                    }
                }}

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                postSubmitResult.setValue(PostSubmitResult.Failure);
                Log.println(Log.ASSERT,"!", t.toString())
;            }
        });
    }


    void reply(
            int userID,
            int postID,
            String text
    ){
        Call<PostResponse> call = APITools.getInstance().getService().reply(
                userID, postID,text, "reply"
        );
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()) {
                    PostResponse body = response.body();
                    if (body.code == 1) {
                        replySubmitResult.setValue(ReplySubmitResult.Success);

                    } else {
                        replySubmitResult.setValue(ReplySubmitResult.Failure);
                    }
                }}

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                replySubmitResult.setValue(ReplySubmitResult.Failure);
                Log.println(Log.ASSERT,"!", t.toString())
                ;            }
        });
    }

}
