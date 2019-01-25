package com.example.administrator.feiyang_mt90.bean;

/**
 * Created by Administrator on 2018\12\7 0007.
 */

public class DataBean {
    String  serialNumber;//序号
    String deviceNumber;//设备编号
    String deviceName;//设备名称
    String type;//型号
    String machine;//机器号
    String quantity;//数量
    String danjia;//单价
    String time;//购买时间
    String  department;//使用科室
    String users;//使用人


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "serialNumber='" + serialNumber + '\'' +
                ", deviceNumber='" + deviceNumber + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", type='" + type + '\'' +
                ", Mmachine='" + machine + '\'' +
                ", quantity='" + quantity + '\'' +
                ", danjia='" + danjia + '\'' +
                ", time='" + time + '\'' +
                ", department='" + department + '\'' +
                ", users='" + users + '\'' +
                '}';
    }
}
