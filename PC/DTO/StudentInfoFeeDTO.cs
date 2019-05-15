using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DAO
{
    public class StudentInfoFeeDTO
    {
        public long STT { get; set; }
        public long id { get; set; }
        public long idlop { get; set; }
        public string NameStudent { get; set; }
        public string Phone { get; set; }
        public long havefee { get; set; }
        public long nohave { get; set; }

        public StudentInfoFeeDTO(long STT, long id, long idlop, string NameStudent, string phone, long havefee, long nohave)
        {
            this.STT = STT;
            this.id = id;
            this.idlop = idlop;
            this.NameStudent = NameStudent;
            this.Phone = phone;
            this.havefee = havefee;
            this.nohave = nohave;

        }

        public StudentInfoFeeDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.idlop = Int64.Parse(row["idclass"].ToString());
            this.NameStudent = (string)row["namestudent"];
            this.Phone = (string)row["phonestudent"];
            this.havefee = Int64.Parse(row["havefee"].ToString());
            this.nohave = Int64.Parse(row["nohave"].ToString());
        }
    }
}
