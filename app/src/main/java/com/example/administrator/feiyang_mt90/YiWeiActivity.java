package com.example.administrator.feiyang_mt90;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.feiyang_mt90.bean.DataBean;
import com.example.administrator.feiyang_mt90.db.SerialDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YiWeiActivity extends BaseActivity {

    @BindView(R.id.yiwei_scanning_tv)
    TextView yiweiScanningTv;
    @BindView(R.id.yiwei_serialNumber_tv)
    TextView yiweiSerialNumberTv;
    @BindView(R.id.yiwei_deviceNumber_tv)
    TextView yiweiDeviceNumberTv;
    @BindView(R.id.yiwei_deviceName_tv)
    TextView yiweiDeviceNameTv;
    @BindView(R.id.yiwei_type_tv)
    TextView yiweiTypeTv;
    @BindView(R.id.yiwei_machine_tv)
    TextView yiweiMachineTv;
    @BindView(R.id.yiwei_quantity_tv)
    TextView yiweiQuantityTv;
    @BindView(R.id.yiwei_danjia_tv)
    TextView yiweiDanjiaTv;
    @BindView(R.id.yiwei_time_tv)
    TextView yiweiTimeTv;
    @BindView(R.id.yiwei_department_tv)
    TextView yiweiDepartmentTv;
    @BindView(R.id.yiwei_users_tv)
    TextView yiweiUsersTv;
    SerialDao serialDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_wei);
        ButterKnife.bind(this);
        serialDao=new SerialDao(this);
    }

    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {
        initData(data);
    }

    private void initData(String data) {
        yiweiScanningTv.setText(data);
      List<DataBean> list= serialDao.select1(data);
        Log.d("feng",list.toString());
        for (int i = 0; i <list.size() ; i++) {
            yiweiSerialNumberTv.setText(list.get(i).getSerialNumber());
            yiweiDeviceNumberTv.setText(list.get(i).getDeviceNumber());
            yiweiDeviceNameTv.setText(list.get(i).getDeviceName());
            yiweiTypeTv.setText(list.get(i).getType());
            yiweiMachineTv.setText(list.get(i).getMachine());
            yiweiQuantityTv.setText(list.get(i).getQuantity());
            yiweiDanjiaTv.setText(list.get(i).getDanjia());
            yiweiTimeTv.setText(list.get(i).getTime());
            yiweiDepartmentTv.setText(list.get(i).getDepartment());
            yiweiUsersTv.setText(list.get(i).getUsers());
        }
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        /**
         * 退出系统
         */
        finish();
    }
}
