package com.tiger.quanlylop.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;

import com.tiger.quanlylop.DAO.DataProvider;
import com.tiger.quanlylop.DAO.UsersDAO;
import com.tiger.quanlylop.Library.BackUpSQLite;
import com.tiger.quanlylop.Library.MD5Hash;
import com.tiger.quanlylop.R;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText editUserName;
    private AppCompatEditText editPassWord;
    private AppCompatButton btnLogin;

    private String[] listPermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)||!checkPermission(Manifest.permission.CALL_PHONE)) this.requestPermissions(listPermissions,0);

        AnhXa();

        if (!UsersDAO.getInstance().isExistUser(LoginActivity.this))
           UsersDAO.getInstance().insertUser(LoginActivity.this, "Võ Nhật Tân", "admin", MD5Hash.getMd5("admin"));

        editPassWord.setOnTouchListener(editPasswordTouch);

        btnLogin.setOnClickListener(btnLoginClick);
    }

    private void AnhXa() {
        editUserName = findViewById(R.id.editUserName);
        editPassWord = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    boolean checkPermission(String per){
        if(ContextCompat.checkSelfPermission(this,per)!= PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

    View.OnTouchListener editPasswordTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (editPassWord.getRight() - editPassWord.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    // your action here
                    if (editPassWord.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        editPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_key, 0, R.drawable.ic_remove_eye , 0);

                    }else{
                        editPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editPassWord.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_key, 0, R.drawable.ic_eye , 0);
                    }
                }
            }
            return false;
        }
    };

    View.OnClickListener btnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String username = editUserName.getText().toString();
            String password = editPassWord.getText().toString();

            UsersDAO.getInstance().Login(LoginActivity.this,username, MD5Hash.getMd5(password));

        }
    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        ;
    }
}