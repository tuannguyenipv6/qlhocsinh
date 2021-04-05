package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseBangDiem;
import com.example.qunlsv.Database.DatabaseSinhVien;
import com.example.qunlsv.DoiTuong.BangDiem;
import com.example.qunlsv.DoiTuong.SinhVien;

public class MainActivity_SuaDiem extends AppCompatActivity {
    TextView TSVCND, MHCND;
    DatabaseBangDiem databaseBangDiem;
    BangDiem bangDiem;
    DatabaseSinhVien databaseSinhVien;
    EditText CN_TC, CN_DThaiDo, CN_DiemKT, CN_TBBoPhan, CN_DiemThi, CN_He10, CN_He4, CN_DiemChu;
    Button CN_OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__sua_diem);
        anhxa();
        //lấy zAuto
        Intent intent = getIntent();
        int zAuto = intent.getIntExtra("mAuto", 0);
        bangDiem = databaseBangDiem.LayBangDiem_auto(zAuto);
       //lấy sinh viên
        SinhVien sinhVien = databaseSinhVien.LaySinhVien(bangDiem.getmMSSV());
        if (sinhVien == null) Toast.makeText(MainActivity_SuaDiem.this, "lỗi lấy SinhVien", Toast.LENGTH_SHORT).show();
        //set lại tên và môn vào tiêu đề màn hình
        TSVCND.setText("Sinh Viên: " + sinhVien.getmTen());
        MHCND.setText("Môn Học: " + bangDiem.getmTenMonHoc());
        getLaiGiaTri();
        //bắt sự kiện click ok lưu lại hàng điểm mới vào database
        CN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TC = CN_TC.getText().toString().trim();
                String DiemThaiDo = CN_DThaiDo.getText().toString().trim();
                String DiemKT = CN_DiemKT.getText().toString().trim();
                String TBBoPhan = CN_TBBoPhan.getText().toString().trim();
                String DiemThi = CN_DiemThi.getText().toString().trim();
                String TBMonH10 = CN_He10.getText().toString().trim();
                String TBMonH4 = CN_He4.getText().toString().trim();
                String mDiemChu = CN_DiemChu.getText().toString().trim();
                if (TC.length() > 0 && DiemThaiDo.length() > 0 && DiemKT.length() > 0 && TBBoPhan.length() > 0 && DiemThi.length() > 0 && TBMonH10.length() > 0 && TBMonH4.length() > 0 && mDiemChu.length() > 0){
                    int mTC = Integer.parseInt(TC);
                    int mDiemThaiDo = Integer.parseInt(DiemThaiDo);
                    int mDiemKT = Integer.parseInt(DiemKT);
                    int mTBBoPhan = Integer.parseInt(TBBoPhan);
                    int mDiemThi = Integer.parseInt(DiemThi);
                    int mTBMonH10 = Integer.parseInt(TBMonH10);
                    int mTBMonH4 = Integer.parseInt(TBMonH4);
                    BangDiem bangDiem1 = new BangDiem(zAuto, mTC, mDiemThaiDo, mDiemKT, mTBBoPhan, mDiemThi, mTBMonH10, mTBMonH4, mDiemChu);
                    int i = databaseBangDiem.UpdateDiem(bangDiem1);
                    if (i > 0){
                        Toast.makeText(MainActivity_SuaDiem.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        MainActivity_BDSV.bangDiems.clear();
                        MainActivity_BDSV.bangDiems.addAll(databaseBangDiem.AllBD_mssv(bangDiem.getmMSSV()));
                        MainActivity_BDSV.bangDiems.add(0, new BangDiem(321, "Tên Môn Học"));
                        MainActivity_BDSV.adapterBangDiem.notifyDataSetChanged();
                        finish();
                    }else Toast.makeText(MainActivity_SuaDiem.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(MainActivity_SuaDiem.this, "Nhập đầy đủ\nĐể số (0) hoặc (i) nếu chưa có!",  Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa(){
        CN_OK = (Button) findViewById(R.id.CN_OK);
        databaseSinhVien = new DatabaseSinhVien(this);
        databaseBangDiem = new DatabaseBangDiem(this);
        MHCND = (TextView) findViewById(R.id.MHCND);
        TSVCND = (TextView) findViewById(R.id.TSVCND);
        CN_TC = (EditText) findViewById(R.id.CN_TC);
        CN_DThaiDo = (EditText) findViewById(R.id.CN_DThaiDo);
        CN_DiemKT = (EditText) findViewById(R.id.CN_DiemKT);
        CN_TBBoPhan = (EditText) findViewById(R.id.CN_TBBoPhan);
        CN_DiemThi = (EditText) findViewById(R.id.CN_DiemThi);
        CN_He10 = (EditText) findViewById(R.id.CN_He10);
        CN_He4 = (EditText) findViewById(R.id.CN_He4);
        CN_DiemChu = (EditText) findViewById(R.id.CN_DiemChu);
    }
    //menu của DS TaiKhoang
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ds_tk, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //bắt sự kiện nút thoát
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tkmnThoat){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //gán lại giá trị vào các view
    private void getLaiGiaTri(){
        CN_TC.setText(String.valueOf(bangDiem.getmTC()));
        CN_DThaiDo.setText(String.valueOf(bangDiem.getmDiemThaiDo()));
        CN_DiemKT.setText(String.valueOf(bangDiem.getmDiemKT()));
        CN_TBBoPhan.setText(String.valueOf(bangDiem.getmTBBoPhan()));
        CN_DiemThi.setText(String.valueOf(bangDiem.getmDiemThi()));
        CN_He10.setText(String.valueOf(bangDiem.getmTBMonH10()));
        CN_He4.setText(String.valueOf(bangDiem.getmTBMonH4()));
        CN_DiemChu.setText(bangDiem.getmDiemChu());
    }
}