package tiger.quanlylop.quanlylop.DAO;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiger.quanlylop.quanlylop.DTO.FeeDTO;

public class FeeDAO {
    private Context context;
    private String Json;

    private static FeeDAO instance = new FeeDAO();

    public static FeeDAO getInstance() {
        if(instance == null) {
            instance = new FeeDAO();

        }
        return instance;
    }

    public FeeDAO()
    {

    }

    public void PostFee(final Context context, final FeeCallback FeeCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Fee/db_fee.php";
        DataProvider.getInstance().PostData(context, link, null, null, new DataProvider.ServerCallback() {
            @Override
            public void onSuccess(String json) {
                FeeCallback.onSuccess(GetFee(json));
            }
        });
    }


    public ArrayList<FeeDTO> GetFee(String json)
    {
        ArrayList<FeeDTO> listClass = new ArrayList<FeeDTO>();
        try {
            JSONArray fees = new JSONArray(json);

            if (fees.length() > 0)
            {
                for (int i=0; i <fees.length(); i++)
                {
                    JSONObject classinfo = fees.getJSONObject(i);
                    int id = classinfo.getInt("id");
                    String NameFee = classinfo.getString("NameFee");
                    String PriceFee = classinfo.getString("PriceFee");

                    FeeDTO fee = new FeeDTO(id, NameFee, PriceFee);
                    listClass.add(fee);
                }


                return listClass;
            }
            else return null;
        } catch (JSONException e) {
            Log.e("Error Json", e.getMessage() );
            return null;
        }
    }

    public void AddFee(final Context context, FeeDTO FeeDTO, final feeCRUDCallback feeCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Fee/db_addfee.php";

        String[] NameColoum = new String[] {"NameFee", "PriceFee"};
        String[] Param = new String[] {FeeDTO.NameFee, FeeDTO.PriceFee};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                feeCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                feeCRUDCallback.onFail();
            }
        });
    }


    public void DeleteFee(final Context context, int ID,  final feeCRUDCallback feeCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Fee/db_deletefee.php";

        String[] NameColoum = new String[] {"id"};
        String[] Param = new String[] { Integer.toString(ID)};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                feeCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                feeCRUDCallback.onFail();
            }
        });
    }

    public void UpdateFee(final Context context, FeeDTO FeeDTO, final feeCRUDCallback feeCRUDCallback)
    {
        this.context = context;
        String link=  Constant.IPLink + Constant.Port + "/Fee/db_updatefee.php";

        String[] NameColoum = new String[] {"id", "NameFee", "PriceFee"};
        String[] Param = new String[] {Integer.toString(FeeDTO.id),FeeDTO.NameFee, FeeDTO.PriceFee};

        DataProvider.getInstance().CRUDData(context, link, NameColoum, Param, new DataProvider.CRUDCallback() {
            @Override
            public void onSuccess() {
                feeCRUDCallback.onSuccess();
            }

            @Override
            public void onFail() {
                feeCRUDCallback.onFail();
            }
        });
    }

    public interface FeeCallback{
        public void onSuccess(ArrayList<FeeDTO> FeeDTOArrayList);
    }

    public interface feeCRUDCallback{
        public void onSuccess();
        public void onFail();
    }
}
