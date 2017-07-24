package com.example.bob.zhihuribao;

import java.io.Serializable;

/**
 * Created by bob on 17-7-8.
 */

public class Main_page implements Serializable{

    private String    text_title;
    private String    images;
    private String    detail_id;

    Main_page(String text_titles,String imagess,String id)
    {
        text_title=text_titles;
        images = imagess;
        detail_id = id;
    }


    public String getText_title(){
        return text_title;
    }
    public String   getImages(){
        return  images;
    }
    public String getDetail_id(){
        return detail_id;
    }
}

