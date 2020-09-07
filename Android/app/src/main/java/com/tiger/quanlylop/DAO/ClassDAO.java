package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.tiger.quanlylop.DTO.ClassDTO;
import com.tiger.quanlylop.DTO.UsersDTO;

import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    public static ClassDAO getInstance() {
        if (instance == null) instance = new ClassDAO();
        return instance;
    }

    private static void setInstance(ClassDAO instance) {
        ClassDAO.instance = instance;
    }

    private static ClassDAO instance;

    public ClassDAO(){

    }

    public List<ClassDTO> getAllClass(Context context){
        List<ClassDTO> listClass =new ArrayList<ClassDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from class order by length(nameclass), nameclass", null);
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            String nameClass = data.getString(data.getColumnIndex("NAMECLASS"));
            String feeClass = data.getString(data.getColumnIndex("FEECLASS"));
            listClass.add(new ClassDTO(id,nameClass,feeClass));
            data.moveToNext();
        }

        data.close();
        return listClass;

    }

    public List<String> getNameClass(Context context){
        List<String> listNameClass =new ArrayList<String>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from class order by length(nameclass), nameclass", null);
        data.moveToFirst();

        while (!data.isAfterLast()) {
            listNameClass.add( data.getString(data.getColumnIndex("NAMECLASS")) );
            data.moveToNext();
        }

        data.close();
        return listNameClass;
    }

    public boolean AddNewClass(Context context, ClassDTO newClass){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMECLASS",newClass.getNameClass());
        contentValues.put("FEECLASS", newClass.getFeeClass());

        long k = DataProvider.getInstance(context).insertObject("Class",contentValues);

        return k>0;
    }

    public boolean DeleteClass(Context context, long IDClass, boolean isCancel){
        boolean ok=false;
        if (isCancel)
            ok = DataProvider.getInstance(context).deleteObject("Class", "ID = ?", new String[]{""+IDClass}) > 0;
        else
            ok = StudentDAO.getInstance().DeleteStudentInClass(context,IDClass);
        return ok;
    }

    public boolean UpdateClass(Context context,  ClassDTO updateClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMECLASS",updateClass.getNameClass());
        contentValues.put("FEECLASS", updateClass.getFeeClass());
        long k = DataProvider.getInstance(context).updateObject("Class", contentValues, "ID = ?", new String[]{""+updateClass.getId()});
        return k>0;
    }
}
