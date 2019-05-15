package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import tiger.quanlylop.quanlylop.DTO.StudentDTO;
import tiger.quanlylop.quanlylop.FragmentApp.EditStudentFragment;

public class StudentDAO  {

    private Context context;
    private String Json;

    private static StudentDAO instance = new StudentDAO();

    public static StudentDAO getInstance() {
        if(instance == null) {
            instance = new StudentDAO();

        }
        return instance;
    }

    public StudentDAO()
    {

    }

    public void PostStudent(final Context context,int IDClass, final StudentCallback studentCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Student/db_student.php";

        String[] NameColoum = new String[] {"idclass"};
        String[] Param = new String[] { Integer.toString(IDClass) };

        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                studentCallback.onSuccess(GetStudent(json));
            }
        });
    }


    public ArrayList<StudentDTO> GetStudent(String json)
    {
        ArrayList<StudentDTO> liststudent = new ArrayList<StudentDTO>();
        try {
            JSONArray student = new JSONArray(json);

            if (student.length() > 0)
            {
                for (int i=0; i <student.length(); i++)
                {
                    JSONObject studentinfo = student.getJSONObject(i);
                    int id = studentinfo.getInt("id");
                    int idclass = studentinfo.getInt("idclass");
                    String NameStudent = studentinfo.getString("NameStudent");
                    String PhoneStudent = studentinfo.getString("PhoneStudent");

                    StudentDTO student_ = new StudentDTO(id,idclass,NameStudent, PhoneStudent);
                    //Toast.makeText(context, NameStudent, Toast.LENGTH_LONG).show();
                    liststudent.add(student_);
                }


                return liststudent;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );

            return null;
        }
    }

    public void AddStudent(final Context context,StudentDTO studentDTO, final StudentCRUDCallback studentCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Student/db_addstudent.php";

        String[] NameColoum = new String[] {"idclass", "namestudent", "phonestudent"};
        String[] Param = new String[] { Integer.toString(studentDTO.idclass), studentDTO.NameStudent, studentDTO.PhoneStudent  };

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                studentCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                studentCRUDCallback.onFail();
            }
        });
    }

    public void DeleteStudent(final Context context, int IDClass, int IDStudent, final StudentCRUDCallback studentCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Student/db_deletestudent.php";

        String[] NameColoum = new String[] {"idclass", "idstudent"};
        String[] Param = new String[] { Integer.toString(IDClass),Integer.toString(IDStudent) };

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                studentCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                studentCRUDCallback.onFail();
            }
        });
    }

    public void UpdateStudent(final Context context, StudentDTO studentDTO, final StudentCRUDCallback studentCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Student/db_updatestudent.php";

        String[] NameColoum = new String[] {"idclass", "idstudent", "namestudent", "phonestudent"};
        String[] Param = new String[] {Integer.toString(studentDTO.idclass), Integer.toString(studentDTO.id), studentDTO.NameStudent, studentDTO.PhoneStudent };

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                studentCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                studentCRUDCallback.onFail();
            }
        });
    }

    public interface StudentCallback{
        public void onSuccess(ArrayList<StudentDTO> studentDTOArrayList);
    }

    public interface StudentCRUDCallback{
        public void onSuccess();
        public void onFail();
    }

}
