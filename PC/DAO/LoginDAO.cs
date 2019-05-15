using QuanLyLop.DTO;
using QuanLyLopPC.DAO;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DAO
{
    public class LoginDAO
    {
        private static LoginDAO instance;

        public static LoginDAO Instance { get { if (instance == null) instance = new LoginDAO(); return instance; } set => instance = value; }

        public DataRow DangNhap(string username, string password)
        {
            

            string query = String.Format("select * from login where username = '{0}' and password = '{1}'", username, password);

            DataTable result = DataProvider.Instance.ExecuteQuery(query);

            if (result.Rows.Count == 1) return result.Rows[0];

            return null;
        }

        public bool UpdateUser(LoginDTO loginDTO)
        {

            string query = String.Format("UPDATE login SET name = N'{1}', username = N'{2}', password = N'{3}' WHERE login.id = {0}", loginDTO.id, loginDTO.name, loginDTO.username, loginDTO.password );

            DataTable data = DataProvider.Instance.ExecuteQuery(query);

            return data.Rows.Count > 0;

        }
    }
}
