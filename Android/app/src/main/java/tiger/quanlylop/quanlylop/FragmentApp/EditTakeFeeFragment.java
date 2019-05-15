package tiger.quanlylop.quanlylop.FragmentApp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.CustomAdapter.AdapterEditTakeFee;
import tiger.quanlylop.quanlylop.DAO.FeeDAO;
import tiger.quanlylop.quanlylop.DAO.TakeFeeDAO;
import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.DTO.TakeFeeDTO;
import tiger.quanlylop.quanlylop.R;

public class EditTakeFeeFragment extends AppCompatActivity {

    public static int REQUEST_CODE_ADD_TAKEFEE = 112;
    public static int REQUEST_CODE_UPDATE_TAKEFEE = 113;

    int IDClass, IDStudent;
    String NameClass, NameStudent;


    Toolbar toolbar;
    ListView listView;
    Spinner spnFee;
    Button btnUpdateFeeStudent;

    ArrayList<TakeFeeDTO> takefeelist;
    ArrayList<FeeDTO> feelist;
    AdapterEditTakeFee adapterEditTakeFee;
    Integer vt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takefee);

        AnhXa();

        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        IDClass = intent.getIntExtra("idclass", -1);
        NameClass = intent.getStringExtra("NameClass");
        IDStudent = intent.getIntExtra("idstudent", -1);
        NameStudent = intent.getStringExtra("NameStudent");


        if (IDClass != -1 && IDStudent != -1) {
            //Toast.makeText(getApplicationContext(), Integer.toString(IDClass)+ Integer.toString(IDStudent), Toast.LENGTH_LONG).show();
            getSupportActionBar().setTitle(NameClass + " - " + NameStudent);
            LoadFeeTake();
            LoadStudentFee(IDClass, IDStudent);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewID = view.getId();

                if (viewID == R.id.imgdeletetakefee) {

                    TakeFeeDAO.getInstance().DeleteTakeFee(getBaseContext(), takefeelist.get(position).id, new TakeFeeDAO.TakeFeeCRUDCallBack() {
                        @Override
                        public void onSuccess() {
                            LoadStudentFee(IDClass, IDStudent);
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
            }
        });
    }

    private void LoadStudentFee(int idClass, int idStudent) {

        TakeFeeDAO.getInstance().PostTakeFee(getApplicationContext(), idClass, idStudent, new TakeFeeDAO.TakeFeeCallBack() {
            @Override
            public void onSuccess(ArrayList<TakeFeeDTO> takeFeeDTOArrayList) {
                takefeelist = new ArrayList<TakeFeeDTO>();
                takefeelist = takeFeeDTOArrayList;

                if (takefeelist != null) {
                    adapterEditTakeFee = new AdapterEditTakeFee(getApplicationContext(), R.layout.layout_custom_edittakefee, takefeelist);
                    listView.setAdapter(adapterEditTakeFee);
                    adapterEditTakeFee.notifyDataSetInvalidated();
                } else
                    listView.setAdapter(null);
            }
        });

        btnUpdateFeeStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int IDFee = feelist.get(spnFee.getSelectedItemPosition()).id;

                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis); //DateTimeNow

                //int idclass, int idstudent, int idfee, Date datetime, int status
                TakeFeeDTO takeFeeDTO = new TakeFeeDTO(1, IDClass, IDStudent, IDFee, date , 1  );
                TakeFeeDAO.getInstance().AddTakeFee(getBaseContext(), takeFeeDTO, new TakeFeeDAO.TakeFeeCRUDCallBack() {
                    @Override
                    public void onSuccess() {
                        LoadStudentFee(IDClass, IDStudent);
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        });

    }


    private void LoadFeeTake() {

        FeeDAO.getInstance().PostFee(getApplicationContext(), new FeeDAO.FeeCallback() {
            @Override
            public void onSuccess(ArrayList<FeeDTO> FeeDTOArrayList) {
                feelist = FeeDTOArrayList;

                if (feelist != null)
                {
                    ArrayList<String> listFee = new ArrayList<>();

                    for (int i=0; i< feelist.size(); i++) listFee.add(feelist.get(i).NameFee);

                    ArrayAdapter<String> adapterFeeSpinner = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, listFee);
                    //adapterFeeSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

                    spnFee.setAdapter(adapterFeeSpinner);
                }
                else
                {
                    spnFee.setAdapter(null);
                }
            }
        });

    }


    public void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        listView = findViewById(R.id.lsttakefee);
        btnUpdateFeeStudent = (Button) findViewById(R.id.btnUpdateFeeStudent);
        spnFee = (Spinner) findViewById(R.id.spnFee);
    }
}


