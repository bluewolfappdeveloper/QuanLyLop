package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.LoginDTO;
import tiger.quanlylop.quanlylop.MainActivitiy;


public class LoginDAO
{
    private Context context;
    String UserResult;
    private LoginDTO user;


    private static LoginDAO instance = new LoginDAO();

    public static LoginDAO getInstance() {
        if(instance == null) {
            instance = new LoginDAO();
        }
        return instance;
    }

    public LoginDAO()
    {

    }

    public LoginDTO GetUser(String json)
    {
        try {
            JSONArray users = new JSONArray(json);

            if (users.length() == 1)
            {
                JSONObject userinfo = users.getJSONObject(0);
                int id = userinfo.getInt("id");
                String name = userinfo.getString("name");
                String username = userinfo.getString("username");
                String password= userinfo.getString("password");
                LoginDTO login = new LoginDTO(id,name,username,password);
                return login;
            }

            return null;

        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            return null;
        }
    }


    public void DangNhap(Context context, String username, String password)
    {
        this.context = context;
        String link= Constant.IPLink + Constant.Port + "/db_login.php";



        String[] NameColoum = new String[] {"username", "password"};
        String[] Param = new String[] {username, password};
        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String Json) {
                CheckLogin(Json);
            }
        });


    }

    public void CheckLogin(String Json)
    {
        user = GetUser(Json);

        //Toast.makeText(context, Json, Toast.LENGTH_LONG).show();
        if (user != null  )
        {
            Intent intent = new Intent(context,MainActivitiy.class);
            intent.putExtra("loginid", user.id);
            intent.putExtra("loginname", user.name);
            intent.putExtra("loginusername", user.username);
            intent.putExtra("loginpassword", user.password);
            context.startActivity(intent);
        }

        else
        {
            CharSequence text = "Vui lòng kiểm tra lại tài khoản";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}



