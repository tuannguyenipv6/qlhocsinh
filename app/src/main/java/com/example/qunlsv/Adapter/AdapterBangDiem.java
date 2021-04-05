package com.example.qunlsv.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qunlsv.DoiTuong.BangDiem;
import com.example.qunlsv.R;

import java.util.List;

public class AdapterBangDiem extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BangDiem> bangDiems;

    public AdapterBangDiem(Context context, int layout, List<BangDiem> bangDiems) {
        this.context = context;
        this.layout = layout;
        this.bangDiems = bangDiems;
    }

    @Override
    public int getCount() {
        return bangDiems.size();
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
        TextView nSTT, nMMH, nTMH, nTC, nDThaiDo, nDiemKT, nTBBoPhan, nDiemThi, nTBMonH10, nTBMonH4, nDiemChu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            //ánh xạ
            viewHolder.nSTT = (TextView) convertView.findViewById(R.id.nSTT);
            viewHolder.nMMH = (TextView) convertView.findViewById(R.id.nMMH);
            viewHolder.nTMH = (TextView) convertView.findViewById(R.id.nTMH);
            viewHolder.nTC = (TextView) convertView.findViewById(R.id.nTC);
            viewHolder.nDThaiDo = (TextView) convertView.findViewById(R.id.nDThaiDo);
            viewHolder.nDiemKT = (TextView) convertView.findViewById(R.id.nDiemKT);
            viewHolder.nTBBoPhan = (TextView) convertView.findViewById(R.id.nTBBoPhan);
            viewHolder.nDiemThi = (TextView) convertView.findViewById(R.id.nDiemThi);
            viewHolder.nTBMonH10 = (TextView) convertView.findViewById(R.id.nTBMonH10);
            viewHolder.nTBMonH4 = (TextView) convertView.findViewById(R.id.nTBMonH4);
            viewHolder.nDiemChu = (TextView) convertView.findViewById(R.id.nDiemChu);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BangDiem bangDiem = bangDiems.get(position);
        if (position == 0){
            convertView.setBackgroundColor(Color.parseColor("#60B5FA"));
            viewHolder.nSTT.setText("STT");
            viewHolder.nMMH.setText("Mã Môn Học");
            viewHolder.nTMH.setText("Tên Môn Học");
            viewHolder.nTC.setText("TC");
            viewHolder.nDThaiDo.setText("Điểm Thái Độ");
            viewHolder.nDiemKT.setText("Điểm KT");
            viewHolder.nTBBoPhan.setText("TB Bộ Phận");
            viewHolder.nDiemThi.setText("Điểm Thi");
            viewHolder.nTBMonH10.setText("TB Hệ Môn 10");
            viewHolder.nTBMonH4.setText("TB Hệ Môn 4");
            viewHolder.nDiemChu.setText("Điểm Chử");
        }else {
            if (position % 2 == 0){
                convertView.setBackgroundColor(Color.parseColor("#C0E0FA"));
            }else convertView.setBackgroundColor(Color.parseColor("#D7FAF1D8"));
            //gán giá trị
            viewHolder.nSTT.setText(String.valueOf(position));
            viewHolder.nMMH.setText(String.valueOf(bangDiem.getmMaMonHoc()));
            viewHolder.nTMH.setText(bangDiem.getmTenMonHoc());
            viewHolder.nTC.setText(MatDinh(bangDiem.getmTC()));
            viewHolder.nDThaiDo.setText(MatDinh(bangDiem.getmDiemThaiDo()));
            viewHolder.nDiemKT.setText(MatDinh(bangDiem.getmDiemKT()));
            viewHolder.nTBBoPhan.setText(MatDinh(bangDiem.getmTBBoPhan()));
            viewHolder.nDiemThi.setText(MatDinh(bangDiem.getmDiemThi()));
            viewHolder.nTBMonH10.setText(MatDinh(bangDiem.getmTBMonH10()));
            viewHolder.nTBMonH4.setText(MatDinh(bangDiem.getmTBMonH4()));
            if (bangDiem.getmDiemChu() == null || bangDiem.getmDiemChu().equals("i")){
                viewHolder.nDiemChu.setText("");
            }else viewHolder.nDiemChu.setText(String.valueOf(bangDiem.getmDiemChu()));
        }
        return convertView;
    }
    private String MatDinh(int diem){
        String result = "";
        if (diem != 0){
            result = String.valueOf(diem);
        }
        return result;
    }
}
