package com.tiger.quanlylop.DTO;

public class ClassDTO {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getFeeClass() {
        return feeClass;
    }

    public void setFeeClass(String feeClass) {
        this.feeClass = feeClass;
    }

    private long id;
    private String nameClass;
    private String feeClass;

    public ClassDTO(){

    }

    public ClassDTO(long id, String nameClass, String feeClass) {
        this.id = id;
        this.nameClass = nameClass;
        this.feeClass = feeClass;
    }

    public ClassDTO(String nameClass, String feeClass) {
        this.nameClass = nameClass;
        this.feeClass = feeClass;
    }

    @Override
    public String toString() {
        return nameClass;
    }
}
