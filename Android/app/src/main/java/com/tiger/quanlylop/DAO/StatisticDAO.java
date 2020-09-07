package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tiger.quanlylop.DTO.FeeDTO;
import com.tiger.quanlylop.DTO.StudentDTO;

import java.util.ArrayList;
import java.util.List;

public class StatisticDAO {

    private static StatisticDAO instance;

    public static StatisticDAO getInstance() {
        if (instance==null) instance = new StatisticDAO();
        return instance;
    }

    private static void setInstance(StatisticDAO instance) {
        StatisticDAO.instance = instance;
    }

    public StatisticDAO(){

    };

    public List<StudentDTO> getStudentNoFee(Context context, long IDClass, long IDFee, Boolean status){
        List<StudentDTO> listStudent =new ArrayList<StudentDTO>();
        Cursor data;
        if (!status)
            data = DataProvider.getInstance(context).excuteQuery("select * from student where idclass = " + IDClass + " and id not in (select idstudent from payfee where idfee = " + IDFee + " and idclass = " + IDClass + ")", null);
        else
            data = DataProvider.getInstance(context).excuteQuery("select * from student where idclass = " + IDClass + " and id in (select idstudent from payfee where idfee = " + IDFee + " and idclass = " + IDClass + ")", null);

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

}
