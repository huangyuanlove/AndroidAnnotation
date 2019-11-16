## 编译时注解框架

### 引用


``` gradle
    implementation 'com.huangyuanlove:view-inject-api:0.0.4'
    implementation 'com.huangyuanlove:view-inject-annotation:0.0.4'
    annotationProcessor 'com.huangyuanlove:view-inject-compile:0.0.4'
//只包含了BindView、ClickResponder、LongClickResponder、IntentValue、BroadcastResponder注解
```

### @BindView
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


### @ClickResponder
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

### @LongClickResponder
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


### @IntentValue
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


### @BroadcastResponder
广播分为本地广播和全局广播，注解接收广播接收器之后，需要自己去解注册
使用方式
``` java
//在 OnCreate 中注册广播接收器
HashMap<Integer, BroadcastReceiver> broadcastReceiverHashMap = BroadcastInject.registerReceiver(this);

//使用注解定义接收广播的action 以及对应的回调方法

@BroadcastResponder(action = {"com.huangyuanblog","com.huangyuanblog.www"})
public void onReceiveBroadcast(Context context, Intent intent){
    Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
}

//type默认本地广播，接收全局广播需要指定type = BroadcastResponder.GLOBAL_BROADCAST
@BroadcastResponder(action = {"com.huangyuanlove",Intent.ACTION_AIRPLANE_MODE_CHANGED},type = BroadcastResponder.GLOBAL_BROADCAST)
public void onReceiveBroadcastOther(Context context, Intent intent){
    Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
}

//在 onDestroy 中解注册

@Override
protected void onDestroy() {
    super.onDestroy();
    if(broadcastReceiverHashMap!=null){
        if(broadcastReceiverHashMap.get(BroadcastResponder.GLOBAL_BROADCAST) !=null){

            unregisterReceiver(broadcastReceiverHashMap.get(BroadcastResponder.GLOBAL_BROADCAST));
        }

        if(broadcastReceiverHashMap.get(BroadcastResponder.LOCAL_BROADCAST) !=null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverHashMap.get(BroadcastResponder.LOCAL_BROADCAST));
        }

    }
}


```
需要注意的是，默认的广播接收器是本地广播，如果需要接收全局广播,比如打开飞行模式等，需要指定`type = BroadcastResponder.GLOBAL_BROADCAST`
千万别忘记解注册


#### @RouterModule and @RouterPath

**相同的 schema 和 host 中不要有相同的path及path对应的方法名**
**相同的 schema 和 host 中不要有相同的path及path对应的方法名**
**相同的 schema 和 host 中不要有相同的path及path对应的方法名**

在不同的Provider中提供相同的schema和host会导致Router被覆盖，无法保证路由目标的正确性。

一般以App的名字做schema，模块(module)的名字做host，目标`Activity`的类名做方法名及path。
比如App的名字为`jandan`，模块(module)名为 `account`，目标Activity的类名为`LoginActivity`
则对应Provider推荐这样写

``` java

@RouterModule(schema = "Jandan",host = "account")
public class AccountModuleRouterProvider {
    
    @RouterPath(value = "login")
    public void toLoginActivity(Context context, String userName){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.put("userName",userName);
        context.startActivity(intent);
    }
    
}

//在其他类中使用
Router.to("Jandan://account/login").addParam(this,"userName").done(new Router.InvokeResultListener() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(EXT_MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(Object o) {

            }
        });

```
Router的本质上是进行的方法调用，可以反依赖调用。就像工程中的`app`模块依赖`example_lib`模块，我们仍然可以在`example_lib`调用`app`中的方法。
当然正向调用也是可以的。

具体示例可以看`example_lib`中的`EXT_MainActivity`类调用`app`中`MainProvider`类方法

----

TODO

- [x] BindView 代替  findViewById 
- [x] ClickResponder 代替 setOnClickListener
- [x] LongClickResponder 代替 setOnLongClickListener
- [x] IntentValue 代替 getIntent().getXXX
- [x] 广播
- [x] 路由
- [ ] 动态权限


持续时间，预计到明年6月完成
