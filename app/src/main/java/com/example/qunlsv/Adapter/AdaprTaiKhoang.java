package com.example.qunlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qunlsv.DoiTuong.TaiKhoang;
import com.example.qunlsv.R;

import java.util.List;

public class AdaprTaiKhoang extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TaiKhoang> list;

    public AdaprTaiKhoang(Context context, int layout, List<TaiKhoang> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView TXTDongTaiKhoang, TXTDongMatKhau;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.TXTDongMatKhau = (TextView) convertView.findViewById(R.id.DongMatKhau1);
            viewHolder.TXTDongTaiKhoang = (TextView) convertView.findViewById(R.id.DongTaiKhoang1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TaiKhoang taiKhoang = list.get(position);
        viewHolder.TXTDongTaiKhoang.setText("TK: " + taiKhoang.getmTaiKhoang());
        viewHolder.TXTDongMatKhau.setText("MK:   " + taiKhoang.getmMatKhau());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieu_ung_ds);
        convertView.startAnimation(animation);

        return convertView;
    }
}
