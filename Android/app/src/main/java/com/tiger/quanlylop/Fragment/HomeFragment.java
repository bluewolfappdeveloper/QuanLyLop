package com.tiger.quanlylop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tiger.quanlylop.Activity.MainActivity;
import com.tiger.quanlylop.Activity.StudentActivity;
import com.tiger.quanlylop.Adapter.HomeApdapter;
import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    GridView gvClassHome;
    List<ClassDTO> listClass;
    HomeApdapter homeApdapter;

    public HomeFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa(view);
        setUpRecycleView();


        return view;
    }

    private void AnhXa(View view) {
        gvClassHome = view.findViewById(R.id.gvClassHome);
    }

    private void setUpRecycleView(){
        listClass = new ArrayList<ClassDTO>();
        listClass= ClassDAO.getInstance().getAllClass(getContext());


        HomeApdapter homeApdapter = new HomeApdapter(getContext(),listClass);

        gvClassHome.setAdapter(homeApdapter);
        gvClassHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //Toast.makeText(getContext(), "Hello"+l, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getContext(), StudentActivity.class);
                myIntent.putExtra("IDCLASS", id);
                myIntent.putExtra("NAMECLASS", listClass.get(pos).getNameClass());
                getContext().startActivity(myIntent);
            }
        });
    }
}