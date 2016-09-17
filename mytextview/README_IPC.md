# IPC学习心得 #
学习自郭大神的《android开发艺术探索》<br>
IPC=Intel-Process Communication，即进程通信<br>
进程就是指一个执行单元，在PC和移动端一般就是一个程序，或一个应用<br>
一个进程包含一个或多个线程，see<br>
例如，IPC在Windows上的体现，就是剪贴板，管道，邮槽等进行进程间的通信（好吧，这里我只懂得剪贴板，就是你在任何地方Ctrl C 都可以在别的应用上Ctrl v就是了）；在Linux上则是命名管道、共享内存、信号量等进行进程间通信，
>  在Android中最具特色的，就是Bind了
> 当然socket也可以

多进程的两种情况<br>
1. 应用本身需要采用多进程来实现，原因可能有些模块由于特殊原因需要运行在单独的进程中，又或者为了加大一个应用可使用的内存；<br>
2. 当前应用需要向其它应用获取数据
> 甚至我们通过系统提供的ContentProvider去查询数据的时候，其实也是一种进程间通信，只不过通信细节被系统屏蔽了

#### 指定android：process，开起多进程，※唯一的办法 ####

> 在Android中使用多进程只有一个办法，就是给四大组件在AndroidManifest中指定android：process属性，除此之外，在软件层没有别的方法。还有一种则要通过JNI在native层去fork一个新的进程（介于我对Linux浅显的了解，只知道这是在Linux终端开启新进程的一种方法）
例如：

```
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:name=".MyApplication">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".Main2Activity" android:process=":remote" />
        <activity android:name=".Main3Activity" android:process="com.ssdy.mytextview.remote"/>
```
![观察Monitor进程](http://i.imgur.com/Ck4mxV9.png)<br>
从上图可以看出这个应用分别运行在三个进程中，除了在DDMS中看进程信息，我们也可以用shell指令查看<br>
郭神使用的是苹果，咱windows要在后面的指令加上双引号：
```
E:\android-sdk\platform-tools>adb shell "ps | grep com.ssdy.mytextview"
u0_a57    3883  1137  1548920 44700 ffffffff 3c3f7f7a S com.ssdy.mytextview
u0_a57    4351  1137  1551088 43872 ffffffff 3c3f7f7a S com.ssdy.mytextview:remote
u0_a57    4413  1137  1548876 41460 ffffffff 3c3f7f7a S com.ssdy.mytextview.remote
```
> 进程名以“：”开头的进程，属于当前应用的**私有进程**，其他应用的组件不可以和它跑在同一个进程中，所以会在当前指定的进程名前面加上当前的包名，这是一种间歇的方法；而其它不以“：”开头的进程属于**全局进程**，其他应用通过ShareUID方式可以和它泡在同一进程。

##### 多进程模式的运行机制 #####
> 多进程绝非只是仅仅制定一个android：process属性这么简单
> 我们知道Android为每一个应用分配了一个独立的虚拟机，或者说为每一个进程分配一个独立的虚拟机，不同的虚拟机在内存分配上有不同的地址空间，这就导致在不同的虚拟机中访问同一个类的对象会产生多份副本。一般来说，使用多进程会造成如下几方面问题：
> 1.静态成员和单例模式在两个进程之间，所能实现的功能完全失效
> 2.线程同步机制，在两进程间失效
> 3.SharePreferenc的可靠性下降
> 4.Application会多次创建（以下将解释这个问题）

查看这三个组件（Activity）->即前面的三个进程，是否运行在不同的Application中，我们在MyApplication中加入一个Log输出当前Application名<br>
```
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = MyUtil.getProcessName(getApplicationContext());
        Log.d(TAG,"application start,process name:"+processName);
    }
}
```
```
public class MyUtil {

    public static String getProcessName(Context context){
        int _pid = android.os.Process.myPid();
        ActivityManager _ActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo:_ActivityManager.getRunningAppProcesses()){
            if(appProcessInfo.pid==_pid){
                return appProcessInfo.processName;
            }
        }
        return null;
    }

}
```
运行之后Log信息：
```
3883-3883/com.ssdy.mytextview D/MyApplication: application start,process name:com.ssdy.mytextview
4351-4351/com.ssdy.mytextview:remote D/MyApplication: application start,process name:com.ssdy.mytextview:remote
4413-4413/com.ssdy.mytextview.remote D/MyApplication: application start,process name:com.ssdy.mytextview.remote
```

## IPC的基础，关于序列化接口，反序列化接口和Binder ##
