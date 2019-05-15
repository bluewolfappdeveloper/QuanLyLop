package tiger.quanlylop.quanlylop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import tiger.quanlylop.quanlylop.CustomAdapter.AdapterClass;
import tiger.quanlylop.quanlylop.DAO.ClassDAO;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.R;

public class ClassFragment extends Fragment
{
    GridView gridView;
    ArrayList<ClassDTO> classlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_class, container, false);

        gridView = view.findViewById(R.id.gvClass);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getActivity(), classlist.get(position).NameClass, Toast.LENGTH_LONG).show();

                if (classlist.get(position).CountStudent > 0) {
                    Intent intent = new Intent(getActivity(), StudentFragment.class);
                    intent.putExtra("idclass", classlist.get(position).id);
                    intent.putExtra("nameclass", classlist.get(position).NameClass);
                    startActivity(intent);
                }
            }
        });

        ClassDAO.getInstance().PostClass(getActivity(), new ClassDAO.ClassCallback() {
            @Override
            public void onSuccess(ArrayList<ClassDTO> classDTOArrayList) {
                classlist = classDTOArrayList;
                if (classlist != null) {
                    AdapterClass adapterclass = new AdapterClass(getActivity(), R.layout.layout_custom_class, classlist);
                    gridView.setAdapter(adapterclass);
                    adapterclass.notifyDataSetInvalidated();
                }
                else
                    gridView.setAdapter(null);

            }
        });

        return view;


    }



}
