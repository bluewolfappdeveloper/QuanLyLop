package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterEditStudent extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<StudentDTO> liststudent;
    ViewHolderStudent viewHolderStudent;
    public int vt;
    public int Position;


    public AdapterEditStudent(Context context, int layout, ArrayList<StudentDTO> liststudent)
    {
        this.context = context;
        this.layout = layout;
        this.liststudent = liststudent;
    }

    @Override
    public int getCount() {
        if (liststudent == null) return -1;
        return liststudent.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (liststudent == null) return -1;
        return liststudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (liststudent == null) return -1;
        return liststudent.get(position).id;
    }

    public class ViewHolderStudent
    {
        TextView tvNameStudent;
        TextView tvPhoneStudent;
        ImageView imgedit;
        ImageView imgdelete;
        
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderStudent = new ViewHolderStudent();

            view = layoutInflater.inflate(R.layout.layout_custom_editstudent,parent,false);

            viewHolderStudent.tvNameStudent = (TextView) view.findViewById(R.id.tvNameStudent);
            viewHolderStudent.tvPhoneStudent = (TextView) view.findViewById(R.id.tvPhoneStudent);
            viewHolderStudent.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            viewHolderStudent.imgedit = (ImageView) view.findViewById(R.id.imgedit);


            if (layout == R.layout.layout_custom_editstudent)
            {
                viewHolderStudent.imgdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((ListView) parent).performItemClick(v, position, R.id.imgdelete);
                    }
                });

                viewHolderStudent.imgedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ListView) parent).performItemClick(v,position, R.id.imgedit);
                    }
                });;
            }

            view.setTag(viewHolderStudent);
        }
        else
            viewHolderStudent = (ViewHolderStudent) view.getTag();

        StudentDTO studentinfo = liststudent.get(position);

        viewHolderStudent.tvNameStudent.setText(studentinfo.NameStudent);
        viewHolderStudent.tvPhoneStudent.setText("SĐT: "+studentinfo.PhoneStudent);

        viewHolderStudent.imgdelete.setTag(position);
        viewHolderStudent.imgedit.setTag(position);


        return view;

    }





}
