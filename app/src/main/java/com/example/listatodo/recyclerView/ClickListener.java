package com.example.listatodo.recyclerView;

public interface ClickListener {
    void onClickItem(int position);
    void onClickButtonFinish(int position);

    void onLongClickItem(int position);
}
