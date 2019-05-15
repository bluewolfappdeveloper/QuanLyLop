package tiger.quanlylop.quanlylop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DAO.Constant;
import tiger.quanlylop.quanlylop.DAO.DataProvider;
import tiger.quanlylop.quanlylop.DAO.LoginDAO;


public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button exit,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();

    }

    private void AnhXa()
    {
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        exit = (Button) findViewById(R.id.btnExit);
        login = (Button) findViewById(R.id.btnLogin);
    }


    public void btnExit_OnClick(View view)
    {
        finish();
    }

    public void btnLogin_OnClick(View view)
    {
        LoginDAO.getInstance().DangNhap(this, user.getText().toString(), pass.getText().toString());


        //LoginDAO login = new LoginDAO(getApplicationContext());
        //login.CheckLogin();


        /*String link= Constant.IPLink + Constant.Port + "/QuanLyLop/db_login.php";
        String[] NameColoum = new String[] {"username", "password"};
        String[] Param = new String[] {user.getText().toString(), pass.getText().toString()};
        new DataProvider(this, link, NameColoum, Param).execute();*/
    }


}
