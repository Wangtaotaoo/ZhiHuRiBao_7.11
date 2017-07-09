package com.example.bob.zhihuribao;

/**
 * Created by bob on 17-7-8.
 */

public class Main_page {

    private String text_title;
    private int   images;


    Main_page(String text_titles,int imagess){
        text_title=text_titles;
        images=imagess;
    }

    public String getText_title(){
        return text_title;
    }
    public int  getImages(){
        return  images;
    }
}

