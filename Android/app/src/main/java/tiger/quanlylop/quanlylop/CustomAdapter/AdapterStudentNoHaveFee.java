package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.StudentNoHaveFeeDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterStudentNoHaveFee extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<StudentNoHaveFeeDTO> listnohaveFee;
    ViewHolderFee viewHolderFee;
    public int vt;
    public int Position;


    public AdapterStudentNoHaveFee(Context context, int layout, ArrayList<StudentNoHaveFeeDTO> listnohaveFee)
    {
        this.context = context;
        this.layout = layout;
        this.listnohaveFee = listnohaveFee;
    }

    @Override
    public int getCount() {
        if (listnohaveFee == null) return -1;
        return listnohaveFee.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (listnohaveFee == null) return -1;
        return listnohaveFee.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listnohaveFee == null) return -1;
        return 1;
    }

    public class ViewHolderFee
    {
        TextView tvNameStudentTake;
        TextView tvPhoneStudentTake;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderFee = new ViewHolderFee();

            view = layoutInflater.inflate(R.layout.layout_custom_studentinfofee,parent,false);

            viewHolderFee.tvNameStudentTake = (TextView) view.findViewById(R.id.tvNameStudentTake);
            viewHolderFee.tvPhoneStudentTake = (TextView) view.findViewById(R.id.tvPhoneStudentTake);



            view.setTag(viewHolderFee);
        }
        else
           viewHolderFee = (ViewHolderFee) view.getTag();

        StudentNoHaveFeeDTO studentInfo = listnohaveFee.get(position);

        viewHolderFee.tvNameStudentTake.setText(studentInfo.namestudent);

        viewHolderFee.tvPhoneStudentTake.setText("SĐT: "+ studentInfo.phonestudent);

        return view;

    }





}
