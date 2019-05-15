package tiger.quanlylop.quanlylop.DTO;


import java.sql.Date;

public class TakeFeeDTO {


    public int id;
    public int idclass;
    public String tenlop;
    public int idstudent;
    public String tenhs;
    public int idfee;
    public String tenhp;
    public String kp;
    public Date datetime;
    public int status;


    public TakeFeeDTO(int id, int idclass, String tenlop, int idstudent, String tenhs,  int idfee, String tenhp, String kp, Date datetime, int status) {
        this.id = id;
        this.idclass = idclass;
        this.tenlop = tenlop;
        this.idstudent = idstudent;
        this.tenhs = tenhs;
        this.idfee = idfee;
        this.tenhp = tenhp;
        this.kp = kp;
        this.datetime = datetime;
        this.status = status;
    }

    public TakeFeeDTO(int id, int idclass, int idstudent, int idfee, Date datetime, int status)
    {
        this.id = id;
        this.idclass = idclass;
        this.idstudent = idstudent;
        this.idfee = idfee;
        this.datetime = datetime;
        this.status = status;
    }
}
