package com.example.qunlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.PowerManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.Database.DatabaseSinhVien;
import com.example.qunlsv.DoiTuong.SinhVien;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity_Show_info_sinhvien extends AppCompatActivity {
    ImageView iHinh;
    TextView iSinhVien, iGhiChu;
    EditText itextGhiChu;
    Button iLuu;
    ImageButton TTD, TTFB, TTCall, TTSms;
    String GioiTinh = "";
    SinhVien sinhVien;
    String TaiKhoan = MainActivity.nTaiKhoang;
    DatabaseSinhVien databaseSinhVien;
    Dialog dialog;
    //tạo 1 request code để xin quyền tự đặt 26.
    int RQC = 26;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__show_info_sinhvien);
        anhxa();

        Intent intent = getIntent();
        int MSSV = intent.getIntExtra("mssv", 0);
        sinhVien = MainActivity_DS_SV.databaseSinhVien.LaySinhVien(MSSV);

        if (sinhVien.getmGioiTinh() == 1){
            iHinh.setImageResource(R.drawable.nam);
            GioiTinh = "Nam";
        }else {
            GioiTinh = "Nữ";
            iHinh.setImageResource(R.drawable.nu);
        }

        iSinhVien.setText("Họ & Tên: " + sinhVien.getmTen() +
                "\nLớp: " + sinhVien.getmLop() +
                "\nMSSV: " + sinhVien.getmMSSV() +
                "\nNăm Sinh: " + sinhVien.getmNamSinh() +
                "\nKhoa: " + sinhVien.getmKhoa() +
                "       Giới Tính: " + GioiTinh +
                "\nDân Tộc: " + sinhVien.getmDanToc() +
                "\nNơi Sinh: " + sinhVien.getmNoiSinh() +
                "\nChức Vụ: " + sinhVien.getmChucVu() +
                "\nSDT: " + sinhVien.getmSDT());
        itextGhiChu.setText(sinhVien.getmNghiChu());
        iLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nGhiChu = itextGhiChu.getText().toString();
                sinhVien.setmNghiChu(nGhiChu);
                int i = MainActivity_DS_SV.databaseSinhVien.UpdateSinhVien(sinhVien);
                itextGhiChu.setText(sinhVien.getmNghiChu());
                if (i > 0){
                    MainActivity_DS_SV. sinhViens.clear();
                    MainActivity_DS_SV.sinhViens.addAll(MainActivity_DS_SV.databaseSinhVien.AllSinhVien(TaiKhoan));
                    MainActivity_DS_SV.adapterSinhVien.notifyDataSetChanged();
                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Đã Lưu!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Thất Bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bắt sự kiện nút fb chuyển qua link fb sv đó
        TTFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sinhVien.getmLinkFB() == null){
                    DiaLinkFB();
                }else {
                    //try ngoại lệ nếu ng dùng nhập không đúng đi định dạng link thì yêu cầu nhập lại
                    try {
                        Intent intent1 =new Intent();
                        intent1.setAction(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse(sinhVien.getmLinkFB()));
                        startActivity(intent1);
                    }catch (Exception e){
                        Toast.makeText(MainActivity_Show_info_sinhvien.this, "Link fb không hợp lệ!\nThiếc lập lại.", Toast.LENGTH_SHORT).show();
                        DiaLinkFB();
                    }
                }
            }
        });
        //bắt sự kiện nhấn dữ thay đổi link fb
        TTFB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DiaLinkFB();
                return false;
            }
        });
        //bắt sự kiện nút sms chuyển qua soạn sms
        TTSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1 =new Intent();
                    intent1.setAction(Intent.ACTION_SENDTO);
                    intent1.putExtra("sms_body", "Sinh Viên: " + sinhVien.getmTen() + "\n");
                    intent1.setData(Uri.parse("sms:" + sinhVien.getmSDT()));
                    startActivity(intent1);
                }catch (Exception e){
                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Chưa cấp quyền\nVào mục cài đặt ứng dụng để cấp quyền này!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bắt sự kiện nút CALL chuyển qua màn hình gọi
        TTCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    Intent intent1 =new Intent();
//                    intent1.setAction(Intent.ACTION_CALL);
//                    intent1.setData(Uri.parse("tel:" + sinhVien.getmSDT()));
//                    startActivity(intent1);
//                }catch (Exception e){
//                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Chưa cấp quyền\nVào mục cài đặt ứng dụng để cấp quyền này!", Toast.LENGTH_SHORT).show();
//                }
                //xin quyền
                ActivityCompat.requestPermissions(MainActivity_Show_info_sinhvien.this, new String[]{Manifest.permission.CALL_PHONE,}, RQC);


            }
        });
        //bắt sự kiện chuyển qua bảng điểm
        TTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity_Show_info_sinhvien.this, MainActivity_BDSV.class);
                intent1.putExtra("datamssv", sinhVien.getmMSSV());
                startActivity(intent1);
            }
        });

    }

    //menu của DS TaiKhoang
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info_sinhvien, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //bắt sự kiện nút thoát
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.infoSua:
                suasinhvien(sinhVien.getmMSSV());
                break;
            case R.id.infoXoa:
                XoaSinhVien(sinhVien.getmMSSV());
                break;
            case R.id.tkmnThoat1:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //ánh xạ
    private void anhxa(){
        iHinh = (ImageView) findViewById(R.id.iHinh);
        iSinhVien = (TextView) findViewById(R.id.iSinhVien);
        iGhiChu = (TextView) findViewById(R.id.ighichu);
        itextGhiChu = (EditText) findViewById(R.id.itextGhiChu);
        iGhiChu.setText(Html.fromHtml("<u>Ghi Chú!</u>"));
        iLuu = (Button) findViewById(R.id.iLuu);
        TTFB = (ImageButton) findViewById(R.id.TTFB);
        TTCall = (ImageButton) findViewById(R.id.TTCall);
        TTD = (ImageButton) findViewById(R.id.TTD);
        TTSms = (ImageButton) findViewById(R.id.TTSms);
        databaseSinhVien = new DatabaseSinhVien(this);
    }

    //qua màn hình sửa sinh viên
    private void suasinhvien(int MSSV){
        Intent intent = new Intent(MainActivity_Show_info_sinhvien.this, MainActivity_Update_SinhVien.class);
        intent.putExtra("vitri", MSSV);
        startActivity(intent);
        finish();
    }

//    //xóa sinhvien:
//    private void XoaSinhVien(int MSSV){
//        MainActivity_DS_SV.databaseSinhVien.DeleteTaiKhoang(MSSV);
//        finish();
//    }



    //dialog xác nhận xóa sinh vien
    public void XoaSinhVien(int STT){
        AlertDialog.Builder dialogXoaSinhVien = new AlertDialog.Builder(MainActivity_Show_info_sinhvien.this);
        //tên thông báo
        dialogXoaSinhVien.setTitle("Thông Báo!");
        //Nội dung thông báo
        dialogXoaSinhVien.setMessage("Bạn có chắc muốn Xóa không");
        //icon của thông báo
        dialogXoaSinhVien.setIcon(R.drawable.rsz_inconxoa);

        //bắt sự kiện đồng ý xoa
        dialogXoaSinhVien.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int result = MainActivity_DS_SV.databaseSinhVien.DeleteTaiKhoang(STT);
                if (result > 0){
                    MainActivity_DS_SV.sinhViens.clear();
                    MainActivity_DS_SV.sinhViens.addAll(MainActivity_DS_SV.databaseSinhVien.AllSinhVien(TaiKhoan));
                    MainActivity_DS_SV.adapterSinhVien.notifyDataSetChanged();
                    dialog.cancel();
                    finish();
                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Thành Công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity_Show_info_sinhvien.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bắt sự kiện không đồng ý
        dialogXoaSinhVien.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(MainActivity_Show_info_sinhvien.this, "Đã Hoàn Tác", Toast.LENGTH_SHORT).show();
            }
        });
        dialogXoaSinhVien.show();
    }
    //dialog đổi linkfb
    private void DiaLinkFB(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_mat_ma_truong);

        //ánh xạ
        EditText edtLinkFB = (EditText) dialog.findViewById(R.id.MatMaNhap);
        Button BtnOK = (Button) dialog.findViewById(R.id.OK);
        Button BtnHuy = (Button) dialog.findViewById(R.id.Huy_MM);
        TextView TXTMM = (TextView) dialog.findViewById(R.id.TXTMM);
        TXTMM.setText("Thiếc lập Link fb cho SinhViên: " + sinhVien.getmTen());
        edtLinkFB.setHint("Link FB!");
        //set lại kiểu nhập
        edtLinkFB.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        //set lại chiều dài
        edtLinkFB.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(100) });
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
                String mLinkFB = edtLinkFB.getText().toString().trim();
                if (mLinkFB.length() > 0 ){
                    int i = databaseSinhVien.UpdateLinkFB(mLinkFB, sinhVien.getmMSSV());
                    if (i > 0){
                        sinhVien.setmLinkFB(mLinkFB);
                        MainActivity_DS_SV.sinhViens.clear();
                        MainActivity_DS_SV.sinhViens.addAll(MainActivity_DS_SV.databaseSinhVien.AllSinhVien(TaiKhoan));
                        MainActivity_DS_SV.adapterSinhVien.notifyDataSetChanged();
                        MainActivity_DS_SV.LV_SV.setAdapter(MainActivity_DS_SV.adapterSinhVien);
                        Toast.makeText(MainActivity_Show_info_sinhvien.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }else  Toast.makeText(MainActivity_Show_info_sinhvien.this, "Vui lòng nhập LinkFB!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();     //hiển thị dialog
    }
    //kiểm tra câu tl của người dùng khi xin quyền call

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // kiểm xem:requestCode có đúng hay ko ( ss với RQC), kiểm tra xem ng dùng có tl hay không grantResults.length, câu tl có thỏa điều kiện hay không.
        if (RQC == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent1 =new Intent();
            intent1.setAction(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel:" + sinhVien.getmSDT()));
            startActivity(intent1);
        }else Toast.makeText(MainActivity_Show_info_sinhvien.this, "Bạn không cho phép mỡ call phone!", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}