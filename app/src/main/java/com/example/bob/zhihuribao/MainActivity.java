package com.example.bob.zhihuribao;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Main_page> main_pageList = new ArrayList<>();
    Main_page main_pager = new Main_page("null","null");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequestWithOkHttp();

        /**设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**设置滑动操作DrawerLayout*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
        }
        /**为菜单栏加入监听事件*/
        /**菜单栏*/
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);//菜单声明
        navView.setCheckedItem(R.id.Main_page);//默认选中栏
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main_page);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        Main_Page_Adapter adapter = new Main_Page_Adapter(main_pageList);
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setAdapter(adapter);/**解决NO adapter attached; skipping layout应该放在onCreate方法中*/
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                 Main_page main_page_content =(Main_page)msg.obj;
                    main_pageList.add(main_page_content);
//                    Main_page main_page_content = new Main_page();
//                    Log.d(msg.obj.toString(), "handleMessage:////////////////////////////////////////// ");
//                    main_page_content.setText_title(msg.obj.toString());
//                    main_pageList.add(main_page_content);
//                case 2:
//                    Log.d(msg.obj.toString(), "handleMessage: ++++++++++++++++++++++++++++++++++++++++");
//                    Main_page main_page_images = new Main_page();
//                    main_page_images.setImages(msg.obj.toString());
//                    main_pageList.add(main_page_images);
                default:
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main_page);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    Main_Page_Adapter adapter = new Main_Page_Adapter(main_pageList);
                    recyclerView.addItemDecoration(new MyDecoration());
                    recyclerView.setAdapter(adapter);/**解决NO adapter attached; skipping layout应该放在onCreate方法中*/
                    break;

            }
        }
    };

    //实例化menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }//用来设置点击事件

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Night:
                break;
            case R.id.Settings:
                Toast.makeText(this, "you clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
        }
        return true;
    }



    /**发送网络请求*/
    /**
     * 新线程
     */
    private void sendRequestWithOkHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://news-at.zhihu.com/api/4/news/latest").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析json格式数据
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {

            JSONObject jsonObject1_1 = new JSONObject(jsonData);//总数据

            JSONArray jsonArray1_1 = jsonObject1_1.getJSONArray("stories");//array 里面是好多个重复的Object
            for (int i = 0; i < jsonArray1_1.length(); i++) {
                JSONObject jsonObject1_1_1 = jsonArray1_1.getJSONObject(i);//巡历每个jsonObject.得到jsonObject 整体数据
                String title_list = jsonObject1_1_1.getString("title");//获得title
                JSONArray jsonArray1_1_1 = jsonObject1_1_1.getJSONArray("images");//这个比较特殊，这里面又是一个arrray，只有一个，用0代替
                String images = jsonArray1_1_1.getString(0);


                main_pager = new Main_page(title_list,images);
                Message message = new Message();
                message.obj = main_pager;
                message.what = 1;
                handler.sendMessage(message);
//                Message message = new Message();
//                message.obj = title_list;
//                message.obj=images;
//                message.what = 1;
//                handler.sendMessage(message);

//                Message message_IMG = new Message();
//                message_IMG.obj = images;
//                message_IMG.what= 2;
//                handler.sendMessage(message_IMG);


//                /**顶部滚动*/
//                JSONArray jsonArray2_1=jsonObject1_1.getJSONArray("top_stories");
//                for(int j=0;j<=jsonArray2_1.length();j++){
//                    JSONObject jsonObject2_1_1 = jsonArray2_1.getJSONObject(j);
//                    String title_roll= jsonObject2_1_1.getString("title");
//                    String image=jsonObject2_1_1.getString("image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

