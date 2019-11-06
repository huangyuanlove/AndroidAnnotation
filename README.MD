## 编译时注解框架

#### 引用

目前IntentValue注解仍在测试，尚未包含在下面开源库中

``` gradle
    implementation 'com.huangyuanlove:view-inject-api:0.0.2'
    implementation 'com.huangyuanlove:view-inject-annotation:0.0.2'
    annotationProcessor 'com.huangyuanlove:view-inject-compile:0.0.2'
//只包含了BindView、ClickResponder、LongClickResponder注解
```

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
需要注意的是：方法的访问修饰符权限必须大于 `protected`,在方法使用前调用(一般是在OnCreate、onCreateView)`ViewInjector.bind(this);`
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
需要注意的是：方法的访问修饰符权限必须大于 `protected`,在方法使用前调用(一般是在OnCreate、onCreateView)`ViewInjector.bind(this);`
支持同一个方法绑定到多个view


#### IntentValue
用来代替 getIntent().getXXX 或者Fragment中的getArguments().getXXX
使用方式：
``` java
@IntentValue(key = "String")
String value = "default"

@IntentValue(key = "parcelableObject",type = IntentValue.PARCELABLE_OBJECT)
ParcelableObject parcelableObject;

@IntentValue(key = "parcelableObjects" ,type = IntentValue.PARCELABLE_ARRAY_OBJECT)
ParcelableObject[] parcelableObjects;

@IntentValue(key = "parcelableObjectArrayList",type = IntentValue.PARCELABLE_ARRAYLIST_OBJECT)
ArrayList<ParcelableObject>  parcelableObjectArrayList;

@IntentValue(key = "serializableObject",type = IntentValue.SERIALIZABLE_OBJECT)
UnParcelableObject serializableObject;

```
注意：
如果传递的是Parcelable对象，type声明为`IntentValue.PARCELABLE_OBJECT`

如果传递的是Parcelable对象数组，type声明为`IntentValue.PARCELABLE_ARRAY_OBJECT`

如果传递的是Parcelable对象ArrayList，type声明为`IntentValue.PARCELABLE_ARRAYLIST_OBJECT`

如果传递的是序列化对象(实现了Serializable接口),type声明为`IntentValue.SERIALIZABLE_OBJECT`

在字段使用前(一般是在Activity的onCreate或者Fragment的onCreateView方法中)调用`BundleInjector.parseBundle(this);`

----

TODO

- [x] BindView 代替  findViewById 
- [x] ClickResponder 代替 setOnClickListener
- [x] LongClickResponder 代替 setOnLongClickListener
- [x] IntentValue 代替 getIntent().getXXX
- [ ] 广播
- [ ] 路由
- [ ] 动态权限


持续时间，预计到明年6月完成
