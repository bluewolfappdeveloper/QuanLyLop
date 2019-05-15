package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.ClassDTO;
import tiger.quanlylop.quanlylop.DTO.TakeFeeDTO;

public class TakeFeeDAO {

    private Context context;
    private String Json;

    private static TakeFeeDAO instance = new TakeFeeDAO();

    public static TakeFeeDAO getInstance() {
        if(instance == null) {
            instance = new TakeFeeDAO();

        }
        return instance;
    }

    public TakeFeeDAO()
    {

    }

    public void PostTakeFee(final Context context, int IDClass, int IDStudent, final TakeFeeCallBack takefeeCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_takefee.php";

        String[] NameColoum = new String[] {"idclass", "idstudent"};
        String[] Param = new String[] {Integer.toString(IDClass),Integer.toString(IDStudent)};

        //Toast.makeText(context, Integer.toString(IDClass)+Integer.toString(IDStudent) , Toast.LENGTH_LONG).show();

        DataProvider.getInstance().PostData(context, link, NameColoum, Param, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                //Toast.makeText(context, json, Toast.LENGTH_LONG).show();
                takefeeCallback.onSuccess(GetTakeFee(json));

            }
        });
    }


    public ArrayList<TakeFeeDTO> GetTakeFee(String json)
    {
        //Toast.makeText(context, json, Toast.LENGTH_LONG).show();

        ArrayList<TakeFeeDTO> listTakeFee = new ArrayList<TakeFeeDTO>();
        try {
            JSONArray takefees = new JSONArray(json);

            if (takefees.length() > 0)
            {
                for (int i=0; i <takefees.length(); i++)
                {
                    JSONObject takefeeinfo = takefees.getJSONObject(i);

                     int id = takefeeinfo.getInt("id");;
                     int idclass= takefeeinfo.getInt("idclass");
                     String tenlop= takefeeinfo.getString("tenlop");
                     int idstudent= takefeeinfo.getInt("idstudent");
                     String tenhs= takefeeinfo.getString("tenhs");
                     int idfee= takefeeinfo.getInt("idfee");
                     String tenhp= takefeeinfo.getString("tenhp");
                     String kp= takefeeinfo.getString("kp");
                     Date datetime= Date.valueOf(takefeeinfo.getString("datetime"));
                     int status= takefeeinfo.getInt("status");


                    TakeFeeDTO feeDTO = new TakeFeeDTO(id,idclass,tenlop,idstudent,tenhs,idfee,tenhp,kp,datetime,status);
                    listTakeFee.add(feeDTO);
                }


                return listTakeFee;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }


    public void AddTakeFee(final Context context, TakeFeeDTO takeFeeDTO, final TakeFeeCRUDCallBack takeFeeCRUDCallBack)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_addstudenttakefee.php";

        String[] NameColoum = new String[] {"idclass", "idstudent", "idfee", "datetime", "status"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String[] Param = new String[] { Integer.toString(takeFeeDTO.idclass), Integer.toString(takeFeeDTO.idstudent),
                Integer.toString(takeFeeDTO.idfee), df.format(takeFeeDTO.datetime), Integer.toString(takeFeeDTO.status)};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                takeFeeCRUDCallBack.onSuccess();
            }

            @Override
            public void onFail() {
                takeFeeCRUDCallBack.onFail();
            }
        });
    }


    public void DeleteTakeFee(final Context context, int ID, final TakeFeeCRUDCallBack takeFeeCRUDCallBack)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/StudentTakeFee/db_deletestudenttakefee.php";

        String[] NameColoum = new String[] {"id"};
        String[] Param = new String[] { Integer.toString(ID)};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                takeFeeCRUDCallBack.onSuccess();
            }

            @Override
            public void onFail() {
                takeFeeCRUDCallBack.onFail();
            }
        });
    }


    public interface TakeFeeCallBack{
        public void onSuccess(ArrayList<TakeFeeDTO> takeFeeDTOArrayList);
    }

    public interface TakeFeeCRUDCallBack{
        public void onSuccess();
        public void onFail();
    }
}

