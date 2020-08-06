package com.example.contact1;

public class Contacts {

    private int Id;
    private String Ten;
    private String So;
    private byte[] hinh;

    public Contacts(int id, String ten, String so, byte[] hinh) {
        Id = id;
        Ten = ten;
        So = so;
        this.hinh = hinh;
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSo() {
        return So;
    }

    public void setSo(String so) {
        So = so;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
