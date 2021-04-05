package com.example.qunlsv.DoiTuong;

public class SinhVien {
    private String mTaiKhoan;
    private int mMSSV;
    private String mTen;
    private String mLop;
    private String mNamSinh;
    private String mKhoa;
    private int mGioiTinh;
    private String mChucVu;
    private String mSDT;
    private String mNghiChu;
    private String mDanToc;
    private String mNoiSinh;
    private String mLinkFB;

    //contructo all



    public SinhVien(String mTaiKhoan, int mMSSV, String mTen, String mLop, String mNamSinh, String mKhoa, int mGioiTinh, String mChucVu, String mSDT, String mNghiChu, String mDanToc, String mNoiSinh) {
        this.mTaiKhoan = mTaiKhoan;
        this.mMSSV = mMSSV;
        this.mTen = mTen;
        this.mLop = mLop;
        this.mNamSinh = mNamSinh;
        this.mKhoa = mKhoa;
        this.mGioiTinh = mGioiTinh;
        this.mChucVu = mChucVu;
        this.mSDT = mSDT;
        this.mNghiChu = mNghiChu;
        this.mDanToc = mDanToc;
        this.mNoiSinh = mNoiSinh;
    }

    public SinhVien(int mMSSV, String mTen, String mLop, String mNamSinh, String mKhoa, int mGioiTinh, String mChucVu, String mSDT, String mNghiChu, String mDanToc, String mNoiSinh) {
        this.mMSSV = mMSSV;
        this.mTen = mTen;
        this.mLop = mLop;
        this.mNamSinh = mNamSinh;
        this.mKhoa = mKhoa;
        this.mGioiTinh = mGioiTinh;
        this.mChucVu = mChucVu;
        this.mSDT = mSDT;
        this.mNghiChu = mNghiChu;
        this.mDanToc = mDanToc;
        this.mNoiSinh = mNoiSinh;
    }

    public SinhVien(int mMSSV, String mTen, String mLop, String mNamSinh, String mKhoa, int mGioiTinh, String mChucVu, String mSDT, String mNghiChu, String mDanToc, String mNoiSinh, String mLinkFB) {
        this.mMSSV = mMSSV;
        this.mTen = mTen;
        this.mLop = mLop;
        this.mNamSinh = mNamSinh;
        this.mKhoa = mKhoa;
        this.mGioiTinh = mGioiTinh;
        this.mChucVu = mChucVu;
        this.mSDT = mSDT;
        this.mNghiChu = mNghiChu;
        this.mDanToc = mDanToc;
        this.mNoiSinh = mNoiSinh;
        this.mLinkFB = mLinkFB;
    }
    //contructor ko có STT & nghi chú

    public SinhVien(String mTen, String mLop, String mNamSinh, String mKhoa, int mGioiTinh, String mChucVu, String mSDT, String mDanToc, String mNoiSinh) {
        this.mTen = mTen;
        this.mLop = mLop;
        this.mNamSinh = mNamSinh;
        this.mKhoa = mKhoa;
        this.mGioiTinh = mGioiTinh;
        this.mChucVu = mChucVu;
        this.mSDT = mSDT;
        this.mDanToc = mDanToc;
        this.mNoiSinh = mNoiSinh;
    }

    //contructor
    public SinhVien() {
    }
    //contructor K CÓ nghi chú
    public SinhVien(int mMSSV, String mTen, String mLop, String mNamSinh, String mKhoa, int mGioiTinh, String mChucVu, String mSDT, String mDanToc, String mNoiSinh) {
        this.mMSSV = mMSSV;
        this.mTen = mTen;
        this.mLop = mLop;
        this.mNamSinh = mNamSinh;
        this.mKhoa = mKhoa;
        this.mGioiTinh = mGioiTinh;
        this.mChucVu = mChucVu;
        this.mSDT = mSDT;
        this.mDanToc = mDanToc;
        this.mNoiSinh = mNoiSinh;
    }
    //setter & getter

    public String getmLinkFB() {
        return mLinkFB;
    }

    public void setmLinkFB(String mLinkFB) {
        this.mLinkFB = mLinkFB;
    }

    public String getmTaiKhoan() {
        return mTaiKhoan;
    }

    public void setmTaiKhoan(String mTaiKhoan) {
        this.mTaiKhoan = mTaiKhoan;
    }

    public String getmDanToc() {
        return mDanToc;
    }

    public void setmDanToc(String mDanToc) {
        this.mDanToc = mDanToc;
    }

    public String getmNoiSinh() {
        return mNoiSinh;
    }

    public void setmNoiSinh(String mNoiSinh) {
        this.mNoiSinh = mNoiSinh;
    }

    public int getmMSSV() {
        return mMSSV;
    }

    public void setmMSSV(int mSTT) {
        this.mMSSV = mSTT;
    }

    public String getmTen() {
        return mTen;
    }

    public void setmTen(String mTen) {
        this.mTen = mTen;
    }

    public String getmLop() {
        return mLop;
    }

    public void setmLop(String mLop) {
        this.mLop = mLop;
    }

    public String getmNamSinh() {
        return mNamSinh;
    }

    public void setmNamSinh(String mNamSinh) {
        this.mNamSinh = mNamSinh;
    }

    public String getmKhoa() {
        return mKhoa;
    }

    public void setmKhoa(String mKhoa) {
        this.mKhoa = mKhoa;
    }

    public int getmGioiTinh() {
        return mGioiTinh;
    }

    public void setmGioiTinh(int mGioiTinh) {
        this.mGioiTinh = mGioiTinh;
    }

    public String getmChucVu() {
        return mChucVu;
    }

    public void setmChucVu(String mChucVu) {
        this.mChucVu = mChucVu;
    }

    public String getmSDT() {
        return mSDT;
    }

    public void setmSDT(String mSDT) {
        this.mSDT = mSDT;
    }

    public String getmNghiChu() {
        return mNghiChu;
    }

    public void setmNghiChu(String mNghiChu) {
        this.mNghiChu = mNghiChu;
    }
}
