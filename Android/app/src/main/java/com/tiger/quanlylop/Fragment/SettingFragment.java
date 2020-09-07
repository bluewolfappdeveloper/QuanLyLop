package com.tiger.quanlylop.Fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tiger.quanlylop.DAO.ClassDAO;
import com.tiger.quanlylop.DAO.DataProvider;
import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.Library.BackUpSQLite;
import com.tiger.quanlylop.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        final List<String> settingText = new ArrayList<String>();
        settingText.add("Xóa toàn bộ lớp học");
        settingText.add("Xóa toàn bộ học sinh");
        settingText.add("Xóa toàn bộ học phí");
        settingText.add("Xóa toàn bộ học sinh đã đóng phí");
        settingText.add("Sao lưu dữ liệu");


        ArrayAdapter<String> settingApdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, settingText);

        ListView listView = view.findViewById(R.id.lvSetting);

        listView.setAdapter(settingApdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String message = "Bạn chắc chắn muốn " + settingText.get(pos).toLowerCase()+" ?";
                switch (pos) {
                    case 0:
                        showDialog(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DataProvider.getInstance(getContext()).deleteFormatTable("Class");
                                DataProvider.getInstance(getContext()).deleteFormatTable("Student");
                                DataProvider.getInstance(getContext()).deleteFormatTable("PayFee");

                                dialogInterface.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        break;
                    case 1:
                        showDialog(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataProvider.getInstance(getContext()).deleteFormatTable("Student");
                                DataProvider.getInstance(getContext()).deleteFormatTable("PayFee");

                                dialogInterface.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        break;
                    case 2:
                        showDialog(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DataProvider.getInstance(getContext()).deleteFormatTable("Fee");
                                DataProvider.getInstance(getContext()).deleteFormatTable("PayFee");

                                dialogInterface.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        break;
                    case 3:
                        showDialog(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataProvider.getInstance(getContext()).deleteFormatTable("PayFee");

                                dialogInterface.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        break;
                    case 4:
                        if (BackUpSQLite.backupDatabase(getContext(), DataProvider.getInstance(getContext()).getDatabaseName())){
                            Toast.makeText(getContext(), "Sao lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        };
                        break;
                }



            }
        });

        return view;
    }

    public void showDialog(String message, DialogInterface.OnClickListener onPositiveClick, DialogInterface.OnClickListener onNegativeClick){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Thông báo");
        builder.setMessage(message);
        builder.setPositiveButton("YES",onPositiveClick);
        builder.setNegativeButton("NO",onNegativeClick);

        AlertDialog alert = builder.create();
        alert.show();
    }
}