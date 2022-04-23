## 编译时注解框架

### 新增AndroidStudio插件
可配合 https://github.com/huangyuanlove/AndroidAnnotation-Plugin 插件使用,类似ButterKnife一键生成代码

### change log
> 新增 广播接收器支持permission
> 新增 广播接收器支持flag
> 新增 广播接收器支持priority
> 优化PermissionUtil
### 引用

``` gradle
implementation 'com.huangyuanlove:view-inject-api:1.1.1'
implementation 'com.huangyuanlove:view-inject-annotation:1.1.0'
annotationProcessor 'com.huangyuanlove:view-inject-compile:1.1.0'
```

### 混淆
如果开启了混淆，则需要在混淆文件中配置
```
-keep public class com.huangyuanlove.view_inject_annotation.**
-keep public class com.huangyuanlove.view_inject_api.**
-keep public class com.huangyuanlove.view_inject_compiler.**
-keep public class **$$Router
-keep public class **$ViewInjector {
  **[] $VALUES;
  public *;
}
```



----

### 重要
@BindView 注解推荐使用idStr，不建议使用id；gradle8.0之后生成的R文件字段不再是final，无法用于注解

### 优化方向

1. idStr不再使用 `getResources().getIdentifier`,可以使用字符串拼接方式，但是需要导入对应R文件才行
2. id方式可以自己复制一份R文件，使其字段变成final类型


** view-inject-annotation1.1.1 变更 **

### 权限相关优化

1.1.1之前的版本(不包含1.1.1)在使用`PermissionUtil`时，从流程上来讲还是挺繁琐的：

1. 先调用`PermissionUtil.hasSelfPermissions`来检测是否有权限
2. 然后调用`PermissionUtil.shouldShowRequestPermissionRationale`判断是否需要弹出权限说明窗
3. 最后调用`ActivityCompat.requestPermissions`来请求权限
4. 然后在重写`Activity`的`onRequestPermissionsResult`方法，在该方法中通过`PermissionUtil.onRequestPermissionsResult(this,requestCode,permissions,grantResults,new PermissionUtil.RequestPermissionResult())`判断用户对权限的响应

优化之后，我们只需要`new Permissions(this).requestPermissions(int requestCode, PermissionFragment.OnPermissionResult onPermissionResult, String... permissions)`这个方法就好了，代码如下：

``` java
PermissionFragment.OnPermissionResult onPermissionResult = new PermissionFragment.OnPermissionResult() {
            @Override
            public void onPermissionResult(int requestCode, ArrayList<String> grantPermission, ArrayList<String> shouldShowRationalePermission, ArrayList<String> neverAskAgainPermission) {
                Log.e("huangyuan","grantPermission:"+grantPermission);
                Log.e("huangyuan","shouldShowRationalePermission:"+shouldShowRationalePermission);
                Log.e("huangyuan","neverAskAgainPermission:"+neverAskAgainPermission);
            }
        };
        
new Permissions(this).requestPermissions(AUDIO_PERMISSION, onPermissionResult, Manifest.permission.RECORD_AUDIO);
```

1. 实现`PermissionFragment.OnPermissionResult`接口
2. 创建`Permissions`
3. 调用`Permissions`的实例方法`requestPermissions`,第一个参数为int类型requestCode，第二个参数为步骤1中的实现类，后面的不定长参数是需要申请的权限

Q:为什么在Activity和Fragment中同时重写了`onRequestPermissionsResult`方法，在Fragment中收不到回调？

A:在Activity中使用`ActivityCompat.requestPermissions`来申请权限，在Fragmen中直接使用`requestPermissions`申请权限；如果Fragment和Activity同时重写了``onRequestPermissionsResult``方法，则需要在Activity重写的方法中调用`super.onRequestPermissionsResult`，这样Fragment中才会收到回调。至于为什么，RTFSC。





----

以下是1.1.0版本说明


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

在字段使用前(一般是在Activity的onCreate或者Fragment的onCreateView方法中)调用`ViewInjector.parseBundle(this);`


### @UriValue

只支持如下几种类型，并且只能在Activity中使用

``` java
@UriValue(key = "name")
String name;
@UriValue(key = "id")
int id;
@UriValue(key = "double")
double aDouble;

@UriValue(key = "float")
float aFloat;

@UriValue(key = "long")
long aLong;

@UriValue(key = "boolean")
boolean aBoolean;
```
在字段使用前(一般是在Activity的onCreate方法中)调用`ViewInjector.parseBundle(this);`


### @BroadcastResponder
广播分为本地广播和全局广播，注解接收广播接收器之后，需要自己去解注册
使用方式

``` java
public static final String NORMAL_LOCAL = "normal_local";
public static final String NORMAL_GLOBAL = "normal_global";
public static final String PERMISSION_GLOBAL = "permission_global";
public static final String BROADCAST_PERMISSION = "com.huangyuanlove.permission_broadcast";


//在 OnCreate 中注册广播接收器 
HashMap<Integer, ArrayList<BroadcastReceiver>> integerArrayListHashMap = ViewInjector.registerReceiver(this);

//使用注解定义接收广播的action 以及对应的回调方法
@BroadcastResponder(action = NORMAL_LOCAL)
void onReceiveNormalLocalBroadcast(Context context, Intent intent) {
  showAction.setText("onReceiveNormalLocalBroadcast:"+intent.getAction());
}

@BroadcastResponder(action = NORMAL_GLOBAL,type = BroadcastResponder.GLOBAL_BROADCAST)
void onReceiveNormalGlobalBroadcast(Context context, Intent intent) {
  showAction.setText("onReceiveNormalGlobalBroadcast:"+intent.getAction());
}

@BroadcastResponder(action = PERMISSION_GLOBAL,permission = BROADCAST_PERMISSION,type = BroadcastResponder.GLOBAL_BROADCAST)
void onReceivePermissionGlobalBroadcast(Context context, Intent intent) {
  showAction.setText("onReceivePermissionGlobalBroadcast"+intent.getAction());
}

//在 onDestroy 中解注册
  @Override
  protected void onDestroy() {
  super.onDestroy();
  if(integerArrayListHashMap!=null){
    ArrayList<BroadcastReceiver> localReceiverList = integerArrayListHashMap.get(BroadcastResponder.LOCAL_BROADCAST);
    if(localReceiverList!=null && localReceiverList.size()>0){
      for(BroadcastReceiver receiver : localReceiverList){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
      }
    }
    ArrayList<BroadcastReceiver> globalReceiverList = integerArrayListHashMap.get(BroadcastResponder.GLOBAL_BROADCAST);
    if(globalReceiverList!=null && globalReceiverList.size()>0){
      for(BroadcastReceiver receiver : globalReceiverList){
        unregisterReceiver(receiver);
      }
    }

  }
}

//发送广播
@ClickResponder(id= R.id.send_normal_local_broadcast)
public void sendNormalLocalBroadcast(View v){
  Intent intent = new Intent();
  intent.setAction(TestBroadcastActivity.NORMAL_LOCAL);
  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
}

@ClickResponder(id= R.id.send_normal_global_broadcast)
public void sendNormalGlobalBroadcast(View v){
  Intent intent = new Intent();
  intent.setAction(TestBroadcastActivity.NORMAL_GLOBAL);
  sendBroadcast(intent);
}
@ClickResponder(id= R.id.send_permisson_global_broadcast)
public void sendPermissonGlobalBroadcast(View v){
  Intent intent = new Intent();
  intent.setAction(TestBroadcastActivity.PERMISSION_GLOBAL);
  sendBroadcast(intent,TestBroadcastActivity.BROADCAST_PERMISSION);
}
```







**以下是0.1.0版本的说明**

``` java
//在 OnCreate 中注册广播接收器
HashMap<Integer, BroadcastReceiver> broadcastReceiverHashMap = ViewInjector.registerReceiver(this);

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


### @RouterModule and @RouterPath

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



#### PermissionUtil

 将要进行的动作，需要某项危险权限时，我们需要先校验权限 PermissionUtil.hasSelfPermissions
 如果有权限，则进行动作。
 如果没有权限，校验是否需要提示 PermissionUtil.shouldShowRequestPermissionRationale；如果需要提示，则弹出提示框，用户点了允许之后再申请权限。如果不需要提示，则直接申请权限；
 申请权限的结果有三种：

 1. 授权onGrant
 2. 禁止onDenied
 3. 禁止并不在提示 onNeverAskAgain


#### 相关博客
*  [https://blog.huangyuanlove.com/2019/11/23/自定义注解-打造自己的框架-上篇/](https://blog.huangyuanlove.com/2019/11/23/自定义注解-打造自己的框架-上篇/)
*  [https://blog.huangyuanlove.com/2019/12/01/自定义注解，打造自己的框架-中篇/](https://blog.huangyuanlove.com/2019/12/01/自定义注解，打造自己的框架-中篇/)
* [https://blog.huangyuanlove.com/2019/12/04/自定义注解，打造自己的框架-下篇/](https://blog.huangyuanlove.com/2019/12/04/自定义注解，打造自己的框架-下篇/)
* [https://blog.huangyuanlove.com/2019/12/09/自定义注解，打造自己的框架-下下篇/](https://blog.huangyuanlove.com/2019/12/09/自定义注解，打造自己的框架-下下篇/)
* [https://blog.huangyuanlove.com/2019/12/10/自定义注解，打造自己的框架-下下下篇/](https://blog.huangyuanlove.com/2019/12/10/自定义注解，打造自己的框架-下下下篇/)
* [https://blog.huangyuanlove.com/2019/12/12/自定义注解，打造自己的框架-最终篇/](https://blog.huangyuanlove.com/2019/12/12/自定义注解，打造自己的框架-最终篇/)

----

TODO

- [x] @BindView 代替  findViewById 
- [x] @ClickResponder 代替 setOnClickListener
- [x] @LongClickResponder 代替 setOnLongClickListener
- [x] @IntentValue 代替 getIntent().getXXX
- [x] @UriValue 代替 getQueryParameter
- [x] @BroadcastResponder 代替 registerReceiver
- [x] @RouterModule、@RouterPath 来进行反依赖传递调用
- [x] PermissionUtil 申请权限


