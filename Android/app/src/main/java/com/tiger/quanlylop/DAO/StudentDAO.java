package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.tiger.quanlylop.DTO.StudentDTO;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static StudentDAO instance;

    public static StudentDAO getInstance() {
        if (instance==null) instance = new StudentDAO();
        return instance;
    }

    private static void setInstance(StudentDAO instance) {
        StudentDAO.instance = instance;
    }

    public StudentDAO(){

    };

    public List<StudentDTO> getAllStudent(Context context, long IDClass){
        List<StudentDTO> listStudent =new ArrayList<StudentDTO>();

        Cursor data = DataProvider.getInstance(context).excuteQuery("select * from  student where idclass = ? order by length(namestudent),  namestudent", new String[]{String.valueOf(IDClass)});
        data.moveToFirst();

        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            long idClass = data.getLong(data.getColumnIndex("IDCLASS"));
            String namestudent = data.getString(data.getColumnIndex("NAMESTUDENT"));
            String phonestudent = data.getString(data.getColumnIndex("PHONESTUDENT"));

            listStudent.add(new StudentDTO(id,idClass, namestudent,phonestudent));
            data.moveToNext();
        }

        data.close();
        return listStudent;
    }

    public boolean AddNewStudent(Context context, StudentDTO newStudent){

        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCLASS", newStudent.getIdClass());
        contentValues.put("NAMESTUDENT", newStudent.getNameStudent());
        contentValues.put("PHONESTUDENT", newStudent.getPhoneStudent());


        long k = DataProvider.getInstance(context).insertObject("Student",contentValues);

        return k>0;
    }

    public boolean DeleteStudent(Context context,long ID, long IDClass, boolean isCancel){
        boolean ok=false;
        if (isCancel)
            ok = DataProvider.getInstance(context).deleteObject("Student", "ID = ? and IDClass = ?", new String[]{""+ID,""+IDClass}) > 0;
        else
            ok = PayFeeDAO.getInstance().deleteStudentPayFee(context,ID);
        return ok;
    }

    public boolean DeleteStudentInClass(Context context, long IDClass){
        boolean ok=false;
        ok = DataProvider.getInstance(context).deleteObject("Student", "IDClass = ?", new String[]{""+IDClass}) > 0;
        ok = PayFeeDAO.getInstance().deleteClassPayFee(context,IDClass);
        return ok;
    }

    public boolean UpdateStudent(Context context,  StudentDTO updateStudent){
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCLASS", updateStudent.getIdClass());
        contentValues.put("NAMESTUDENT", updateStudent.getNameStudent());
        contentValues.put("PHONESTUDENT", updateStudent.getPhoneStudent());

        long k = DataProvider.getInstance(context).updateObject("Student", contentValues, "ID = ?", new String[]{""+updateStudent.getId()});
        return k>0;
    }
}
