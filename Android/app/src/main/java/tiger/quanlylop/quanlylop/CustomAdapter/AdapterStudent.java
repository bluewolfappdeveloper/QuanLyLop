package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterStudent extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<StudentDTO> liststudent;
    ViewHolderStudent viewHolderStudent;
    public int vt;
    public int Position;


    public AdapterStudent(Context context, int layout, ArrayList<StudentDTO> liststudent)
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
        TextView tvNameStudentCustom;
        TextView tvPhoneStudentCustom;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderStudent = new ViewHolderStudent();

            view = layoutInflater.inflate(R.layout.layout_custom_student,parent,false);

            viewHolderStudent.tvNameStudentCustom = (TextView) view.findViewById(R.id.tvNameStudentCustom);
            viewHolderStudent.tvPhoneStudentCustom = (TextView) view.findViewById(R.id.tvPhoneStudentCustom);

            view.setTag(viewHolderStudent);
        }
        else
            viewHolderStudent = (ViewHolderStudent) view.getTag();

        StudentDTO studentinfo = liststudent.get(position);

        viewHolderStudent.tvNameStudentCustom.setText(studentinfo.NameStudent);
        viewHolderStudent.tvPhoneStudentCustom.setText("SĐT: "+studentinfo.PhoneStudent);

        return view;

    }



}
