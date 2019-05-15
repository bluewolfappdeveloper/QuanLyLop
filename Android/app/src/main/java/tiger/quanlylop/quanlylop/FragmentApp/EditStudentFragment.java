package tiger.quanlylop.quanlylop.FragmentApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.CRUDActivity.AddStudent;
import tiger.quanlylop.quanlylop.CRUDActivity.UpdateStudent;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterEditStudent;
import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class EditStudentFragment extends Fragment {

    public static int REQUEST_CODE_ADD_STUDENT = 113;
    public static int REQUEST_CODE_UPDATE_STUDENT = 113;
    public static String NameClass;
    public static int IDClass;
    public static int IDStudent;


    ArrayList<ClassDTO> classlist;
    ArrayList<StudentDTO> studentlist;
    Spinner spinner;
    ListView listView;
    AdapterEditStudent adapterstudent;
    Integer vt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_editstudent, container, false);
        setHasOptionsMenu(true);


        spinner =  (Spinner) view.findViewById(R.id.pinClass);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                if (classlist != null )
                {
                    //Toast.makeText(getActivity(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    IDClass = classlist.get(position).id;
                    NameClass = classlist.get(position ).NameClass;
                    LoadStudent(IDClass);
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        listView = view.findViewById(R.id.lststudent);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewID = view.getId();

                if (viewID == R.id.imgedit) {
                        Intent intent = new Intent(getActivity(),UpdateStudent.class);
                        intent.putExtra("ID", studentlist.get(position).id );
                        intent.putExtra("NameStudent", studentlist.get(position).NameStudent );
                        intent.putExtra("PhoneStudent", studentlist.get(position).PhoneStudent );
                        startActivityForResult(intent, REQUEST_CODE_UPDATE_STUDENT);

                } else if (viewID == R.id.imgdelete) {
                    IDStudent = studentlist.get(position).id;
                    showAlertDialogDelStudent(position);
                }// else Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
            }
        });


        LoadClass();


        return view;
    }

    public  void LoadClass()
    {
        ClassDAO.getInstance().PostClass(getActivity(), new ClassDAO.ClassCallback() {
            @Override
            public void onSuccess(ArrayList<ClassDTO> classDTOArrayList) {

                 classlist = classDTOArrayList;

                if (classlist != null)
                {
                    ArrayList<String> listclass = new ArrayList<>();

                    for (int i=0; i< classlist.size(); i++) listclass.add(classlist.get(i).NameClass);

                    ArrayAdapter<String> adapterClassSpinner = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listclass);
                    //adapterClassSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spinner.setAdapter(adapterClassSpinner);
                }
                else
                {
                    listView.setAdapter(null);
                }
            }
        });

    }

    public void LoadStudent(int id) {

        StudentDAO.getInstance().PostStudent(getActivity(), IDClass, new StudentDAO.StudentCallback() {
            @Override
            public void onSuccess(ArrayList<StudentDTO> studentDTOArrayList) {

                studentlist = studentDTOArrayList;

                if (studentlist != null)
                {
                    adapterstudent = new AdapterEditStudent(getActivity(), R.layout.layout_custom_editstudent, studentlist);
                    listView.setAdapter(adapterstudent);
                    adapterstudent.notifyDataSetInvalidated();
                }
                else
                    listView.setAdapter(null);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1, R.id.itaddstudent,1,R.string.themhocsinh);
        item.setIcon(R.drawable.addperson);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = spinner.getSelectedItemPosition();
        Intent intent = new Intent(getActivity(),AddStudent.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_STUDENT);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_STUDENT)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadStudent(IDClass);
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_STUDENT)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadStudent(IDClass);
            }
        }
    }



    public void showAlertDialogDelStudent(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa học sinh");
        builder.setMessage("Bạn có muốn xóa học sinh "+ studentlist.get(pos).NameStudent+"  không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //Toast.makeText(context, "Không thoát được", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                StudentDAO.getInstance().DeleteStudent(getActivity(), IDClass, IDStudent, new StudentDAO.StudentCRUDCallback() {
                    @Override
                    public void onSuccess() {
                        LoadStudent(IDClass);
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_LONG).show();
                    }
                });

                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}


