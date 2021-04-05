package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.SinhVien;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSinhVien extends SQLiteOpenHelper {

    //Contructor
    public DatabaseSinhVien(@Nullable Context context) {
        super(context, "DatabaseSinhVien.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tại 1 bảng tên Sinh Viên với các cột có thuộc tính như sau
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "SinhVien(zMSSV INTEGER PRIMARY KEY, " +
                "zTen VARCHAR(200), " +
                "zLop VARCHAR(200), " +
                "zNamSinh VARCHAR(200), " +
                "zKhoa VARCHAR(200), " +
                "zGioiTinh INTERGER(10), " +
                "zChucVu VARCHAR(200), " +
                "zSDT VARCHAR(200), " +
                "zNghiChu VARCHAR(200), " +
                "zDanToc VARCHAR(200), " +
                "zNoiSinh VARCHAR(200), " +
                "zTaiKhoang VARCHAR(200), " +
                "zLinkFB VARCHAR(200))";
        db.execSQL(sqlTaiKhoangCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //them 1 sinh vien
    public void ThemSinhVien(SinhVien sinhVien, String TaiKhoang){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để nghi lên Database

        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();
        contentValues.put("zMSSV", sinhVien.getmMSSV());
        contentValues.put("zTen", sinhVien.getmTen());
        contentValues.put("zLop", sinhVien.getmLop());
        contentValues.put("zNamSinh", sinhVien.getmNamSinh());
        contentValues.put("zKhoa", sinhVien.getmKhoa());
        contentValues.put("zGioiTinh", sinhVien.getmGioiTinh());
        contentValues.put("zChucVu", sinhVien.getmChucVu());
        contentValues.put("zSDT", sinhVien.getmSDT());
        contentValues.put("zDanToc", sinhVien.getmDanToc());
        contentValues.put("zNoiSinh", sinhVien.getmNoiSinh());
        contentValues.put("zTaiKhoang", TaiKhoang);


        database.insert("SinhVien", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }

    //lấy tất cả Sinh Viên trong database theo tên TaiKhoan
    public List<SinhVien> AllSinhVien(String TaiKhoan){
        String sql = "SELECT * FROM SinhVien ";        //truy vấn tất cả trong bản SinhVien
        List<SinhVien> sinhViens = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
           String xTaiKhoan = cursor.getString(11);
           if (xTaiKhoan.equals(TaiKhoan)){
               int xMSSV = cursor.getInt(0);
               String xTen = cursor.getString(1);
               String xLop = cursor.getString(2);
               String xNamSinh = cursor.getString(3);
               String xKhoa = cursor.getString(4);
               int xGioiTinh = cursor.getInt(5);
               String xChucVu = cursor.getString(6);
               String xSDT = cursor.getString(7);
               String xDanToc = cursor.getString(9);
               String xNoiSinh = cursor.getString(10);
               SinhVien sinhVien = new SinhVien(xMSSV, xTen, xLop, xNamSinh, xKhoa, xGioiTinh, xChucVu, xSDT, xDanToc, xNoiSinh);
               sinhViens.add(sinhVien);
           }
            cursor.moveToNext();
        }
        this.close();
        return sinhViens;
    }

    //Sửa Sinh Vien
    public int UpdateSinhVien(SinhVien sinhVien){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTen", sinhVien.getmTen());
        contentValues.put("zLop", sinhVien.getmLop());
        contentValues.put("zNamSinh", sinhVien.getmNamSinh());
        contentValues.put("zKhoa", sinhVien.getmKhoa());
        contentValues.put("zGioiTinh", sinhVien.getmGioiTinh());
        contentValues.put("zChucVu", sinhVien.getmChucVu());
        contentValues.put("zSDT", sinhVien.getmSDT());
        contentValues.put("zNghiChu", sinhVien.getmNghiChu());
        contentValues.put("zDanToc", sinhVien.getmDanToc());
        contentValues.put("zNoiSinh", sinhVien.getmNoiSinh());
        return sqLiteDatabase.update("SinhVien", contentValues, "zMSSV" + "=?", new String[]{String.valueOf(sinhVien.getmMSSV())});
    }
    //Sửa Linkfb
    public int UpdateLinkFB(String LinkFB, int MSSV){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("zLinkFB", LinkFB);
        return sqLiteDatabase.update("SinhVien", contentValues, "zMSSV" + "=?", new String[]{String.valueOf(MSSV)});
    }

    //kiểm tra MSSV
    public boolean KtrMSSV(int MSSV){
        boolean result = false;  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM SinhVien ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int xMSSV = cursor.getInt(0);
            //ktra đúng sai
            if (MSSV == xMSSV){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //lấy sinh viên theo MSSV
    public SinhVien LaySinhVien(int MSSV){
        SinhVien result = null;
        String sql = "SELECT * FROM SinhVien ";    //truy vấn vào bảng database sinhvien
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int xMSSV = cursor.getInt(0);
            //ktra đúng sai
            if (MSSV == xMSSV){
                String xTen = cursor.getString(1);
                String xLop = cursor.getString(2);
                String xNamSinh = cursor.getString(3);
                String xKhoa = cursor.getString(4);
                int xGioiTinh = cursor.getInt(5);
                String xChucVu = cursor.getString(6);
                String xSDT = cursor.getString(7);
                String xGhiChu = cursor.getString(8);
                String xDanToc = cursor.getString(9);
                String xNoiSinh = cursor.getString(10);
                String xLinkFB = cursor.getString(12);
                result = new SinhVien(xMSSV, xTen, xLop, xNamSinh, xKhoa, xGioiTinh, xChucVu, xSDT, xGhiChu, xDanToc, xNoiSinh, xLinkFB);
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //xóa SinhVien
    public int DeleteTaiKhoang(int MSSV){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("SinhVien", "zMSSV" + "=?", new String[]{String.valueOf(MSSV)});
    }
    //kiểm tra sinh viên đã đc tạo trong tài khoảng đó chưa
    public boolean KTRSV(String TaiKhoang){
        boolean result = false;  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM SinhVien ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String xTaiKhoan = cursor.getString(11);
            if (xTaiKhoan.equals(TaiKhoang)){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
}
