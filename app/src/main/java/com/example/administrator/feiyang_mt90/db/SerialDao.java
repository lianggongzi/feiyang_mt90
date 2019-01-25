package com.example.administrator.feiyang_mt90.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.feiyang_mt90.bean.DataBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018\11\6 0006.
 * 导入资料的数据库
 */

public class SerialDao {

    private MyOpenHelper helper;
    private SQLiteDatabase db;

    public SerialDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(DataBean dataBean) {
        boolean isExist = isNewsExist(dataBean);
        if (isExist) {
            db.close();
            return false; //返回添加失败
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serialNumber", dataBean.getSerialNumber());
            contentValues.put("deviceNumber", dataBean.getDeviceNumber());
            contentValues.put("deviceName", dataBean.getDeviceName());
            contentValues.put("type", dataBean.getType());
            contentValues.put("machine", dataBean.getMachine());
            contentValues.put("quantity", dataBean.getQuantity());
            contentValues.put("danjia", dataBean.getDanjia());
            contentValues.put("time", dataBean.getTime());
            contentValues.put("department", dataBean.getDepartment());
            contentValues.put("users", dataBean.getUsers());
            db.insert("Customer", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }


    //删除的方法
    public void delete() {
        init();
        //根据newsURL进行数据删除
        db.delete("Customer", null, null);
        db.close();
    }

    //查询的方法
    public List<DataBean> select1(String str) {
        init();
        List<DataBean> list = new ArrayList<>();
        Cursor cursor = db.query("Customer", null, "machine = ?", new String[]{str}, null, null, null);
        while (cursor.moveToNext()) {
            String serialNumber = cursor.getString(cursor.getColumnIndex("serialNumber"));
            String deviceNumber = cursor.getString(cursor.getColumnIndex("deviceNumber"));
            String deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String machine = cursor.getString(cursor.getColumnIndex("machine"));
            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
            String danjia = cursor.getString(cursor.getColumnIndex("danjia"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String department = cursor.getString(cursor.getColumnIndex("department"));
            String users = cursor.getString(cursor.getColumnIndex("users"));
            DataBean bean = new DataBean();
            bean.setSerialNumber(serialNumber);
            bean.setDeviceNumber(deviceNumber);
            bean.setDeviceName(deviceName);
            bean.setType(type);
            bean.setMachine(machine);
            bean.setQuantity(quantity);
            bean.setDanjia(danjia);
            bean.setTime(time);
            bean.setDepartment(department);
            bean.setUsers(users);
            list.add(bean);
        }
        return list;
    }


    //判断是否存在
    public boolean isNewsExist(DataBean dataBean) {
        init();
        Cursor cursor = db.query("Customer", null, "machine = ?", new String[]{dataBean.getMachine()}, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
        
    }
}
