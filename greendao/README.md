# GreenDao #
## 配置 ##
我采用3.0的版本
#### app.gradle ####
```

dependencies {
    ......其它依赖库
    compile 'org.greenrobot:greendao:3.1.0'
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.0.0'
    }
}

// In current version 3.0.0, this must precede the android plugin!
apply plugin: 'org.greenrobot.greendao'

//这里是为了gradle不报，错误: 编码GBK的不可映射字符
tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}
```
### 方法1：原始的用纯Java工程生成dao文件的方法 ###
1.在app/src/main目录下（这里可以根据自己需求，后面有指定目录的配置），新建一个java-gen的目录，然后在app.gradle中加入
```
android {
    ......其它例如SDK的配置
    sourceSets {
        main {
            java.srcDirs = ['src/main/java', 'src/main/java-gen']
        }
    }
}

```

2.新建一个Java Library Module，做为generator生成器，这个module的gradle也叫加上GreenDao依赖库
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'org.greenrobot:greendao-generator:3.1.0'
}
```

3.然后在新module里的类，建立main函数，就可以开始写新建表的语句了
##### 新建一个1对1的关联表 #####
```
public class GreenDao {
    public static void  main(String[] args) throws Exception {
        int version = 1;
        String defaultPackage = "com.example.bean";
        //创建模式对象，指定版本号和自动生成的bean对象的包名
        Schema schema = new Schema(version, defaultPackage);
        //指定自动生成的dao对象的包名,不指定则都DAO类生成在"com.example.bean"包中
        schema.setDefaultJavaPackageDao("com.example.dao");

        //添加用户实体
        Entity user = schema.addEntity("User");
        //指定表名，如不指定，表名则为 Persion（即实体类名）
        user.setTableName("user");

        user.implementsSerializable();

        //给实体类中添加成员（即给表中添加列，并设置列的属性）
        /**
         * 1.若想直接生成id，不自己命名列明的话，可以.addIdProperty().autoincrement();这里就直接将id作为主键
         * 2.primaryKey().autoincrement();这俩的顺序不能变，不然会报错AUTOINCREMENT is only available to primary key properties of type long/Long
         * 意思是，必须先说明是主键，才有自增的功能
         */
        user.addLongProperty("id").primaryKey().autoincrement();//添加Id,自增长
        user.addStringProperty("name").notNull();//添加String类型的name,不能为空
        user.addIntProperty("age");//添加Int类型的age
        user.addDoubleProperty("high");//添加Double类型的high

        //建立相关用户详细信息表，与用户表一对一关联
        Entity info =  schema.addEntity("information");
        info.implementsSerializable();
        info.addLongProperty("id").primaryKey().autoincrement();
        info.addStringProperty("address").notNull();
        info.addStringProperty("icon_url").notNull();
        info.addIntProperty("score").notNull();

       
        //information表添加外键userId
        Property propertyBack = info.addLongProperty("user_id").getProperty();
        //设置关联user表
        info.addToOne(user,propertyBack);
        


        //java-gen路径
        String outDir = "../MainPercentTest/app/src/main/java-gen";
        //自动生成代码到之前创建的java-gen目录下
        new DaoGenerator().generateAll(schema, outDir);

    }

}

```

