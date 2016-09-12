package com.ssdy.mainpercenttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.User;

import java.util.List;

/**
 * Name: ${Name}
 * Describe:
 * Created by ys on 2016/9/12 17:55.
 */
public class SimpleAdapter extends BaseAdapter {

    private LayoutInflater _inflater;
    private List<User> _users;

    public SimpleAdapter(Context _context, List<User> _users){
        this._inflater=LayoutInflater.from(_context);
        this._users = _users;
    }
    @Override
    public int getCount() {
        return _users.size();
    }

    @Override
    public Object getItem(int position) {
        return _users.get(position);
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
            holder.uName = (TextView) convertView.findViewById(R.id.uName);
            holder.age = (TextView) convertView.findViewById(R.id.age);
            holder.height = (TextView) convertView.findViewById(R.id.height);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        holder.uName.setText(_users.get(position).getName());
        holder.age.setText(_users.get(position).getAge()+"");
        holder.height.setText(_users.get(position).getHigh()+"");

        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView uName;
        public TextView age;
        public TextView height;
    }
}
