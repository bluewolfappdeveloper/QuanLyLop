package com.tiger.quanlylop.DTO;


import java.sql.Date;

public class PayFeeDTO {
    public PayFeeDTO(long id, long idClass, long idStudent, long idFee, Date datePay, String nameClass, String nameStudent, String nameFee) {
        this.id = id;
        this.idClass = idClass;
        this.idStudent = idStudent;
        this.idFee = idFee;
        this.datePay = datePay;
        this.nameClass = nameClass;
        this.nameStudent = nameStudent;
        this.nameFee = nameFee;
    }

    public PayFeeDTO(long id, long idClass, long idStudent, long idFee, Date datePay) {
        this.id = id;
        this.idClass = idClass;
        this.idStudent = idStudent;
        this.idFee = idFee;
        this.datePay = datePay;
    }

    public PayFeeDTO(long id, long idClass, long idStudent, long idFee, Date datePay, String nameFee) {
        this.id = id;
        this.idClass = idClass;
        this.idStudent = idStudent;
        this.idFee = idFee;
        this.datePay = datePay;
        this.nameFee = nameFee;
    }

    private long id;
    private long idClass;
    private long idStudent;
    private long idFee;
    private Date datePay;

    private String nameClass;
    private String nameStudent;
    private String nameFee;

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

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdFee() {
        return idFee;
    }

    public void setIdFee(long idFee) {
        this.idFee = idFee;
    }

    public Date getDatePay() {
        return datePay;
    }

    public void setDatePay(Date datePay) {
        this.datePay = datePay;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getNameFee() {
        return nameFee;
    }

    public void setNameFee(String nameFee) {
        this.nameFee = nameFee;
    }
}
