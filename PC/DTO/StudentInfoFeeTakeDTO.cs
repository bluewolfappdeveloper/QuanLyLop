using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class StudentInfoFeeTakeDTO
    {
        public long STT { get; set; }
        public long id { get; set; }
        public string NameFee { get; set; }
        public string PriceFee { get; set; }
        public string datetime { get; set; }

        public StudentInfoFeeTakeDTO(long STT, long id, string NameFee, string PriceFee, string datetime)
        {
            this.STT = STT;
            this.id = id;
            this.NameFee = NameFee;
            this.PriceFee = PriceFee;
            this.datetime = datetime;
        }

        public StudentInfoFeeTakeDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.NameFee = (string)row["NameFee"];
            this.PriceFee = (string)row["PriceFee"];
            this.datetime = ((DateTime)row["datetime"]).ToShortDateString();
           
        }
    }
}
