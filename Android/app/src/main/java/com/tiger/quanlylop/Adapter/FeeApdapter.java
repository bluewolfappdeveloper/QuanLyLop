package com.tiger.quanlylop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tiger.quanlylop.DAO.FeeDAO;
import com.tiger.quanlylop.DTO.FeeDTO;
import com.tiger.quanlylop.R;

import java.util.List;

public class FeeApdapter extends RecyclerView.Adapter<FeeApdapter.ViewHolder> {

    //Dữ liệu hiện thị là danh sách sinh viên
    private List<FeeDTO> listFee;
    private Context context;

    public FeeApdapter(Context context, List<FeeDTO> listFee) {
        this.listFee = listFee;
        this.context = context;
    }

    @NonNull
    @Override
    public FeeApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View feeView = inflater.inflate(R.layout.layout_item_fee, parent, false);

        ViewHolder viewHolder = new ViewHolder(feeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeeDTO fee = listFee.get(position);

        StringBuffer priceFee = new StringBuffer(fee.getPriceFee());
        int index=priceFee.length() - 3;
        while (index > 0){
            priceFee.insert(index,",");
            index-=3;
        }

        holder.lblNameFee.setText(fee.getNameFee());
        holder.lblPriceFee.setText(priceFee);
        holder.switchShow.setChecked(fee.isShow());
        holder.switchShow.setTag(fee.getId());

        holder.switchShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                FeeDAO.getInstance().UpdateShowFee(context, (long)compoundButton.getTag(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public AppCompatTextView lblNameFee;
            public AppCompatTextView lblPriceFee;
            public SwitchCompat switchShow;

            public ViewHolder(View itemView){
                super(itemView);
                lblNameFee = itemView.findViewById(R.id.lblNameFee);
                lblPriceFee = itemView.findViewById(R.id.lblPriceFee);
                switchShow = itemView.findViewById(R.id.switchShow);
            }

    }
}
