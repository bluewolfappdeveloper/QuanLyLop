using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class StudentTakeFeeDTO
    {
        public long STT { get; set; }
        public long id { get; set; }
        public long idclass { get; set; }
        public long idstudent { get; set; }
        public long idfee { get; set; }
        public DateTime datetime { get; set; }

        public StudentTakeFeeDTO(long STT, long id, long idclass, long idstudent, long idfee, DateTime datetime)
        {
            this.STT = STT;
            this.id = id;
            this.idclass = idclass;
            this.idstudent = idstudent;
            this.idfee = idfee;
            this.datetime = datetime;
        }

        public StudentTakeFeeDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.idclass = Int64.Parse(row["idclass"].ToString());
            this.idstudent = Int64.Parse(row["idstudent"].ToString());
            this.idfee = Int64.Parse(row["idfee"].ToString());
            this.datetime = (DateTime)row["datetime"];
        }
    }
}
