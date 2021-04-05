package com.example.qunlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class MainActivity_Update_SinhVien extends AppCompatActivity {
    EditText uHoTen, uLop, uKhoa, uChucVu, uSDT, uDanToc, uNoiSinh;
    TextView uBtnOK, uBtnHuy, uNamSinh;
    RadioGroup uRGioiTinh;
    RadioButton uBNam, uBNu;
    int MSSV = 0;
    String xGhiChu = "";
    String TaiKhoan = MainActivity.nTaiKhoang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__update__sinh_vien);
        anhxa();

        uNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        //gán lại các thuộc tính củ của sinh viên update vào view

        Intent intent = getIntent();
        int MSSV = intent.getIntExtra("vitri", 0);
        SinhVien sinhVien = MainActivity_DS_SV.databaseSinhVien.LaySinhVien(MSSV);
        xGhiChu = sinhVien.getmNghiChu();

        uHoTen.setText(sinhVien.getmTen());
        uLop.setText(sinhVien.getmLop());
        uNamSinh.setText(String.valueOf(sinhVien.getmNamSinh()));
        uChucVu.setText(sinhVien.getmChucVu());
        uKhoa.setText(sinhVien.getmKhoa());
        uSDT.setText(sinhVien.getmSDT());
        uDanToc.setText(sinhVien.getmDanToc());
        uNoiSinh.setText(sinhVien.getmNoiSinh());

        if (sinhVien.getmGioiTinh() == 1){
            uBNam.setChecked(true);
        }else {
            uBNu.setChecked(true);
        }

        //hủy update
        uBtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Update lại SinhVien
        uBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upHoTen = uHoTen.getText().toString().trim();
                String upLop = uLop.getText().toString().trim();
                String upNamSinh0 = uNamSinh.getText().toString().trim();
                String upKhoa = uKhoa.getText().toString().trim();
                String upChucVu = uChucVu.getText().toString().trim();
                String upSDT = uSDT.getText().toString().trim();
                String upDanToc = uDanToc.getText().toString().trim();
                String upNoiSinh = uNoiSinh.getText().toString().trim();
                int upGioiTinh = 0;
                if (uBNam.isChecked()){
                    upGioiTinh = 1;
                }else {
                    upGioiTinh = 2;
                }

                if (upDanToc.length() == 0 || upNoiSinh.length() == 0 || upHoTen.length() == 0 || upLop.length() == 0 || upNamSinh0.length() == 0 || upKhoa.length() == 0 || upChucVu.length() == 0 || upSDT.length() == 0){
                    Toast.makeText(MainActivity_Update_SinhVien.this, "Chưa Nhập Đủ Thông Tin! ", Toast.LENGTH_SHORT).show();
                }else {
                    //sửa sinh viên
                    SinhVien sinhVien = new SinhVien(MSSV, upHoTen, upLop, upNamSinh0, upKhoa, upGioiTinh, upChucVu, upSDT, xGhiChu, upDanToc, upNoiSinh);
                    int result = MainActivity_DS_SV.databaseSinhVien.UpdateSinhVien(sinhVien);
                    if (result > 0){
                        MainActivity_DS_SV. sinhViens.clear();
                        MainActivity_DS_SV.sinhViens.addAll(MainActivity_DS_SV.databaseSinhVien.AllSinhVien(TaiKhoan));
                        MainActivity_DS_SV.adapterSinhVien.notifyDataSetChanged();
                        finish();
                        Toast.makeText(MainActivity_Update_SinhVien.this, "Thành công!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity_Update_SinhVien.this, "Thất Bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    //ãnh xạ
    private void anhxa(){
        uHoTen = (EditText) findViewById(R.id.uHoTen);
        uLop = (EditText) findViewById(R.id.uLop);
        uBtnOK = (TextView) findViewById(R.id.uBtnOK);
        uBtnHuy = (TextView) findViewById(R.id.uBtnHuy);
        uNamSinh = (TextView) findViewById(R.id.uNamSinh);
        uKhoa = (EditText) findViewById(R.id.uKhoa);
        uChucVu = (EditText) findViewById(R.id.uChucVu);
        uSDT = (EditText) findViewById(R.id.uSDT);
        uRGioiTinh = (RadioGroup) findViewById(R.id.uRGioiTinh);
        uBNam = (RadioButton) findViewById(R.id.uBNam);
        uBNu = (RadioButton) findViewById(R.id.uBNu);
        uDanToc = (EditText) findViewById(R.id.uDanToc);
        uNoiSinh = (EditText) findViewById(R.id.uNoiSinh);
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
                uNamSinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, Nam, Thang, Ngay);
        datePickerDialog.show();
    }
}