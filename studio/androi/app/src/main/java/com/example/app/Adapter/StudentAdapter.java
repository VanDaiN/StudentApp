package com.example.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app.MainActivity;
import com.example.app.Model.Students;
import com.example.app.R;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter {
    TextView tvId,tvName,tvEmail;
    Button btnXoa;
    Context context;
    ArrayList<Students> students;

    public StudentAdapter(@NonNull Context context, ArrayList<Students> students) {
        super(context, 0,students);
        this.context = context;
        this.students = students;
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;

        if (v == null){
            v= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        }

        final Students sv = students.get(position);
        if (sv !=null) {
            //anh xa
            tvId = (TextView) v.findViewById(R.id.tvId);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvEmail = (TextView) v.findViewById(R.id.tvEmail);
            btnXoa = (Button)v.findViewById(R.id.btnXoa);
            //set data len layout custom

            tvId.setText(sv.id);
            tvName.setText(sv.name);
            tvEmail.setText(sv.email);

        }
        // click vao item

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Ban vua click dong "+position, Toast.LENGTH_SHORT).show();
                ((MainActivity)context).txtId.setText(sv.id);
                ((MainActivity)context).txtName.setText(sv.name);
                ((MainActivity)context).txtEmail.setText(sv.email);
                ((MainActivity)context)._id = sv._id;

            }
        });


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Truy cap den bien nao do trong MainActivity ((MainActivity)context)
                ((MainActivity)context).xoaStudent(sv._id);

            }
        });




        return v;
    }
}

