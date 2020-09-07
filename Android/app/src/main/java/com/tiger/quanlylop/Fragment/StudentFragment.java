package com.tiger.quanlylop.Fragment;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tiger.quanlylop.Adapter.StudentApdapter;
import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DAO.DataProvider;
import com.tiger.quanlylop.DAO.StudentDAO;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.DTO.StudentDTO;
import com.tiger.quanlylop.Library.RecyclerViewSwipeDecorator;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment {

    public StudentFragment() {
        // Required empty public constructor
    }

    FloatingActionButton fabAddStudent;
    RecyclerView listViewStudent;
    AppCompatSpinner spinnerListClass;

    StudentApdapter studentApdapter;

    List<ClassDTO> listClass;
    List<StudentDTO> listStudent;

    CoordinatorLayout student_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        AnhXa(view);

        listClass  = ClassDAO.getInstance().getAllClass(getContext());
        setUpRecycleView();
        if (!listClass.isEmpty()){
            ArrayAdapter<ClassDTO> adapter = new ArrayAdapter<ClassDTO>(getContext(), R.layout.support_simple_spinner_dropdown_item, listClass);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            spinnerListClass.setAdapter(adapter);
            spinnerListClass.setOnItemSelectedListener(itemStudentSelected);
            fabAddStudent.setOnClickListener(fabAddStudentClicked);

            ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.colorWhiteBlueDark);
            fabAddStudent.setBackgroundTintList(colorStateList);
        }
        else{
            ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), R.color.ic_grey);
            fabAddStudent.setBackgroundTintList(colorStateList);
           // fabAddStudent.setBackgroundTintList(getResources().getColor(R.color.ic_grey));
          fabAddStudent.setEnabled(false);
        }


        return view;
    }

    private void AnhXa(View view) {
        spinnerListClass = view.findViewById(R.id.spinnerListClass);
        listViewStudent = view.findViewById(R.id.listViewStudent);
        fabAddStudent = view.findViewById(R.id.fabAddStudent);
        student_container = view.findViewById(R.id.student_container);
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
                    if (spinnerListClass.getSelectedItemPosition() > -1){
                        deleteItemStudent(listStudent.get(pos), pos);
                    }

                    break;
                case ItemTouchHelper.RIGHT:
                    if (spinnerListClass.getSelectedItemPosition() > -1){
                        studentApdapter.notifyItemChanged(pos);
                        openDialogUpdate(listStudent.get(pos));
                    }

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


    View.OnClickListener fabAddStudentClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openDialogAdd();
        }
    };

    AdapterView.OnItemSelectedListener itemStudentSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            listStudent = StudentDAO.getInstance().getAllStudent(getContext(),getIDClass());
            studentApdapter = new StudentApdapter(getContext(),listStudent);
            listViewStudent.setAdapter(studentApdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    //endregion


    //region Process
    private long getIDClass(){
        return listClass.get(spinnerListClass.getSelectedItemPosition()).getId();
    }

    private void setUpRecycleView(){
        listStudent = new ArrayList<StudentDTO>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        listViewStudent.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        listViewStudent.addItemDecoration(itemDecoration);

        studentApdapter = new StudentApdapter(getContext(),listStudent);
        listViewStudent.setAdapter(studentApdapter);

        new ItemTouchHelper(itemTouch).attachToRecyclerView(listViewStudent);
    }

    private void setSnackBar(String text, String actionName, View.OnClickListener onClickListener, Snackbar.Callback snackBarCallBack){

        Snackbar snackbar = Snackbar.make(student_container, text , Snackbar.LENGTH_LONG)
                .setAction(actionName,onClickListener);

        snackbar.setBackgroundTint(ContextCompat.getColor(getContext(),R.color.colorWhiteBlueDark));
        snackbar.setTextColor(ContextCompat.getColor(getContext(),R.color.colorBlackLight));
        snackbar.setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorOrangeLight));
        snackbar.addCallback(snackBarCallBack);
        snackbar.show();


    }

    private void deleteItemStudent(final StudentDTO deleteStudent, final int pos){
        listStudent.remove(pos);
        studentApdapter.notifyDataSetChanged();
        StudentDAO.getInstance().DeleteStudent(getContext(),deleteStudent.getId(),deleteStudent.getIdClass(), true);

        setSnackBar("Đã xóa " + deleteStudent.getNameStudent(), "Hoàn tác", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentDAO.getInstance().AddNewStudent(getContext(),deleteStudent);
                listStudent.add(pos, deleteStudent);
                studentApdapter.notifyDataSetChanged();
            }
        }, new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (!listStudent.equals(deleteStudent))
                    StudentDAO.getInstance().DeleteStudent(getContext(),deleteStudent.getId(),deleteStudent.getIdClass(), false);

            }
        });

    }

    private void openDialogUpdate(final StudentDTO oldUpdateStudent){
        final Dialog updateStudentDialog = new Dialog(getContext());
        updateStudentDialog.setContentView(R.layout.dialog_updatestudent);

        final AppCompatTextView lblUpdateNameClassStudent = updateStudentDialog.findViewById(R.id.lblUpdateNameClassStudent);
        final AppCompatEditText editUpdateNameStudent = updateStudentDialog.findViewById(R.id.editUpdateNameStudent);
        final AppCompatEditText editUpdatePhoneStudent = updateStudentDialog.findViewById(R.id.editUpdatePhoneStudent);

        AppCompatButton btnUpdateStudent = updateStudentDialog.findViewById(R.id.btnUpdateStudent);
        AppCompatButton btnExitUpdateStudent = updateStudentDialog.findViewById(R.id.btnExitUpdateStudent);

        lblUpdateNameClassStudent.setText(listClass.get(spinnerListClass.getSelectedItemPosition()).getNameClass());
        editUpdateNameStudent.setText(oldUpdateStudent.getNameStudent());
        editUpdatePhoneStudent.setText(oldUpdateStudent.getPhoneStudent());

        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStudent = editUpdateNameStudent.getText().toString();
                String phoneStudent = editUpdatePhoneStudent.getText().toString();

                if (nameStudent.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    StudentDTO newUpdateStudent = new StudentDTO(oldUpdateStudent.getId(), oldUpdateStudent.getIdClass(), nameStudent, phoneStudent);
                    boolean isOK = StudentDAO.getInstance().UpdateStudent(getContext(),newUpdateStudent);

                    if (isOK){
                        Toast.makeText(getContext(), "Cập nhật học sinh thành công", Toast.LENGTH_SHORT).show();
                        listStudent = StudentDAO.getInstance().getAllStudent(getContext(),getIDClass());
                        studentApdapter = new StudentApdapter(getContext(),listStudent);
                        listViewStudent.setAdapter(studentApdapter);

                        updateStudentDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Cập nhật học phí thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudentDialog.dismiss();
            }
        });

        updateStudentDialog.show();
    }

    private void openDialogAdd(){
        final Dialog addStudentDialog = new Dialog(getContext());
        addStudentDialog.setContentView(R.layout.dialog_addstudent);

        final AppCompatTextView lblAddNameClassStudent = addStudentDialog.findViewById(R.id.lblAddNameClassStudent);
        final AppCompatEditText editAddNameStudent = addStudentDialog.findViewById(R.id.editAddNameStudent);
        final AppCompatEditText editAddPhoneStudent = addStudentDialog.findViewById(R.id.editAddPhoneStudent);

        AppCompatButton btnAddStudent = addStudentDialog.findViewById(R.id.btnAddStudent);
        AppCompatButton btnExitAddStudent = addStudentDialog.findViewById(R.id.btnExitAddStudent);

        lblAddNameClassStudent.setText(spinnerListClass.getSelectedItem().toString());

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStudent = editAddNameStudent.getText().toString();
                String phoneStudent = editAddPhoneStudent.getText().toString();

                if (nameStudent.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    StudentDTO newUpdateStudent = new StudentDTO(getIDClass(), nameStudent, phoneStudent);
                    boolean isOK = StudentDAO.getInstance().AddNewStudent(getContext(),newUpdateStudent);

                    if (isOK){
                        Toast.makeText(getContext(), "Thêm học sinh thành công", Toast.LENGTH_SHORT).show();
                        listStudent = StudentDAO.getInstance().getAllStudent(getContext(),getIDClass());
                        studentApdapter = new StudentApdapter(getContext(),listStudent);
                        listViewStudent.setAdapter(studentApdapter);

                        addStudentDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Thêm học phí thất bại \nVui lòng kiểm tra thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExitAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudentDialog.dismiss();
            }
        });

        addStudentDialog.show();
    }

    //endregion
}