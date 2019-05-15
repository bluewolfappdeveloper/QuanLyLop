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

import tiger.quanlylop.quanlylop.DTO.StudentNoHaveFeeDTO;

public class StudentNoHaveFeeDAO {
    private Context context;
    private String Json;

    private static StudentNoHaveFeeDAO instance = new StudentNoHaveFeeDAO();

    public static StudentNoHaveFeeDAO getInstance() {
        if(instance == null) {
            instance = new StudentNoHaveFeeDAO();

        }
        return instance;
    }

    public StudentNoHaveFeeDAO()
    {

    }

    public void PostStudentNoHaveFee(final Context context, int IDClass, int IDFee, final StudentNoHaveFeeCallback studentNoHaveFeeCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_studentnohavefee.php";

        String[] NameColoum = new String[] {"idclass","idfee"};
        String[] Param = new String[] {Integer.toString(IDClass), Integer.toString(IDFee)};

        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                studentNoHaveFeeCallback.onSuccess(GetStudentNoHaveFee(json));
            }
        });
    }


    public ArrayList<StudentNoHaveFeeDTO> GetStudentNoHaveFee(String json)
    {
        ArrayList<StudentNoHaveFeeDTO> studentNoHaveFeeDTOArrayList = new ArrayList<StudentNoHaveFeeDTO>();
        try {
            JSONArray studentnohavefee = new JSONArray(json);

            if (studentnohavefee.length() > 0)
            {
                for (int i=0; i <studentnohavefee.length(); i++)
                {
                    JSONObject studentnohavefeeinfo = studentnohavefee.getJSONObject(i);

                    int idclass = studentnohavefeeinfo.getInt("idclass");
                    int idstudent= studentnohavefeeinfo.getInt("idstudent");
                    String namestudent= studentnohavefeeinfo.getString("namestudent");
                    String phonestudent= studentnohavefeeinfo.getString("phonestudent");

                    StudentNoHaveFeeDTO classInfoFeeDTO = new StudentNoHaveFeeDTO(idclass, idstudent, namestudent, phonestudent);
                    studentNoHaveFeeDTOArrayList.add(classInfoFeeDTO);
                }


                return studentNoHaveFeeDTOArrayList;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public interface StudentNoHaveFeeCallback{
        public void onSuccess(ArrayList<StudentNoHaveFeeDTO> noHaveFeeDTOArrayList);
    }
    

}
