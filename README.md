# MainPercentTest
学习网址：<br>
EventBus：<br>
http://greenrobot.org/eventbus/documentation/how-to-get-started/ 新的eventBus已经加上注解了<br>
http://blog.csdn.net/harvic880925/article/details/40660137 启舰的<br>
http://blog.csdn.net/yanbober/article/details/45671407 工匠若水的<br>
<br>
Reactive X on Android：<br>
http://code.tutsplus.com/tutorials/getting-started-with-reactivex-on-android--cms-24387<br>
<br>
Lambda：<br>
http://ifeve.com/lambda/<br>
还有一个Gradle运用相关的：<br>
dependencies {<br>
}<br>
后面加上<br>
buildscript {<br>
    repositories {<br>
        mavenCentral()<br>
    }<br>
<br>
    dependencies {<br>
        classpath 'me.tatarka:gradle-retrolambda:2.5.0'<br>
    }<br>
}<br>
<br>
repositories {<br>
    mavenCentral()<br>
}<br>
<br>
apply plugin: 'me.tatarka.retrolambda'<br>
也是从某个博客上看到了，没有地址，以下是重点<br>
1：buildToolsVersion "24.0.2"<br>
buildTool必须使用24以上版本<br>
2：android{//这里也是app里本身有的，即Java编译器jdk必须使用1.8或以上吧，我猜<br>
compileOptions {<br>
        targetCompatibility 1.8<br>
        sourceCompatibility 1.8<br>
    }<br>
}<br>
