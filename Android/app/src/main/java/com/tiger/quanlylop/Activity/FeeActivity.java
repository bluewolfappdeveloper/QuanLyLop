package com.tiger.quanlylop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tiger.quanlylop.Adapter.ClassApdapter;
import com.tiger.quanlylop.Adapter.PayFeeApdapter;
import com.tiger.quanlylop.Adapter.StudentApdapter;
import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DAO.DataProvider;
import com.tiger.quanlylop.DAO.FeeMainDAO;
import com.tiger.quanlylop.DAO.StudentDAO;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.DTO.FeeMainDTO;
import com.tiger.quanlylop.DTO.PayFeeDTO;
import com.tiger.quanlylop.DTO.StudentDTO;
import com.tiger.quanlylop.Library.RecyclerViewSwipeDecorator;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class FeeActivity extends AppCompatActivity {

    AppCompatTextView textViewSelectedFee;
    Toolbar toolbar;
    List<FeeMainDTO> listFee;
    List<PayFeeDTO> listPayFee;
    AlertDialog dialogInsertFee, dialogUpdatePayFee;
    RecyclerView listViewPayFeeMain;
    Button btnAddFeeMain;

    PayFeeApdapter payFeeApdapter;
    String textSelectedFee="";
    Long idClass, idStudent;
    Boolean isSelected =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        //int k =  DataProvider.getInstance(FeeActivity.this).deleteObject("payfee", null, null);
        Intent myIntent = getIntent();
        String nameClass = myIntent.getStringExtra("NAMECLASS");
        final String nameStudent = myIntent.getStringExtra("NAMESTUDENT");
        idClass = myIntent.getLongExtra("IDCLASS", -1);
        idStudent = myIntent.getLongExtra("IDSTUDENT", -1);

       // Toast.makeText(this, ""+idClass+" "+idStudent, Toast.LENGTH_SHORT).show();

        AnhXa();
        setUpRecycleView();

        setSupportActionBar(toolbar);
        toolbar.setTitle(nameClass + " - " + nameStudent);

        onCreateDialog();

        textViewSelectedFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInsertFee.show();
            }
        });

        btnAddFeeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected){

                    FeeMainDAO.getInstance().addNewPayFee(FeeActivity.this,idClass, idStudent, nameStudent, listFee);

                    onCreateDialog();

                    textSelectedFee = "- Học phí -";
                    textViewSelectedFee.setText(textSelectedFee);

                    listPayFee = FeeMainDAO.getInstance().getAllFeeStudent(FeeActivity.this, idClass, idStudent);
                    payFeeApdapter = new PayFeeApdapter(FeeActivity.this, listPayFee);
                    listViewPayFeeMain.setAdapter(payFeeApdapter);

                    isSelected = false;
                }
            }
        });

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarFee);
        textViewSelectedFee = findViewById(R.id.textViewSelectedFee);
        listViewPayFeeMain = findViewById(R.id.listViewPayFeeMain);
        btnAddFeeMain = findViewById(R.id.btnAddFeeMain);
    }

    ItemTouchHelper.SimpleCallback itemTouch = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    deleteItemPayFee(listPayFee.get(pos));
                    break;
                case ItemTouchHelper.RIGHT:
                    payFeeApdapter.notifyItemChanged(pos);
                    openDialogUpdate(listPayFee.get(pos).getId());
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(FeeActivity.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(FeeActivity.this,R.color.colorRed))
                    .setSwipeLeftLabelColor(ContextCompat.getColor(FeeActivity.this,R.color.colorYellow))
                    .addSwipeLeftLabel("Xóa")
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(FeeActivity.this,R.color.colorGreenMid
                    ))
                    .setSwipeRightLabelColor(ContextCompat.getColor(FeeActivity.this,R.color.colorWhiteGrey))
                    .addSwipeRightLabel("Cập nhật")
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    //region Process

    private void setUpRecycleView(){
        listPayFee = new ArrayList<PayFeeDTO>();
        listPayFee = FeeMainDAO.getInstance().getAllFeeStudent(FeeActivity.this, idClass, idStudent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FeeActivity.this, LinearLayoutManager.VERTICAL,false);
        listViewPayFeeMain.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(FeeActivity.this, linearLayoutManager.getOrientation());
        listViewPayFeeMain.addItemDecoration(itemDecoration);

        payFeeApdapter = new PayFeeApdapter(FeeActivity.this, listPayFee);
        listViewPayFeeMain.setAdapter(payFeeApdapter);

        new ItemTouchHelper(itemTouch).attachToRecyclerView(listViewPayFeeMain);
    }

    private void setSnackBar(String text, String actionName, View.OnClickListener onClickListener){
        Snackbar snackbar = Snackbar.make(listViewPayFeeMain, text , Snackbar.LENGTH_LONG)
                .setAction(actionName,onClickListener);

        snackbar.setBackgroundTint(ContextCompat.getColor(this,R.color.colorWhiteBlueDark));
        snackbar.setTextColor(ContextCompat.getColor(this,R.color.colorBlackLight));
        snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.colorOrangeLight));
        snackbar.show();
    }

    public void onCreateDialog() {
        listFee = FeeMainDAO.getInstance().getAllFeeEnable(FeeActivity.this,idClass, idStudent);

        AlertDialog.Builder builder = new AlertDialog.Builder(FeeActivity.this);
        builder.setTitle("Chọn học phí");

        String[] nameFee = new String[listFee.size()];
        final boolean[] checkedItems = new boolean[listFee.size()];

        for (int i = 0; i < listFee.size(); i++) {
            nameFee[i] = listFee.get(i).getNameFee();
            checkedItems[i] = listFee.get(i).isSelected();
        }


        builder.setMultiChoiceItems(nameFee, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textSelectedFee = "";
                for (int i = 0; i < listFee.size(); i++) {
                    listFee.get(i).setSelected(checkedItems[i]);
                    if(checkedItems[i]) textSelectedFee += listFee.get(i).getNameFee().trim() + " , ";
                }

                if (textSelectedFee.isEmpty() || textSelectedFee.length()-3 < 0) textSelectedFee = "- Học phí -";
                else textSelectedFee = textSelectedFee.substring(0,textSelectedFee.length()-3);

                if (textSelectedFee.isEmpty()) textSelectedFee = "- Học phí -";

                textViewSelectedFee.setText(textSelectedFee);
                if (listFee.size() > 0) isSelected = true;
            }
        });

        dialogInsertFee = builder.create();
    }

    private void deleteItemPayFee(final PayFeeDTO payfee){
        if (FeeMainDAO.getInstance().deletePayFee(this,payfee.getId())){

            onCreateDialog();

            listPayFee = FeeMainDAO.getInstance().getAllFeeStudent(FeeActivity.this, idClass, idStudent);
            payFeeApdapter = new PayFeeApdapter(FeeActivity.this, listPayFee);
            listViewPayFeeMain.setAdapter(payFeeApdapter);

            setSnackBar("Đã xóa " + payfee.getNameFee(), "Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FeeMainDAO.getInstance().undoPayFee(FeeActivity.this, payfee);

                    onCreateDialog();
                    listPayFee = FeeMainDAO.getInstance().getAllFeeStudent(FeeActivity.this, idClass, idStudent);
                    payFeeApdapter = new PayFeeApdapter(FeeActivity.this, listPayFee);
                    listViewPayFeeMain.setAdapter(payFeeApdapter);
                }
            });
        }
    }

    public void openDialogUpdate(final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FeeActivity.this);
        builder.setTitle("Cập nhật học phí");

        String[] nameFee = new String[listFee.size()];

        final int selected = 0;
        for (int i = 0; i < listFee.size(); i++) { nameFee[i] = listFee.get(i).getNameFee(); }


        builder.setSingleChoiceItems(nameFee, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ListView lw = ((AlertDialog)dialog).getListView();
                int pos  = lw.getCheckedItemPosition();

                if (FeeMainDAO.getInstance().updatePayFee(FeeActivity.this, id, listFee.get(pos).getId())){
                    listPayFee = FeeMainDAO.getInstance().getAllFeeStudent(FeeActivity.this, idClass, idStudent);
                    payFeeApdapter = new PayFeeApdapter(FeeActivity.this, listPayFee);
                    listViewPayFeeMain.setAdapter(payFeeApdapter);
                    Toast.makeText(FeeActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    onCreateDialog();
                }

                onCreateDialog();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogUpdatePayFee = builder.create();
        dialogUpdatePayFee.setCanceledOnTouchOutside(false);
        dialogUpdatePayFee.show();
    }

    //endregion

}