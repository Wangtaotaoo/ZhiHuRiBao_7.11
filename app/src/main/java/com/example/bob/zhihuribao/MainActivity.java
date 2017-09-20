package com.example.bob.zhihuribao;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Main_page> main_pageList = new ArrayList<>();
    private List<Main_page> main_pageList_header = new ArrayList<>();//存头部的数据
    Main_page main_pager = new Main_page("null","null","null");
    Main_page main_page_header = new Main_page("null","null","null");
//获得今日的日报的时间
    private  String   date;
    //设置下滑刷新的次数
    private  int times=0;


    //viewpager
    private View page1;
    private View page2;
    private View page3;
    private View page4;
    private View page5;
    private List<View> pagelist;
    public static My_Viewpager_Adapter my_viewpager_adapter;//用static声明为公有，使得所有类都可以调用

  //下拉刷新
  private XRecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequestWithOkHttp();

        /**设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView view  = (NavigationView) findViewById(R.id.nav_view);//菜单声明
        /**设置滑动操作DrawerLayout*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);

        }

        LayoutInflater inflater = getLayoutInflater();//找到布局id等
        page1 = inflater.inflate(R.layout.viewpager_head1, null);
        page2 = inflater.inflate(R.layout.viewpager_head2, null);
        page3 = inflater.inflate(R.layout.viewpager_head3, null);
        page4 = inflater.inflate(R.layout.viewpager_head4, null);
        page5 = inflater.inflate(R.layout.viewpager_head5, null);
        pagelist = new ArrayList<>();//pagelist = new ArrayList<View>();
        pagelist.add(page1);
        pagelist.add(page2);
        pagelist.add(page3);
        pagelist.add(page4);
        pagelist.add(page5);

        /**为侧滑栏加入监听事件*/
        /**侧滑栏*/

        view .setCheckedItem(R.id.Main_page);//默认选中栏
        view .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
/**布局中Navigation View应该写在之后
 * <DrawerLayout>
 <FrameLayout />
 <NavigationView />
 </DrawerLayout>*/
            @Override
            public boolean onNavigationItemSelected(MenuItem Menuitem) {
                switch(Menuitem.getItemId()) {
                    case R.id.Cai_jing:
                        Toast.makeText(MyApplication.getContext(),"Caijingf",Toast.LENGTH_SHORT).show();
                       makeTheme_happen("财经日报");
                        break;
                    case R.id.Xin_li:
                        makeTheme_happen("日常心理学");
                        break;
                    case R.id.Tui_jian:
                        makeTheme_happen("用户推荐日报");
                        break;
                    case R.id.Movie:
                        makeTheme_happen("电影日报");
                        break;
                    case R.id.Wu_liao:
                        makeTheme_happen("不许无聊");
                        break;
                    case R.id.She_ji:
                        makeTheme_happen("设计日报");
                        break;

                    case R.id.Da_gong_si:
                        makeTheme_happen("大公司日报");
                        break;
                    case R.id.Hu_lian_wang:
                        makeTheme_happen("互联网安全");
                        break;
                    case R.id.Game:
                        makeTheme_happen("开始游戏");
                        break;
                    case R.id.Music:
                        makeTheme_happen("音乐日报");
                        break;
                    case R.id.Dong_man:
                        makeTheme_happen("动漫日报");
                        break;
                    case R.id.Ti_yu:
                        makeTheme_happen("体育日报");
                        break;
default:
    break;
                }


                return true;
            }
        });


         mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_view_main_page);
        mRecyclerView.addItemDecoration(new MyDecoration());//添加分割线
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        Main_Page_Adapter adapter = new Main_Page_Adapter(main_pageList);
        mRecyclerView.setAdapter(adapter);/**解决NO adapter attached; skipping layout应该放在onCreate方法中*/

    }


    private  void makeTheme_happen(String code){            //跳转到下一个活动

        Intent intent = new Intent(MainActivity.this,Theme.class);
        intent.putExtra("key",code);
        startActivity(intent);
    }




    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                   mRecyclerView = (XRecyclerView) findViewById(R.id.recycler_view_main_page);
                    mRecyclerView.addItemDecoration(new MyDecoration());//添加分割线
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    final Main_Page_Adapter adapter = new Main_Page_Adapter(main_pageList);
                    mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener(){
                        @Override
                        public void onRefresh(){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    mRecyclerView.refreshComplete();
                                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MyApplication.getContext(),"刷新",Toast.LENGTH_SHORT).show();
                                    finish();
                                }//刷新是通过xRecyclerView  Intent 重启一遍主活动。
                            },2000);
                        }

                        @Override
                        public void onLoadMore(){

                            sendRequestWithOkHttp_history();
                            adapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    });
                    mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                    mRecyclerView.setAdapter(adapter);/**解决NO adapter attached; skipping layout应该放在onCreate方法中*/

                    adapter.setOnItemClickListener(new Main_Page_Adapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view , int position){
                            String  id_detail = main_pageList.get(position-2).getDetail_id();//这里要设置—2 因为前面加了一个viewholder
                            String  image_detail=main_pageList.get(position-2).getImages();
                            String  title_detail=main_pageList.get(position-2).getText_title();
                            Log.d(id_detail, "onItemClick:****************************0 ");
                            Log.d(id_detail, "onItemClick: +++++++++++++++++++++++++++++0");
                           Main_page for_detail =new Main_page(title_detail,image_detail,id_detail);

                            Intent intent = new Intent(MainActivity.this,Show_detail.class);
                            Bundle bundle =new Bundle();
                            bundle.putSerializable("key",for_detail);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;

                case 2:
                    my_viewpager_adapter = new My_Viewpager_Adapter(pagelist,main_pageList_header);
//一开始只需要传过去id 后来发现还要传过去title和images （大图）；
                    break;
                default:
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
Intent intent =new Intent(MainActivity.this,Save_SQLitepal.class);
                startActivity(intent);
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
        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/news/latest",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call ,Response response)throws IOException{
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }
            @Override
            public void onFailure(Call call,IOException e){
                //处理
            }
        });
    }

    /**
     * 解析json格式数据
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {

            JSONObject jsonObject1_1 = new JSONObject(jsonData);//总数据
            date = jsonObject1_1.getString("date");
            Log.d(date, "parseJSONWithJSONObject: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            JSONArray jsonArray1_1 = jsonObject1_1.getJSONArray("stories");//array 里面是好多个重复的Object
            for (int i = 0; i < jsonArray1_1.length(); i++) {
                JSONObject jsonObject1_1_1 = jsonArray1_1.getJSONObject(i);//巡历每个jsonObject.得到jsonObject 整体数据
                String title_list = jsonObject1_1_1.getString("title");//获得title
                String id_detail = jsonObject1_1_1.getString("id");
                JSONArray jsonArray1_1_1 = jsonObject1_1_1.getJSONArray("images");//这个比较特殊，这里面又是一个arrray，只有一个，用0代替
                String images = jsonArray1_1_1.getString(0);


                main_pager = new Main_page(title_list,images,id_detail);
                main_pageList.add(main_pager);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
            final JSONArray jsA = jsonObject1_1.getJSONArray("top_stories");//解析得到这三个。
            for(int j =0;j<jsA.length();j++){
                JSONObject jsO= jsA.getJSONObject(j);
                final String title_head =jsO.getString("title");
                final String image_head = jsO.getString("image");
                final String id_head = jsO.getString("id");
                main_page_header = new Main_page(title_head,image_head,id_head);
                main_pageList_header.add(main_page_header);
                Message message_v = new Message();
                message_v.what=2;
//                message_v.obj = main_pageList_header;
                handler.sendMessage(message_v);

            }
            runOnUiThread(new Runnable() {//进行ui操作。功能相当于handler；
                @Override
                public void run() {


                    TextView textView1=page1.findViewById(R.id.view_pager_head1_text);
                    textView1.setText(main_pageList_header.get(0).getText_title());
                    ImageView imageView1 = page1.findViewById(R.id.view_pager_head1_image);
                    Picasso.with(MyApplication.getContext()).load(main_pageList_header.get(0).getImages()).into(imageView1);

                    TextView textView2 =page2.findViewById(R.id.view_pager_head2_text);
                    textView2.setText(main_pageList_header.get(1).getText_title());
                    ImageView imageView2 = page2.findViewById(R.id.view_pager_head2_image);
                    Picasso.with(MyApplication.getContext()).load(main_pageList_header.get(1).getImages()).into(imageView2);

                    TextView textView3 =page3.findViewById(R.id.view_pager_head3_text);
                    textView3.setText(main_pageList_header.get(2).getText_title());
                    ImageView imageView3 =page3.findViewById(R.id.view_pager_head3_image);
                    Picasso.with(MyApplication.getContext()).load(main_pageList_header.get(2).getImages()).into(imageView3);

                    TextView textView4 = page4.findViewById(R.id.view_pager_head4_text);
                    ImageView imageView4=page4.findViewById(R.id.view_pager_head4_image);
                    textView4.setText(main_pageList_header.get(3).getText_title());
                    Picasso.with(MyApplication.getContext()).load(main_pageList_header.get(3).getImages()).into(imageView4);

                    TextView  textView5 =page5.findViewById(R.id.view_pager_head5_text);
                    ImageView imageView5 =page5.findViewById(R.id.view_pager_head5_image);
                    textView5.setText(main_pageList_header.get(4).getText_title());
                    Picasso.with(MyApplication.getContext()).load(main_pageList_header.get(4).getImages()).into(imageView5);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void  sendRequestWithOkHttp_history(){
        Log.d("运行了？？？？？ ", "sendRequestWithOkHttp_history: ");
        int s = Integer.parseInt(date);
        Log.d(date, "sendRequestWithOkHttp_history:*************************************************** ");
        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/news/before/"+s,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call,Response response)throws IOException{
                String responseData= response.body().string();
                para_history(responseData);
            }
            public  void onFailure(Call Call,IOException e){
                //处理异常。
            }
        });
    }
    private void para_history(String response){
try{
    JSONObject json = new JSONObject(response);
     date = json.getString("date");
    JSONArray jsonA = json.getJSONArray("stories");
    for(int k =0;k<jsonA.length();k++){
        JSONObject json1 = jsonA.getJSONObject(k);
        String title_history = json1.getString("title");
        String id_history =json1.getString("id");
        JSONArray jsonA1 =json1.getJSONArray("images");
        Log.d(id_history, "para_history: ");
        String images_history = jsonA1.getString(0);
        Log.d(images_history, "para_history: ");

        Main_page page_history =new Main_page(title_history,images_history,id_history);
        main_pageList.add(page_history);
    }
}catch (Exception e){
    e.printStackTrace();
}
    }

}

