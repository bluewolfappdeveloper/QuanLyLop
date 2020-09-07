package com.tiger.quanlylop.Fragment;

import android.app.Dialog;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tiger.quanlylop.Adapter.ClassApdapter;
import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DAO.FeeDAO;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.Library.RecyclerViewSwipeDecorator;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;


public class ClassFragment extends Fragment {

    FloatingActionButton fabAddClass;
    RecyclerView listViewClass;
    ClassApdapter classApdapter;
    List<ClassDTO> listClass;
    CoordinatorLayout class_container;

    public ClassFragment() {
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
        View view = inflater.inflate(R.layout.fragment_class, container, false);



        AnhXa(view);
        setUpRecycleView();
        fabAddClass.setOnClickListener(fabAddClassClicked);

        return view;
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
                    deleteItemClass(listClass.get(pos), pos);
                    break;
                case ItemTouchHelper.RIGHT:
                    classApdapter.notifyItemChanged(pos);
                    openDialogUpdate(listClass.get(pos));
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

    //region ViewOnClick
    View.OnClickListener fabAddClassClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openDialogAdd();
        }

    };

    //endregion

    //endregion

    //region Process

    private void AnhXa(View view){
        fabAddClass = view.findViewById(R.id.fabAddClass);
        listViewClass = view.findViewById(R.id.listViewClass);
        class_container = view.findViewById(R.id.class_container);
    }

    private void setUpRecycleView(){
        listClass = new ArrayList<ClassDTO>();
        listClass= ClassDAO.getInstance().getAllClass(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        listViewClass.setLayoutManager(linearLayoutManager);

        DividerItemDecoration  itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        listViewClass.addItemDecoration(itemDecoration);

        classApdapter = new ClassApdapter(getContext(),listClass);
        listViewClass.setAdapter(classApdapter);

        new ItemTouchHelper(itemTouch).attachToRecyclerView(listViewClass);

    }

    private void setSnackBar(String text, String actionName, View.OnClickListener onClickListener, Snackbar.Callback snackBarCallBack){
        Snackbar snackbar = Snackbar.make(class_container, text , Snackbar.LENGTH_LONG)
                .setAction(actionName,onClickListener);

        snackbar.setBackgroundTint(ContextCompat.getColor(getContext(),R.color.colorWhiteBlueDark));
        snackbar.setTextColor(ContextCompat.getColor(getContext(),R.color.colorBlackLight));
        snackbar.setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorOrangeLight));
        snackbar.addCallback(snackBarCallBack);
        snackbar.show();
    }

    private void deleteItemClass(final ClassDTO deleteClass, final int pos){

        ClassDAO.getInstance().DeleteClass(getContext(), deleteClass.getId(), true);
        listClass = ClassDAO.getInstance().getAllClass(getContext());
        classApdapter = new ClassApdapter(getContext(),listClass);
        listViewClass.setAdapter(classApdapter);

        setSnackBar("Đã xóa " + deleteClass.getNameClass(), "Hoàn tác", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassDAO.getInstance().AddNewClass(getContext(),deleteClass);

                listClass = ClassDAO.getInstance().getAllClass(getContext());
                classApdapter = new ClassApdapter(getContext(),listClass);
                listViewClass.setAdapter(classApdapter);

            }
        }, new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (!listClass.equals(deleteClass))
                    ClassDAO.getInstance().DeleteClass(getContext(), deleteClass.getId(), false);

            }
        });
    }

    private void openDialogUpdate(final ClassDTO oldUpdateClass){
        final Dialog updateClassDialog = new Dialog(getContext());
        updateClassDialog.setContentView(R.layout.dialog_updateclass);

        final AppCompatEditText editUpdateNameClass = updateClassDialog.findViewById(R.id.editUpdateNameClass);
        final AppCompatEditText editUpdateFeeClass = updateClassDialog.findViewById(R.id.editUpdateFeeClass);
        AppCompatButton btnUpdateClass = updateClassDialog.findViewById(R.id.btnUpdateClass);
        AppCompatButton btnExitUpdateClass = updateClassDialog.findViewById(R.id.btnExitUpdateClass);

        editUpdateNameClass.setText(oldUpdateClass.getNameClass());
        editUpdateFeeClass.setText(oldUpdateClass.getFeeClass());

        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameClass = editUpdateNameClass.getText().toString();
                String feeClass = editUpdateFeeClass.getText().toString();

                if (nameClass.isEmpty()  || feeClass.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    ClassDTO newUpdateClass = new ClassDTO(oldUpdateClass.getId(), nameClass,feeClass);
                    boolean isOK = ClassDAO.getInstance().UpdateClass(getContext(),newUpdateClass);

                    if (isOK){
                        Toast.makeText(getContext(), "Cập nhật lớp thành công", Toast.LENGTH_SHORT).show();
                        listClass = ClassDAO.getInstance().getAllClass(getContext());

                        classApdapter = new ClassApdapter(getContext(),listClass);
                        listViewClass.setAdapter(classApdapter);
                        updateClassDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Cập nhật lớp thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClassDialog.dismiss();
            }
        });

        updateClassDialog.show();
    }

    private void openDialogAdd(){
        final Dialog addClassDialog = new Dialog(getContext());
        addClassDialog.setContentView(R.layout.dialog_addclass);

        final AppCompatEditText editAddNameClass = addClassDialog.findViewById(R.id.editAddNameClass);
        final AppCompatEditText editAddFeeClass = addClassDialog.findViewById(R.id.editAddFeeClass);
        AppCompatButton btnAddClass = addClassDialog.findViewById(R.id.btnAddClass);
        AppCompatButton btnExitAddClass = addClassDialog.findViewById(R.id.btnExitAddClass);

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameClass = editAddNameClass.getText().toString();
                String feeClass = editAddFeeClass.getText().toString();

                if (nameClass.isEmpty() || feeClass.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    ClassDTO newClass = new ClassDTO(nameClass,feeClass);
                    boolean isOK = ClassDAO.getInstance().AddNewClass(getContext(),newClass);

                    if (isOK){
                        Toast.makeText(getContext(), "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
                        listClass = ClassDAO.getInstance().getAllClass(getContext());

                        classApdapter = new ClassApdapter(getContext(),listClass);
                        listViewClass.setAdapter(classApdapter);
                        addClassDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Thêm lớp thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassDialog.dismiss();
            }
        });

        addClassDialog.show();
    }

    //endregion
}