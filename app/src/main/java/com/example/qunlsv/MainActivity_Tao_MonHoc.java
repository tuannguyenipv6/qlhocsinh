package com.example.qunlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseMonHoc;
import com.example.qunlsv.DoiTuong.MonHoc;

public class MainActivity_Tao_MonHoc extends AppCompatActivity {
    Button OKTaoMonHoc;
    EditText edtTenMonHoc, edtMaMonHoc;
    public static DatabaseMonHoc databaseMonHoc;
    String TaiKhoang = MainActivity.nTaiKhoang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__tao__mon_hoc);
        anhxa();
        //ok tạo Mon học mới
        OKTaoMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lTenMonHoc = edtTenMonHoc.getText().toString().trim();
                int lMaMonHoc = Integer.parseInt(edtMaMonHoc.getText().toString().trim());
                //kiểm tra xem mã môn học đã tồn tại hay chưa.
                if (!databaseMonHoc.KtrMaMonHoc(lMaMonHoc)){
                    MonHoc monHoc = new MonHoc(TaiKhoang, lMaMonHoc, lTenMonHoc);
                    databaseMonHoc.ThemMonHoc(monHoc);
                    Toast.makeText(MainActivity_Tao_MonHoc.this, "Đã thêm!", Toast.LENGTH_SHORT).show();
                    finish();
                }else Toast.makeText(MainActivity_Tao_MonHoc.this, "Mã Môn Học đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ánh xạ
    private void anhxa(){
        OKTaoMonHoc = (Button) findViewById(R.id.OKTaoMonHoc);
        edtTenMonHoc = (EditText) findViewById(R.id.edtTenMonHoc);
        edtMaMonHoc = (EditText) findViewById(R.id.edtMaMonHoc);
        databaseMonHoc = new DatabaseMonHoc(this);
    }
}