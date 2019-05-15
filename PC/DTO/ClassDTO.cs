using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QuanLyLopPC.DTO
{
    public class ClassDTO
    {
        public ClassDTO(long STT, long id, string nameclass, string FeeClass, long Count)
        {
            this.STT = STT;
            this.id = id;
            this.NameClass = nameclass;
            this.FeeClass = FeeClass;
            this.Count = Count;

            if (FeeClass != "") this.FormatFeeClass = string.Format("{0:#,0}", Int64.Parse(FeeClass));
        }

        public ClassDTO(long STT, DataRow row)
        {
            this.STT = STT;
            this.id = Int64.Parse(row["id"].ToString());
            this.NameClass = (string)row["nameclass"];
            this.FeeClass = (string)row["FeeClass"];
            this.Count = Int64.Parse(row["CountStudent"].ToString());

            if (FeeClass != "") this.FormatFeeClass = string.Format("{0:#,0}", Int64.Parse(FeeClass));
        }

        public ClassDTO(long id, string nameclass, string FeeClass)
        {
            this.id = id;
            this.NameClass = nameclass;
            this.FeeClass = FeeClass;

            if (FeeClass != "") this.FormatFeeClass = string.Format("{0:#,0}", Int64.Parse(FeeClass));
        }

        public long STT { get; set; }
        public long id { get; set; }
        public string NameClass { get; set; }
        public string FeeClass { get; set; }
        public string FormatFeeClass { get; set; }
        public long Count { get; set; }
    }
}
