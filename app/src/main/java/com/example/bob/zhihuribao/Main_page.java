package com.example.bob.zhihuribao;

/**
 * Created by bob on 17-7-8.
 */

public class Main_page {

    private String text_title;
    private String    images;


    Main_page(String text_titles,String imagess)
    {
        text_title=text_titles;
        images = imagess;
    }

    public void setText_title(String temp_text_title)
    {
        text_title = temp_text_title;
    }
    public void setImages(String  temp_images)
    {
        images=temp_images;
    }

    public String getText_title(){
        return text_title;
    }
    public String   getImages(){
        return  images;
    }
}

