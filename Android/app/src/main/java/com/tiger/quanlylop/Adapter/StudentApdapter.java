package com.tiger.quanlylop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.tiger.quanlylop.DTO.StudentDTO;
import com.tiger.quanlylop.R;

import java.util.List;

public class StudentApdapter extends RecyclerView.Adapter<StudentApdapter.ViewHolder> {

    //Dữ liệu hiện thị là danh sách sinh viên
    private List<StudentDTO> listStudent;
    private Context context;
    private onClickListener onClickListener;

    public StudentApdapter(Context context, List<StudentDTO> listStudent, onClickListener onClickListener) {
        this.listStudent = listStudent;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public StudentApdapter(Context context, List<StudentDTO> listStudent) {
        this.listStudent = listStudent;
        this.context = context;
        this.onClickListener = null;
    }


    @NonNull
    @Override
    public StudentApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View studentView = inflater.inflate(R.layout.layout_item_student, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView, onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StudentDTO student = listStudent.get(position);

        holder.lblNameStudent.setText(student.getNameStudent());

        if (student.getPhoneStudent().isEmpty()){
            holder.lblPhoneStudent.setText("Phone: Không có");
            holder.imgPhone.setImageResource(R.drawable.ic_phone_not_exists);
        }
        else {
            holder.lblPhoneStudent.setText("Phone: "+student.getPhoneStudent());
            holder.imgPhone.setImageResource(R.drawable.ic_phone_exists);
        }

        holder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.getPhoneStudent().isEmpty() == false) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + student.getPhoneStudent()));
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listStudent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public AppCompatTextView lblNameStudent;
            public AppCompatTextView lblPhoneStudent;
            public AppCompatImageView imgPhone;

            public onClickListener clickListener;

            public ViewHolder(View itemView, onClickListener onClickListener){
                super(itemView);
                lblNameStudent = itemView.findViewById(R.id.lblNameStudent);
                lblPhoneStudent = itemView.findViewById(R.id.lblPhoneStudent);
                imgPhone = itemView.findViewById(R.id.imgPhone);

                clickListener = onClickListener;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (clickListener != null)
                          clickListener.onClick(getAdapterPosition());
                    }
                });
            }

    }

    public interface onClickListener{
        void onClick(int pos);
    }
}
