package com.example.smartcook.home.ml;

import com.example.smartcook.classes.Reciepe;

public class ReciepeResponse {
    int code;
    Reciepe reciepe;

    public int getCode() {
        return code;
    }

    public Reciepe getReciepe() {
        return reciepe;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setReciepe(Reciepe reciepe) {
        this.reciepe = reciepe;
    }
}
