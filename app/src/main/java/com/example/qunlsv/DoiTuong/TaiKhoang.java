package com.example.qunlsv.DoiTuong;

public class TaiKhoang {
    private String mTaiKhoang;
    private String mMatKhau;

    //setter & getter
    public String getmTaiKhoang() {
        return mTaiKhoang;
    }

    public void setmTaiKhoang(String mTaiKhoang) {
        this.mTaiKhoang = mTaiKhoang;
    }

    public String getmMatKhau() {
        return mMatKhau;
    }

    public void setmMatKhau(String mMatKhau) {
        this.mMatKhau = mMatKhau;
    }

    //contructo
    public TaiKhoang(String mTaiKhoang, String mMatKhau) {
        this.mTaiKhoang = mTaiKhoang;
        this.mMatKhau = mMatKhau;
    }
    public TaiKhoang() {
    }
}
