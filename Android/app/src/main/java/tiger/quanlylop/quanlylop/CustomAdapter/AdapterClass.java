package tiger.quanlylop.quanlylop.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
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

import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.R;

public class AdapterClass extends BaseAdapter
{

    Context context;
    int layout;
    ArrayList<ClassDTO> listclass;
    ViewHolderClass viewHolderClass;
    ArrayList<StudentDTO> studentlist;


    public AdapterClass(Context context, int layout, ArrayList<ClassDTO> listclass)
    {
        this.context = context;
        this.layout = layout;
        this.listclass = listclass;
    }

    @Override
    public int getCount() {
        return listclass.size();
    }

    @Override
    public Object getItem(int position) {
        return listclass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listclass.get(position).id;
    }


    public class ViewHolderClass
    {
        ImageView imgclassroom;
        TextView txtnameclass;
    }

    @Override
    public View getDropDownView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderClass = new ViewHolderClass();

            view = layoutInflater.inflate(R.layout.layout_custom_class,parent,false);

            viewHolderClass.imgclassroom = (ImageView) view.findViewById(R.id.imClassRoom);
            viewHolderClass.txtnameclass = (TextView) view.findViewById(R.id.txtNameClass);


            //viewHolderClass.imgclassroom.setVisibility(View.GONE);

            if (layout ==  R.layout.layout_custom_class)
            {
                viewHolderClass.imgclassroom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GridView) parent).performItemClick(v, position, 0);
                    }
                });

                viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GridView) parent).performItemClick(v, position, 0);

                    }
                });
            }

            if (layout == android.R.layout.simple_spinner_item)
            {
                viewHolderClass.imgclassroom.setVisibility(View.GONE);

                viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Spinner) parent).performItemClick(v, position, 0);

                    }
                });
            }


            viewHolderClass.imgclassroom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);
                }
            });

            viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);

                }
            });

            //view.setOnClickListener(this);

            view.setTag(viewHolderClass);
        }
        else
            viewHolderClass = (ViewHolderClass) view.getTag();

        ClassDTO classinfo = listclass.get(position);
        viewHolderClass.txtnameclass.setText(classinfo.NameClass);

        viewHolderClass.txtnameclass.setTag(position);
        viewHolderClass.imgclassroom.setTag(position);
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {

        View view = convertView;
        if (view == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderClass = new ViewHolderClass();

            view = layoutInflater.inflate(R.layout.layout_custom_class,parent,false);

            viewHolderClass.imgclassroom = (ImageView) view.findViewById(R.id.imClassRoom);
            viewHolderClass.txtnameclass = (TextView) view.findViewById(R.id.txtNameClass);


            //viewHolderClass.imgclassroom.setVisibility(View.GONE);

            if (layout ==  R.layout.layout_custom_class)
            {
                viewHolderClass.imgclassroom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((GridView) parent).performItemClick(v, position, 0);
                    }
                });

                viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((GridView) parent).performItemClick(v, position, 0);

                    }
                });
            }

            if (layout == android.R.layout.simple_spinner_item)
            {
                viewHolderClass.imgclassroom.setVisibility(View.GONE);

                viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Spinner) parent).performItemClick(v, position, 0);

                    }
                });
            }


            if (listclass.get(position).CountStudent <= 0)
            {
                viewHolderClass.txtnameclass.setTextColor(ContextCompat.getColor(context, R.color.grey));
                viewHolderClass.imgclassroom.setImageResource(R.drawable.class_disable);
            }
            viewHolderClass.imgclassroom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);
                }
            });

            viewHolderClass.txtnameclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GridView) parent).performItemClick(v, position, 0);

                }
            });

            //view.setOnClickListener(this);

             view.setTag(viewHolderClass);
        }
        else
            viewHolderClass = (ViewHolderClass) view.getTag();

        ClassDTO classinfo = listclass.get(position);
        viewHolderClass.txtnameclass.setText(classinfo.NameClass);

        viewHolderClass.txtnameclass.setTag(position);
        viewHolderClass.imgclassroom.setTag(position);

        return view;
    }



}
