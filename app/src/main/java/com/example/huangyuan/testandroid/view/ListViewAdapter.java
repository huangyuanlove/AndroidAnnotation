package com.example.huangyuan.testandroid.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.List;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-30
 * Email: huangyuan@chunyu.me
 */
public class ListViewAdapter extends BaseAdapter {

    private List<String> data;
    private Activity context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(List<String> data, Activity context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            convertView = layoutInflater.inflate(R.layout.item_list_view,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textOne.setText(data.get(position));
        holder.textTwo.setText(data.get(position) + ":"+data.get(position));

        return convertView;
    }


     public static  class ViewHolder{
        View view;

        @BindView(id= R.id.text_one)
        public TextView textOne;
        @BindView(id =R.id.text_two)
        public TextView textTwo;

        public ViewHolder(View view) {
            this.view = view;
            ViewInjector.bind(this,view);
        }
    }
}
