package com.huangyuanlove.view_inject_api.template;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-29
 * Email: huangyuan@chunyu.me
 */
public class TemplateView$$InjectView implements InjectView<TemplateView> {
    @Override
    public void injectView(TemplateView templateView) {
        int id = -1;
        id = 10086;
        templateView.buttonOne = templateView.findViewById(id);
        id = 10086;
        templateView.buttonTwo = templateView.findViewById(id);


    }
}
