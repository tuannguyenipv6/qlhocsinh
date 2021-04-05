package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseMonHoc;
import com.example.qunlsv.Database.DatabaseSinhVien;
import com.example.qunlsv.Database.DatabaseTenLop;
import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.TaiKhoang;
import com.example.qunlsv.DoiTuong.TenLop;

public class MainActivity_SinhVien extends AppCompatActivity {
    TextView txtTenLop;
    ImageView okDSMonHoc, okDSSV;
    public static DatabaseMonHoc databaseMonHoc;
    String TaiKhoang = MainActivity.nTaiKhoang;
    public static DatabaseTenLop databaseTenLop;
    DatabaseSinhVien databaseSinhVien;
    Dialog dialog;
    public static Dialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__sinh_vien);
        anhxa();
        LayTenLop();
        txtTenLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenlop = txtTenLop.getText().toString().trim();
                if (tenlop.equals("Lớp: Chưa Có!")){
                    Toast.makeText(MainActivity_SinhVien.this, "Đặt tên lớp", Toast.LENGTH_SHORT).show();
                    DiaLogTenLop(1);
                }else {
                    DiaLogTenLop(2);
                }
            }
        });
        //chuyển qua ds sinh viên
        okDSSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenlop = txtTenLop.getText().toString().trim();
                if (tenlop.equals("Lớp: Chưa Có!")){
                    Toast.makeText(MainActivity_SinhVien.this, "Đặt tên lớp", Toast.LENGTH_SHORT).show();
                    DiaLogTenLop(1);
                }else {
                    Intent intent = new Intent(MainActivity_SinhVien.this, MainActivity_DS_SV.class);
                    startActivity(intent);
                }
            }
        });
        //chuyển qua ds môn học
        okDSMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenlop = txtTenLop.getText().toString().trim();
                if (tenlop.equals("Lớp: Chưa Có!")){
                    Toast.makeText(MainActivity_SinhVien.this, "Đặt Tên Lớp!", Toast.LENGTH_SHORT).show();
                    DiaLogTenLop(1);
                }else if (!databaseSinhVien.KTRSV(TaiKhoang)){
                    Toast.makeText(MainActivity_SinhVien.this, "Sinh Viên chưa được khởi tạo!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (databaseMonHoc.KtrBang(TaiKhoang) == 0){
                        Toast.makeText(MainActivity_SinhVien.this, "Môn học chưa có khởi tạo!", Toast.LENGTH_SHORT).show();
                        DiaLogT1MonHoc(MainActivity_SinhVien.this);
                    }else {
                        Intent intent = new Intent(MainActivity_SinhVien.this, MainActivity_DS_MonHoc.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private void anhxa(){
        txtTenLop = (TextView) findViewById(R.id.txtTenLop);
        okDSMonHoc = (ImageView) findViewById(R.id.okDSMonHoc);
        okDSSV = (ImageView) findViewById(R.id.okDSSV);
        databaseMonHoc = new DatabaseMonHoc(this);
        databaseTenLop = new DatabaseTenLop(this);
        databaseSinhVien = new DatabaseSinhVien(this);

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

    //Dialog (hộp thoại) đặt tên lớp
    private void DiaLogTenLop(int i){
        dialog = new Dialog(this);  //khởi tạo dialog

        dialog.setContentView(R.layout.dialog_dat_tenlop); //set khung mình đã tạo sẳn:

        //ánh xạ các view trong Dialog
        EditText edtTL = (EditText) dialog.findViewById(R.id.edtTL);
        Button okHTL = (Button) dialog.findViewById(R.id.okHTL);
        Button okTTL = (Button) dialog.findViewById(R.id.okTTL);

        //Hủy Tạo Tenlop
        okHTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();     //hủy hiển thị dialog
            }
        });
        //Xác nhận tạo tên lớp (lưu Database)
        okTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenlop = edtTL.getText().toString().trim();
                if (tenlop.length() > 0){
                    if (!databaseTenLop.KtrTenLop(tenlop)){
                        TenLop tenLop = new TenLop(TaiKhoang, tenlop);
                        if (i == 1){
                            databaseTenLop.ThemLop(tenLop);
                        }else databaseTenLop.SuaTenLop(tenLop);
                        dialog.cancel();
                        LayTenLop();
                        Toast.makeText(MainActivity_SinhVien.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity_SinhVien.this, "Tên lớp đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity_SinhVien.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //hiển thị dialog bằng lệnh show:
        dialog.show();
    }
    private void LayTenLop(){
        String TenLop = databaseTenLop.LayTenLop(TaiKhoang);
        if (!TenLop.equals("Lớp: Chưa Có!")){
            txtTenLop.setText("Lớp: " + TenLop);
        }
    }
    //Dialog (hộp thoại) tạo 1 môn học khi bảng monhoc chưa có gtr
    public static void DiaLogT1MonHoc(Context context){
        dialog1 = new Dialog(context);  //khởi tạo dialog

        dialog1.setContentView(R.layout.dialog_t_taikhoan); //set khung mình đã tạo sẳn:

        //ánh xạ các view trong Dialog
        EditText EdtT_MH = (EditText) dialog1.findViewById(R.id.TK_Tao);
        EditText EdtM_MH = (EditText) dialog1.findViewById(R.id.MK_Tao);
        TextView X_N_Tao = (TextView) dialog1.findViewById(R.id.X_N_Tao);
        TextView Huy_Tao = (TextView) dialog1.findViewById(R.id.Huy_Tao_TK);
        TextView txtTenDialog = (TextView) dialog1.findViewById(R.id.BMTK);
        //set lại tên các view
        txtTenDialog.setText("Tạo Môn Học Mới!");
        EdtT_MH.setHint("Nhập tên môn học");
        EdtM_MH.setHint("Nhập mã môn học");
        //set lại chiều dài kí tự
        EdtT_MH.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(100) });
        EdtM_MH.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(100) });
        //set lại kiểu nhập cho editexxt
        EdtT_MH.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        EdtM_MH.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);


        //Hủy Tạo TK
        Huy_Tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();     //hủy hiển thị dialog
            }
        });

        //Xác nhận tạo tk (lưu tk mk vào Database)
        X_N_Tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenMonHoc = EdtT_MH.getText().toString().trim();
                String i = EdtM_MH.getText().toString().trim();


                if (TenMonHoc.length() > 0 && i.length() > 0){
                    int MaMonHoc = Integer.parseInt(i);
                    if (!databaseMonHoc.KtrMaMonHoc(MaMonHoc)){
                        MonHoc monHoc = new MonHoc(MainActivity.nTaiKhoang, MaMonHoc, TenMonHoc);
                        databaseMonHoc.ThemMonHoc(monHoc);
                        dialog1.cancel();
                        if (MainActivity_DS_MonHoc.monHocs != null && MainActivity_DS_MonHoc.adapterMonHoc != null){
                            MainActivity_DS_MonHoc.monHocs.clear();
                            MainActivity_DS_MonHoc.monHocs.addAll(MainActivity_SinhVien.databaseMonHoc.AllMonHoc(MainActivity.nTaiKhoang));
                            MainActivity_DS_MonHoc.adapterMonHoc.notifyDataSetChanged();
                        }
                        Toast.makeText(context, "Tạo thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Mã Môn Học đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //hiển thị dialog bằng lệnh show:
        dialog1.show();
    }
}