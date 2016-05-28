package com.seymour.todolist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by seymour on 2016/05/27.
 */
public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    RecyclerAdapter adapter;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper( RecyclerAdapter adapter) {
        super(ItemTouchHelper.LEFT,ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.deleteTask(viewHolder.getAdapterPosition());
    }
}
