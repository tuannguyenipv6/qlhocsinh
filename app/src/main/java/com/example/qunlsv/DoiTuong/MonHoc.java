package com.example.qunlsv.DoiTuong;

public class MonHoc {
    private String mTaiKhoang;
    private int mMaMonHoc;
    private String TenMonHoc;
    //contructo

    public MonHoc(String mTaiKhoang, int mMaMonHoc, String tenMonHoc) {
        this.mTaiKhoang = mTaiKhoang;
        this.mMaMonHoc = mMaMonHoc;
        this.TenMonHoc = tenMonHoc;
    }

    public MonHoc(int mMaMonHoc, String tenMonHoc) {
        this.mMaMonHoc = mMaMonHoc;
        this.TenMonHoc = tenMonHoc;
    }


    public MonHoc() {
    }

    //set&get
    public String getmTaiKhoang() {
        return mTaiKhoang;
    }

    public void setmTaiKhoang(String mTaiKhoang) {
        this.mTaiKhoang = mTaiKhoang;
    }

    public int getmMaMonHoc() {
        return mMaMonHoc;
    }

    public void setmMaMonHoc(int mMaMonHoc) {
        this.mMaMonHoc = mMaMonHoc;
    }

    public String getTenMonHoc() {
        return TenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        TenMonHoc = tenMonHoc;
    }
}
