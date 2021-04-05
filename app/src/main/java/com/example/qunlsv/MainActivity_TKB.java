package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseTKB;
import com.example.qunlsv.DoiTuong.TKB;

import java.util.ArrayList;

public class MainActivity_TKB extends AppCompatActivity {
    TableLayout tableLayout;
    ScaleGestureDetector scaleGestureDetector;
    public static DatabaseTKB databaseTKB;
    TextView BST2, BST3, BST4, BST5, BST6, BST7, BST8, BCT2, BCT3, BCT4, BCT5, BCT6, BCT7, BCT8;
    String TaiKhoang = MainActivity.nTaiKhoang;
    TKB tkb1 = new TKB();
    TKB tkb2 = new TKB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__t_k_b);
        anhxa();

        //phóng to thu nhỏ
        scaleGestureDetector = new ScaleGestureDetector(this, new MyGesture());
        tableLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });


        //lấy TKB từ database
        if (databaseTKB.KtraTK(TaiKhoang)){
            tkb1 = databaseTKB.LayTKB(TaiKhoang, 1);
            tkb2 = databaseTKB.LayTKB(TaiKhoang, 2);
            if (tkb1 == null || tkb2 == null){
                Toast.makeText(MainActivity_TKB.this, "Lỗi lấy TKB!", Toast.LENGTH_SHORT).show();
            }else {
                getBSang(tkb1);
                getBChieu(tkb2);
            }
        }else Toast.makeText(MainActivity_TKB.this, "TKB chưa Khởi Tạo!", Toast.LENGTH_SHORT).show();
    }


    //các funsion dùng để phóng to thu nhỏ
    class MyGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        //khai báo 3 biến lll: biến lấy giá trị phóng ban đầu, biến giữ giá trị phóng, và biến cuôi cùng kết thúc phóng
        float scale = 1.0F, onScaleStart = 0, onScaleEnd = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //gọi lại biến bắt đầu "scale" nhân cho tỉ lệ mà người dùng phóng "detector.getScaleFactor();"
            scale *= detector.getScaleFactor();
            tableLayout.setScaleX(scale); //chiều rộn
            tableLayout.setScaleY(scale); //chiều cao
//            lvTKB.setSca(scale);
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

    //tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tkb, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện 3 nút (thoát, cập nhật, sửa)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setTKB:
                Intent intent = new Intent(MainActivity_TKB.this, MainActivity_Tao_TKB.class);
                startActivity(intent);
                break;
            case R.id.ThoatTKB:
                finish();
                break;
            case R.id.ResetTKB:
                if (databaseTKB.KtraTK(TaiKhoang)){
                    tkb1 = databaseTKB.LayTKB(TaiKhoang, 1);
                    tkb2 = databaseTKB.LayTKB(TaiKhoang, 2);
                    if (tkb1 == null && tkb2 == null){
                        Toast.makeText(MainActivity_TKB.this, "Lỗi lấy TKB!", Toast.LENGTH_SHORT).show();
                    }else {
                        getBSang(tkb1);
                        getBChieu(tkb2);
                    }
                }else Toast.makeText(MainActivity_TKB.this, "TKB chưa Khởi Tạo!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private String ktra(String getTKB){
        String result = "";
        if (!getTKB.equals("khongco")){
            result = getTKB;
        }
        return result;
    }
    private void getBSang(TKB tkb){
        BST2.setText(ktra(tkb.getmThu2()));
        BST3.setText(ktra(tkb.getmThu3()));
        BST4.setText(ktra(tkb.getmThu4()));
        BST5.setText(ktra(tkb.getmThu5()));
        BST6.setText(ktra(tkb.getmThu6()));
        BST7.setText(ktra(tkb.getmThu7()));
        BST8.setText(ktra(tkb.getmCN()));
    }
    private void getBChieu(TKB tkb){
        BCT2.setText(ktra(tkb.getmThu2()));
        BCT3.setText(ktra(tkb.getmThu3()));
        BCT4.setText(ktra(tkb.getmThu4()));
        BCT5.setText(ktra(tkb.getmThu5()));
        BCT6.setText(ktra(tkb.getmThu6()));
        BCT7.setText(ktra(tkb.getmThu7()));
        BCT8.setText(ktra(tkb.getmCN()));
    }
    private void anhxa(){
        TaiKhoang = MainActivity.nTaiKhoang;
        tkb1 = new TKB();
        tkb2 = new TKB();

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        databaseTKB = new DatabaseTKB(this);
        BST2 = (TextView) findViewById(R.id.BST2);
        BST3 = (TextView) findViewById(R.id.BST3);
        BST4 = (TextView) findViewById(R.id.BST4);
        BST5 = (TextView) findViewById(R.id.BST5);
        BST6 = (TextView) findViewById(R.id.BST6);
        BST7 = (TextView) findViewById(R.id.BST7);
        BST8 = (TextView) findViewById(R.id.BST8);
        BCT2 = (TextView) findViewById(R.id.BCT2);
        BCT3 = (TextView) findViewById(R.id.BCT3);
        BCT4 = (TextView) findViewById(R.id.BCT4);
        BCT5 = (TextView) findViewById(R.id.BCT5);
        BCT6 = (TextView) findViewById(R.id.BCT6);
        BCT7 = (TextView) findViewById(R.id.BCT7);
        BCT8 = (TextView) findViewById(R.id.BCT8);
    }
}