using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class FeeDTO
    {
        public FeeDTO(long STT, long id, string NameFee, string PriceFee)
        {
            this.STT = STT;
            this.id = id;
            this.NameFee = NameFee;
            this.PriceFee = PriceFee;

            if (PriceFee != "") this.FormatPriceFee = string.Format("{0:#,0}", Int64.Parse(PriceFee));
        }

        public FeeDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.NameFee = (string)row["NameFee"];
            this.PriceFee = (string)row["PriceFee"];

            if (PriceFee != "") this.FormatPriceFee = string.Format("{0:#,0}", Int64.Parse(PriceFee));
        }

        public FeeDTO(long id, string NameFee, string PriceFee)
        {
            this.STT = STT;
            this.id = id;
            this.NameFee = NameFee;
            this.PriceFee = PriceFee;

            if (PriceFee != "") this.FormatPriceFee = string.Format("{0:#,0}", Int64.Parse(PriceFee));
        }

        public long STT { get; set; }
        public long id { get; set; }
        public string NameFee { get; set; }
        public string PriceFee { get; set; }
        public string FormatPriceFee { get; set; }
    }
}
