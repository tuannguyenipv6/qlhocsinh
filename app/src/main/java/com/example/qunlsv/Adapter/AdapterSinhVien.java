package com.example.qunlsv.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlsv.DoiTuong.SinhVien;
import com.example.qunlsv.DoiTuong.TaiKhoang;
import com.example.qunlsv.MainActivity_DS_SV;
import com.example.qunlsv.MainActivity_Show_info_sinhvien;
import com.example.qunlsv.R;

import java.util.List;

public class AdapterSinhVien extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SinhVien> sinhViens;
    private String TaiKhoan;

    //contructor
    public AdapterSinhVien(Context context, int layout, List<SinhVien> sinhViens, String TaiKhoan) {
        this.context = context;
        this.layout = layout;
        this.sinhViens = sinhViens;
        this.TaiKhoan = TaiKhoan;
    }

    @Override
    public int getCount() {
        return sinhViens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView ImgmHinh;
        TextView zTen, zLop, zSDT, zChucVu, zSTT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            //ánh xạ
            viewHolder.ImgmHinh = (ImageView) convertView.findViewById(R.id.ImgmHinh);
            viewHolder.zTen = (TextView) convertView.findViewById(R.id.zTen);
            viewHolder.zLop = (TextView) convertView.findViewById(R.id.zLop);
            viewHolder.zSDT = (TextView) convertView.findViewById(R.id.zSDT);
            viewHolder.zChucVu = (TextView) convertView.findViewById(R.id.zChucVu);
            viewHolder.zSTT = (TextView) convertView.findViewById(R.id.zSTT);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SinhVien sinhVien = sinhViens.get(position);
        viewHolder.zTen.setText("Tên:   " + sinhVien.getmTen());
        viewHolder.zLop.setText("Lớp:   " + sinhVien.getmLop());
        viewHolder.zSDT.setText("SDT:   " + sinhVien.getmSDT());
        viewHolder.zChucVu.setText("Chức Vụ:   " + sinhVien.getmChucVu());
        viewHolder.zSTT.setText("MSSV: " + sinhVien.getmMSSV());
        if (sinhVien.getmGioiTinh() == 2){
            viewHolder.ImgmHinh.setImageResource(R.drawable.nu);
        }else {
            viewHolder.ImgmHinh.setImageResource(R.drawable.nam);
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                XoaSinhVien(MainActivity_DS_SV.sinhViens.get(position).getmMSSV(), context);
                return false;
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity_Show_info_sinhvien.class);
                intent.putExtra("mssv", MainActivity_DS_SV.sinhViens.get(position).getmMSSV());
                context.startActivity(intent);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieu_ung_ds);
        convertView.startAnimation(animation);

        return convertView;
    }

    //dialog xác nhận xóa sinh vien
    public void XoaSinhVien(int STT, Context context){
        AlertDialog.Builder dialogXoaSinhVien = new AlertDialog.Builder(context);
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
                    Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //bắt sự kiện không đồng ý
        dialogXoaSinhVien.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(context, "Đã Hoàn Tác", Toast.LENGTH_SHORT).show();
            }
        });
//        dialogXoaSinhVien.show();
        if(!((Activity) context).isFinishing())
        {
            //show dialog
            dialogXoaSinhVien.show();
        }else Toast.makeText(context, "Không thể hiển thị vui lòng khởi động lại", Toast.LENGTH_SHORT).show();
    }
}
