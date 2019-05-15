package tiger.quanlylop.quanlylop.DTO;

public class ClassDTO {
    public ClassDTO(int id, String NameClass, String FeeClass, int CountStudent)
    {
        this.id = id;
        this.NameClass = NameClass;
        this.FeeClass = FeeClass;
        this.CountStudent = CountStudent;
    }

    public int id;
    public String NameClass;
    public String FeeClass;
    public int CountStudent;
}
