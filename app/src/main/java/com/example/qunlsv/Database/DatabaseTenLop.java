package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.SinhVien;
import com.example.qunlsv.DoiTuong.TKB;
import com.example.qunlsv.DoiTuong.TaiKhoang;
import com.example.qunlsv.DoiTuong.TenLop;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTenLop extends SQLiteOpenHelper {
    public DatabaseTenLop(@Nullable Context context) {
        super(context, "TenLop.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tại 1 bảng TenLop
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "TenLop(zTaiKhoang VARCHAR PRIMARY KEY, " +
                "zTenLop VARCHAR(100))";
        db.execSQL(sqlTaiKhoangCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //thêm Lớp
    public void ThemLop(TenLop tenLop){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để ghi lên Database
        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues(); //khai báo
        contentValues.put("zTaiKhoang", tenLop.getmTaiKhoang());
        contentValues.put("zTenLop", tenLop.getmTenLop());
        database.insert("TenLop", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }
    //kiểm tra tên lớp đã có chưa
    public boolean KtrTenLop(String TenLop){
        boolean result = false;  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM TenLop ";    //truy vấn vào bảng database TenLop
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTenLop = cursor.getString(1);
            //ktra đúng sai
            if (zTenLop.equals(TenLop)){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
    //lấy TenLop theo Ten TK
    public String LayTenLop(String TaiKhoang){
        String result = "Lớp: Chưa Có!";
        String sql = "SELECT * FROM TenLop ";    //truy vấn vào bảng database TenLop
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String xTaiKhoang = cursor.getString(0);
            //ktra đúng sai
            if (xTaiKhoang.equals(TaiKhoang)){
                result = cursor.getString(1);
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }
    //lấy tất cả tenlop trong database
    public List<String> AllTenLop(){
        String sql = "SELECT * FROM TenLop ";        //truy vấn tất cả trong bản SinhVien
        List<String> tenlops = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            String xTenLop = cursor.getString(1);
            tenlops.add(xTenLop);
            cursor.moveToNext();
        }
        this.close();
        return tenlops;
    }
    //Sửa Tên Lớp
    public int SuaTenLop(TenLop tenLop){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq vào database được mà phải thông qua ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTenLop", tenLop.getmTenLop());
        return sqLiteDatabase.update("TenLop", contentValues, "zTaiKhoang" + "=?", new String[]{tenLop.getmTaiKhoang()});
    }
}
