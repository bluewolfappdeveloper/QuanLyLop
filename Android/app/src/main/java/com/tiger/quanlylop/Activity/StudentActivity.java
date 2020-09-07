package com.tiger.quanlylop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tiger.quanlylop.Adapter.StudentApdapter;
import com.tiger.quanlylop.DAO.StudentDAO;
import com.tiger.quanlylop.DTO.StudentDTO;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    List<StudentDTO> listStudent;
    RecyclerView listViewStudentMain;
    StudentApdapter studentApdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        listViewStudentMain = findViewById(R.id.listViewStudentMain);
        Toolbar toolbar = findViewById(R.id.toolbarStudent);

        Intent intent = getIntent();
        long IDClass =  intent.getLongExtra("IDCLASS", 0);
        String nameClass = intent.getStringExtra("NAMECLASS");

        setSupportActionBar(toolbar);
        toolbar.setTitle(nameClass);

        setUpRecycleView(IDClass,nameClass);
    }

    private void setUpRecycleView(final long IDClass, final String nameClass){
        listStudent = new ArrayList<StudentDTO>();
        listStudent = StudentDAO.getInstance().getAllStudent(StudentActivity.this, IDClass);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StudentActivity.this, LinearLayoutManager.VERTICAL,false);
        listViewStudentMain.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(StudentActivity.this, linearLayoutManager.getOrientation());
        listViewStudentMain.addItemDecoration(itemDecoration);

        studentApdapter = new StudentApdapter(StudentActivity.this, listStudent, new StudentApdapter.onClickListener() {
            @Override
            public void onClick(int pos) {
                //Toast.makeText(StudentActivity.this, ""+pos, Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(StudentActivity.this, FeeActivity.class);
                myIntent.putExtra("IDCLASS", IDClass);
                myIntent.putExtra("IDSTUDENT", listStudent.get(pos).getId());
                myIntent.putExtra("NAMECLASS", nameClass);
                myIntent.putExtra("NAMESTUDENT", listStudent.get(pos).getNameStudent());


                StudentActivity.this.startActivity(myIntent);
            }
        });
        listViewStudentMain.setAdapter(studentApdapter);

    }
}