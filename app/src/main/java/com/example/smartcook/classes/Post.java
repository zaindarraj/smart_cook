package com.example.smartcook.classes;

import java.lang.reflect.Array;
import java.util.List;

public class Post {
    public int post_id;

    public int user_id;
    public String post_text;
    public List<Reply> replies;
}
