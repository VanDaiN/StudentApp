package com.example.app.DAO;

import android.content.Context;
import android.util.Log;


import com.example.app.Model.Students;
import com.example.app.noUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class StudentDAO {

    Context context;
    String serverUrl = "http://192.168.1.6:3001";
    List<Students> list = new ArrayList<Students>();


    //Khai bao socketx`
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(serverUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //lang nghe insert
    private Emitter.Listener onInsertStudent = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data =  args[0].toString();

            noUI noui = new noUI(context);

            if(data == "true"){
                Log.i("insert","Insert thanh cong");

                noui.toast("Insert thanh cong");


                noui.capnhatListView();






            }else{

                Log.i("insert","Insert that bai");

                noui.toast("Insert that bai");

            }

        }
    };
    //lang nghe getAll
    private Emitter.Listener onGetAllStudent = new Emitter.Listener() {

        @Override
        public void call(Object... args) {



            noUI noui = new noUI(context);
            Students sv = new Students();

            JSONObject jsonObject = (JSONObject) args[0];
            //parser JSON
            try {
                sv._id = jsonObject.getString("_id");
                sv.id = jsonObject.getString("id");
                sv.name = jsonObject.getString("name");
                sv.email = jsonObject.getString("email");

                list.add(sv);
                Log.i("GetAll","Get thanh cong 1 student");



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    //lang nghe Update
    private Emitter.Listener onUpdateStudent = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data =  args[0].toString();

            noUI noui = new noUI(context);

            if(data == "true"){
                Log.i("update","Update thanh cong");

                noui.toast("Update thanh cong");


                noui.capnhatListView();






            }else{

                Log.i("update","Update that bai");

                noui.toast("Update that bai");

            }

        }
    };

    //lang nghe Delete
    private Emitter.Listener onDeleteStudent = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data =  args[0].toString();

            noUI noui = new noUI(context);

            if(data == "true"){
                Log.i("Delete","Delete thanh cong");

                noui.toast("Delete thanh cong");


                noui.capnhatListView();






            }else{

                Log.i("Delete","Delete that bai");

                noui.toast("Delete that bai");

            }

        }
    };

    public StudentDAO(Context context) {
        this.context = context;
        mSocket.connect();
        mSocket.on("insertStudent", onInsertStudent);
        mSocket.on("getStudent", onGetAllStudent);
        mSocket.on("updateStudent", onUpdateStudent);
        mSocket.on("deleteStudent", onDeleteStudent);
    }



    public void insert(final Students sv) {

        mSocket.emit("insertStudent", sv.id, sv.name, sv.email);


    }

    public List<Students> getAll(){

        list.clear();

        mSocket.emit("getStudent","Client Android get All Student");



        return list;
    }

    public void update(final Students sv) {

        mSocket.emit("updateStudent", sv._id,sv.id, sv.name, sv.email);

    }

    public void delete(final String id) {

        mSocket.emit("deleteStudent", id);

    }
}
