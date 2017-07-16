package com.example.bob.zhihuribao;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bob on 17-7-8.
 */

public  class Main_Page_Adapter extends RecyclerView.Adapter<Main_Page_Adapter.ViewHolder>implements View.OnClickListener

{

    private List<Main_page> mShow_List;
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {                       //接口
        void onItemClick(View view , int position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{            //持有每个ITEM的界面元素
        ImageView text_Images;
        TextView text_Text_title;
        public ViewHolder(View view){
            super(view);

            text_Images= view.findViewById(R.id.show_main_page_images);
            text_Text_title= view.findViewById(R.id.show_main_page_text);
        }
    }

    public Main_Page_Adapter(List<Main_page> Show_List){//构造函数
        mShow_List=Show_List;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_page_item, parent, false);
       final ViewHolder holder = new ViewHolder(view);//固定写法/*LayoutInflater inflater = LayoutInflater.from(context)*/
        view.setOnClickListener(this);//设置监听事件 /*View view = inflater.inflate(R.layout.item,parent,false)*/
        return  holder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
                            Main_page main_page = mShow_List.get(position);
        /**加载图片*/       Picasso.with(MyApplication.getContext()).load(main_page.getImages()).into
                            (holder.text_Images);
        /**加载文字*/        holder.text_Text_title.setText(main_page.getText_title());
        /**保存position在itemView的tag中*/ holder.itemView.setTag(position);//
    }


    @Override//返回界面元素item的个数
    public int getItemCount()  {
        return mShow_List.size();
    }



    public void onClick(View v) {
        if (mOnItemClickListener != null) {//注意这里使用getTag方法获取position

            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}

