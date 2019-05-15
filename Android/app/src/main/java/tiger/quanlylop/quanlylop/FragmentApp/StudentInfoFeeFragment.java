package tiger.quanlylop.quanlylop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.APublicLibrary.StringFormat;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterStudent;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterStudentHaveFee;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterStudentNoHaveFee;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DAO.StudentHaveFeeDAO;
import tiger.quanlylop.quanlylop.DAO.StudentNoHaveFeeDAO;
import tiger.quanlylop.quanlylop.DTO.StudentHaveFeeDTO;
import tiger.quanlylop.quanlylop.DTO.StudentHaveFeeDTO;
import tiger.quanlylop.quanlylop.DTO.StudentNoHaveFeeDTO;
import tiger.quanlylop.quanlylop.R;

public class StudentInfoFeeFragment extends AppCompatActivity {

    ListView listview;
    ArrayList<StudentHaveFeeDTO> liststudentHaveFee;
    ArrayList<StudentNoHaveFeeDTO> liststudentNoHaveFee;
    RadioButton radioTake;
    RadioButton radioUntake;
    
    AdapterStudentHaveFee adapterStudentHaveFee;
    AdapterStudentNoHaveFee adapterStudentNoHaveFee;

    int IDClass, IDFee; String NameClass, NameFee;
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_studentfeeinfo);

        AnhXa();

        Intent intent = this.getIntent();
        IDClass = intent.getIntExtra("idclass", -1);
        IDFee = intent.getIntExtra("idfee", -1);
        NameClass = intent.getStringExtra("nameclass");
        NameFee = intent.getStringExtra("namefee");


        setSupportActionBar(toolBar);

        getSupportActionBar().setTitle(NameClass+ " - " + NameFee);


        radioTake.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                {
                    radioUntake.setChecked(false);
                    //Toast.makeText(getApplicationContext(), Integer.toString(IDClass)+ Integer.toString(IDFee), Toast.LENGTH_LONG).show();

                    if (IDClass != -1 && IDFee != -1)

                        LoadStudentHaveFee(IDClass,IDFee);
                }

            }
        });

        radioUntake.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                {
                    radioTake.setChecked(false);
                    //Toast.makeText(getApplicationContext(), Integer.toString(IDClass)+ Integer.toString(IDFee), Toast.LENGTH_LONG).show();

                    if (IDClass != -1 && IDFee != -1)

                        LoadStudentNoHaveFee(IDClass,IDFee);
                }

            }
        });


        radioTake.setChecked(true);

    }

    public void AnhXa() {
        toolBar = (Toolbar) findViewById(R.id.toolBarStudentFeeInfo);
        listview = findViewById(R.id.lststudentfeeinfo);
        radioTake = findViewById(R.id.rdotake);
        radioUntake = findViewById(R.id.rdountake);

    }


    public void LoadStudentHaveFee(int IDClas, int IDFee) {
        StudentHaveFeeDAO.getInstance().PostStudentHaveFee(getApplicationContext(), IDClass, IDFee, new StudentHaveFeeDAO.StudentHaveFeeCallback() {
            @Override
            public void onSuccess(ArrayList<StudentHaveFeeDTO> studentHaveFeeDTOArrayList) {
                liststudentHaveFee = studentHaveFeeDTOArrayList;

                if (liststudentHaveFee != null) {
                    adapterStudentHaveFee = new AdapterStudentHaveFee(getApplicationContext(), R.layout.layout_custom_studentinfofee, liststudentHaveFee);
                    listview.setAdapter(adapterStudentHaveFee);
                    adapterStudentHaveFee.notifyDataSetInvalidated();
                } else
                    listview.setAdapter(null);
            }
        });
    }


    public void LoadStudentNoHaveFee(int IDClas, int IDFee) {

        StudentNoHaveFeeDAO.getInstance().PostStudentNoHaveFee(getApplicationContext(), IDClass, IDFee, new StudentNoHaveFeeDAO.StudentNoHaveFeeCallback() {
            @Override
            public void onSuccess(ArrayList<StudentNoHaveFeeDTO> noHaveFeeDTOArrayList) {
                liststudentNoHaveFee = noHaveFeeDTOArrayList;

                if (liststudentNoHaveFee != null) {
                    adapterStudentNoHaveFee = new AdapterStudentNoHaveFee(getApplicationContext(), R.layout.layout_custom_studentinfofee, liststudentNoHaveFee);
                    listview.setAdapter(adapterStudentNoHaveFee);
                    adapterStudentNoHaveFee.notifyDataSetInvalidated();
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
