package tiger.quanlylop.quanlylop.DTO;

import java.sql.Date;

public class StudentHaveFeeDTO {

    public StudentHaveFeeDTO(String namestudent, Date datetime) {
        this.namestudent = namestudent;
        this.datetime = datetime;
    }

    public String namestudent;
    public Date datetime;
}
