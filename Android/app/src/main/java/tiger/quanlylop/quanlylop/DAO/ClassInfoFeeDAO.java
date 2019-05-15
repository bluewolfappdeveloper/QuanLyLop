package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.ClassInfoFeeDTO;

public class ClassInfoFeeDAO {
    private Context context;
    private String Json;

    private static ClassInfoFeeDAO instance = new ClassInfoFeeDAO();

    public static ClassInfoFeeDAO getInstance() {
        if(instance == null) {
            instance = new ClassInfoFeeDAO();

        }
        return instance;
    }

    public ClassInfoFeeDAO()
    {

    }

    public void PostClass(final Context context, int IDFee, final ClassInfoFeeCallback classInfoFeeCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_classinfofee.php";

        String[] NameColoum = new String[] {"idfee"};
        String[] Param = new String[] {Integer.toString(IDFee)};

        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                classInfoFeeCallback.onSuccess(GetClassInfoFee(json));
            }
        });
    }


    public ArrayList<ClassInfoFeeDTO> GetClassInfoFee(String json)
    {
        ArrayList<ClassInfoFeeDTO> listClassInfoFee = new ArrayList<ClassInfoFeeDTO>();
        try {
            JSONArray classes = new JSONArray(json);

            if (classes.length() > 0)
            {
                for (int i=0; i <classes.length(); i++)
                {
                    JSONObject classinfo = classes.getJSONObject(i);
                    int idclass = classinfo.getInt("idclass");
                    String nameclass = classinfo.getString("nameclass");
                    int take = classinfo.getInt("take");
                    int untake= classinfo.getInt("untake");

                    ClassInfoFeeDTO classInfoFeeDTO = new ClassInfoFeeDTO(idclass,nameclass,take,untake);
                    listClassInfoFee.add(classInfoFeeDTO);
                }


                return listClassInfoFee;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public interface ClassInfoFeeCallback{
        public void onSuccess(ArrayList<ClassInfoFeeDTO> classInfoFeeDTOS);
    }
    

}
