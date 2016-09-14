package com.ssdy.mainpercenttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.Information;

import java.util.List;

/**
 * Name: SimpleAdapter2
 * Describe: 信息表
 * Created by ys on 2016/9/12 17:55.
 */
public class SimpleAdapter2 extends BaseAdapter {

    private LayoutInflater _inflater;
    private List<Information> _Informations;

    public SimpleAdapter2(Context _context, List<Information> _Informations){
        this._inflater=LayoutInflater.from(_context);
        this._Informations = _Informations;
    }

    public void setData(List<Information> _Informations){
        this._Informations=_Informations;
    }

    @Override
    public int getCount() {
        return _Informations.size();
    }

    @Override
    public Object getItem(int position) {
        return _Informations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = _inflater.inflate(R.layout.item,null);
            holder = new ViewHolder();
            holder.uId = (TextView)convertView.findViewById(R.id.uId);
            holder.uName = (TextView) convertView.findViewById(R.id.uName);
            holder.age = (TextView) convertView.findViewById(R.id.age);
            holder.height = (TextView) convertView.findViewById(R.id.height);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        holder.uId.setText(_Informations.get(position).getInfoId()+"");
        holder.uName.setText(_Informations.get(position).getAddress());
        holder.age.setText(_Informations.get(position).getIcon_url());
        holder.height.setText(_Informations.get(position).getScore()+"");

        return convertView;
    }

    public final class ViewHolder{
        public TextView uId;
        public TextView uName;
        public TextView age;
        public TextView height;
    }
}
