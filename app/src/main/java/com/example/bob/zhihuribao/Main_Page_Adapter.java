package com.example.bob.zhihuribao;

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

public class Main_Page_Adapter extends RecyclerView.Adapter<Main_Page_Adapter.ViewHolder> {

    private List<Main_page> mShow_List;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View click_view;
        ImageView text_Images;
        TextView text_Text_title;

        public ViewHolder(View view){
            super(view);
            click_view = view;
            text_Images= view.findViewById(R.id.show_main_page_images);
            text_Text_title= view.findViewById(R.id.show_main_page_text);
        }
    }

    public Main_Page_Adapter(List<Main_page> Show_List){
        mShow_List=Show_List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_page_item, parent, false);
       final ViewHolder holder = new ViewHolder(view);
        holder.click_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();

                Toast.makeText(MyApplication.getContext(),position+"xxx",Toast.LENGTH_SHORT).show();
            }
        });
        return  holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
        Main_page main_page = mShow_List.get(position);
        Picasso.with(MyApplication.getContext()).load(main_page.getImages()).into(holder.text_Images);
       // holder.text_Images.setImageResource(main_page.getImages());
        holder.text_Text_title.setText(main_page.getText_title());
    }
    @Override
    public int getItemCount()  {
        return mShow_List.size();
    }
}

