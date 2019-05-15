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

import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterEditFee extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<FeeDTO> listfee;
    ViewHolderFee viewHolderFee;
    public int vt;
    public int Position;


    public AdapterEditFee(Context context, int layout, ArrayList<FeeDTO> listfee)
    {
        this.context = context;
        this.layout = layout;
        this.listfee = listfee;
    }

    @Override
    public int getCount() {
        if (listfee == null) return -1;
        return listfee.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (listfee == null) return -1;
        return listfee.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listfee == null) return -1;
        return listfee.get(position).id;
    }

    public class ViewHolderFee
    {
        TextView tvNameFee;
        TextView tvPriceFee;
        ImageView imgedit;
        ImageView imgdelete;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderFee = new ViewHolderFee();

            view = layoutInflater.inflate(R.layout.layout_custom_editfee,parent,false);

            viewHolderFee.tvNameFee = (TextView) view.findViewById(R.id.tvNameFee);
            viewHolderFee.tvPriceFee = (TextView) view.findViewById(R.id.tvPriceFee);
            viewHolderFee.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            viewHolderFee.imgedit = (ImageView) view.findViewById(R.id.imgedit);

            if (layout == R.layout.layout_custom_editclass)
            {
                viewHolderFee.imgdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((ListView) parent).performItemClick(v, position, R.id.imgdelete);
                    }
                });

                viewHolderFee.imgedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ListView) parent).performItemClick(v,position, R.id.imgedit);
                    }
                });;
            }


            view.setTag(viewHolderFee);
        }
        else
           viewHolderFee = (ViewHolderFee) view.getTag();

        FeeDTO feeinfo = listfee.get(position);

        viewHolderFee.tvNameFee.setText(feeinfo.NameFee);
        viewHolderFee.tvPriceFee.setText("Học phí: "+feeinfo.PriceFee);

        return view;

    }





}
