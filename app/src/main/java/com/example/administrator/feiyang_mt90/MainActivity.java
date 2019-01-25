package com.example.administrator.feiyang_mt90;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.feiyang_mt90.bean.DataBean;
import com.example.administrator.feiyang_mt90.db.SerialDao;
import com.example.administrator.feiyang_mt90.filemanager.FileManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_barcode)
    RelativeLayout mainBarcode;
    @BindView(R.id.main_qr)
    RelativeLayout mainQr;
    @BindView(R.id.main_data)
    RelativeLayout mainData;
    @BindView(R.id.main_luru_barcode)
    RelativeLayout mainLuruBarcode;
    @BindView(R.id.delete_dao)
    RelativeLayout deleteDao;
    private ExcelManager em = new ExcelManager();
    private String excelFile = "";
    public boolean isError = true;
    private Handler myHandler;
    private ProgressDialog m_Dialog;
    private boolean isCloseDialog = false;
    SerialDao serialDao;
    private SweetAlertDialog MEIDDialog;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MEIDDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        MEIDDialog.setCancelable(false);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Log.d("feng",tm.getDeviceId());
//        if (tm.getDeviceId().equals("867382030919785")) {
//            initHandler();
//        } else {
//            initDialog();
//        }
        initHandler();
    }

    private void initDialog() {
        MEIDDialog
                .setTitleText("请联系商家");
        MEIDDialog.setConfirmText("确定");
        MEIDDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                MEIDDialog.dismiss();
                System.exit(0);
            }
        });
        MEIDDialog.show();
    }


    private void getPermission() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        List<String> permissionsList = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permissions[i]);
            }
        }
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]), 123);
        } else {
            onPermission(new String[]{});
        }
    }

    private void onPermission(String[] strings) {
        if (strings.length > 0) {
            Toast.makeText(this, "访问需要获取存储权限！", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("feng", "获取成功");
    }

    private void initHandler() {
        serialDao = new SerialDao(this);
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = new Bundle();
                bundle = msg.getData();
                excelFile = bundle.getString("xuanze");
                if (isError == false) {
                    if (em.isEXL(excelFile)) {
                        if (excelToSheet(excelFile) == true) {
                            isCloseDialog = true;
                            isError = true;
                            myHandler.sendEmptyMessageDelayed(0, 100);
                            Toast.makeText(MainActivity.this, "数据导入成功",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            isCloseDialog = true;
                            isError = true;
                            myHandler.sendEmptyMessageDelayed(0, 100);
                            Toast.makeText(MainActivity.this, "数据导入失败",
                                    Toast.LENGTH_SHORT).show();
                            // m_Dialog.dismiss();
                        }
                    } else {
                        isCloseDialog = true;
                        isError = true;
                        myHandler.sendEmptyMessageDelayed(0, 100);
                        Toast.makeText(MainActivity.this, "你打开的EXCEL文件格式不对。",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (isCloseDialog) {
                        m_Dialog.dismiss();
                        isCloseDialog = false;
                        isError = false;
                    } else {
                        myHandler.sendEmptyMessageDelayed(0, 100);
                        isError = true;
                    }
                }
            }
        };

    }

    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {

    }

    @OnClick({R.id.main_barcode, R.id.main_qr, R.id.main_data, R.id.main_luru_barcode, R.id.delete_dao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_barcode:
                startActivity(new Intent(this, YiWeiActivity.class));
                break;
            case R.id.main_qr:
                startActivity(new Intent(this, QRActivity.class));
                break;
            case R.id.main_data:
                getPermission();
                openFileManager();
                break;
            case R.id.main_luru_barcode:
                startActivity(new Intent(this, InputActivity.class));

                break;
            case R.id.delete_dao:
                sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelText("取消");
                sweetAlertDialog.setTitleText("确定清除数据库吗?");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        serialDao.delete();
                        sweetAlertDialog.dismiss();
                        Toast.makeText(MainActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                sweetAlertDialog.show();
                break;
        }
    }

    private void openFileManager() {
        // 调用文件资源管理器
        Intent intent = new Intent();
        intent.setClass(this, FileManager.class);
        // 向资源管理器传递参数
        Bundle bundle = new Bundle();
        bundle.putString("xuanze", "");
        bundle.putBoolean("iserror", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                // 取得来自文件管理器传递的文件名
                Message msg = new Message();
                Bundle bundle = data.getExtras();
                excelFile = bundle.getString("xuanze");
                isError = bundle.getBoolean("iserror");
                msg.setData(bundle);
//				myHandler.sendEmptyMessageDelayed(0, 100);
                myHandler.sendMessage(msg);
                m_Dialog = ProgressDialog.show(this, "请等待...",
                        "正在导入数据...", true);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * excel表格数据进行导入
     */
    private boolean excelToSheet(String file) {
        boolean isErr = false;
        List<DataBean> list = em.findMereExcelRecord(file, DataBean.class);
        if (list.size() > 0) {
            isErr = true;
        }
        for (DataBean dataBean : list) {
            serialDao.insert(dataBean);
        }

        return isErr;
    }

}
