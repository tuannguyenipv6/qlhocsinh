package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdaprTaiKhoang;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_DS_TaiKhoang extends AppCompatActivity {
    AdaprTaiKhoang adapterTaiKhoang;
    ListView LVTaiKhoang;
    List<TaiKhoang> taiKhoangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__d_s__tai_khoan);

        LVTaiKhoang = (ListView) findViewById(R.id.LVTaiKhoang);
        taiKhoangs = new ArrayList<TaiKhoang>();
        taiKhoangs = MainActivity.databaseTaiKhoang.AllTaiKhoang();     //lấy tất cả TaiKhoang từ databaseTaiKhoang đã khởi tạo
        setAdapterTK();

        //xóa TaiKhoang
        LVTaiKhoang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String TentaiKhoang = taiKhoangs.get(position).getmTaiKhoang();
                    if (TentaiKhoang.equals(MainActivity.nTaiKhoang)){
                        Toast.makeText(MainActivity_DS_TaiKhoang.this, "Không thể xóa tài khoảng đang đăng nhập", Toast.LENGTH_SHORT).show();
                    }else {
                        XacNhanXoa(TentaiKhoang);
                    }
                return false;
            }
        });

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

    //set Adapter
     public void setAdapterTK(){
        if (adapterTaiKhoang == null){
            adapterTaiKhoang = new AdaprTaiKhoang(MainActivity_DS_TaiKhoang.this, R.layout.item_dong_taikhoan, taiKhoangs);
            LVTaiKhoang.setAdapter(adapterTaiKhoang);
        }else {
            if (taiKhoangs != null){
                taiKhoangs.clear();
            }
            taiKhoangs.addAll(MainActivity.databaseTaiKhoang.AllTaiKhoang());
            adapterTaiKhoang.notifyDataSetChanged();
            //chuyển suống dòng cuối cùng của listview
            LVTaiKhoang.setSelection(adapterTaiKhoang.getCount() - 1);
        }
    }

    //thông báo xn xóa TK
    private void XacNhanXoa(String TenTk){
        AlertDialog.Builder dialogXNX = new AlertDialog.Builder(this);
        //tên thông báo
        dialogXNX.setTitle("Thông Báo!");
        //Nội dung thông báo
        dialogXNX.setMessage("Bạn muốn xóa TK: (" + TenTk + ")");
        //icon của thông báo
        dialogXNX.setIcon(R.drawable.rsz_inconxoa);

        //bắt sự kiện đồng ý xoa
        dialogXNX.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.databaseTaiKhoang.DeleteTaiKhoang(TenTk);
                setAdapterTK();
                Toast.makeText(MainActivity_DS_TaiKhoang.this, "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        //bắt sự kiện không đồng ý
        dialogXNX.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity_DS_TaiKhoang.this, "Bạn đã hoàn tác", Toast.LENGTH_SHORT).show();
            }
        });
        dialogXNX.show();
    }
}