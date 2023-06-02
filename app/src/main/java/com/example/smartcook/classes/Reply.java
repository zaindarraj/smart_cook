package com.example.smartcook.classes;

public class Reply {
    int post_id;
    int user_id;
    int reply_id;
    String reply_text;

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public void setReply_text(String reply_text) {
        this.reply_text = reply_text;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getReply_text() {
        return reply_text;
    }
}
