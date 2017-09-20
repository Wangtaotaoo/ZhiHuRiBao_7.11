package com.example.bob.zhihuribao;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by bob on 17-7-8.
 */

import static com.example.bob.zhihuribao.MainActivity.my_viewpager_adapter;

/**
 * Created by bob on 17-7-8.
 */

public  class Main_Page_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>

{

    private List<Main_page> mShow_List;
    private OnItemClickListener mOnItemClickListener = null;
    private static final int HEAD_VIEW = 0;
    private static final int BODY_VIEW = 1;


    public  interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public Main_Page_Adapter(List<Main_page> Show_List){
        mShow_List=Show_List;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {

        if(viewType==BODY_VIEW){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_page_item, parent, false);
            final MyBodyViewHolder holder = new MyBodyViewHolder(view);
            return holder;}
        if(viewType==HEAD_VIEW){
            View view =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.head_viewpager,parent,false);
            final MyHeadViewHolder holder = new MyHeadViewHolder(view);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder ,int position) {
        if (holder instanceof MyBodyViewHolder) {
            Main_page main_page = mShow_List.get(position-1);
            final MyBodyViewHolder viewHolder = (MyBodyViewHolder) holder;
            Picasso.with(MyApplication.getContext()).load(main_page.getImages()).into
                    (viewHolder.text_Images);
            viewHolder.text_Text_title.setText(main_page.getText_title());
        }

        if(holder instanceof MyHeadViewHolder){
            ((MyHeadViewHolder)holder).mvp.setAdapter(my_viewpager_adapter);
        }

    }
         @Override  public int getItemCount()  {
         return mShow_List.size()+1;
         }


         @Override
       public int  getItemViewType(int position){
             if(position ==0){
                 return HEAD_VIEW;
             }else{
                 return BODY_VIEW;
             }
         }

        //自定义的viewHolder
    class MyBodyViewHolder extends RecyclerView.ViewHolder{
        TextView text_Text_title;
        ImageView text_Images;
        public MyBodyViewHolder(View view){
            super(view);

            text_Images= view.findViewById(R.id.show_main_page_images);
            text_Text_title= view.findViewById(R.id.show_main_page_text);
            //绑定点击事件
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {

                        mOnItemClickListener.onItemClick(v,getLayoutPosition());
                    }
                }
            });
        }
    }

    class MyHeadViewHolder extends RecyclerView.ViewHolder{
        ViewPager mvp;
        public MyHeadViewHolder(View view){
            super(view);
            mvp =view.findViewById(R.id.vp_headView);
        }
    }
}

