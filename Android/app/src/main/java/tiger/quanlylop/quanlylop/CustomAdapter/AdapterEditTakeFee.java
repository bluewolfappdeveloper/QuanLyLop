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

import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.DTO.TakeFeeDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterEditTakeFee extends BaseAdapter{

    Context context;
    int layout;
    ArrayList<TakeFeeDTO> listtakefee;
    ViewHolderTakeFee viewHolderFee;
    public int vt;
    public int Position;


    public AdapterEditTakeFee(Context context, int layout, ArrayList<TakeFeeDTO> listtakefee)
    {
        this.context = context;
        this.layout = layout;
        this.listtakefee = listtakefee;
    }

    @Override
    public int getCount() {
        if (listtakefee == null) return -1;
        return listtakefee.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (listtakefee == null) return -1;
        return listtakefee.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (listtakefee == null) return -1;
        return listtakefee.get(position).id;
    }

    public class ViewHolderTakeFee
    {
        TextView tvNameFeeTake;
        TextView tvDateTake;
        TextView tvTakeFee;
        ImageView imgdeletetakefee;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderFee = new ViewHolderTakeFee();

            view = layoutInflater.inflate(R.layout.layout_custom_edittakefee,parent,false);

            viewHolderFee.tvNameFeeTake = (TextView) view.findViewById(R.id.tvNameFeeTake);
            viewHolderFee.tvDateTake = (TextView) view.findViewById(R.id.tvDateTake);
            viewHolderFee.tvTakeFee = (TextView) view.findViewById(R.id.tvTakeFee);

            viewHolderFee.imgdeletetakefee = (ImageView) view.findViewById(R.id.imgdeletetakefee);

            if (layout == R.layout.layout_custom_edittakefee)
            {
                viewHolderFee.imgdeletetakefee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((ListView) parent).performItemClick(v, position, R.id.imgdeletetakefee);
                    }
                });

            }


            view.setTag(viewHolderFee);
        }
        else
           viewHolderFee = (ViewHolderTakeFee) view.getTag();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        viewHolderFee.tvNameFeeTake.setText(listtakefee.get(position).tenhp);
        viewHolderFee.tvDateTake.setText("Ngày đóng: " + df.format(listtakefee.get(position).datetime));
        viewHolderFee.tvTakeFee.setText("Học phí: "+ listtakefee.get(position).kp);
        TakeFeeDTO feeinfo = listtakefee.get(position);
        return view;

    }





}
