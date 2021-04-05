package com.example.qunlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qunlsv.DoiTuong.BangChon;
import com.example.qunlsv.R;

import java.util.List;

public class AdapterBangChon extends BaseAdapter {
    private Context context;
    private List<BangChon> bangChons;
    private int layout;

    public AdapterBangChon(Context context, int layout, List<BangChon> bangChons) {
        this.context = context;
        this.bangChons = bangChons;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return bangChons.size();
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
        ImageView Hinh;
        TextView NoiDung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder.Hinh = (ImageView) convertView.findViewById(R.id.HinhBang);
            viewHolder.NoiDung = (TextView) convertView.findViewById(R.id.NoiDungBang);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder) convertView.getTag();
        BangChon bangChon = bangChons.get(position);
        viewHolder.Hinh.setImageResource(bangChon.getmHinh());
        viewHolder.NoiDung.setText(bangChon.getmNoiDung());
        return convertView;
    }
}
