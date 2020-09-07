package com.tiger.quanlylop.DTO;

public class UsersDTO {

    public UsersDTO(){

    }

    public UsersDTO(long ID, String displayName) {
        this.ID = ID;
        this.displayName = displayName;
    }

    public UsersDTO(String displayName, String userName, String passWord) {
        this.displayName = displayName;
        this.userName = userName;
        this.passWord = passWord;
    }

    public UsersDTO(long ID, String displayName, String userName, String passWord) {
        this.ID = ID;
        this.displayName = displayName;
        this.userName = userName;
        this.passWord = passWord;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    private long ID;
    private String displayName;
    private String userName;
    private String passWord;


}
