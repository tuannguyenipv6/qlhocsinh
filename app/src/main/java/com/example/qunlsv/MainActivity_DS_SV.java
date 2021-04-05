package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdapterSinhVien;
import com.example.qunlsv.Database.DatabaseSinhVien;
import com.example.qunlsv.DoiTuong.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_DS_SV extends AppCompatActivity {
    private Dialog dialog;
    public static DatabaseSinhVien databaseSinhVien;
    public static AdapterSinhVien adapterSinhVien;
    public static List<SinhVien> sinhViens;
    public static ListView LV_SV;
    String TaiKhoan = MainActivity.nTaiKhoang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__d_s__s_v);
        anhxa();

        if (sinhViens != null){
            sinhViens.clear();
            sinhViens.addAll(databaseSinhVien.AllSinhVien(TaiKhoan));
            adapterSinhVien.notifyDataSetChanged();
            LV_SV.setAdapter(adapterSinhVien);
        }else {
            sinhViens = databaseSinhVien.AllSinhVien(TaiKhoan);
            setAdapterSinhVien(this);
        }


    }



    //ánh xạ
    private void anhxa(){
        LV_SV = (ListView) findViewById(R.id.LV_SV);
        databaseSinhVien = new DatabaseSinhVien(MainActivity_DS_SV.this);
        sinhViens = new ArrayList<SinhVien>();
        adapterSinhVien = new AdapterSinhVien(MainActivity_DS_SV.this, R.layout.item_dong_sinhvien, sinhViens, MainActivity.nTaiKhoang);
    }



    //setAdapter
    public static void setAdapterSinhVien(Context context){
        if (adapterSinhVien == null){
            adapterSinhVien = new AdapterSinhVien(context, R.layout.item_dong_sinhvien, sinhViens, MainActivity.nTaiKhoang);
            LV_SV.setAdapter(adapterSinhVien);
        }else {
            if (sinhViens != null){
                sinhViens.clear();
            }
            LV_SV.setAdapter(adapterSinhVien);
            sinhViens.addAll(databaseSinhVien.AllSinhVien(MainActivity.nTaiKhoang));
            adapterSinhVien.notifyDataSetChanged();
            //chuyển suống dòng cuối cùng của listview
            LV_SV.setSelection(adapterSinhVien.getCount() - 1);
        }
    }

    //menu của ds sv
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ds_sv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện menu của ds dv
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){      //item.getItemId() là cái mà người dùng click vào
            case R.id.mnThoat:
                finish();
                break;
            case R.id.mnSua:
                DiaViTriUpdate();
                break;
            case R.id.mnThem:
                Intent intent = new Intent(MainActivity_DS_SV.this, MainActivity_Them_SinhVien.class);
                startActivity(intent);
                break;
            case R.id.mnDSTaiKhoang:
                DialogMatMa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //dialog xác nhận mật mã để xem được TK & MK
    private void DialogMatMa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_mat_ma_truong);

        //ánh xạ
        EditText EdtMM = (EditText) dialog.findViewById(R.id.MatMaNhap);
        Button BtnOK = (Button) dialog.findViewById(R.id.OK);
        Button BtnHuy = (Button) dialog.findViewById(R.id.Huy_MM);

        //bắt sự kiện hủy
        BtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //Bắt sự kiện đồng ý
        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MM = EdtMM.getText().toString().trim();
                if(MM.equals(MainActivity.nMatKhau)){       //So sánh với MK Đã đăng nhập ở login
                    Intent intent = new Intent(MainActivity_DS_SV.this, MainActivity_DS_TaiKhoang.class);       //chuyển qua màn hình DS TaiKhoang
                    startActivity(intent);      //chạy Intent
                    dialog.cancel();        //thoát dialog
                }else {
                    Toast.makeText(MainActivity_DS_SV.this, "Bạn nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();     //hiển thị dialog
    }

    //dialog Hỏi Vị trí update
    private void DiaViTriUpdate(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hoi_stt_update);

        //ánh xạ
        EditText uSTT = (EditText) dialog.findViewById(R.id.uSTT);
        Button uOK = (Button) dialog.findViewById(R.id.uOK);
        Button uHuy = (Button) dialog.findViewById(R.id.uHuy);

        //bắt sự kiện hủy
        uHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //Bắt sự kiện đồng ý
        uOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int STT = Integer.parseInt(uSTT.getText().toString().trim());
                if (databaseSinhVien.KtrMSSV(STT)){
                    Intent intent = new Intent(MainActivity_DS_SV.this, MainActivity_Update_SinhVien.class);
                    intent.putExtra("vitri", STT);
                    startActivity(intent);
                    dialog.cancel();
                }else {
                    Toast.makeText(MainActivity_DS_SV.this, "Không tồn tại MSSV này", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


}