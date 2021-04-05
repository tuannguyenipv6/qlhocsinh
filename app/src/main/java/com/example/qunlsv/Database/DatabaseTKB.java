package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.SinhVien;
import com.example.qunlsv.DoiTuong.TKB;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTKB extends SQLiteOpenHelper {

    public DatabaseTKB(@Nullable Context context) {
        super(context, "TKB.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tại 1 bảng TKB
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "TKB(zAuto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "zTaiKhoang VARCHAR, " +
                "zBuoi INTEGER(100), " +
                "zThu2 VARCHAR(100), " +
                "zThu3 VARCHAR(100), " +
                "zThu4 VARCHAR(100), " +
                "zThu5 VARCHAR(100), " +
                "zThu6 VARCHAR(100), " +
                "zThu7 VARCHAR(100), " +
                "zCN VARCHAR(200))";
        db.execSQL(sqlTaiKhoangCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //lấy hàng BSáng of BChieu
    public TKB LayTKB(String TaiKhoan, int Buoi){
        TKB result = null;
        String sql = "SELECT * FROM TKB ";    //truy vấn vào bảng database TKB
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String xTaiKhoan = cursor.getString(1);
            int xBuoi = cursor.getInt(2);
            //ktra đúng sai
            if (xTaiKhoan.equals(TaiKhoan) && xBuoi == Buoi){
                String xThu2 = cursor.getString(3);
                String xThu3 = cursor.getString(4);
                String xThu4 = cursor.getString(5);
                String xThu5 = cursor.getString(6);
                String xThu6 = cursor.getString(7);
                String xThu7 = cursor.getString(8);
                String xCN = cursor.getString(9);
                result = new TKB(xTaiKhoan, xBuoi, xThu2, xThu3, xThu4, xThu5, xThu6, xThu7, xCN);
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //thêm TKB
    public void ThemTKB(TKB tkb){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để nghi lên Database
        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTaiKhoang", tkb.getmTaiKhoang());
        contentValues.put("zBuoi", tkb.getmBuoi());
        contentValues.put("zThu2", tkb.getmThu2());
        contentValues.put("zThu3", tkb.getmThu3());
        contentValues.put("zThu4", tkb.getmThu4());
        contentValues.put("zThu5", tkb.getmThu5());
        contentValues.put("zThu6", tkb.getmThu6());
        contentValues.put("zThu7", tkb.getmThu7());
        contentValues.put("zCN", tkb.getmCN());
        //"taikhoang" & "matkhau" là 2 cột trong bảng TaiKhoang.
        database.insert("TKB", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }


    //kiểm tra cột tk và tiết đã có hay chưa
    public int XemTK_Buoi(String TaiKhoan, int Buoi){
        int result = 0;
        String sql = "SELECT * FROM TKB ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTaiKhoang = cursor.getString(1);
            int zBuoi = cursor.getInt(2);
            //ktra đúng sai
            if (TaiKhoan.equals(zTaiKhoang) && zBuoi == Buoi){
                result = cursor.getInt(0);
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
    public Boolean KtraTK(String TaiKhoan){
        boolean result = false;
        String sql = "SELECT * FROM TKB ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTaiKhoang = cursor.getString(1);
            //ktra đúng sai
            if (TaiKhoan.equals(zTaiKhoang)){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //update lại TKB.
    public int UpdateTKB(TKB tkb, String zStrAuto){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();

        contentValues.put("zTaiKhoang", tkb.getmTaiKhoang());
        contentValues.put("zBuoi", tkb.getmBuoi());
        contentValues.put("zThu2", tkb.getmThu2());
        contentValues.put("zThu3", tkb.getmThu3());
        contentValues.put("zThu4", tkb.getmThu4());
        contentValues.put("zThu5", tkb.getmThu5());
        contentValues.put("zThu6", tkb.getmThu6());
        contentValues.put("zThu7", tkb.getmThu7());
        contentValues.put("zCN", tkb.getmCN());

        return sqLiteDatabase.update("TKB", contentValues, "zAuto" + "=?", new String[]{zStrAuto});
    }
}
