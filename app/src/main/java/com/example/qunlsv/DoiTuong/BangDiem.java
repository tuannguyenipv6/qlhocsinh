package com.example.qunlsv.DoiTuong;

public class BangDiem {
    private int mAuto;
    private int mMSSV;
    private int mMaMonHoc;
    private String mTenMonHoc;
    private int mTC;
    private int mDiemThaiDo;
    private int mDiemKT;
    private int mTBBoPhan;
    private int mDiemThi;
    private int mTBMonH10;
    private int mTBMonH4;
    private String mDiemChu;

    //contructo


    public BangDiem(int mAuto, int mMSSV, int mMaMonHoc, String mTenMonHoc, int mTC, int mDiemThaiDo, int mDiemKT, int mTBBoPhan, int mDiemThi, int mTBMonH10, int mTBMonH4, String mDiemChu) {
        this.mAuto = mAuto;
        this.mMSSV = mMSSV;
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
        this.mTC = mTC;
        this.mDiemThaiDo = mDiemThaiDo;
        this.mDiemKT = mDiemKT;
        this.mTBBoPhan = mTBBoPhan;
        this.mDiemThi = mDiemThi;
        this.mTBMonH10 = mTBMonH10;
        this.mTBMonH4 = mTBMonH4;
        this.mDiemChu = mDiemChu;
    }
    public BangDiem(int mMSSV, int mMaMonHoc, String mTenMonHoc, int mTC, int mDiemThaiDo, int mDiemKT, int mTBBoPhan, int mDiemThi, int mTBMonH10, int mTBMonH4, String mDiemChu) {
        this.mMSSV = mMSSV;
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
        this.mTC = mTC;
        this.mDiemThaiDo = mDiemThaiDo;
        this.mDiemKT = mDiemKT;
        this.mTBBoPhan = mTBBoPhan;
        this.mDiemThi = mDiemThi;
        this.mTBMonH10 = mTBMonH10;
        this.mTBMonH4 = mTBMonH4;
        this.mDiemChu = mDiemChu;
    }
    public BangDiem(int mMaMonHoc, String mTenMonHoc, int mTC, int mDiemThaiDo, int mDiemKT, int mTBBoPhan, int mDiemThi, int mTBMonH10, int mTBMonH4, String mDiemChu) {
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
        this.mTC = mTC;
        this.mDiemThaiDo = mDiemThaiDo;
        this.mDiemKT = mDiemKT;
        this.mTBBoPhan = mTBBoPhan;
        this.mDiemThi = mDiemThi;
        this.mTBMonH10 = mTBMonH10;
        this.mTBMonH4 = mTBMonH4;
        this.mDiemChu = mDiemChu;
    }

    public BangDiem(int mAuto, int mTC, int mDiemThaiDo, int mDiemKT, int mTBBoPhan, int mDiemThi, int mTBMonH10, int mTBMonH4, String mDiemChu) {
        this.mAuto = mAuto;
        this.mTC = mTC;
        this.mDiemThaiDo = mDiemThaiDo;
        this.mDiemKT = mDiemKT;
        this.mTBBoPhan = mTBBoPhan;
        this.mDiemThi = mDiemThi;
        this.mTBMonH10 = mTBMonH10;
        this.mTBMonH4 = mTBMonH4;
        this.mDiemChu = mDiemChu;
    }

    public BangDiem(int mMaMonHoc, String mTenMonHoc) {
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
    }

    public BangDiem(int mMSSV, int mMaMonHoc, String mTenMonHoc) {
        this.mMSSV = mMSSV;
        this.mMaMonHoc = mMaMonHoc;
        this.mTenMonHoc = mTenMonHoc;
    }

    public BangDiem() {
    }

    //set&get


    public String getmTenMonHoc() {
        return mTenMonHoc;
    }

    public void setmTenMonHoc(String mTenMonHoc) {
        this.mTenMonHoc = mTenMonHoc;
    }

    public int getmMSSV() {
        return mMSSV;
    }

    public void setmMSSV(int mMSSV) {
        this.mMSSV = mMSSV;
    }

    public int getmAuto() {
        return mAuto;
    }

    public void setmAuto(int mAuto) {
        this.mAuto = mAuto;
    }

    public int getmMaMonHoc() {
        return mMaMonHoc;
    }

    public void setmMaMonHoc(int mMaMonHoc) {
        this.mMaMonHoc = mMaMonHoc;
    }

    public int getmTC() {
        return mTC;
    }

    public void setmTC(int mTC) {
        this.mTC = mTC;
    }

    public int getmDiemThaiDo() {
        return mDiemThaiDo;
    }

    public void setmDiemThaiDo(int mDiemThaiDo) {
        this.mDiemThaiDo = mDiemThaiDo;
    }

    public int getmDiemKT() {
        return mDiemKT;
    }

    public void setmDiemKT(int mDiemKT) {
        this.mDiemKT = mDiemKT;
    }

    public int getmTBBoPhan() {
        return mTBBoPhan;
    }

    public void setmTBBoPhan(int mTBBoPhan) {
        this.mTBBoPhan = mTBBoPhan;
    }

    public int getmDiemThi() {
        return mDiemThi;
    }

    public void setmDiemThi(int mDiemThi) {
        this.mDiemThi = mDiemThi;
    }

    public int getmTBMonH10() {
        return mTBMonH10;
    }

    public void setmTBMonH10(int mTBMonH10) {
        this.mTBMonH10 = mTBMonH10;
    }

    public int getmTBMonH4() {
        return mTBMonH4;
    }

    public void setmTBMonH4(int mTBMonH4) {
        this.mTBMonH4 = mTBMonH4;
    }

    public String getmDiemChu() {
        return mDiemChu;
    }

    public void setmDiemChu(String mDiemChu) {
        this.mDiemChu = mDiemChu;
    }
}
