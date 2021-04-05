package com.example.qunlsv.DoiTuong;

public class TenLop {
    private String mTaiKhoang;
    private String mTenLop;

    //contructo
    public TenLop(String mTaiKhoang, String mTenLop) {
        this.mTaiKhoang = mTaiKhoang;
        this.mTenLop = mTenLop;
    }
    public TenLop() {
    }
    //set&get
    public String getmTaiKhoang() {
        return mTaiKhoang;
    }

    public void setmTaiKhoang(String mTaiKhoang) {
        this.mTaiKhoang = mTaiKhoang;
    }

    public String getmTenLop() {
        return mTenLop;
    }

    public void setmTenLop(String mTenLop) {
        this.mTenLop = mTenLop;
    }
}
