package com.example.administrator.feiyang_mt90;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.feiyang_mt90.common.CommonAdapter;
import com.example.administrator.feiyang_mt90.common.ViewHolder;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class InputActivity extends BaseActivity {

    @BindView(R.id.input_lrv)
    LRecyclerView inputLrv;
    @BindView(R.id.input_tv)
    TextView inputTv;
    @BindView(R.id.save_btn)
    Button saveBtn;


    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<String> adapter;
    private List<String> datas = new ArrayList<>(); //PDA机屏幕上的List集合
    private SweetAlertDialog sweetAlertDialog;
    private SweetAlertDialog chongfuDialog;
    boolean isChongfu = false;//没有重复录入
    private File file;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = {"序列号"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
        chongfuDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        initAdapter();
    }


    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {
        inputTv.setText(data);
        initData(data);
    }

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
        exportExcel(DateUtils.getCurrentTime1());
    }

    private void initAdapter() {
        adapter = new CommonAdapter<String>(this, R.layout.adapter_scanning, datas) {
            @Override
            public void setData(ViewHolder holder, String string) {
                holder.setText(R.id.adapter_data_tv, string);
            }
        };
        inputLrv.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        inputLrv.setAdapter(lRecyclerViewAdapter);
        inputLrv.setLoadMoreEnabled(false);
        inputLrv.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                sweetAlertDialog = new SweetAlertDialog(InputActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelText("取消");
                sweetAlertDialog.setTitleText("确定删除此条信息?");
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
                        datas.remove(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
            }
        });
    }

    private void initData(String data) {
        for (int i = 0; i < datas.size(); i++) {
            if (data.equals(datas.get(i))) {
                chongfuDialog
                        .setTitleText("重复录入...");
                chongfuDialog.setContentText(data);
                chongfuDialog.setConfirmText("确定");
                chongfuDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        chongfuDialog.dismiss();
                    }
                });

                chongfuDialog.show();
                isChongfu = true;  //重复了
                return;
            } else {
                isChongfu = false;  //没有重复了
            }
        }
        if (isChongfu == false) {
            chongfuDialog.dismiss();
            datas.add(data);
            lRecyclerViewAdapter.notifyDataSetChanged();
//                mainSku1Edt1.setText("");
        }
    }


    /**
     * 导出excel
     *
     * @param
     */
    public void exportExcel(String excelName) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/" + excelName + ".xls", title);
        ExcelUtils.writeObjListToExcel(getRecordData(), file.toString() + "/" + excelName + ".xls", this);
        datas.clear();
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(datas.get(i));
            recordList.add(beanList);

        }
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
