package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdapterBangDiem;
import com.example.qunlsv.Database.DatabaseBangDiem;
import com.example.qunlsv.Database.DatabaseMonHoc;
import com.example.qunlsv.DoiTuong.BangDiem;
import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.SinhVien;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_BDSV extends AppCompatActivity {
    ScaleGestureDetector scaleGestureDetector;
    public static ListView LVBSV;
    public static AdapterBangDiem adapterBangDiem;
    public static List<BangDiem> bangDiems;
    SinhVien sinhVien;
    DatabaseMonHoc databaseMonHoc;
    DatabaseBangDiem databaseBangDiem;
    List<MonHoc> monHocs;
    String TaiKhoan = MainActivity.nTaiKhoang;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__b_d_s_v);
        anhxa();
        monHocs = databaseMonHoc.AllMonHoc(TaiKhoan);

        //lấy MSSV và lấy sinh viên theo mssv
        Intent intent1 = getIntent();
        int STT = intent1.getIntExtra("datamssv", 0);
        sinhVien = MainActivity_DS_SV.databaseSinhVien.LaySinhVien(STT);

        //phóng to thu nhỏ
        scaleGestureDetector = new ScaleGestureDetector(this, new MyGesture());
        //không áp dụng được cho Listview nên k bỏ vào scaleGestureDetector
//        LVBSV.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                scaleGestureDetector.onTouchEvent(event);
//                return true;
//            }
//        });

        try {
            //tạo bảng điểm nếu chưa có
            for (MonHoc x:monHocs ){
                if (!databaseBangDiem.KtrBD(sinhVien.getmMSSV(), x.getmMaMonHoc())){
                    BangDiem bangDiem = new BangDiem(sinhVien.getmMSSV(), x.getmMaMonHoc(), x.getTenMonHoc());
                    databaseBangDiem.TaoBangDiem(bangDiem);
                }
            }
        }catch (Exception e){
            BangDiem bangDiem1 = new BangDiem(20, 10010, "Mặt định");
            //tạo bảng điểm nếu chưa có
            for (MonHoc x:monHocs ){
                if (!databaseBangDiem.KtrBD(sinhVien.getmMSSV(), x.getmMaMonHoc())){
                    BangDiem bangDiem = new BangDiem(sinhVien.getmMSSV(), x.getmMaMonHoc(), x.getTenMonHoc());
                    databaseBangDiem.TaoBangDiem(bangDiem);
                }
            }
        }

        //lấy bảng điểm theo mssv
        bangDiems = databaseBangDiem.AllBD_mssv(sinhVien.getmMSSV());
        bangDiems.add(0, new BangDiem(321, "Tên Môn Học"));
        //đổ dữ liệu ra ListView
        adapterBangDiem = new AdapterBangDiem(MainActivity_BDSV.this, R.layout.item_dong_bangdiem, bangDiems);
        LVBSV.setAdapter(adapterBangDiem);
    }
    //các funsion dùng để phóng to thu nhỏ
    class MyGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        //khai báo 3 biến lll: biến lấy giá trị phóng ban đầu, biến giữ giá trị phóng, và biến cuôi cùng kết thúc phóng
        float scale = 1.0F, onScaleStart = 0, onScaleEnd = 0;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //gọi lại biến bắt đầu "scale" nhân cho tỉ lệ mà người dùng phóng "detector.getScaleFactor();"
            scale *= detector.getScaleFactor();
            LVBSV.setScaleX(scale); //chiều rộn
            LVBSV.setScaleY(scale); //chiều cao
            return super.onScale(detector);
        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            //lần lần phóng tiếp theo t cũng bắt nó tự độ phóng trk đó và gán vào onScaleStart
            onScaleStart = scale;
            return super.onScaleBegin(detector);
        }
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            //tương tự khi phóng kết thúc thì gán lại vào onScaleEnd
            onScaleEnd = scale;
            super.onScaleEnd(detector);
        }
    }
    private void anhxa(){
        databaseBangDiem = new DatabaseBangDiem(this);
        databaseMonHoc = new DatabaseMonHoc(this);
        LVBSV = (ListView) findViewById(R.id.LVBDSV);
        bangDiems = new ArrayList<BangDiem>();

    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bdsv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện 2 nút thoát và Sửa điểm.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnSuaDiem:
                DialogMMH();
                break;
            case R.id.mnThoatDiem:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //dialog hỏi Mã Môn Học để sửa điểm cho sinh viên
    private void DialogMMH(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_mat_ma_truong);

        //ánh xạ
        EditText edtMMH = (EditText) dialog.findViewById(R.id.MatMaNhap);
        Button BtnOK = (Button) dialog.findViewById(R.id.OK);
        Button BtnHuy = (Button) dialog.findViewById(R.id.Huy_MM);
        TextView TXTMM = (TextView) dialog.findViewById(R.id.TXTMM);
        //set lại tên dialog
        TXTMM.setText("Sửa điểm sinh viên:\n" + sinhVien.getmTen());
        edtMMH.setHint("Nhập Mã Môn Học!");
        //set lại chiều dài nhập vào
        edtMMH.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(100) });
        //set lại kiểu nhập!
        edtMMH.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

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
                int MMH = Integer.parseInt(edtMMH.getText().toString().trim());
                int zAuto = databaseBangDiem.LayzAuto(sinhVien.getmMSSV(), MMH);
                if (zAuto > 0){
                    Intent intent = new Intent(MainActivity_BDSV.this, MainActivity_SuaDiem.class);
                    intent.putExtra("mAuto", zAuto);
                    startActivity(intent);
                    dialog.cancel();
                }else Toast.makeText(MainActivity_BDSV.this, "Không tìm thấy Mã Môn Học này!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();     //hiển thị dialog
    }
}