package com.example.querygenie.domain.query;

import android.os.Handler;

import java.net.URL;

public class HTTPRequest implements Runnable {
    static final String APIREQUEST = "http://94.126.205.209:8000/generate";
    URL url;
    Handler handler;

    public HTTPRequest(Handler h) {
    }

    @Override
    public void run() {

    }
}
