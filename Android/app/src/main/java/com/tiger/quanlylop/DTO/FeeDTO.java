package com.tiger.quanlylop.DTO;

public class FeeDTO {
    public FeeDTO(long id, String nameFee, String priceFee, boolean show) {
        this.id = id;
        this.nameFee = nameFee;
        this.priceFee = priceFee;
        this.show = show;
    }

    public FeeDTO(String nameFee, String priceFee, boolean show) {
        this.nameFee = nameFee;
        this.priceFee = priceFee;
        this.show = show;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameFee() {
        return nameFee;
    }

    public void setNameFee(String nameFee) {
        this.nameFee = nameFee;
    }

    public String getPriceFee() {
        return priceFee;
    }

    public void setPriceFee(String priceFee) {
        this.priceFee = priceFee;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private long id;
    private String nameFee;
    private String priceFee;
    private boolean show;
}
