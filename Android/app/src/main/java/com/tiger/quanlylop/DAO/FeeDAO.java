package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.tiger.quanlylop.DTO.FeeDTO;

import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    private static FeeDAO instance;
    
    public static FeeDAO getInstance() {
        if (instance==null) instance = new FeeDAO();
        return instance;
    }

    private static void setInstance(FeeDAO instance) {
        FeeDAO.instance = instance;
    }

    public FeeDAO(){
        
    };

    public List<FeeDTO> getAllFee(Context context){
        List<FeeDTO> listFee =new ArrayList<FeeDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from fee order by length(namefee), namefee, show DESC", null);
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            String nameFee = data.getString(data.getColumnIndex("NAMEFEE"));
            String priceFee = data.getString(data.getColumnIndex("PRICEFEE"));
            boolean show  = data.getInt(data.getColumnIndex("SHOW")) > 0;

            listFee.add(new FeeDTO(id,nameFee,priceFee,show));
            data.moveToNext();
        }

        data.close();
        return listFee;

    }

    public List<FeeDTO> getShowFee(Context context){
        List<FeeDTO> listFee =new ArrayList<FeeDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from fee where show > 0 order by length(namefee), namefee, show DESC", null);
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            String nameFee = data.getString(data.getColumnIndex("NAMEFEE"));
            String priceFee = data.getString(data.getColumnIndex("PRICEFEE"));
            boolean show  = data.getInt(data.getColumnIndex("SHOW")) > 0;

            listFee.add(new FeeDTO(id,nameFee,priceFee,show));
            data.moveToNext();
        }

        data.close();
        return listFee;

    }

    public boolean AddNewFee(Context context, FeeDTO newFee){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMEFEE", newFee.getNameFee());
        contentValues.put("PRICEFEE", newFee.getPriceFee());
        contentValues.put("SHOW", newFee.isShow());


        long k = DataProvider.getInstance(context).insertObject("Fee",contentValues);

        return k>0;
    }

    public boolean DeleteFee(Context context, long ID, boolean isCancel){
        boolean ok=false;


        if (isCancel)
            ok = DataProvider.getInstance(context).deleteObject("Fee", "ID = ?", new String[]{""+ID}) > 0;
        else
            ok = PayFeeDAO.getInstance().deleteFeePayFee(context, ID);


        return ok;
    }

    public boolean UpdateFee(Context context,  FeeDTO updateFee){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMEFEE", updateFee.getNameFee());
        contentValues.put("PRICEFEE", updateFee.getPriceFee());
        contentValues.put("SHOW", updateFee.isShow());

        long k = DataProvider.getInstance(context).updateObject("Fee", contentValues, "ID = ?", new String[]{""+updateFee.getId()});
        return k>0;
    }

    public boolean UpdateShowFee(Context context, long id, boolean isShow){
        ContentValues contentValues = new ContentValues();
        contentValues.put("SHOW", isShow);
        long k = DataProvider.getInstance(context).updateObject("Fee", contentValues, "ID = ?", new String[]{""+id});

        return k>0;
    }



}
