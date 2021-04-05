package com.example.qunlsv.DoiTuong;

public class BangChon {
    private String mNoiDung;
    private int mHinh;

    public BangChon(String mNoiDung, int mHinh) {
        this.mNoiDung = mNoiDung;
        this.mHinh = mHinh;
    }

    public String getmNoiDung() {
        return mNoiDung;
    }

    public void setmNoiDung(String mNoiDung) {
        this.mNoiDung = mNoiDung;
    }

    public int getmHinh() {
        return mHinh;
    }

    public void setmHinh(int mHinh) {
        this.mHinh = mHinh;
    }
}
