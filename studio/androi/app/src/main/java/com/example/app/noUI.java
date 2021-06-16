package com.example.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class noUI {

    public Context context;

    public noUI(Context context) {
        this.context = context;
    }

    // toast non-ui
    public void toast(final String text) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }
    // capnhat Listview
    public void capnhatListView(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                ((MainActivity)context).capnhatLV();
            }
        });
    }
}
