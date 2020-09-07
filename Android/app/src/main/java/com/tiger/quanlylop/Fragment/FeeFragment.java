package com.tiger.quanlylop.Fragment;

import android.app.Dialog;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tiger.quanlylop.Adapter.FeeApdapter;
import com.tiger.quanlylop.DAO.FeeDAO;
import com.tiger.quanlylop.DTO.FeeDTO;
import com.tiger.quanlylop.Library.RecyclerViewSwipeDecorator;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class FeeFragment extends Fragment {

    FloatingActionButton fabAddFee;
    RecyclerView listViewFee;
    FeeApdapter feeApdapter;
    List<FeeDTO> listFee;
    CoordinatorLayout fee_container;

    public FeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fee, container, false);


        AnhXa(view);
        setUpRecycleView();
        fabAddFee.setOnClickListener(fabAddFeeClicked);

        return view;

    }

    private void AnhXa(View view){
        fabAddFee = view.findViewById(R.id.fabAddFee);
        listViewFee = view.findViewById(R.id.listViewFee);
        fee_container = view.findViewById(R.id.fee_container);
    }

    //region Event Methods

    //region Item Touch Helper
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
                    deleteItemFee(listFee.get(pos), pos);
                    break;
                case ItemTouchHelper.RIGHT:
                    feeApdapter.notifyItemChanged(pos);
                    openDialogUpdate(listFee.get(pos));
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(getContext(),c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorRed))
                    .setSwipeLeftLabelColor(ContextCompat.getColor(getContext(),R.color.colorYellow))
                    .addSwipeLeftLabel("Xóa")
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenMid
                    ))
                    .setSwipeRightLabelColor(ContextCompat.getColor(getContext(),R.color.colorWhiteGrey))
                    .addSwipeRightLabel("Cập nhật")
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    //endregion


    View.OnClickListener fabAddFeeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openDialogAdd();
        }
    };
    //endregion


    //region Process
    private void setUpRecycleView(){
        listFee = new ArrayList<FeeDTO>();
        listFee= FeeDAO.getInstance().getAllFee(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        listViewFee.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        listViewFee.addItemDecoration(itemDecoration);

        feeApdapter = new FeeApdapter(getContext(),listFee);
        listViewFee.setAdapter(feeApdapter);

        new ItemTouchHelper(itemTouch).attachToRecyclerView(listViewFee);

    }

    private void setSnackBar(String text, String actionName, View.OnClickListener onClickListener, Snackbar.Callback snackBarCallBack){
        Snackbar snackbar = Snackbar.make(fee_container, text , Snackbar.LENGTH_LONG)
                .setAction(actionName,onClickListener);

        snackbar.setBackgroundTint(ContextCompat.getColor(getContext(),R.color.colorWhiteBlueDark));
        snackbar.setTextColor(ContextCompat.getColor(getContext(),R.color.colorBlackLight));
        snackbar.setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorOrangeLight));

        snackbar.addCallback(snackBarCallBack);

        snackbar.show();
    }

    private void deleteItemFee(final FeeDTO deleteFee, final int pos){

        FeeDAO.getInstance().DeleteFee(getContext(),deleteFee.getId(), true);
        listFee = FeeDAO.getInstance().getAllFee(getContext());
        feeApdapter = new FeeApdapter(getContext(),listFee);
        listViewFee.setAdapter(feeApdapter);

        setSnackBar("Đã xóa " + deleteFee.getNameFee(), "Hoàn tác", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeeDAO.getInstance().AddNewFee(getContext(), deleteFee);
                listFee = FeeDAO.getInstance().getAllFee(getContext());
                feeApdapter = new FeeApdapter(getContext(),listFee);
                listViewFee.setAdapter(feeApdapter);

            }
        }, new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (!listFee.equals(deleteFee))
                    FeeDAO.getInstance().DeleteFee(getContext(),deleteFee.getId(), false);
            }
        });

    }

    private void openDialogUpdate(final FeeDTO oldUpdateFee){
        final Dialog updateFeeDialog = new Dialog(getContext());
        updateFeeDialog.setContentView(R.layout.dialog_updatefee);

        final AppCompatEditText editUpdateNameFee = updateFeeDialog.findViewById(R.id.editUpdateNameFee);
        final AppCompatEditText editUpdatePriceFee = updateFeeDialog.findViewById(R.id.editUpdatePriceFee);
        final AppCompatCheckBox editUpdateShowFee = updateFeeDialog.findViewById(R.id.editUpdateShowFee);
        AppCompatButton btnUpdateFee = updateFeeDialog.findViewById(R.id.btnUpdateFee);
        AppCompatButton btnExitUpdateFee = updateFeeDialog.findViewById(R.id.btnExitUpdateFee);

        editUpdateNameFee.setText(oldUpdateFee.getNameFee());
        editUpdatePriceFee.setText(oldUpdateFee.getPriceFee());
        editUpdateShowFee.setChecked(oldUpdateFee.isShow());

        btnUpdateFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameFee = editUpdateNameFee.getText().toString();
                String priceFee = editUpdatePriceFee.getText().toString();

                if (nameFee.isEmpty()  || priceFee.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    FeeDTO newUpdateFee = new FeeDTO(oldUpdateFee.getId(), nameFee,priceFee, editUpdateShowFee.isChecked());
                    boolean isOK = FeeDAO.getInstance().UpdateFee(getContext(),newUpdateFee);

                    if (isOK){
                        Toast.makeText(getContext(), "Cập nhật học phí thành công", Toast.LENGTH_SHORT).show();
                        listFee = FeeDAO.getInstance().getAllFee(getContext());
                        feeApdapter = new FeeApdapter(getContext(),listFee);
                        listViewFee.setAdapter(feeApdapter);

                        updateFeeDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Cập nhật học phí thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitUpdateFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFeeDialog.dismiss();
            }
        });

        updateFeeDialog.show();
    }

    private void openDialogAdd(){
        final Dialog addFeeDialog = new Dialog(getContext());
        addFeeDialog.setContentView(R.layout.dialog_addfee);

        final AppCompatEditText editAddNameFee = addFeeDialog.findViewById(R.id.editAddNameFee);
        final AppCompatEditText editAddPriceFee = addFeeDialog.findViewById(R.id.editAddPriceFee);
        final AppCompatCheckBox editUpdateShowFee = addFeeDialog.findViewById(R.id.editAddShowFee);
        AppCompatButton btnAddFee = addFeeDialog.findViewById(R.id.btnAddFee);
        AppCompatButton btnExitAddFee = addFeeDialog.findViewById(R.id.btnExitAddFee);

        btnAddFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameFee = editAddNameFee.getText().toString();
                String priceFee = editAddPriceFee.getText().toString();

                if (nameFee.isEmpty() || priceFee.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    FeeDTO newFee = new FeeDTO(nameFee,priceFee, editUpdateShowFee.isChecked());
                    boolean isOK = FeeDAO.getInstance().AddNewFee(getContext(),newFee);

                    if (isOK){
                        Toast.makeText(getContext(), "Thêm học phí thành công", Toast.LENGTH_SHORT).show();
                        listFee = FeeDAO.getInstance().getAllFee(getContext());
                        feeApdapter = new FeeApdapter(getContext(),listFee);
                        listViewFee.setAdapter(feeApdapter);
                        addFeeDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Thêm học phí thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitAddFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeeDialog.dismiss();
            }
        });

        addFeeDialog.show();
    }

    //endregion
}