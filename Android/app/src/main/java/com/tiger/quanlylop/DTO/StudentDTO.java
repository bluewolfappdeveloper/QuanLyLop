package com.tiger.quanlylop.DTO;

public class StudentDTO {
    public StudentDTO(long id, long idClass, String nameStudent, String phoneStudent) {
        this.id = id;
        this.idClass = idClass;
        this.nameStudent = nameStudent;
        this.phoneStudent = phoneStudent;
    }

    public StudentDTO(long idClass, String nameStudent, String phoneStudent) {
        this.idClass = idClass;
        this.nameStudent = nameStudent;
        this.phoneStudent = phoneStudent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdClass() {
        return idClass;
    }

    public void setIdClass(long idClass) {
        this.idClass = idClass;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getPhoneStudent() {
        return phoneStudent;
    }

    public void setPhoneStudent(String phoneStudent) {
        this.phoneStudent = phoneStudent;
    }

    private long id;
    private long idClass;
    private String  nameStudent;
    private String phoneStudent;
}
