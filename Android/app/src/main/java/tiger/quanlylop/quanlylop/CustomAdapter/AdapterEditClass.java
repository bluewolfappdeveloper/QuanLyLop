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

import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterEditClass extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<ClassDTO> listclass;
    ViewHolderStudent viewHolderStudent;
    public int vt;
    public int Position;


    public AdapterEditClass(Context context, int layout, ArrayList<ClassDTO> listclass)
    {
        this.context = context;
        this.layout = layout;
        this.listclass = listclass;
    }

    @Override
    public int getCount() {
        if (listclass == null) return -1;
        return listclass.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (listclass == null) return -1;
        return listclass.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listclass == null) return -1;
        return listclass.get(position).id;
    }

    public class ViewHolderStudent
    {
        TextView tvNameClass;
        TextView tvFeeClass;
        TextView tvCountClass;
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

            view = layoutInflater.inflate(R.layout.layout_custom_editclass,parent,false);

            viewHolderStudent.tvNameClass = (TextView) view.findViewById(R.id.tvNameClass);
            viewHolderStudent.tvFeeClass = (TextView) view.findViewById(R.id.tvFeeClass);
            viewHolderStudent.tvCountClass = (TextView) view.findViewById(R.id.tvCountClass);
            viewHolderStudent.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            viewHolderStudent.imgedit = (ImageView) view.findViewById(R.id.imgedit);


            viewHolderStudent.tvNameClass.setText(listclass.get(position).NameClass);
            viewHolderStudent.tvFeeClass.setText("Học Phí: "+listclass.get(position).FeeClass);
            viewHolderStudent.tvCountClass.setText("Số Lượng: "+listclass.get(position).CountStudent);

            if (layout == R.layout.layout_custom_editclass)
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

        ClassDTO studentinfo = listclass.get(position);

        return view;

    }





}
