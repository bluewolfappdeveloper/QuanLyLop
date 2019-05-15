package tiger.quanlylop.quanlylop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import tiger.quanlylop.quanlylop.APublicLibrary.StringFormat;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterStudent;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class StudentFragment extends AppCompatActivity {
    ListView listview;
    ArrayList<StudentDTO> studentlist;

    AdapterStudent adapterstudent;
    int IDClass;
    String NameClass;
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student);

        AnhXa();

        setSupportActionBar(toolBar);

        listview = findViewById(R.id.lststudentclass);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), EditTakeFeeFragment.class);
                    intent.putExtra("idclass", studentlist.get(position).idclass);
                    intent.putExtra("idstudent", studentlist.get(position).id);
                    intent.putExtra("NameStudent", studentlist.get(position).NameStudent);
                    intent.putExtra("NameClass", NameClass);
                    startActivity(intent);
            }
        });

        Intent intent = this.getIntent();
        IDClass = intent.getIntExtra("idclass", -1);
        NameClass = intent.getStringExtra("nameclass");


        if (IDClass != -1) {
            //Toast.makeText(getApplicationContext(), Integer.toString(IDClass), Toast.LENGTH_LONG).show();
            getSupportActionBar().setTitle(NameClass);
            LoadStudent(IDClass);
        }
    }

    public void AnhXa() {
        toolBar = (Toolbar) findViewById(R.id.toolBarStudent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String findstring = StringFormat.getInstance().removeAccent(query).toLowerCase();

                if (query.isEmpty() == true)
                {
                    LoadStudent(IDClass);
                    return false;
                }
                else
                {
                    ArrayList<StudentDTO> findstudent = new ArrayList<StudentDTO>();

                    //Toast.makeText(getApplicationContext(), Integer.toString(studentlist.size()), Toast.LENGTH_LONG).show();
                    for (int i=0; i<studentlist.size(); i++ ) {
                        StudentDTO studentDTO = studentlist.get(i);
                        String studentname = StringFormat.getInstance().removeAccent(studentDTO.NameStudent).toLowerCase();
                        //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                         if (studentname.indexOf(findstring) >= 0) findstudent.add(studentDTO);

                    }
                    studentlist = findstudent;
                    adapterstudent = new AdapterStudent(getApplicationContext(), R.layout.layout_custom_student, studentlist);
                    listview.setAdapter(adapterstudent);
                    adapterstudent.notifyDataSetInvalidated();

                    return false;
                    // return false;
                }


            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String findstring = StringFormat.getInstance().removeAccent(newText).toLowerCase();

                if (newText.isEmpty() == true)
                {
                    LoadStudent(IDClass);
                    return false;
                }
                else
                {
                    ArrayList<StudentDTO> findstudent = new ArrayList<StudentDTO>();

                    //Toast.makeText(getApplicationContext(), Integer.toString(studentlist.size()), Toast.LENGTH_LONG).show();
                    for (int i=0; i<studentlist.size(); i++ ) {
                        StudentDTO studentDTO = studentlist.get(i);
                        String studentname = StringFormat.getInstance().removeAccent(studentDTO.NameStudent).toLowerCase();
                        //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                        if (studentname.indexOf(findstring) >= 0) findstudent.add(studentDTO);

                    }

                    studentlist = findstudent;

                    adapterstudent = new AdapterStudent(getApplicationContext(), R.layout.layout_custom_student, studentlist);
                    listview.setAdapter(adapterstudent);
                    adapterstudent.notifyDataSetInvalidated();
                    return false;
                }

            }


        });

        return super.onCreateOptionsMenu(menu);
    }

    public void LoadStudent(int id) {



        StudentDAO.getInstance().PostStudent(getApplicationContext(), id, new StudentDAO.StudentCallback() {
            @Override
            public void onSuccess(ArrayList<StudentDTO> studentDTOArrayList) {

                studentlist = new ArrayList<StudentDTO>();
                studentlist = studentDTOArrayList;

                if (studentlist != null) {
                    adapterstudent = new AdapterStudent(getApplicationContext(), R.layout.layout_custom_student, studentlist);
                    listview.setAdapter(adapterstudent);
                    adapterstudent.notifyDataSetInvalidated();
                } else
                    listview.setAdapter(null);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
