package com.huangyuanlove.view_inject_api.template;

import android.app.Activity;
import android.view.View;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public class TemplateFragment$$InjectView implements InjectView<TemplateFragment> {
    @Override
    public void injectView(final TemplateFragment templateFragment) {
        int id = -1;
        Activity activity = templateFragment.getActivity();

        id = 10086;
        templateFragment.buttonOne = activity.findViewById(id);
        templateFragment.buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateFragment.onClickButtonOne(view);
            }
        });

        id = 10010;
        templateFragment.buttonTwo = activity.findViewById(id);
        templateFragment.buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateFragment.onClickButtonTwo(view);
            }
        });
    }
}
