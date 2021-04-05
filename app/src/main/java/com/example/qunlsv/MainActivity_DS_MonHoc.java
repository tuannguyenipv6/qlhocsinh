package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Adapter.AdapterMonHoc;
import com.example.qunlsv.Adapter.AdapterSinhVien;
import com.example.qunlsv.Database.DatabaseBangDiem;
import com.example.qunlsv.Database.DatabaseMonHoc;
import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.DoiTuong.TaiKhoang;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

public class MainActivity_DS_MonHoc extends AppCompatActivity {
    ListView DSMH;
    Dialog dialog;
    public static List<MonHoc> monHocs;
    public static AdapterMonHoc adapterMonHoc;
    String TaiKhoan = MainActivity.nTaiKhoang;
    DatabaseMonHoc databaseMonHoc;
    DatabaseBangDiem databaseBangDiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__d_s__mon_hoc);
        anhxa();
        monHocs = MainActivity_SinhVien.databaseMonHoc.AllMonHoc(TaiKhoan);
//        setAdapterMonHoc();
        adapterMonHoc = new AdapterMonHoc(MainActivity_DS_MonHoc.this, R.layout.item_dong_monhoc, monHocs);
        DSMH.setAdapter(adapterMonHoc);
        //long click xóa môn học
        DSMH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                XoaMonHoc(monHocs.get(position).getmMaMonHoc());
                return false;
            }
        });

    }

    //menu của môn học
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ds_monhoc, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //bắt sự kiện menu của ds dv
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){      //item.getItemId() là cái mà người dùng click vào
            case R.id.outDSMH:
                finish();
                break;
            case R.id.editDSMH:
                DiaLogUpdateMH();
                break;
            case R.id.addDSMH:
                MainActivity_SinhVien.DiaLogT1MonHoc(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhxa(){
        DSMH = (ListView) findViewById(R.id.LVDSMH);
        monHocs = new ArrayList<>();
        databaseBangDiem = new DatabaseBangDiem(this);
        databaseMonHoc = new DatabaseMonHoc(this);
    }





    //Dialog (hộp thoại) Chỉn sửa
    private void DiaLogUpdateMH(){
        dialog = new Dialog(MainActivity_DS_MonHoc.this);  //khởi tạo dialog

        dialog.setContentView(R.layout.dialog_t_taikhoan); //set khung mình đã tạo sẳn:

        //ánh xạ các view trong Dialog
        EditText EdtT_MH = (EditText) dialog.findViewById(R.id.TK_Tao);
        EditText EdtM_MH = (EditText) dialog.findViewById(R.id.MK_Tao);
        TextView X_N_Tao = (TextView) dialog.findViewById(R.id.X_N_Tao);
        TextView Huy_Tao = (TextView) dialog.findViewById(R.id.Huy_Tao_TK);
        TextView txtTenDialog = (TextView) dialog.findViewById(R.id.BMTK);
        //set lại tên các view
        txtTenDialog.setText("Chỉn sửa tên môn học!");
        EdtT_MH.setHint("Tên môn học mới");
        EdtM_MH.setHint("Nhập lại mã môn học");
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
                dialog.cancel();     //hủy hiển thị dialog
            }
        });

        X_N_Tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenMonHoc = EdtT_MH.getText().toString().trim();
                String i = EdtM_MH.getText().toString().trim();


                if (TenMonHoc.length() == 0 || i.length() == 0){
                    Toast.makeText(MainActivity_DS_MonHoc.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    int MaMonHoc = Integer.parseInt(i);
                    if (MainActivity_SinhVien.databaseMonHoc.KtrMaMonHoc(MaMonHoc)){
                        MonHoc monHoc = new MonHoc(MainActivity.nTaiKhoang, MaMonHoc, TenMonHoc);
                        MainActivity_SinhVien.databaseMonHoc.EditMonHoc(monHoc);
                        databaseBangDiem.UpdateMonHoc(monHoc.getTenMonHoc(), monHoc.getmMaMonHoc());
                        dialog.cancel();
                        setAdapterMonHoc();
                        Toast.makeText(MainActivity_DS_MonHoc.this, "Đã Thay Đổi!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity_DS_MonHoc.this, "Mã Môn Học không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //hiển thị dialog bằng lệnh show:
        dialog.show();
    }
    //setAdapter
    public void setAdapterMonHoc(){
        if (adapterMonHoc == null){
            adapterMonHoc = new AdapterMonHoc(MainActivity_DS_MonHoc.this, R.layout.item_dong_monhoc, monHocs);
            DSMH.setAdapter(adapterMonHoc);
        }else {
            if (monHocs != null){
                monHocs.clear();
            }
            DSMH.setAdapter(adapterMonHoc);
            monHocs.addAll(MainActivity_SinhVien.databaseMonHoc.AllMonHoc(TaiKhoan));
            adapterMonHoc.notifyDataSetChanged();
            //chuyển suống dòng cuối cùng của listview
            DSMH.setSelection(adapterMonHoc.getCount() - 1);
        }
    }

    //dialog xác nhận xóa Môn Học
    public void XoaMonHoc(int MMH){
        AlertDialog.Builder dialogXoaSinhVien = new AlertDialog.Builder(MainActivity_DS_MonHoc.this);
        //tên thông báo
        dialogXoaSinhVien.setTitle("Thông Báo!");
        //Nội dung thông báo
        dialogXoaSinhVien.setMessage("Bạn có chắc muốn Xóa Môn Học này\nĐồng nghĩa với xóa ALL cột điểm này của SinhVien?");
        //icon của thông báo
        dialogXoaSinhVien.setIcon(R.drawable.rsz_inconxoa);

        //bắt sự kiện đồng ý xoa
        dialogXoaSinhVien.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int result = databaseBangDiem.DeleteBangDiem(MMH);
                int result1 = databaseMonHoc.DeleteMonHoc(MMH);
                if (result > 0 && result1 > 0){
                    //set lại list môn học
                    monHocs.clear();
                    monHocs.addAll(databaseMonHoc.AllMonHoc(TaiKhoan));
                    adapterMonHoc.notifyDataSetChanged();
                    //set lại list Bảng điểm
                    MainActivity_BDSV.bangDiems.clear();
                    dialog.cancel();
                    Toast.makeText(MainActivity_DS_MonHoc.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity_DS_MonHoc.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bắt sự kiện không đồng ý
        dialogXoaSinhVien.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(MainActivity_DS_MonHoc.this, "Đã Hoàn Tác", Toast.LENGTH_SHORT).show();
            }
        });
//        dialogXoaSinhVien.show();
        if(!((Activity) MainActivity_DS_MonHoc.this).isFinishing())
        {
            //show dialog
            dialogXoaSinhVien.show();
        }else Toast.makeText(MainActivity_DS_MonHoc.this, "Không thể hiển thị vui lòng khởi động lại", Toast.LENGTH_SHORT).show();
    }
}