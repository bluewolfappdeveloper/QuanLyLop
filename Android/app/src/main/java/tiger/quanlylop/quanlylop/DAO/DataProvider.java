package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class DataProvider {

    private String[] NameColoum;
    private String[] Param;

    private static DataProvider instance = new DataProvider();

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    public DataProvider()
    {

    }


    public void PostData(final Context context, String link, String[] nameColoum, String[] param, final ServerCallback callback)
    {
        //Toast.makeText(context, "Hello Hi", Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        NameColoum = nameColoum;
        Param = param;

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            callback.onSuccess(response);
                        }
                    },
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                         Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        if (NameColoum != null && Param != null )
                        {
                            HashMap<String, String> params = new HashMap<>();
                            for (int i=0; i< NameColoum.length; i++) {
                                params.put(NameColoum[i], Param[i]);
                            }
                            return params;
                        }

                        return super.getParams();
                    }
                };


        requestQueue.add(jsonArrayRequest);
    }

    public void CRUDData(final Context context, String link, String[] nameColoum, String[] param, final CRUDCallback crudCallback)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        NameColoum = nameColoum;
        Param = param;

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.indexOf("Success") != -1) crudCallback.onSuccess();
                         else crudCallback.onFail();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                if (NameColoum != null && Param != null )
                {
                    HashMap<String, String> params = new HashMap<>();
                    for (int i=0; i< NameColoum.length; i++) {
                        params.put(NameColoum[i], Param[i]);
                    }
                    return params;
                }

                return super.getParams();
            }
        } ;



        requestQueue.add(jsonArrayRequest);
    }

    public interface ServerCallback{
        public void onSuccess(String Json);
    }

    public interface CRUDCallback{
        public void onSuccess();
        public void onFail();
    }
}


