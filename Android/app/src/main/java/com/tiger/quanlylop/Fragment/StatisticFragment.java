package com.tiger.quanlylop.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tiger.quanlylop.Activity.FeeActivity;
import com.tiger.quanlylop.Adapter.PayFeeApdapter;
import com.tiger.quanlylop.Adapter.StudentApdapter;
import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DAO.FeeDAO;
import com.tiger.quanlylop.DAO.FeeMainDAO;
import com.tiger.quanlylop.DAO.StatisticDAO;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.DTO.FeeDTO;
import com.tiger.quanlylop.DTO.StudentDTO;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {

    List<ClassDTO> listClass;
    List<StudentDTO> listStudent;
    List<FeeDTO> listFee;

    int posFee = -1, posClass = -1;
    StudentApdapter studentApdapter;

    AlertDialog dialogFee, dialogClass;

    AppCompatTextView textViewSelectedClass;
    AppCompatTextView textViewSelectedFee;
    RecyclerView listViewStatusPayFeeStudent;
    RadioGroup rdoGroupStatus;


    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        AnhXa(view);

        setUpRecycleView();
        openDialogClass();
        openDialogFee();


        rdoGroupStatus.check(R.id.rdoPayFee);
        rdoGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getData();
            }
        });

        textViewSelectedClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogClass.show();
            }
        });

        textViewSelectedFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFee.show();
            }
        });

        return view;
    }

    private void AnhXa(View view) {
        textViewSelectedClass = view.findViewById(R.id.textViewSelectedClass);
        textViewSelectedFee = view.findViewById(R.id.textViewSelectedFee);
        listViewStatusPayFeeStudent = view.findViewById(R.id.listViewStatusPayFeeStudent);
        rdoGroupStatus = view.findViewById(R.id.rdoStatus);
    }

    private void getData(){
        boolean status = true;
        int selectedId = rdoGroupStatus.getCheckedRadioButtonId();

        if (posClass >= 0 && posFee >= 0 && selectedId > -1){
            if (selectedId == R.id.rdoNoPayFee) status = false;
            getStudentNoPayFee(listClass.get(posClass).getId(), listFee.get(posFee).getId(),status);
        }
        else {
            if (posFee < 0) textViewSelectedFee.setText("- Học Phí -");
            if (posClass < 0) textViewSelectedClass.setText("- Lớp học -");
        }
    }

    private void setUpRecycleView(){
        listStudent = new ArrayList<StudentDTO>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        listViewStatusPayFeeStudent.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        listViewStatusPayFeeStudent.addItemDecoration(itemDecoration);

        studentApdapter = new StudentApdapter(getContext(),listStudent);
        listViewStatusPayFeeStudent.setAdapter(studentApdapter);
    }

    public void openDialogFee() {
        listFee = FeeDAO.getInstance().getShowFee(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chọn học phí");

        final String[] nameFee = new String[listFee.size()];

        final int selected = -1;
        for (int i = 0; i < listFee.size(); i++) { nameFee[i] = listFee.get(i).getNameFee(); }


        builder.setSingleChoiceItems(nameFee, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                ListView lw = ((AlertDialog)dialog).getListView();
                posFee  = lw.getCheckedItemPosition();
                if (posFee >= 0) textViewSelectedFee.setText(nameFee[posFee]);

                getData();
            }
        });

        /*builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog)dialog).getListView();
                posFee  = lw.getCheckedItemPosition();
                if (posFee >= 0) textViewSelectedFee.setText(nameFee[posFee]);

                getData();

            }
        });*/


        dialogFee = builder.create();
       // dialogFee.setCanceledOnTouchOutside(false);
    }

    public void openDialogClass() {
        listClass = ClassDAO.getInstance().getAllClass(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chọn Lớp");

        final String[] nameClass = new String[listClass.size()];

        final int selected = -1;
        for (int i = 0; i < listClass.size(); i++) { nameClass[i] = listClass.get(i).getNameClass(); }


        builder.setSingleChoiceItems(nameClass, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                ListView lw = ((AlertDialog)dialog).getListView();
                posClass  = lw.getCheckedItemPosition();

                if (posClass >= 0) textViewSelectedClass.setText(nameClass[posClass]);

                getData();
            }
        });

        /*builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ListView lw = ((AlertDialog)dialog).getListView();
                posClass  = lw.getCheckedItemPosition();

                if (posClass >= 0) textViewSelectedClass.setText(nameClass[posClass]);

                getData();

            }
        });*/


        dialogClass = builder.create();
        //dialogClass.setCanceledOnTouchOutside(false);
    }

    public void getStudentNoPayFee(long IDClass, long IDFee, boolean status){
        listStudent = StatisticDAO.getInstance().getStudentNoFee(getContext(),IDClass, IDFee, status);
        studentApdapter = new StudentApdapter(getContext(), listStudent);
        listViewStatusPayFeeStudent.setAdapter(studentApdapter);
    }

}