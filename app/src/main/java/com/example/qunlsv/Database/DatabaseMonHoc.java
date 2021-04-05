package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.SinhVien;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMonHoc extends SQLiteOpenHelper {
    public DatabaseMonHoc(@Nullable Context context) {
        super(context, "MonHoc.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tại 1 bảng tên BangDiem với các cột có thuộc tính như sau
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "MonHoc(zTaiKhoang VARCHAR(200), " +
                "zMaMonHoc INTEGER(200) PRIMARY KEY, " +
                "zTenMonHoc VARCHAR(200))";
        db.execSQL(sqlTaiKhoangCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //thêm 1 môn học
    public void ThemMonHoc(MonHoc monHoc){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để ghi lên Database

        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();

        contentValues.put("zTaiKhoang", monHoc.getmTaiKhoang());
        contentValues.put("zMaMonHoc", monHoc.getmMaMonHoc());
        contentValues.put("zTenMonHoc", monHoc.getTenMonHoc());
        database.insert("MonHoc", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }

    //kiểm tra Mã mon học đã có hay chưa
    public boolean KtrMaMonHoc(int MaMonHoc){
        boolean result = false;
        String sql = "SELECT * FROM MonHoc ";    //truy vấn vào bảng database MonHoc
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int zMaMonHoc = cursor.getInt(1);
            //ktra đúng sai
            if (zMaMonHoc == MaMonHoc){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
    //kiểm tra Bang MonHoc đã có gtr chưa
    public int KtrBang(String TaiKhoan){
        int result = 0;
        String sql = "SELECT * FROM MonHoc ";    //truy vấn vào bảng database MonHoc
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String xTaiKhoan = cursor.getString(0);
            if (xTaiKhoan.equals(TaiKhoan)){
                result++;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
    //Sửa Môn Học
    public int EditMonHoc(MonHoc monHoc){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTaiKhoang", monHoc.getmTaiKhoang());
        contentValues.put("zTenMonHoc", monHoc.getTenMonHoc());
        return sqLiteDatabase.update("MonHoc", contentValues, "zMaMonHoc" + "=?", new String[]{String.valueOf(monHoc.getmMaMonHoc())});
    }
    //lấy tất cả MônHọc trong database theo tên taikhoang
    public List<MonHoc> AllMonHoc(String TaiKhoang){
        String sql = "SELECT * FROM MonHoc ";        //truy vấn tất cả trong bản SinhVien
        List<MonHoc> monHocs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            String xTaiKhoang = cursor.getString(0);
            if (xTaiKhoang.equals(TaiKhoang)){
                int xMaMonHoc = cursor.getInt(1);
                String xTenMonHoc = cursor.getString(2);
                MonHoc monHoc = new MonHoc(xTaiKhoang, xMaMonHoc, xTenMonHoc);
                monHocs.add(monHoc);
            }
            cursor.moveToNext();
        }
        this.close();
        return monHocs;
    }
    //xóa Môn Học
    public int DeleteMonHoc(int MMH){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("MonHoc", "zMaMonHoc" + "=?", new String[]{String.valueOf(MMH)});
    }
}
