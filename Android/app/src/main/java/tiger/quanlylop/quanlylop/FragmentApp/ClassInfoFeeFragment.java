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
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterClassInfoFee;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterEditStudent;
import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DAO.ClassInfoFeeDAO;
import tiger.quanlylop.quanlylop.DAO.FeeDAO;
import tiger.quanlylop.quanlylop.DAO.StudentDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.ClassInfoFeeDTO;
import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class ClassInfoFeeFragment extends Fragment {


    ArrayList<ClassInfoFeeDTO> listclassInfoFee;
    ArrayList<FeeDTO> feelist;
    Spinner spinner;
    ListView listView;
    AdapterClassInfoFee adapterClassInfoFee;
    Integer vt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_classinfofee, container, false);
        setHasOptionsMenu(true);

        spinner = view.findViewById(R.id.pinFeeInfo);
        listView = view.findViewById(R.id.lstclassFee);

        LoadFee();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (feelist !=  null)
                LoadClass(feelist.get(position).id);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent intent = new Intent(getActivity(), StudentInfoFeeFragment.class);
                    intent.putExtra("idclass", listclassInfoFee.get(position).idclass);
                    intent.putExtra("idfee", feelist.get(spinner.getSelectedItemPosition()).id);
                    intent.putExtra("nameclass", listclassInfoFee.get(position).nameclass);
                    intent.putExtra("namefee", feelist.get(spinner.getSelectedItemPosition()).NameFee);
                    startActivity(intent);

            }
        });


        LoadFee();


        return view;
    }


    public  void LoadClass(int IDFee)
    {
        ClassInfoFeeDAO.getInstance().PostClass(getActivity(), IDFee, new ClassInfoFeeDAO.ClassInfoFeeCallback() {
            @Override
            public void onSuccess(ArrayList<ClassInfoFeeDTO> classInfoFeeDTOS) {
                listclassInfoFee = classInfoFeeDTOS;
                adapterClassInfoFee = new AdapterClassInfoFee(getActivity(), R.layout.layout_custom_classinfofee , listclassInfoFee);
                listView.setAdapter(adapterClassInfoFee);
                adapterClassInfoFee.notifyDataSetInvalidated();
            }
        });

    }

    private void LoadFee() {

        FeeDAO.getInstance().PostFee(getActivity(), new FeeDAO.FeeCallback() {
            @Override
            public void onSuccess(ArrayList<FeeDTO> FeeDTOArrayList) {
                feelist = FeeDTOArrayList;

                if (feelist != null)
                {
                    ArrayList<String> listFee = new ArrayList<>();
                    for (int i=0; i< feelist.size(); i++) listFee.add(feelist.get(i).NameFee);
                    ArrayAdapter<String> adapterFeeSpinner = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listFee);
                    spinner.setAdapter(adapterFeeSpinner);
                }
                else
                {
                    spinner.setAdapter(null);
                }
            }
        });

    }



}


