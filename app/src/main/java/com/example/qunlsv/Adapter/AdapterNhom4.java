package com.example.qunlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qunlsv.DoiTuong.Nhom4;
import com.example.qunlsv.R;

import java.util.List;

public class AdapterNhom4 extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Nhom4> nhom4s;

    public AdapterNhom4(Context context, int layout, List<Nhom4> nhom4s) {
        this.context = context;
        this.layout = layout;
        this.nhom4s = nhom4s;
    }

    @Override
    public int getCount() {
        return nhom4s.size();
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
        ImageView dIMG;
        TextView dTen, dLop, dMSSV;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.dIMG = (ImageView) convertView.findViewById(R.id.dHinh);
            viewHolder.dTen = (TextView) convertView.findViewById(R.id.dTen);
            viewHolder.dLop = (TextView) convertView.findViewById(R.id.dLop);
            viewHolder.dMSSV = (TextView) convertView.findViewById(R.id.dMSSV);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Nhom4 nhom4 = nhom4s.get(position);
        viewHolder.dTen.setText(nhom4.getmTen());
        viewHolder.dLop.setText(nhom4.getmLop());
        viewHolder.dMSSV.setText(nhom4.getmMSSV());
        viewHolder.dIMG.setImageResource(nhom4.getmHinh());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieu_ung_ds);
        convertView.startAnimation(animation);

        return convertView;
    }
}
