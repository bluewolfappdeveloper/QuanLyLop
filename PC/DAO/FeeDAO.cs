using QuanLyLopPC.Class;
using QuanLyLopPC.DAO;
using QuanLyLopPC.DTO;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DAO
{
    public class FeeDAO
    {
        private static FeeDAO instance;

        public static FeeDAO Instance { get { if (instance == null) { instance = new FeeDAO(); } return instance; } set => instance = value; }

        public FeeDAO()
        {

        }

        public ObservableCollection<FeeDTO> GetFee()
        {
            long STT = 0;
            ObservableCollection<FeeDTO> listfee = new ObservableCollection<FeeDTO>();

            String query = "SELECT * FROM fee ORDER BY length(NameFee), NameFee";

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            foreach (DataRow row in data.Rows)
            {
                STT += 1;
                FeeDTO FeeDTO = new FeeDTO(STT, row);
                listfee.Add(FeeDTO);
            }

            return listfee;
        }

        public bool AddFee(FeeDTO FeeDTO)
        {

            string query = String.Format("INSERT INTO fee (id, NameFee, PriceFee) VALUES (NULL, N'{0}', N'{1}');", FeeDTO.NameFee, FeeDTO.PriceFee);

            long data = DataProvider.Instance.ExecuteNonQuery(query);

            return data > 0;
           
        }

        public bool DeleteFee(long idfee)
        {

            string query = String.Format("DELETE FROM fee WHERE fee.id = {0}; Delete from studenttakefee where idfee = {0}", idfee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);
            TakeFeeDAO.Instance.DelTakeFeeByIDFee(idfee);


            return data.Rows.Count > 0;

        }

        public bool UpdateFee(FeeDTO FeeDTO)
        {

            string query = String.Format("UPDATE fee SET NameFee = N'{1}', PriceFee = N'{2}' WHERE fee.id = {0}", FeeDTO.id, FeeDTO.NameFee, FeeDTO.PriceFee);

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }

        public ObservableCollection<FeeDTO> FindFee(ObservableCollection<FeeDTO> listfee, String text)
        {
            ObservableCollection<FeeDTO> listfeefind =  new ObservableCollection<FeeDTO>();

            String findtext = TextFormat.Instance.RemoveAccent(text).ToLower();

            for (int i = 0; i< listfee.Count; i++)
            {
                FeeDTO FeeDTO = listfee.ElementAt(i);
                String studentname = TextFormat.Instance.RemoveAccent(FeeDTO.NameFee).ToLower();
                    //Toast.makeText(getApplicationContext(), Integer.toString(studentname.indexOf(findstring)), Toast.LENGTH_LONG).show();

                    if (studentname.IndexOf(findtext) >= 0) listfeefind.Add(FeeDTO);
              }

            return listfeefind;
        }

    }
}
