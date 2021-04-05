package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseTaiKhoang;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    TextView DangNhap, T_TaiKhoang;
    EditText EdtTenTK, EdtTenMK;
    TextView DoiMatKhau, HoTro;
    CheckBox NhoTK;
    public static String nMatKhau = "";
    public static String nTaiKhoang = "";
    private Dialog dialog;
    public static DatabaseTaiKhoang databaseTaiKhoang;
    SharedPreferences sharedPreferences;    //khởi tạo SharedPreferences (dùng để lưu tên dn vào)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        //nhớ tk đăng nhập
        //khai báo đặt tên và dung kiểu MODE_PRIVATE, "datalogin"--tên tự đặt
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);

        //lấy giá trị đã lưu từ sharedPreferences

        EdtTenTK.setText(sharedPreferences.getString("taikhoang", ""));
        EdtTenMK.setText(sharedPreferences.getString("matkhau", ""));//tên và giá trị mặt định nếu k có        CkbNhoTK.setChecked(sharedPreferences.getBoolean("checked", false));
        NhoTK.setChecked(sharedPreferences.getBoolean("checked", false));

        //vào dialog tạo tài khoảng
        T_TaiKhoang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaLogTaiKhoang();
            }
        });

        //đăng nhập thành công
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTaiKhoang = EdtTenTK.getText().toString().trim();
                nMatKhau = EdtTenMK.getText().toString().trim();

                if (nTaiKhoang.length() > 0 && nMatKhau.length() > 0){
                    if (MainActivity.databaseTaiKhoang.KiemTraTaiKhoang(nTaiKhoang, nMatKhau)){
                        //chuyển qua màn hình DS sinh viên
                        Intent intent = new Intent(MainActivity.this, MainActivity_GiaoDien.class);
                        startActivity(intent);  //chạy intent bằng lênh start
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        //luu tài khoảng
                        TaiKhoang taiKhoang = new TaiKhoang(nTaiKhoang, nMatKhau);
                        LuuTaiKhoang(taiKhoang);
                    }else {
                        Toast.makeText(MainActivity.this, "Nhập sai khoảng mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Hỗ trợ
        HoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHoTro();
            }
        });

        //Đổi mật khẩu
        DoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDoiMatKhau();
            }
        });


    }





    //ánh xạ cho các view khai báo
    private void anhxa(){
        DangNhap = (TextView) findViewById(R.id.DangNhap);
        T_TaiKhoang = (TextView) findViewById(R.id.T_TaiKhoan);
        EdtTenTK = (EditText) findViewById(R.id.TenTK);
        EdtTenMK = (EditText) findViewById(R.id.TenMK);
        NhoTK = (CheckBox) findViewById(R.id.NhoTK);
        databaseTaiKhoang = new DatabaseTaiKhoang(MainActivity.this);
        DoiMatKhau = (TextView) findViewById(R.id.DoiMatKhau);
        HoTro = (TextView) findViewById(R.id.HoTro);
        DoiMatKhau.setText(Html.fromHtml("<u>Đổi Mật Khẩu?</u>"));
        HoTro.setText(Html.fromHtml("<u>Hỗ Trợ?</u>"));
    }
    private void LuuTaiKhoang(TaiKhoang taiKhoang){
        //nếu có check thì lưu lại tk mk
        if (NhoTK.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();  //mỡ file và nghi nội dung vào qua editor
            editor.putString("taikhoang", taiKhoang.getmTaiKhoang());  // đưa vào tên và giá trị lưu
            editor.putString("matkhau", taiKhoang.getmMatKhau());
            editor.putBoolean("checked", true);
            editor.commit();    //xác nhận đưa vào tk mk bằng lệnh commit();
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("taikhoang");
            editor.remove("matkhau");   //xóa gtri đã lưu nếu ng dùng bỏ check
            editor.remove("checked");
            editor.commit();    //xác nhận bằng commit
        }
    }
    //Dialog (hộp thoại) tạo tài khoảng
    private void DiaLogTaiKhoang(){
        dialog = new Dialog(this);  //khởi tạo dialog

        dialog.setContentView(R.layout.dialog_t_taikhoan); //set khung mình đã tạo sẳn:

        //ánh xạ các view trong Dialog
        EditText EdtT_TK = (EditText) dialog.findViewById(R.id.TK_Tao);
        EditText EdtT_MK = (EditText) dialog.findViewById(R.id.MK_Tao);
        TextView X_N_Tao = (TextView) dialog.findViewById(R.id.X_N_Tao);
        TextView Huy_Tao = (TextView) dialog.findViewById(R.id.Huy_Tao_TK);

        //Hủy Tạo TK
        Huy_Tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();     //hủy hiển thị dialog
            }
        });

        //Xác nhận tạo tk (lưu tk mk vào Database)
        X_N_Tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoang = EdtT_TK.getText().toString().trim();
                String matkhau = EdtT_MK.getText().toString().trim();

                if (taikhoang.length() > 0 && matkhau.length() > 0){
                    if (!databaseTaiKhoang.KtrTaiKhoang(taikhoang)){
                        TaiKhoang taiKhoang = new TaiKhoang(taikhoang, matkhau);
                        databaseTaiKhoang.ThemTaiKhoang(taiKhoang);
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Tài khoảng đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //hiển thị dialog bằng lệnh show:
        dialog.show();
    }



    //dialog hỗ trợ
    private void DialogHoTro(){
        AlertDialog.Builder dialogHoTrO = new AlertDialog.Builder(this);
        //tên thông báo
        dialogHoTrO.setTitle("Ứng dụng do nhóm 4 thực hiện gồm:");
        //Nội dung thông báo
        dialogHoTrO.setMessage("\n+Nguyễn Quốc Tuấn\n+Nguyễn Quốc Doanh\n+Bàng Tuấn Bình\n+Nguyễn Văn Phú.");
        //icon của thông báo
        dialogHoTrO.setIcon(R.drawable.hotro);

        dialogHoTrO.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPause();
            }
        });
        dialogHoTrO.show();
    }



    //dialog hộp thoại đổi mật khẩu
    private void DialogDoiMatKhau(){
        dialog = new Dialog(this);  //khởi tạo dialog
        dialog.setContentView(R.layout.dialog_doi_mk); //set khung mình đã tạo sẳn:

        //anh xạ
        EditText DoiTK = (EditText) dialog.findViewById(R.id.DoiTK);
        EditText DoiMKCu = (EditText) dialog.findViewById(R.id.DoiMKCu);
        EditText DoiMKMoi = (EditText) dialog.findViewById(R.id.DoiMKMoi);
        Button BtnHuy = (Button) dialog.findViewById(R.id.HuyDoimk);
        Button BtnDoi = (Button) dialog.findViewById(R.id.Doi);

        //hủy đổi
        BtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //xác nhận tạo tài khoảng và bắt lỗi người dùng
        BtnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenTaiKhoang = DoiTK.getText().toString().trim();
                String MKCu = DoiMKCu.getText().toString().trim();
                String MKMoi = DoiMKMoi.getText().toString().trim();
                //bắt lỗi ng dùng nhập k đủ thông tin
                if (TenTaiKhoang.length() > 0 && MKCu.length() > 0 && MKMoi.length() > 0){
                    //bắt lỗi ng dùng nhập sai tên TK
                    if (databaseTaiKhoang.KtrTaiKhoang(TenTaiKhoang)){
                        //bắt lỗi nhập sai mk củ
                        if (MKCu.equals(databaseTaiKhoang.LayMK(TenTaiKhoang))){
                            //tạo TK Thành công
                            TaiKhoang taiKhoang = new TaiKhoang(TenTaiKhoang, MKMoi);
                            if (databaseTaiKhoang.UpdateTaiKhoang(taiKhoang) > 0){
                                Toast.makeText(MainActivity.this, "Đã đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }else {
                                //phát sinh lỗi k mong muốn.
                                Toast.makeText(MainActivity.this, "Không Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Mật khẩu củ không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Tài Khoảng không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //hiển thị dialog bằng lệnh show:
        dialog.show();
    }



    //icon nhóm 4
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nhom4, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //bắt sự kiện icon nhóm 4
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nhom4){
            Intent intent = new Intent(MainActivity.this, MainActivity_nhom4.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}