package com.example.administrator.feiyang_mt90;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRActivity extends BaseActivity {


    @BindView(R.id.qr_ps_tv)
    TextView qrPsTv;
    @BindView(R.id.qr_tv)
    TextView qrTv;
    @BindView(R.id.qr_btn)
    Button qrBtn;

    private File file;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = {"二维码信息"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
    }

    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {
        qrPsTv.setVisibility(View.INVISIBLE);
        qrTv.setText(data);
        qrTv.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.qr_btn)
    public void onViewClicked() {
        exportExcel("二维"+DateUtils.getCurrentTime3());
    }




    /**
     * 导出excel
     *
     * @param
     */
    public void exportExcel(String excelName) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        String fileName = (String) SPUtils.get(this, "fileName", "");
        if (fileName.equals("")) {
            String excelFile = file.toString() + "/" + excelName + ".xls";
            ExcelUtils.initExcels(getRecordData(), excelFile, title, excelName, this);
//            ExcelUtils.writeObjListToExcels(getRecordData(), fileName, excelName,  getActivity());
        } else {
            ExcelUtils.writeObjListToExcels(getRecordData(), fileName, excelName, this);
        }

    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
//        for (int i = 0; i < datas.size(); i++) {
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(qrTv.getText().toString());
            recordList.add(beanList);

//        }
        return recordList;
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }
}
