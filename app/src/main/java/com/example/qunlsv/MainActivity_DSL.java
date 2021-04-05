package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdapterBangChon;
import com.example.qunlsv.Database.DatabaseTenLop;
import com.example.qunlsv.DoiTuong.BangChon;
import com.example.qunlsv.DoiTuong.TenLop;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_DSL extends AppCompatActivity {
    ListView LVDSL;
    ArrayList<BangChon> bangChons;
    AdapterBangChon adapterBangChon;
    List<String> tenLops;
    DatabaseTenLop databaseTenLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__d_s_l);
        anhxa();
        tenLops = databaseTenLop.AllTenLop();
        if (tenLops.size() == 0 ){
            Toast.makeText(MainActivity_DSL.this, "Chưa có lớp nào!", Toast.LENGTH_SHORT).show();
        }else {
            for (String x : tenLops){
                BangChon bangChon = new BangChon(x, R.drawable.iconlop);
                bangChons.add(bangChon);
            }
            adapterBangChon = new AdapterBangChon(MainActivity_DSL.this, R.layout.item_dong_bang_chon, bangChons);
            LVDSL.setAdapter(adapterBangChon);
        }
    }
    private void anhxa(){
        LVDSL = (ListView) findViewById(R.id.LVDSL);
        bangChons = new ArrayList<>();
        tenLops = new ArrayList<String>();
        databaseTenLop = new DatabaseTenLop(this);
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