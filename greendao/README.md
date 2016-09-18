# GreenDao #
## 配置 ##
我采用3.0的版本
  - [方法1](#方法1：原始的用纯Java工程生成dao文件)
  - [方法2](#方法2：原始注解生成dao文件)

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
### 方法1：原始的用纯Java工程生成dao文件 ###
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

        //添加用户实体,相当于生成bean的类名
        Entity user = schema.addEntity("User");
        //指定表名，如不指定，表名则为 user
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
        Entity info =  schema.addEntity("Information");
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
在Activity里面的写法：
```
//这里是获取数据库对应的session
helper = new DaoMaster.DevOpenHelper(this, "GreenDaoDemo-db");
db = helper.getWritableDatabase();
master = new DaoMaster(db);
session = master.newSession();

		btn.setOnClickListener(View -> {
            User user = new User(null, "user1", 24+(int)(1+Math.random()*(10)), 175.5d);
            userdao.insert(user);
            adapter.setData(getData());
            adapter.notifyDataSetChanged();
        });
        btn2.setOnClickListener(View ->{
            //这里需要作判断，会插入重复项
            Information info = new Information(null,"Shenzhen","123",30,3L);
            infoDao.insert(info);
            Log.d("GreenDaoDemo", "Insert success:" + info.getAddress()+info.getIcon_url()+info.getScore()+info.getUser().getAge());
        });

	//selectAllUser查询所有用户
    public List<User> getData(){
        List<User> _users = new ArrayList<User>();
        cursor = db.query(userdao.getTablename(), userdao.getAllColumns(), null, null, null, null, null);
        Log.d("GreenDaoDemo", cursor.getCount() + "");
        User user;
        while (cursor.moveToNext()) {
            user = new User();
            user.setUserId(cursor.getLong(cursor.getColumnIndex("USER_ID")));
            user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            user.setAge(cursor.getInt(cursor.getColumnIndex("AGE")));
            user.setHigh(cursor.getDouble(cursor.getColumnIndex("HIGH")));
            _users.add(user);
        }
        return _users;
    }

	//selectAllInfo查询所有用户信息
    public List<Information>  fetInfo(){
        List<Information> _Informations = new ArrayList<Information>();
        cursor = db.query(infoDao.getTablename(), infoDao.getAllColumns(), null, null, null, null, null);
        Log.d("GreenDaoDemo", cursor.getCount() + "");
        Information info;
        while (cursor.moveToNext()) {
            info = new Information();
            info.setInfoId(cursor.getLong(cursor.getColumnIndex("INFO_ID")));
            info.setAddress(cursor.getString(cursor.getColumnIndex("ADDRESS")));
            info.setIcon_url(cursor.getString(cursor.getColumnIndex("ICON_URL")));
            info.setScore(cursor.getInt(cursor.getColumnIndex("SCORE")));
            info.setUser_id(cursor.getLong(cursor.getColumnIndex("USER_ID")));
            _Informations.add(info);
        }
        return _Informations;
    }
```
```
 //还自动生成了获取对应User的代码
 User relativeUser = info.getUser();
```
如上面的代码，再添加info实体时，若添加的userid不存在，会抛出异常
##### 新建一个1对多的关联表 #####









### Usage2
### 方法2：原始注解生成dao文件 ###
产生问题：
由于生成的dao文件总是出现
> 错误: UserDao不是抽象的, 并且未覆盖AbstractDao中的抽象方法hasKey(User)
public class UserDao extends AbstractDao<User, Long> {

所以，索性将gradle改成
```
compile 'org.greenrobot:greendao:3.0.1'
```
就可以了
#### 建立1对多关联表
在Bean包里建立两个类，User和Book
User可以对应多个Book（实际还没实现，暂时是1对1）
```
@Entity
public class User {
    @Id
    private Long user_id;
    @NotNull
    private String Name;
    private int age;
    private int height;
    private String bookName;
    @ToMany(joinProperties = {
            @JoinProperty(name = "bookName",referencedName = "book")
    })
    private List<Book> ownedBooks;
}
```

```
@Entity
public class Book{
    @Id
    private Long id;
    @NotNull
    private String book;
}
```
> 在MyApplication里面获取数据库操作权限
```
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ……
        //创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"library_db");
        //DevOpenHelper helper = new DevOpenHelper(this, 是否加密 ? "notes-db-encrypted" : "notes-db");
        //获取数据库读写的权限，如果进行加密调用helper.getEncryptedWritableDb("super-secret")，参数为设置的密码
        //Database db = 是否加密 ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
}
```
> 在MainActivity插入和查询User
```
private void initData() {
        DaoSession _daoSession = ((MyApplication) getApplication()).getDaoSession();
        _UserDao = _daoSession.getUserDao();
        //插入、保存数据：如果key属性不为null，会更新这个对象；如果为null，会插入这个对象：
        if(_UserDao.queryBuilder().where(UserDao.Properties.Name.eq("Ice")).build().list().size()==0) {
            User _User = new User(null, "Ice", 12, 142, "冰与火之歌");
            _UserDao.insert(_User);
        }
        //查询数据
        User _SelectUser = _UserDao.queryBuilder().where(UserDao.Properties.Name.eq("OK")).build().unique();
        //unique()表示查询结果为一条数据，若数据不存在，_SelectUser为null。
        Log.d("GreenDao", _SelectUser.getUser_id() + "");
        //获取多个结果
        List<User> _Users = _UserDao.queryBuilder()
                .where(UserDao.Properties.User_id.notEq(10)) //查询条件
                .orderAsc(UserDao.Properties.User_id) //按首字母排列
                .limit(10)  //限制查询结果个数
                .build().list(); //结果放进list中
        Log.d("GreenDao", _Users.size() + "");

    }
```

