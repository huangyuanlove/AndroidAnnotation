package com.huangyuanlove.view_inject_api.template;

import android.app.Activity;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public class TemplateFragment$$InjectView implements InjectView<TemplateFragment> {
    @Override
    public void injectView(TemplateFragment templateFragment) {
        int id = -1;
        Activity activity = templateFragment.getActivity();

        id = 10086;
        templateFragment.buttonOne = activity.findViewById(id);

        id = 10010;
        templateFragment.buttoTwo = activity.findViewById(id);
    }
}
