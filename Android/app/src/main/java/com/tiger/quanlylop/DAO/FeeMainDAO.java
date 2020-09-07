package com.tiger.quanlylop.DAO;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tiger.quanlylop.DTO.FeeMainDTO;
import com.tiger.quanlylop.DTO.PayFeeDTO;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FeeMainDAO {

    private static FeeMainDAO instance;

    public static FeeMainDAO getInstance() {
        if (instance==null) instance = new FeeMainDAO();
        return instance;
    }

    private static void setInstance(FeeMainDAO instance) {
        FeeMainDAO.instance = instance;
    }

    public FeeMainDAO(){
        
    };

    public List<FeeMainDTO> getAllFeeEnable(Context context, long IDClass, long IDStudent){
        List<FeeMainDTO> listFee =new ArrayList<FeeMainDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from fee where show>0 and  id not in (select idfee from payfee where idclass = ? and idstudent = ?)", new String[]{""+IDClass,""+IDStudent});
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            String nameFee = data.getString(data.getColumnIndex("NAMEFEE"));
            String priceFee = data.getString(data.getColumnIndex("PRICEFEE"));

            listFee.add(new FeeMainDTO(id,nameFee,priceFee,false));
            data.moveToNext();
        }

        data.close();
        return listFee;
    }

    public List<PayFeeDTO> getAllFeeStudent(Context context, long IDClass, long IDStudent){
        List<PayFeeDTO> listFeeStudent =new ArrayList<PayFeeDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select pf.*, f.namefee from payfee as pf, fee as f where idclass=? and idstudent = ? and pf.idfee = f.id and f.show>0 order by pf.date DESC, length(f.namefee), f.namefee",new String[]{""+IDClass,""+IDStudent});
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            long idClass = data.getLong(data.getColumnIndex("IDCLASS"));
            long idStudent = data.getLong(data.getColumnIndex("IDSTUDENT"));
            long idFee = data.getLong(data.getColumnIndex("IDFEE"));
            String dateNo= data.getString(data.getColumnIndex("DATE"));
            String nameFee= data.getString(data.getColumnIndex("NAMEFEE"));

            Date date = Date.valueOf(dateNo);
            listFeeStudent.add(new PayFeeDTO(id,idClass,idStudent,idFee,date,nameFee));
            data.moveToNext();
        }

        data.close();
        return listFeeStudent;
    }

    public void addNewPayFee(final Context context, final long IDClass, final long IDStudent, String nameStudent, List<FeeMainDTO> listFee){
        int onSuccess =0;
        int onFail =0;

        for (final FeeMainDTO fee:listFee) {
            if (fee.isSelected()){
                ContentValues contentValues = new ContentValues();
                contentValues.put("IDCLASS", IDClass);
                contentValues.put("IDSTUDENT", IDStudent);
                contentValues.put("IDFEE", fee.getId());

                java.util.Date newDate = new java.util.Date();
                Date date = new Date(newDate.getTime());

                contentValues.put("DATE", date.toString());

                if (DataProvider.getInstance(context).insertObject("PayFee", contentValues) > 0){
                    onSuccess++;
                }else onFail++;
            }
        }
        Toast.makeText(context, "Thành công: "+onSuccess+"\nThất bại: "+onFail, Toast.LENGTH_SHORT).show();
    }

    public boolean undoPayFee(Context context, PayFeeDTO payFee){
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCLASS", payFee.getIdClass());
        contentValues.put("IDSTUDENT", payFee.getIdStudent());
        contentValues.put("IDFEE", payFee.getIdFee());
        contentValues.put("DATE", payFee.getDatePay().toString());

        return DataProvider.getInstance(context).insertObject("PayFee", contentValues) > 0;
    }

    public boolean deletePayFee(Context context, long ID){
        return DataProvider.getInstance(context).deleteObject("payfee", "id = ?", new String[]{""+ID}) > 0;
    }

    public boolean updatePayFee(Context context, long ID, long IDFee){
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDFEE", IDFee);
        return DataProvider.getInstance(context).updateObject("payfee",contentValues, "id= ?", new String[]{""+ID}) > 0;
    }
}
