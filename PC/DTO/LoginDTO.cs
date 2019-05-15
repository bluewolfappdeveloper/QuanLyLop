using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLop.DTO
{
    public class LoginDTO
    {
        public LoginDTO(long id, string name, string username, string password)
        {
            this.id = id;
            this.name = name;
            this.username = username;
            this.password = password;
        }

        public LoginDTO(DataRow row)
        {
            this.id = (long)row["id"];
            this.name = row["name"].ToString();
            this.username = row["username"].ToString();
            this.password = row["password"].ToString();
        }

        public long id { get; set; }
        public string name { get; set; }
        public string username { get; set; }
        public string password { get; set; }
    }
}
