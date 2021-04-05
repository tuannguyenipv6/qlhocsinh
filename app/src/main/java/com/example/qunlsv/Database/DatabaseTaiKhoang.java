package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTaiKhoang extends SQLiteOpenHelper {

    //Contructor
    public DatabaseTaiKhoang(@Nullable Context context){
        super(context, "DatabaseTaiKhoang.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //khởi tại 1 bảng tên TaiKhoang có 2 cột
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "TaiKhoang(taikhoang VARCHAR(200) PRIMARY KEY, " +
                "matkhau VARCHAR(200))";
        database.execSQL(sqlTaiKhoangCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    //thêm 1 tài khoảng
    public void ThemTaiKhoang(TaiKhoang taiKhoang){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để nghi lên Database

        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();

        contentValues.put("taikhoang", taiKhoang.getmTaiKhoang());
        contentValues.put("matkhau", taiKhoang.getmMatKhau());
        //"taikhoang" & "matkhau" là 2 cột trong bảng TaiKhoang.

        database.insert("TaiKhoang", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }

    //kiểm tra tài khoảng có hay không
    public boolean KtrTaiKhoang(String TenTaiKhoang){
        boolean result = false;  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM TaiKhoang ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTaiKhoang = cursor.getString(0);
            //ktra đúng sai
            if (TenTaiKhoang.equals(zTaiKhoang)){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //Đổi MK Khi quên MK
    public String LayMK(String TenTaiKhoang){
        String result = "";  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM TaiKhoang ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTaiKhoang = cursor.getString(0);
            //ktra đúng sai
            if (TenTaiKhoang.equals(zTaiKhoang)){
                result = cursor.getString(1);
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }


    //kiểm tra Tài Khoảng đúng hay sai để đăng nhập thành công
    public boolean KiemTraTaiKhoang(String CheckTaiKhoang, String CheckMatKhau){
        boolean result = false;  //tạo 1 tham số trả về đúng sai

        String sql = "SELECT * FROM TaiKhoang ";    //truy vấn vào bảng database TaiKhoang
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            String zTaiKhoang = cursor.getString(0);
            String zMatKhau = cursor.getString(1);
            //ktra đúng sai
            if (CheckTaiKhoang.equals(zTaiKhoang) && CheckMatKhau.equals(zMatKhau)){
                result = true;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        return result;
    }

    //lấy tất cả tài khoảng
    public List<TaiKhoang> AllTaiKhoang(){
        String sql = "SELECT * FROM TaiKhoang ";        //truy vấn tất cả trong bản TaiKhoang
        List<TaiKhoang> taiKhoangs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            String zTaiKhoang = cursor.getString(0);
            String zMatKhau = cursor.getString(1);
            TaiKhoang tk = new TaiKhoang(zTaiKhoang, zMatKhau);
            taiKhoangs.add(tk);
            cursor.moveToNext();
        }
        this.close();
        return taiKhoangs;
    }

    //Xóa TaiKhoang trong database
    public void DeleteTaiKhoang(String TenTK){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("TaiKhoang", "taikhoang" + "=?", new String[]{TenTK});
    }

    //đổi mật khẩu
    public int UpdateTaiKhoang(TaiKhoang taiKhoang){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        //contentValues.put("taikhoang", taiKhoang.getmTaiKhoang());
        contentValues.put("matkhau", taiKhoang.getmMatKhau());
        return sqLiteDatabase.update("TaiKhoang", contentValues, "taikhoang" + "=?", new String[]{taiKhoang.getmTaiKhoang()});
    }




}
