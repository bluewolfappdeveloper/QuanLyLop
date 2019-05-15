package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.ClassInfoFeeDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterClassInfoFee extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<ClassInfoFeeDTO> listclassinfofee;
    ViewHolderClassInfoFee viewHolderClassInfoFee;
    ArrayList<StudentDTO> studentlist;


    public AdapterClassInfoFee(Context context, int layout, ArrayList<ClassInfoFeeDTO> listclassinfofee) {
        this.context = context;
        this.layout = layout;
        this.listclassinfofee = listclassinfofee;
    }

    @Override
    public int getCount() {
        return listclassinfofee.size();
    }

    @Override
    public Object getItem(int position) {
        return listclassinfofee.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listclassinfofee.get(position).idclass;
    }


    public class ViewHolderClassInfoFee {
        TextView tvNameClassInfo;
        TextView tvTakeFeeClass;
        TextView tvUnTakeFeeClass;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = convertView;
        if (view == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderClassInfoFee = new ViewHolderClassInfoFee();

            view = layoutInflater.inflate(R.layout.layout_custom_classinfofee, parent, false);

            viewHolderClassInfoFee.tvNameClassInfo = (TextView) view.findViewById(R.id.tvNameClassInfo);
            viewHolderClassInfoFee.tvTakeFeeClass = (TextView) view.findViewById(R.id.tvTakeFeeClass);
            viewHolderClassInfoFee.tvUnTakeFeeClass = (TextView) view.findViewById(R.id.tvUnTakeFeeClass);


            viewHolderClassInfoFee.tvNameClassInfo.setText(listclassinfofee.get(position).nameclass);
            viewHolderClassInfoFee.tvTakeFeeClass.setText("Đã đóng: " + listclassinfofee.get(position).take);
            viewHolderClassInfoFee.tvUnTakeFeeClass.setText("Chưa đóng: " + listclassinfofee.get(position).untake);


            view.setTag(viewHolderClassInfoFee);
        } else
            viewHolderClassInfoFee = (ViewHolderClassInfoFee) view.getTag();


        return view;
    }




}
