package com.huangyuanlove.view_inject_api.template;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public interface InjectView<T> {

    void injectView(T t);


}
