package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

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
        //给实体类中添加成员（即给表中添加列，并设置列的属性）
        /**
         * 1.若想直接生成id，不自己命名列明的话，可以.addIdProperty().autoincrement();这里就直接将id作为主键
         * 2.primaryKey().autoincrement();这俩的顺序不能变，不然会报错AUTOINCREMENT is only available to primary key properties of type long/Long
         * 意思是，必须先说明是主键，才有自增的功能
         */
        user.addLongProperty("userId").primaryKey().autoincrement();//添加Id,自增长
        user.addStringProperty("name").notNull();//添加String类型的name,不能为空
        user.addIntProperty("age");//添加Int类型的age
        user.addDoubleProperty("high");//添加Double类型的high

        //建立相关用户详细信息表，与用户表一对一关联
        Entity info =  schema.addEntity("information");
        info.addLongProperty("infoId").primaryKey().autoincrement();
        info.addStringProperty("address").notNull();
        info.addStringProperty("icon_url").notNull();
        info.addIntProperty("score").notNull();

        //user表添加外键infoId
        Property property = user.addLongProperty("infoId").getProperty();
        //设置关联information表
        user.addToOne(info,property);

        //information表添加外键userId
        Property propertyBack = info.addLongProperty("userId").getProperty();
        //设置关联user表
        info.addToOne(user,propertyBack);

        //java-gen路径
        String outDir = "../MainPercentTest/app/src/main/java-gen";
        //自动生成代码到之前创建的java-gen目录下
        new DaoGenerator().generateAll(schema, outDir);

    }

}
