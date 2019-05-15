package tiger.quanlylop.quanlylop.FragmentApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.CRUDActivity.AddClass;
import tiger.quanlylop.quanlylop.CRUDActivity.UpdateClass;
import tiger.quanlylop.quanlylop.CRUDActivity.UpdateStudent;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterEditClass;
import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.R;

public class EditClassFragment extends Fragment {

    public static int REQUEST_CODE_ADD_CLASS = 112;
    public static int REQUEST_CODE_UPDATE_CLASS = 113;
    public static String NameClass;
    public static int IDClass;

    ArrayList<ClassDTO> classlist;
    ListView listView;
    AdapterEditClass adapterEditClass;
    Integer vt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_editclass, container, false);
        setHasOptionsMenu(true);


        listView = view.findViewById(R.id.lstclass);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewID = view.getId();

                if (viewID == R.id.imgedit) {
                        Intent intent = new Intent(getActivity(),UpdateClass.class);
                        intent.putExtra("ID", classlist.get(position).id );
                        intent.putExtra("NameClass", classlist.get(position).NameClass );
                        intent.putExtra("FeeClass", classlist.get(position).FeeClass);
                        startActivityForResult(intent, REQUEST_CODE_UPDATE_CLASS);

                } else if (viewID == R.id.imgdelete) {
                    IDClass =  classlist.get(position).id;
                    showAlertDialogDelClass(position);

                }
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
                    ClassDAO.getInstance().PostClass(getActivity(), new ClassDAO.ClassCallback() {
                        @Override
                        public void onSuccess(ArrayList<ClassDTO> classDTOArrayList) {
                            adapterEditClass = new AdapterEditClass(getActivity(), R.layout.layout_custom_editclass , classlist);
                            listView.setAdapter(adapterEditClass);
                            adapterEditClass.notifyDataSetInvalidated();
                        }
                    });
                }
                else
                {
                    listView.setAdapter(null);
                }
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1, R.id.itaddclass,1,R.string.themlop);
        item.setIcon(R.drawable.addclass);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(getActivity(), AddClass.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CLASS);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_CLASS)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadClass();
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_CLASS)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadClass();
            }
        }
    }



    public void showAlertDialogDelClass(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa lớp học");
        builder.setMessage("Bạn có muốn xóa "+ classlist.get(pos).NameClass +"  không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ClassDAO.getInstance().DeleteClass(getActivity(), IDClass, new ClassDAO.ClassCRUDCallback() {
                    @Override
                    public void onSuccess() {
                        LoadClass();
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


