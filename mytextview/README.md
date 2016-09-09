# EventBus的学习心得
3.0以后的EventBus已经由原来的同名函数改为了使用注解
例如，原来使用的PostThread,MainThread,BackgroundThread,Async
改为注解：
```
@Subscribe(threadMode = ThreadMode.POSTING)  
```
## 1.下面是四种模式 ##
```
ThreadMode.POSTING：默认使用该模式，表示该方法会在当前发布事件的线程执行
ThreadMode.MAIN：表示会在UI线程中执行
ThreadMode.BACKGROUND：若当前线程非UI线程则在当前线程中执行，否则加入后台任务队列，使用线程池调用
ThreadMode.ASYNC：加入后台任务队列，使用线程池调用
```
测试下四种方式运行所在的线程：
```
 @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MessageEvent event) {
        Log.d("thread_test_main",Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackEvent(MessageEvent event){
        Log.d("thread_test_background",Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onASYNCEvent(MessageEvent event){
        Log.d("thread_test_Asyn",Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(MessageEvent event){
        Log.d("thread_test_posting",Thread.currentThread().getName());
    }
```
运行结果：
```
mytextview D/thread_test_Asyn: pool-1-thread-1
mytextview D/thread_test_main: main
mytextview D/thread_test_posting: main
mytextview D/thread_test_background: pool-1-thread-2
```
可以看出，由于是Main2Activity返回来的字符串，所以background会将操作放入子线程中操作，而且跟异步Asyn同属于一个线程池。

## 2.接下来讲下优先级 ##
优先级越高越优先执行，默认的优先级为0，将上面的操作重复进行一次，
运行结果：
```
mytextview D/thread_test_Asyn: pool-1-thread-3
mytextview D/thread_test_background: pool-1-thread-4
mytextview D/thread_test_main: main
mytextview D/thread_test_posting: main
```
我们尝试将main模式的方法优先级设为1，priority = 1，结果可想而知
```
@Subscribe(priority = 1,threadMode = ThreadMode.MAIN)
    public void onMainEvent(MessageEvent event) {
        Log.d("thread_test_main",Thread.currentThread().getName());
    }
```

## 3.粘性事件StickyEvent ##
官网教程：
>EventBus keeps the last sticky event of a certain type in memory. The sticky event can be delivered to subscribers or queried explicitly. Thus, you don’t need any special logic to consider already available data.
我大概翻译为：
>使用黏性模式，EventBus会把你最后发送的确切类型（如，此处使用的MessageEvent）粘性事件保存到内存中，粘性时间可以分发给subscribers或者显示查询？，因此，我们不需要考虑太多的逻辑上的问题。比如何时注册eventBus。
简单的讲就是，在发送时间之后，再注册EventBus也可以接收到消息，有点类似于粘性广播。

测试，在Main2Activity发送事件，新启动个Main3Activity进行订阅
```
Main2
EventBus.getDefault().postSticky(new MessageEvent(str));
startActivity(new Intent(Main2Activity.this,Main3Activity.class));

Main3
 @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onMainEvent(MessageEvent event) {
        tv1.setText(event.message);
    }
```
移除StickyEvent
例如，此时我们点击Main3的textview后跳转到Main4，在Main4注册粘性事件，还会会收到该消息，如果我们不想收到这个粘性消息了，我们只需要在onCreate中加上如下代码：
```
MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
if (stickyEvent != null) {
	// "Consume" the sticky event
    EventBus.getDefault().removeStickyEvent(stickyEvent);
}
```

## 4.封装EventBus ##
BaseActivity方便调用四种模式，继承时设置泛型统一该Activity的订阅消息类型
当然如果一个Activity将订阅多种类型的消息，将不适用
```
public class BaseActivty<T> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus","Register");
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!EventBus.getDefault().isRegistered(this)) {
            Log.d("EventBus", "unRegister");
            EventBusUtil.unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(T event) {
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackEvent(T event){
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onASYNCEvent(T event){
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(T event){
    }

}

```
EventBusUtil工具类，方便，注册注销EventBus，发送事件
```
public class EventBusUtil {
    public static <T> void register(T context){
    if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }
    public static <T> void unregister(T context){
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }

    public static  <T>  void postEvent(T t){
        EventBus.getDefault().post(t);
    }
    public static  <T>  void postStickyEvent(T t){
        EventBus.getDefault().postSticky(t);
    }
}
```


### 参考文章： ###
[http://greenrobot.org/eventbus/documentation/how-to-get-started/](http://greenrobot.org/eventbus/documentation/how-to-get-started/) 新的eventBus已经加上注解了
[http://blog.csdn.net/harvic880925/article/details/40660137](http://blog.csdn.net/harvic880925/article/details/40660137)
[http://blog.csdn.net/yanbober/article/details/45671407](http://blog.csdn.net/yanbober/article/details/45671407)
[http://www.jianshu.com/p/da9e193e8b03](http://www.jianshu.com/p/da9e193e8b03)
[http://blog.csdn.net/wl1769127285/article/details/51065015](http://blog.csdn.net/wl1769127285/article/details/51065015)
[http://www.cnblogs.com/ouyanliu/p/5744862.html](http://www.cnblogs.com/ouyanliu/p/5744862.html)
