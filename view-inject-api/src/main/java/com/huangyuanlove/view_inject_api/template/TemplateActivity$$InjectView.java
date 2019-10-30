package com.huangyuanlove.view_inject_api.template;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.huangyuanlove.view_inject_api.R;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public class TemplateActivity$$InjectView implements InjectView<TemplateActivity> {
    @Override
    public void injectView(final TemplateActivity activity) {
        int id = -1;

        id = 10086;
        activity.buttonOne = activity.findViewById(id);
        activity.buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onClickButtonOne(view);
            }
        });

        id = 1001;
        activity.buttonTwo = activity.findViewById(id);
        activity.buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onClickButtonTwo(view);
            }
        });

    }


}
