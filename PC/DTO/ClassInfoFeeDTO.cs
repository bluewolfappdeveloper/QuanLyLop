using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class ClassInfoFeeCheck
    {
        public long STT { get; set; }
        public string namestudent { get; set; }
        public string phonestudent { get; set; }
        public string datetime { get; set; }

        public ClassInfoFeeCheck(long STT, string namestudent, string phonestudent, string datetime)
        {
            this.STT = STT;
            this.namestudent = namestudent;
            this.datetime = datetime;
            this.phonestudent = phonestudent;
        }

        public ClassInfoFeeCheck(long STT, DataRow row)
        {
            this.STT = STT;
            this.namestudent = row["namestudent"].ToString();
            this.phonestudent = row["phonestudent"].ToString();
            this.datetime = ((DateTime)row["datetime"]).ToShortDateString();
        }
    }

    public class ClassInfoFeeUnCheck
    {
        public long STT { get; set; }
        public string namestudent { get; set; }
        public string phonestudent { get; set; }

        public ClassInfoFeeUnCheck(long STT, string namestudent, string phonestudent)
        {
            this.STT = STT;
            this.namestudent = namestudent;
            this.phonestudent = phonestudent;
        }

        public ClassInfoFeeUnCheck(long STT, DataRow row)
        {
            this.STT = STT;
            this.namestudent = row["namestudent"].ToString();
            this.phonestudent = row["phonestudent"].ToString();
        }
    }
}
