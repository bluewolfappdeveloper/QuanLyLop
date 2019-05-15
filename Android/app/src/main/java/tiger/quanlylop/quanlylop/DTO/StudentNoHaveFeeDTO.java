package tiger.quanlylop.quanlylop.DTO;

public class StudentNoHaveFeeDTO {

    public StudentNoHaveFeeDTO(int idclass, int idstudent, String namestudent, String phonestudent) {
        this.idclass = idclass;
        this.idstudent = idstudent;
        this.namestudent = namestudent;
        this.phonestudent = phonestudent;

    }

    public int idclass;
    public int idstudent;
    public String namestudent;
    public String phonestudent;
}
