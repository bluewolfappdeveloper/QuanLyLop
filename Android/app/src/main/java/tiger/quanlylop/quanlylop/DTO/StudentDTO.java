package tiger.quanlylop.quanlylop.DTO;

public class StudentDTO
{
    public StudentDTO(int id, int idclass, String NameStudent, String PhoneStudent)
    {
        this.id = id;
        this.idclass = idclass;
        this.NameStudent = NameStudent;
        this.PhoneStudent = PhoneStudent;
    }

    public int id;
    public int idclass;
    public String NameStudent;
    public String PhoneStudent;
}
