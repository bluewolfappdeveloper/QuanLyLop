using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class StudentDTO
    {
        public StudentDTO(long STT, long id, long idlop, string NameStudent, string phone)
        {
            this.STT = STT;
            this.id = id;
            this.idlop = idlop;
            this.NameStudent = NameStudent;
            this.Phone = phone;
        }

        public StudentDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.idlop = Int64.Parse(row["idclass"].ToString());
            this.NameStudent = (string)row["namestudent"];
            this.Phone = (string)row["phonestudent"];
        }

        public StudentDTO(long id, long idlop, string NameStudent, string phone)
        {
            this.id = id;
            this.idlop = idlop;
            this.NameStudent = NameStudent;
            this.Phone = phone;
        }

        public long STT { get; set; }
        public long id { get; set; }
        public long idlop { get; set; }
        public string NameStudent { get; set; }
        public string Phone { get; set; }
    }
}
