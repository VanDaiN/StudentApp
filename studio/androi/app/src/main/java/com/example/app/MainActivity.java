package com.example.app;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.app.Adapter.StudentAdapter;
import com.example.app.DAO.StudentDAO;
import com.example.app.Model.Students;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnThem, btnSua;
    public EditText txtId, txtName, txtEmail;
    public String _id;
    ListView lv;
    Students sv;
    StudentDAO svDao;
    ArrayList<Students> students;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.lv1);
        txtId=findViewById(R.id.edtId);
        txtName=findViewById(R.id.edtTen);
        txtEmail=findViewById(R.id.edtEmail);

        btnThem=findViewById(R.id.btnThem);
        btnSua=findViewById(R.id.btnSua);

        svDao = new StudentDAO(this);

        capnhatLV();


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv = new Students();
                sv.id=txtId.getText().toString();
                sv.name=txtName.getText().toString();
                sv.email=txtEmail.getText().toString();

                if(!sv.id.isEmpty() && !sv.name.isEmpty()){
                    //them student
                    svDao.insert(sv);

                    capnhatLV();


                }else{
                    Toast.makeText(MainActivity.this, "Vui long nhap du thong tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv = new Students();
                sv._id = _id;
                sv.id=txtId.getText().toString();
                sv.name=txtName.getText().toString();
                sv.email=txtEmail.getText().toString();

                svDao.update(sv);
            }
        });

    }

    public void xoaStudent(String id){

        svDao.delete(id);



    }

    public void capnhatLV(){

        // getAll Student
        students = (ArrayList<Students>) svDao.getAll();

        // gan adapter

        adapter = new StudentAdapter(this, students);

        lv.setAdapter(adapter);
    }
}