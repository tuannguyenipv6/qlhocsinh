package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdapterBangChon;
import com.example.qunlsv.Database.DatabaseSinhVien;
import com.example.qunlsv.DoiTuong.BangChon;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;

public class MainActivity_GiaoDien extends AppCompatActivity {
    ArrayList<BangChon> arrayList;
    ListView LVBangChon;
    AdapterBangChon adapterBangChon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__giao_dien);
        anhxa();
        adapterBangChon = new AdapterBangChon(MainActivity_GiaoDien.this, R.layout.item_dong_bang_chon, arrayList);
        LVBangChon.setAdapter(adapterBangChon);
        LVBangChon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity_GiaoDien.this, MainActivity_SinhVien.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity_GiaoDien.this, MainActivity_TKB.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(MainActivity_GiaoDien.this, MainActivity_DSL.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent1 = new Intent(MainActivity_GiaoDien.this, MainActivity_nhom4.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }
    private void anhxa(){
        arrayList = new ArrayList<BangChon>();
        LVBangChon = (ListView) findViewById(R.id.LVBangChon);
        arrayList.add(new BangChon("Thông Tin Sinh Viên", R.drawable.dshocsinh));
        arrayList.add(new BangChon("Thời Khóa Biểu", R.drawable.tkb));
        arrayList.add(new BangChon("Xem DS Lớp Hiện Có", R.drawable.dslop));
        arrayList.add(new BangChon("Nhóm 4 Thực Hiện!", R.drawable.nhom));
    }
    //menu của DS TaiKhoang
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_out, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện nút thoát
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dangxuat){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}