package com.tiger.quanlylop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.tiger.quanlylop.DTO.FeeDTO;
import com.tiger.quanlylop.DTO.UsersDTO;
import com.tiger.quanlylop.Activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class UsersDAO {


    public static UsersDAO getInstance() {
        if (instance == null) instance = new UsersDAO();
        return instance;
    }


    private UsersDAO(){

    }

    public void insertUser(Context context, String displayName, String userName, String passWord){
        ContentValues content = new ContentValues();
        content.put("displayName",displayName);
        content.put("username",userName);
        content.put("password",passWord);
        DataProvider.getInstance(context).insertObject("Users", content);
    }

    public boolean updateUser(Context context, UsersDTO user){
        ContentValues content = new ContentValues();
        content.put("displayName",user.getDisplayName());
        content.put("username",user.getUserName());
        content.put("password",user.getPassWord());
        return DataProvider.getInstance(context).updateObject("Users", content, "id = ?", new String[]{""+user.getID()}) > 0;
    }

    public void ShowAllUser(Context context){
        Cursor data = DataProvider.getInstance(context).excuteQuery("Select * from users",null);
        List<UsersDTO> users = new ArrayList<UsersDTO>();
        data.moveToFirst();

        Toast.makeText(context, ""+ data.getCount(), Toast.LENGTH_SHORT).show();
        while (!data.isAfterLast()) {
            long id = data.getLong(data.getColumnIndex("ID"));
            String displayName = data.getString(data.getColumnIndex("DISPLAYNAME"));
            users.add(new UsersDTO(id,displayName));
            data.moveToNext();
        }

        data.close();
    }

    public boolean isExistUser(Context context){
        Cursor data = DataProvider.getInstance(context).excuteQuery("Select * from users",null);
        boolean isExist = data.getCount() > 0;
        data.close();
        return isExist;
    }

    public void Login(Context context, String username, String password){
        Cursor data = DataProvider.getInstance(context).excuteQuery("Select * from users where username = ? and password = ?",new String[]{username, password});

        data.moveToFirst();
        if (data.getCount() == 1){
            long id = data.getLong(data.getColumnIndex("ID"));
            String displayName = data.getString(data.getColumnIndex("DISPLAYNAME"));
            Toast.makeText(context, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("id",id);//Optional parameters
            myIntent.putExtra("displayName", displayName);
            context.startActivity(myIntent);

        }else{
            Toast.makeText(context, "Vui lòng kiểm tra tài khoản !", Toast.LENGTH_LONG).show();
        }
    }

    private static UsersDAO instance;

}
