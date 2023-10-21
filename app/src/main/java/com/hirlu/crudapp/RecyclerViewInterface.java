package com.hirlu.crudapp;

import android.view.View;

public interface RecyclerViewInterface {

    void onItemClick(View view, int pos);
    void onItemLongClick(int pos);
}
