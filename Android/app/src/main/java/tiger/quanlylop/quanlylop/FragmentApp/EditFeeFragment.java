package tiger.quanlylop.quanlylop.FragmentApp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.CRUDActivity.AddFee;
import tiger.quanlylop.quanlylop.CRUDActivity.UpdateFee;
import tiger.quanlylop.quanlylop.CustomAdapter.AdapterEditFee;
import tiger.quanlylop.quanlylop.DAO.FeeDAO;
import tiger.quanlylop.quanlylop.DTO.FeeDTO;
import tiger.quanlylop.quanlylop.R;

public class EditFeeFragment extends Fragment {

    public static int REQUEST_CODE_ADD_FEE = 112;
    public static int REQUEST_CODE_UPDATE_FEE = 113;
    public static String NameFee;
    public static int IDFee;

    ArrayList<FeeDTO> feelist;
    ListView listView;
    AdapterEditFee adapterEditFee;
    Integer vt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fee, container, false);
        setHasOptionsMenu(true);


        listView = view.findViewById(R.id.lstfee);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewID = view.getId();

                if (viewID == R.id.imgedit) {

                    Intent intent = new Intent(getActivity(), UpdateFee.class);
                    intent.putExtra("ID", feelist.get(position).id);
                    intent.putExtra("NameFee",feelist.get(position).NameFee);
                    intent.putExtra("PriceFee",feelist.get(position).PriceFee);

                    startActivityForResult(intent, REQUEST_CODE_UPDATE_FEE);

                } else if (viewID == R.id.imgdelete) {
                    IDFee = feelist.get(position).id;
                    showAlertDialogDelFee(position);

                }
            }
        });


        LoadFee();


        return view;
    }

    public  void LoadFee()
    {
        FeeDAO.getInstance().PostFee(getActivity(), new FeeDAO.FeeCallback() {
            @Override
            public void onSuccess(ArrayList<FeeDTO> FeeDTOArrayList) {

                feelist = FeeDTOArrayList;

                if (feelist != null)
                {
                    adapterEditFee = new AdapterEditFee(getActivity(), R.layout.layout_custom_editclass , feelist);
                    listView.setAdapter(adapterEditFee);
                    adapterEditFee.notifyDataSetInvalidated();
                }
                else listView.setAdapter(null);


            }
        });


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1, R.id.itaddfee,1,R.string.themhocphi);
        item.setIcon(R.drawable.addfee);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(getActivity(), AddFee.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_FEE);

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_FEE || requestCode == REQUEST_CODE_UPDATE_FEE)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadFee();
            }
        }

        if (requestCode == REQUEST_CODE_UPDATE_FEE)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                LoadFee();
            }
        }
    }



    public void showAlertDialogDelFee(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa khoản phí");
        builder.setMessage("Bạn có muốn xóa "+ feelist.get(pos).NameFee +"  không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            FeeDAO.getInstance().DeleteFee(getActivity(), IDFee, new FeeDAO.feeCRUDCallback() {
                @Override
                public void onSuccess() {
                    LoadFee();
                }

                @Override
                public void onFail() {
                }
            });

                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}


