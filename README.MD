## 编译时注解框架

#### @BindView
使用方法：
在application module中
``` java
@BindView(id = R.id.xxx)
    protected Button buttonOne;

```
在 library module中
``` java
@BindView(idStr = "xxx")
    protected Button buttonTwo;
```
需要注意的是：字段的访问修饰符权限必须大于 `protected`,在字段使用前调用(一般是在OnCreate、onCreateView)`ViewInjector.bind(this);`
Activity示例: `TestViewInjectActivity.java`
Fragment示例: `TestViewInjectFragment.java`
Adapter示例: `ListViewAdapter`


#### @ClickResponder
在application module中
``` java
    @ClickResponder(id = {R.id.xxx,R.id.yyy})
    public void onClickButtonOne(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_one", Toast.LENGTH_SHORT).show();
    }

```
在 library module中
``` java
    @ClickResponder(idStr = {"xxx","yyy"})
    public void onClickButtonTwo(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_two", Toast.LENGTH_SHORT).show();
    }
```
支持同一个方法绑定到多个view

---

#### @LongClickResponder
在 application module中
``` java
 @LongClickResponder(idStr = {"test_view_inject_two"})
    public void onLongClickButtonTwo(View v){
        Toast.makeText(TestViewInjectActivity.this, "long click button two", Toast.LENGTH_SHORT).show();
    }
```
在 library module 中
``` java
    @LongClickResponder(id = R.id.test_long_click)
    public void onLongClick(View v){
        Toast.makeText(TestViewInjectActivity.this, "test_long_click", Toast.LENGTH_SHORT).show();
    }
```
支持同一个方法绑定到多个view




TODO

- [x] findViewById
- [x] 替换为javapoet https://github.com/square/javapoet
- [x] 点击事件、长按事件
- [ ] 广播
- [ ] 路由
- [ ] 动态权限


持续时间，预计到明年6月完成
