package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Adapter;
import android.widget.GridView;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tiger.quanlylop.quanlylop.CustomAdapter.AdapterClass;
import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.MainActivitiy;
import tiger.quanlylop.quanlylop.R;

public class ClassDAO {

    private Context context;
    private String Json;

    private static ClassDAO instance = new ClassDAO();

    public static ClassDAO getInstance() {
        if(instance == null) {
            instance = new ClassDAO();

        }
        return instance;
    }

    public ClassDAO()
    {

    }

    public void PostClass(final Context context, final ClassCallback classCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Class/db_class.php";
        DataProvider.getInstance().PostData(context, link, null, null, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                classCallback.onSuccess(GetClass(json));
            }
        });
    }


    public ArrayList<ClassDTO> GetClass(String json)
    {
        ArrayList<ClassDTO> listClass = new ArrayList<ClassDTO>();
        try {
            JSONArray classes = new JSONArray(json);

            if (classes.length() > 0)
            {
                for (int i=0; i <classes.length(); i++)
                {
                    JSONObject classinfo = classes.getJSONObject(i);
                    int id = classinfo.getInt("ID");
                    String NameClass = classinfo.getString("NameClass");
                    String FeeClass = classinfo.getString("FeeClass");
                    int countstudent= classinfo.getInt("CountStudent");

                    ClassDTO class_ = new ClassDTO(id,NameClass,FeeClass,countstudent);
                    listClass.add(class_);
                }


                return listClass;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void AddClass(final Context context, ClassDTO classDTO, final ClassCRUDCallback classCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Class/db_addclass.php";

        String[] NameColoum = new String[] {"nameclass", "feeclass"};
        String[] Param = new String[] {classDTO.NameClass, classDTO.FeeClass};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                classCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                classCRUDCallback.onFail();
            }
        });
    }


    public void DeleteClass(final Context context, int IDClass,  final ClassCRUDCallback classCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Class/db_deleteclass.php";

        String[] NameColoum = new String[] {"id"};
        String[] Param = new String[] { Integer.toString(IDClass)};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                classCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                classCRUDCallback.onFail();
            }
        });
    }

    public void UpdateClass(final Context context, ClassDTO classDTO, final ClassCRUDCallback classCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Class/db_updateclass.php";

        String[] NameColoum = new String[] {"id", "nameclass", "feeclass"};
        String[] Param = new String[] {Integer.toString(classDTO.id),classDTO.NameClass, classDTO.FeeClass};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                classCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                classCRUDCallback.onFail();
            }
        });
    }

    public interface ClassCallback{
        public void onSuccess(ArrayList<ClassDTO> classDTOArrayList);
    }

    public interface ClassCRUDCallback{
        public void onSuccess();
        public void onFail();
    }
}

