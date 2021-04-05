package com.example.qunlsv.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qunlsv.DoiTuong.MonHoc;
import com.example.qunlsv.R;

import java.util.List;

public class AdapterMonHoc extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MonHoc> monHocs;

    public AdapterMonHoc(Context context, int layout, List<MonHoc> monHocs) {
        this.context = context;
        this.layout = layout;
        this.monHocs = monHocs;
    }

    @Override
    public int getCount() {
        return monHocs.size();
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
        TextView TenMH, MaMH;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            //ánh xạ
            viewHolder.MaMH = (TextView) convertView.findViewById(R.id.txtDMMH);
            viewHolder.TenMH = (TextView) convertView.findViewById(R.id.txtDTMH);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MonHoc monHoc = monHocs.get(position);
        viewHolder.MaMH.setText(String.valueOf(monHoc.getmMaMonHoc()));
        viewHolder.TenMH.setText(monHoc.getTenMonHoc());
        return convertView;
    }
}
