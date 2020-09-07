package com.tiger.quanlylop.DTO;

public class FeeMainDTO {

    public FeeMainDTO(long id, String nameFee, String priceFee, boolean selected) {
        this.id = id;
        this.nameFee = nameFee;
        this.priceFee = priceFee;
        this.selected = selected;
    }

    public FeeMainDTO(String nameFee, String priceFee, boolean selected) {
        this.nameFee = nameFee;
        this.priceFee = priceFee;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private long id;
    private String nameFee;
    private String priceFee;
    private boolean selected;


}
