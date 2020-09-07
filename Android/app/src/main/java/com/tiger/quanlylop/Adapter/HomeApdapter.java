package com.tiger.quanlylop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.R;

import java.util.List;

public class HomeApdapter extends BaseAdapter {

    //Dữ liệu hiện thị là danh sách sinh viên
    private List<ClassDTO> listClass;
    private Context context;

    public HomeApdapter(Context context, List<ClassDTO> listClass) {
        this.listClass = listClass;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listClass.size();
    }

    @Override
    public Object getItem(int i) {
        return listClass.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listClass.get(i).getId();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View classView = LayoutInflater.from(context).inflate(R.layout.layout_item_home,null);
        AppCompatTextView lblNameClassHome = classView.findViewById(R.id.lblNameClassHome);

        lblNameClassHome.setText(listClass.get(pos).getNameClass().toUpperCase());

        return classView;
    }
}
