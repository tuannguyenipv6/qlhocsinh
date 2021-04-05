package com.example.qunlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.DoiTuong.SinhVien;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity_Them_SinhVien extends AppCompatActivity {
    EditText newHoTen, newLop, newKhoa, newChucVu, newSDT, newDanToc, newNoiSinh, newMSSV;
    RadioGroup newRGioiTinh;
    TextView newNamSinh, newBtnOK, newBtnHuy;
    RadioButton newBNam, newBNu;
    String TaiKhoang = MainActivity.nTaiKhoang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__them__sinh_vien);
        anhxa();
        String TenLop = MainActivity_SinhVien.databaseTenLop.LayTenLop(TaiKhoang);
        newLop.setText(TenLop);

        newNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        //hủy thêm
        newBtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Thêm sinh viên
        newBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mnewHoTen = newHoTen.getText().toString().trim();
                    String mnewLop = newLop.getText().toString().trim();
                    String mnewNamSinh0 = newNamSinh.getText().toString().trim();
                    String mnewKhoa = newKhoa.getText().toString().trim();
                    String mnewChucVu = newChucVu.getText().toString().trim();
                    String mnewSDT = newSDT.getText().toString().trim();
                    String mnewDanToc = newDanToc.getText().toString().trim();
                    String mnewNoiSinh = newNoiSinh.getText().toString().trim();
                    String mssv = newMSSV.getText().toString().trim();

                    int mnewGioiTinh = 0;
                    if (newBNam.isChecked()){
                        mnewGioiTinh = 1;
                    }else {
                        mnewGioiTinh = 2;
                    }

                    if (mssv.length() == 0 || mnewNoiSinh.length() == 0 || mnewDanToc.length() == 0 || mnewHoTen.length() == 0 || mnewLop.length() == 0 || mnewKhoa.length() == 0 || mnewChucVu.length() == 0 || mnewSDT.length() == 0 || mnewNamSinh0.length() == 0){
                        Toast.makeText(MainActivity_Them_SinhVien.this, "Chưa Nhập Đủ Thông Tin! ", Toast.LENGTH_SHORT).show();
                    }else {
                        int mnewMSSV = Integer.parseInt(mssv);
                        if (MainActivity_DS_SV.databaseSinhVien.KtrMSSV(mnewMSSV)){
                            Toast.makeText(MainActivity_Them_SinhVien.this, "MSSV Đã Tồn Tại! ", Toast.LENGTH_SHORT).show();
                        }else {
                            //thêm sinh vien vào database
                            SinhVien sinhVien = new SinhVien(mnewMSSV, mnewHoTen, mnewLop, mnewNamSinh0, mnewKhoa, mnewGioiTinh, mnewChucVu, mnewSDT, "", mnewDanToc, mnewNoiSinh);
                            MainActivity_DS_SV.databaseSinhVien.ThemSinhVien(sinhVien, MainActivity.nTaiKhoang);
                            MainActivity_DS_SV. sinhViens.clear();
                            MainActivity_DS_SV.sinhViens.addAll(MainActivity_DS_SV.databaseSinhVien.AllSinhVien(TaiKhoang));
                            MainActivity_DS_SV.adapterSinhVien.notifyDataSetChanged();
                            finish();
                            Toast.makeText(MainActivity_Them_SinhVien.this, "Đã Thêm Sinh Viên: " + mnewHoTen, Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }



    private void anhxa(){
        newHoTen = (EditText) findViewById(R.id.newHoTen);
        newLop = (EditText) findViewById(R.id.newLop);
        newBtnOK = (TextView) findViewById(R.id.newBtnOK);
        newBtnHuy = (TextView) findViewById(R.id.newBtnHuy);
        newNamSinh = (TextView) findViewById(R.id.newNamSinh);
        newKhoa = (EditText) findViewById(R.id.newKhoa);
        newChucVu = (EditText) findViewById(R.id.newChucVu);
        newSDT = (EditText) findViewById(R.id.newSDT);
        newRGioiTinh = (RadioGroup) findViewById(R.id.newRGioiTinh);
        newBNam = (RadioButton) findViewById(R.id.newBNam);
        newBNu = (RadioButton) findViewById(R.id.newBNu);
        newDanToc = (EditText) findViewById(R.id.newDanToc);
        newNoiSinh = (EditText) findViewById(R.id.newNoiSinh);
        newMSSV = (EditText) findViewById(R.id.newMSSV);
    }
    private int XacDinhGioiTinh(){
        int result = 1;
        if (!newBNam.isChecked()){
            result = 0;
        }
        return result;
    }

    private void ChonNgay(){
        Calendar calendar = Calendar.getInstance();
        int Ngay = calendar.get(Calendar.DATE);
        int Thang = calendar.get(Calendar.MONTH);
        int Nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                newNamSinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, Nam, Thang, Ngay);
        datePickerDialog.show();
    }
}