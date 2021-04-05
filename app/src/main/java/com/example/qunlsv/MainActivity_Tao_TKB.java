package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qunlsv.DoiTuong.TKB;

public class MainActivity_Tao_TKB extends AppCompatActivity {
    Button OK_TaoTKB;
    EditText ST2, ST3, ST4, ST5, ST6, ST7, ST8, CT2, CT3, CT4, CT5, CT6, CT7,CT8;
    String mST2, mST3, mST4, mST5, mST6, mST7, mST8, mCT2, mCT3, mCT4, mCT5, mCT6, mCT7, mCT8;
    String TaiKhoang = MainActivity.nTaiKhoang;
    TKB tkb1, tkb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__tao__t_k_b);
        anhxa();
        //gán lại TKB củ vào các view
        if (MainActivity_TKB.databaseTKB.KtraTK(TaiKhoang)){
            tkb1 = MainActivity_TKB.databaseTKB.LayTKB(TaiKhoang, 1);
            tkb2 = MainActivity_TKB.databaseTKB.LayTKB(TaiKhoang, 2);
            if (tkb1 == null || tkb2 == null){
                Toast.makeText(MainActivity_Tao_TKB.this, "Lỗi lấy TKB!", Toast.LENGTH_SHORT).show();
            }else {
                getBSang(tkb1);
                getBChieu(tkb2);
            }
        }

        OK_TaoTKB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetText();
                if (mST2.length() > 0 || mST3.length() > 0 || mST4.length() > 0 || mST5.length() > 0 || mST6.length() > 0 || mST7.length() > 0 || mST8.length() > 0 || mCT2.length() > 0 || mCT3.length() > 0 || mCT4.length() > 0 || mCT5.length() > 0 || mCT6.length() > 0 || mCT7.length() > 0 || mCT8.length() > 0){
                    TKB tkb1 = new TKB(TaiKhoang, 1, mST2, mST3, mST4, mST5, mST6, mST7, mST8);
                    TKB tkb2 = new TKB(TaiKhoang, 2, mCT2, mCT3, mCT4, mCT5, mCT6, mCT7, mCT8);
                    Toast.makeText(MainActivity_Tao_TKB.this, "Để tránh lỗi vui lòng nhập chử (khongco) nếu bỏ trống", Toast.LENGTH_SHORT).show();
                    if (!MainActivity_TKB.databaseTKB.KtraTK(TaiKhoang)){
                        MainActivity_TKB.databaseTKB.ThemTKB(tkb1);
                        MainActivity_TKB.databaseTKB.ThemTKB(tkb2);
                        finish();
                    }else{
                        int BSang = MainActivity_TKB.databaseTKB.XemTK_Buoi(TaiKhoang, 1);
                        int BChieu = MainActivity_TKB.databaseTKB.XemTK_Buoi(TaiKhoang, 2);
                        MainActivity_TKB.databaseTKB.UpdateTKB(tkb1, String.valueOf(BSang));
                        MainActivity_TKB.databaseTKB.UpdateTKB(tkb2, String.valueOf(BChieu));
                        finish();
                    }
                }else {
                    Toast.makeText(MainActivity_Tao_TKB.this,  "Bạn chưa sửa đổi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhxa() {
        OK_TaoTKB = (Button) findViewById(R.id.OK_TaoTKB);
        ST2 = (EditText) findViewById(R.id.ST2);
        ST3 = (EditText) findViewById(R.id.ST3);
        ST4 = (EditText) findViewById(R.id.ST4);
        ST5 = (EditText) findViewById(R.id.ST5);
        ST6 = (EditText) findViewById(R.id.ST6);
        ST7 = (EditText) findViewById(R.id.ST7);
        ST8 = (EditText) findViewById(R.id.ST8);
        CT2 = (EditText) findViewById(R.id.CT2);
        CT3 = (EditText) findViewById(R.id.CT3);
        CT4 = (EditText) findViewById(R.id.CT4);
        CT5 = (EditText) findViewById(R.id.CT5);
        CT6 = (EditText) findViewById(R.id.CT6);
        CT7 = (EditText) findViewById(R.id.CT7);
        CT8 = (EditText) findViewById(R.id.CT8);
        tkb1 = new TKB();
        tkb2 = new TKB();
    }
    //lấy giá trị
    private void GetText(){
        mST2 = ST2.getText().toString().trim();
        mST3 = ST3.getText().toString().trim();
        mST4 = ST4.getText().toString().trim();
        mST5 = ST5.getText().toString().trim();
        mST6 = ST6.getText().toString().trim();
        mST7 = ST7.getText().toString().trim();
        mST8 = ST8.getText().toString().trim();
        mCT2 = CT2.getText().toString().trim();
        mCT3 = CT3.getText().toString().trim();
        mCT4 = CT4.getText().toString().trim();
        mCT5 = CT5.getText().toString().trim();
        mCT6 = CT6.getText().toString().trim();
        mCT7 = CT7.getText().toString().trim();
        mCT8 = CT8.getText().toString().trim();
    }
    //gán tkb buổi sáng
    private void getBSang(TKB tkb){
        ST2.setText(tkb.getmThu2());
        ST3.setText(tkb.getmThu3());
        ST4.setText(tkb.getmThu4());
        ST5.setText(tkb.getmThu5());
        ST6.setText(tkb.getmThu6());
        ST7.setText(tkb.getmThu7());
        ST8.setText(tkb.getmCN());
    }
    //gán tkb buổi chiều
    private void getBChieu(TKB tkb){
        CT2.setText(tkb.getmThu2());
        CT3.setText(tkb.getmThu3());
        CT4.setText(tkb.getmThu4());
        CT5.setText(tkb.getmThu5());
        CT6.setText(tkb.getmThu6());
        CT7.setText(tkb.getmThu7());
        CT8.setText(tkb.getmCN());
    }
    //menu của Tạo TKB
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