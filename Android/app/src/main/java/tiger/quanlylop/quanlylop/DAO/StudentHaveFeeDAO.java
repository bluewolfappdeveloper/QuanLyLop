package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.StudentHaveFeeDTO;

public class StudentHaveFeeDAO {
    private Context context;
    private String Json;

    private static StudentHaveFeeDAO instance = new StudentHaveFeeDAO();

    public static StudentHaveFeeDAO getInstance() {
        if(instance == null) {
            instance = new StudentHaveFeeDAO();

        }
        return instance;
    }

    public StudentHaveFeeDAO()
    {

    }

    public void PostStudentHaveFee(final Context context, int IDClass, int IDFee, final StudentHaveFeeCallback studentHaveFeeCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_studenthavefee.php";

        String[] NameColoum = new String[] {"idclass","idfee"};
        String[] Param = new String[] {Integer.toString(IDClass), Integer.toString(IDFee)};

        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                studentHaveFeeCallback.onSuccess(GetStudentHaveFee(json));
            }
        });
    }


    public ArrayList<StudentHaveFeeDTO> GetStudentHaveFee(String json)
    {
        ArrayList<StudentHaveFeeDTO> haveFeeDTOArrayList = new ArrayList<StudentHaveFeeDTO>();
        try {
            JSONArray studenthavefee = new JSONArray(json);

            if (studenthavefee.length() > 0)
            {
                for (int i=0; i <studenthavefee.length(); i++)
                {
                    JSONObject studenthavefeeinfo = studenthavefee.getJSONObject(i);
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                    String namestudent = studenthavefeeinfo.getString("namestudent");


                    Date datetime = Date.valueOf(studenthavefeeinfo.getString("datetime"));


                    StudentHaveFeeDTO studentHaveFeeDTO = new StudentHaveFeeDTO(namestudent, datetime);
                    haveFeeDTOArrayList.add(studentHaveFeeDTO);
                }


                return haveFeeDTOArrayList;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public interface StudentHaveFeeCallback{
        public void onSuccess(ArrayList<StudentHaveFeeDTO> studentHaveFeeDTOArrayList);
    }
    

}
