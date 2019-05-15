package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.StudentHaveFeeDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterStudentHaveFee extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<StudentHaveFeeDTO> listhaveFee;
    ViewHolderFee viewHolderFee;
    public int vt;
    public int Position;


    public AdapterStudentHaveFee(Context context, int layout, ArrayList<StudentHaveFeeDTO> listhaveFee)
    {
        this.context = context;
        this.layout = layout;
        this.listhaveFee = listhaveFee;
    }

    @Override
    public int getCount() {
        if (listhaveFee == null) return -1;
        return listhaveFee.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (listhaveFee == null) return -1;
        return listhaveFee.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listhaveFee == null) return -1;
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

        StudentHaveFeeDTO studentInfo = listhaveFee.get(position);

        viewHolderFee.tvNameStudentTake.setText(studentInfo.namestudent);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        viewHolderFee.tvPhoneStudentTake.setText("Ngày đóng: " + df.format(listhaveFee.get(position).datetime));

        return view;

    }





}
