package com.example.qunlsv.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlsv.DoiTuong.BangDiem;
import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseBangDiem extends SQLiteOpenHelper {
    public DatabaseBangDiem(@Nullable Context context) {
        super(context, "BangDiem.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //khởi tại 1 bảng tên BangDiem với các cột có thuộc tính như sau
        String sqlTaiKhoangCreate = "CREATE TABLE IF NOT EXISTS " +
                "BangDiem(zAuto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "zMSSV INTEGER(200), " +
                "zMaMonHoc INTEGER(200), " +
                "zTenMonHoc VARCHAR(200), " +
                "zTC INTEGER(200), " +
                "zDiemThaiDo INTEGER(200), " +
                "zDiemKT INTEGER(200), " +
                "zTBBoPhan INTEGER(10), " +
                "zDiemThi INTEGER(200), " +
                "zTBMonH10 INTEGER(200), " +
                "zTBMonH4 INTEGER(200), " +
                "zDiemChu VARCHAR(200))";
        db.execSQL(sqlTaiKhoangCreate);
        String sql1 = "INSERT INTO BangDiem(zMSSV, zMaMonHoc, zTenMonHoc, zTC, zDiemThaiDo, zDiemKT, zTBBoPhan, zDiemThi, zTBMonH10, zTBMonH4, zDiemChu) VALUES('1', '1', 'tmh', '1', '1', '1', '1', '1', '1', '1', 'A')";
        db.execSQL(sql1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //thêm 1 bảng điểm cho 1 SinhVien
    public void TaoBangDiem(BangDiem bangDiem){
        SQLiteDatabase database = this.getWritableDatabase();   //phương thức getWritableDatabase() dùng để nghi lên Database

        //Không thể lưu trực tiếp 1 đối tượng vào thẳng Database mà phải thông qua ContenValues để lưu g.trị vào Database
        ContentValues contentValues = new ContentValues();

        contentValues.put("zMSSV", bangDiem.getmMSSV());
        contentValues.put("zMaMonHoc", bangDiem.getmMaMonHoc());
        contentValues.put("zTenMonHoc", bangDiem.getmTenMonHoc());
        contentValues.put("zTC", bangDiem.getmTC());
        contentValues.put("zDiemThaiDo", bangDiem.getmDiemThaiDo());
        contentValues.put("zDiemKT", bangDiem.getmDiemKT());
        contentValues.put("zTBBoPhan", bangDiem.getmTBBoPhan());
        contentValues.put("zDiemThi", bangDiem.getmDiemThi());
        contentValues.put("zTBMonH10", bangDiem.getmTBMonH10());
        contentValues.put("zTBMonH4", bangDiem.getmTBMonH4());
        contentValues.put("zDiemChu", bangDiem.getmDiemChu());
        //"taikhoang" & "matkhau" là 2 cột trong bảng TaiKhoang.

        database.insert("BangDiem", "", contentValues);//insert dữ liệu vừa thêm vào database
        database.close();
    }
    //kiểm Bảng điểm đã tồn tại chưa theo mssv mà mmh
    public boolean KtrBD(int MSSV, int MMH){
        boolean result = false;  //tạo 1 tham số trả về đúng sai
        String sql = "SELECT * FROM BangDiem ";    //truy vấn vào bảng database BangDiem
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn

        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.

        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int xMSSV = cursor.getInt(1);
            int xMMH = cursor.getInt(2);
            //ktra đúng sai
            if (MSSV == xMSSV && xMMH == MMH){
                result = true;      //đã tồn tại
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        this.close();
        return result;
    }
    //lấy tất cả MônHọc trong database theo MSSV
    public List<BangDiem> AllBD_mssv(int MSSV){
        String sql = "SELECT * FROM BangDiem ";        //truy vấn tất cả trong bản SinhVien
        List<BangDiem> bangDiems = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            int xMSSV = cursor.getInt(1);
            if (xMSSV == MSSV){
                int xMaMonHoc = cursor.getInt(2);
                String xTenMonHoc = cursor.getString(3);
                int xTC = cursor.getInt(4);
                int xDiemThaiDo = cursor.getInt(5);
                int xDiemKT = cursor.getInt(6);
                int xTBBoPhan = cursor.getInt(7);
                int xDiemThi = cursor.getInt(8);
                int xTBMonH10 = cursor.getInt(9);
                int xTBMonH4 = cursor.getInt(10);
                String xDiemChu = cursor.getString(11);
                BangDiem bangDiem = new BangDiem(xMSSV, xMaMonHoc, xTenMonHoc, xTC, xDiemThaiDo, xDiemKT, xTBBoPhan, xDiemThi, xTBMonH10, xTBMonH4, xDiemChu);
                bangDiems.add(bangDiem);
            }
            cursor.moveToNext();
        }
        this.close();
        return bangDiems;
    }
    //kiểm tra Bang diem đã có gtr chưa
    public int KtrBang(){
        int result = 0;
        String sql = "SELECT * FROM BangDiem ";    //truy vấn vào bảng database MonHoc
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int xAuto = cursor.getInt(0);
            if (xAuto > 0 ){
                result++;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        this.close();
        return result;
    }
    //kiểm tra Bang điểm có gtri MSSV và MMH chưa ( nếu có trả về zAuto )
    public int LayzAuto(int MSSV, int MaMonHoc){
        int result = 0;
        String sql = "SELECT * FROM BangDiem ";    //truy vấn vào bảng database MonHoc
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
        //isAfterLast(): sau cùng
        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
            int xMMSV = cursor.getInt(1);
            int xMMH = cursor.getInt(2);
            if (xMMH == MaMonHoc && xMMSV == MSSV){
                int xAuto = cursor.getInt(0);
                result = xAuto;
                break;
            }
            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
        }
        this.close();
        return result;
    }
    //lấy hàng điểm môn theo Auto
    public BangDiem LayBangDiem_auto(int Auto){
        String sql = "SELECT * FROM BangDiem ";        //truy vấn tất cả trong bản SinhVien
        BangDiem bangDiem = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){                                      //vòng lặp while chạy từ hàng đầu tiên tới hàng cuối cùng qua cursor
            int xAuto = cursor.getInt(0);
            if (xAuto == Auto){
                int xMSSV = cursor.getInt(1);
                int xMaMonHoc = cursor.getInt(2);
                String xTenMonHoc = cursor.getString(3);
                int xTC = cursor.getInt(4);
                int xDiemThaiDo = cursor.getInt(5);
                int xDiemKT = cursor.getInt(6);
                int xTBBoPhan = cursor.getInt(7);
                int xDiemThi = cursor.getInt(8);
                int xTBMonH10 = cursor.getInt(9);
                int xTBMonH4 = cursor.getInt(10);
                String xDiemChu = cursor.getString(11);
                bangDiem = new BangDiem(xMSSV, xMaMonHoc, xTenMonHoc, xTC, xDiemThaiDo, xDiemKT, xTBBoPhan, xDiemThi, xTBMonH10, xTBMonH4, xDiemChu);
                break;
            }
            cursor.moveToNext();
        }
        this.close();
        return bangDiem;
    }
    //Sửa bảng điểm
    public int UpdateDiem(BangDiem bangDiem){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTC", bangDiem.getmTC());
        contentValues.put("zDiemThaiDo", bangDiem.getmDiemThaiDo());
        contentValues.put("zDiemKT", bangDiem.getmDiemKT());
        contentValues.put("zTBBoPhan", bangDiem.getmTBBoPhan());
        contentValues.put("zDiemThi", bangDiem.getmDiemThi());
        contentValues.put("zTBMonH10", bangDiem.getmTBMonH10());
        contentValues.put("zTBMonH4", bangDiem.getmTBMonH4());
        contentValues.put("zDiemChu", bangDiem.getmDiemChu());
        return sqLiteDatabase.update("BangDiem", contentValues, "zAuto" + "=?", new String[]{String.valueOf(bangDiem.getmAuto())});
    }
    //xóa Cột điểm
    public int DeleteBangDiem(int MMH){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("BangDiem", "zMaMonHoc" + "=?", new String[]{String.valueOf(MMH)});
    }
    //Sửa Môn Học
    public int UpdateMonHoc(String TenMonHoc, int MMH){
        //cần quyền đọc database và viết lại nên ta dùng đến getReadableDatabase();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //không thể truyền thẳng 1 đối tuoq taikhoan vào database được mà phải thông qua ContentValues để put(key, value) vào, key là tên trường(cột), value giá trị thay đổi.
        ContentValues contentValues = new ContentValues();
        contentValues.put("zTenMonHoc", TenMonHoc);
        return sqLiteDatabase.update("BangDiem", contentValues, "zMaMonHoc" + "=?", new String[]{String.valueOf(MMH)});
    }
    //kiểm tra mmh đã có gtr chưa
//    public boolean KtrMMH(int MMH){
//        boolean result = false;
//        String sql = "SELECT * FROM BangDiem ";    //truy vấn vào bảng database
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);     //dùng cursor để điều hướng kết quả truy vấn
//        cursor.moveToFirst();       //moveToFirst : di chuyển con trỏ đến hàng đầu tiên trong kết quả truy vấn.
//        //isAfterLast(): sau cùng
//        while (!cursor.isAfterLast()){    //((!cursor.isAfterLast() là chưa đến hàng cuối cùng)
//            int xMMH = cursor.getInt(2);
//            if (xMMH == MMH ){
//                result = true;
//            }
//            cursor.moveToNext();      //moveToNext : di chuyển con trỏ đến hàng tiếp theo
//        }
//        this.close();
//        return result;
//    }
}
