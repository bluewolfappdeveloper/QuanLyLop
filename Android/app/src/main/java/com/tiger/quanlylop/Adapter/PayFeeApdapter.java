package com.tiger.quanlylop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tiger.quanlylop.DAO.FeeDAO;
import com.tiger.quanlylop.DTO.PayFeeDTO;
import com.tiger.quanlylop.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class PayFeeApdapter extends RecyclerView.Adapter<PayFeeApdapter.ViewHolder> {

    //Dữ liệu hiện thị là danh sách sinh viên
    private List<PayFeeDTO> listPayFee;
    private Context context;

    public PayFeeApdapter(Context context, List<PayFeeDTO> listPayFee) {
        this.listPayFee = listPayFee;
        this.context = context;
    }

    @NonNull
    @Override
    public PayFeeApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View payFeeView = inflater.inflate(R.layout.layout_item_pay_fee, parent, false);

        ViewHolder viewHolder = new ViewHolder(payFeeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PayFeeDTO fee = listPayFee.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.lblNamePayFee.setText(fee.getNameFee());
        holder.lblDatePayFee.setText(formatter.format(fee.getDatePay()));

    }

    @Override
    public int getItemCount() {
        return listPayFee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public AppCompatTextView lblNamePayFee;
            public AppCompatTextView lblDatePayFee;

            public ViewHolder(View itemView){
                super(itemView);
                lblNamePayFee = itemView.findViewById(R.id.lblNamePayFee);
                lblDatePayFee = itemView.findViewById(R.id.lblDatePayFee);
            }

    }
}
