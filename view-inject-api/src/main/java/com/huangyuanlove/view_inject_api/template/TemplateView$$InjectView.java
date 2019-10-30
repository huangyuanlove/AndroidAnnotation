package com.huangyuanlove.view_inject_api.template;

import android.view.View;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public class TemplateView$$InjectView implements InjectView<TemplateView> {
    @Override
    public void injectView(final TemplateView templateView) {
        int id = -1;

        id = 10086;
        templateView.buttonOne = templateView.findViewById(id);
        templateView.buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateView.onClickButtonOne(view);
            }
        });


        id = 10086;
        templateView.buttonTwo = templateView.findViewById(id);
        templateView.buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateView.onClickButtonTwo(view);
            }
        });


    }
}
