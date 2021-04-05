package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.qunlsv.Adapter.AdapterNhom4;
import com.example.qunlsv.DoiTuong.Nhom4;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_nhom4 extends AppCompatActivity {
    ListView LVNHOM;
    AdapterNhom4 adapterNhom4;
    List<Nhom4> nhom4s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nhom4);
        LVNHOM = (ListView) findViewById(R.id.LVNHOM);
        nhom4s = new ArrayList<Nhom4>();
        nhom4s.add(new Nhom4("Nguyễn Quốc Tuấn", "18CCT", "241766168", R.drawable.tuantv));
        nhom4s.add(new Nhom4("Bàng Tuấn Bình", "18CCT", "25948959", R.drawable.binhtv));
        nhom4s.add(new Nhom4("Nguyễn Quốc Doanh", "18CCT", "381890290", R.drawable.doanhtv));
        nhom4s.add(new Nhom4("Nguyễn Văn Phú", "18CCT", "02333303", R.drawable.phutv));
        adapterNhom4 = new AdapterNhom4(MainActivity_nhom4.this, R.layout.item_dong_nhom4, nhom4s);
        LVNHOM.setAdapter(adapterNhom4);
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
}