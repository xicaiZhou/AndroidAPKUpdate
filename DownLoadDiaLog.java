package fun.flyee.sunshine4u.android.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.lxj.xpopup.core.CenterPopupView;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.updateContent;

public class DownLoadDiaLog extends CenterPopupView {

    private Context mContext;
    private OnSelectListener selectListener;

    private TextView downLoadContent, downLoadTitle, versionName;
    private Button downLoadBtn;

    private updateContent content;

    public DownLoadDiaLog(@NonNull Context context, updateContent content, OnSelectListener selectListener) {
        super(context);
        this.mContext = context;
        this.selectListener = selectListener;
        this.content = content;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        downLoadContent = findViewById(R.id.downLoad_content);
        downLoadTitle = findViewById(R.id.downLoad_title);
        downLoadBtn = findViewById(R.id.downLoad_btn);
        versionName = findViewById(R.id.downLoad_versionName);

        downLoadTitle.setText("发现新版本");
        downLoadContent.setText(content.getUpdateMsg());
        versionName.setText("V"+content.getVersionName());
        downLoadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null){
                    selectListener.goUpdateApp();
                }
            }
        });

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.downloaddialog;
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    public interface OnSelectListener{
        void goUpdateApp();
    }

}


