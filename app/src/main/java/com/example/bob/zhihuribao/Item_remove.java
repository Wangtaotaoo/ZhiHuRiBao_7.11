package com.example.bob.zhihuribao;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jcodecraeer.xrecyclerview.ItemTouchHelperAdapter;

import static com.example.bob.zhihuribao.Save_SQLitepal.my_saveAdapter;

/**
 * Created by bob on 17-7-23.
 */

public class Item_remove extends ItemTouchHelper.Callback{

    private ItemTouchHelperAdapter mAdapter;

    public Item_remove(ItemTouchHelperAdapter adapter){
        mAdapter =adapter;
    }

    @Override
public int getMovementFlags(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder){
        //设置左右滑动
        int swipeFlags = ItemTouchHelper.LEFT;/**只允许从右向左滑动*/
        //上下滑动
        int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        return  makeMovementFlags(dragFlags,swipeFlags);
    }
    @Override
    //上下滑动，用不到 ，但是必须重写
    public  boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target){

        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }
    //左右滑动
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder,int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
