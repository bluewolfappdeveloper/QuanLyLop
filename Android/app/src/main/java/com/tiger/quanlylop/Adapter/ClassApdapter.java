package com.tiger.quanlylop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.R;

import java.util.List;

public class ClassApdapter extends RecyclerView.Adapter<ClassApdapter.ViewHolder> {

    //Dữ liệu hiện thị là danh sách sinh viên
    private List<ClassDTO> listClass;
    private Context context;

    public ClassApdapter(Context context, List<ClassDTO> listClass) {
        this.listClass = listClass;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View classView = inflater.inflate(R.layout.layout_item_class, parent, false);

        ViewHolder viewHolder = new ViewHolder(classView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassDTO _class = listClass.get(position);

        StringBuffer fee = new StringBuffer(_class.getFeeClass());
        int index=fee.length() - 3;
        while (index > 0){
            fee.insert(index,",");
            index-=3;
        }

        holder.lblNameClass.setText(_class.getNameClass());
        holder.lblFeeClass.setText(fee);
    }

    @Override
    public int getItemCount() {
        return listClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public AppCompatTextView lblNameClass;
            public AppCompatTextView lblFeeClass;
            public AppCompatImageView imgClass;

            public ViewHolder(View itemView){
                super(itemView);
                lblNameClass = itemView.findViewById(R.id.lblNameClass);
                lblFeeClass = itemView.findViewById(R.id.lblFeeClass);
                imgClass = itemView.findViewById(R.id.imgClass);
            }

    }
}
