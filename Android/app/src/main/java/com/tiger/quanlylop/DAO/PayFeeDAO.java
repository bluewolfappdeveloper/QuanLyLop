package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.tiger.quanlylop.DTO.FeeMainDTO;
import com.tiger.quanlylop.DTO.PayFeeDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PayFeeDAO {

    private static PayFeeDAO instance;

    public static PayFeeDAO getInstance() {
        if (instance==null) instance = new PayFeeDAO();
        return instance;
    }

    private static void setInstance(PayFeeDAO instance) {
        PayFeeDAO.instance = instance;
    }

    public PayFeeDAO(){
        
    };

    public boolean deleteClassPayFee(Context context, long IDClass){
        return DataProvider.getInstance(context).deleteObject("payfee", "idclass = ?", new String[]{""+IDClass}) > 0;
    }

    public boolean deleteStudentPayFee(Context context, long IDStudent){
        return DataProvider.getInstance(context).deleteObject("payfee", "idclass = ?", new String[]{""+IDStudent}) > 0;
    }

    public boolean deleteFeePayFee(Context context, long IDfee){
        return DataProvider.getInstance(context).deleteObject("payfee", "idfee = ?", new String[]{""+IDfee}) > 0;
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
