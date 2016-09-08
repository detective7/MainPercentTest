# MainPercentTest
9/8/2016 6:11:19 PM 
### EventBus： ###
http://greenrobot.org/eventbus/documentation/how-to-get-started/ 新的eventBus已经加上注解了

http://blog.csdn.net/harvic880925/article/details/40660137 启舰的

http://blog.csdn.net/yanbober/article/details/45671407 工匠若水的

<br>
### Reactive X on Android：
http://code.tutsplus.com/tutorials/getting-started-with-reactivex-on-android--cms-24387

### Lambda：
http://ifeve.com/lambda/
还有一个Gradle运用相关的：
```
dependencies { }
```

后面加上:
  ```
    buildscript {
		repositories {
			mavenCentral()
		}

		dependencies {
			classpath 'me.tatarka:gradle-retrolambda:2.5.0'
		}
	}

	repositories {
		mavenCentral()
	}

	apply plugin: 'me.tatarka.retrolambda'
```
也是从某个博客上看到了，没有地址，以下是重点<br>
1：buildTool必须使用24以上版本
```
buildToolsVersion "24.0.2"
```

2：这里也是app里本身有的，即Java编译器jdk必须使用1.8或以上
```
    android{  
    	compileOptions {
    		targetCompatibility 1.8
    		sourceCompatibility 1.8
    	}
    }
```
