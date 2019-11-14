package fun.flyee.sunshine4u.android.models;

import java.io.Serializable;

public class updateContent implements Serializable {


    /**
     * forceUpdates : true
     * versionName : 2.0.1
     * updateMsg : 2.0版本上线啦！新增钱包功能！
     * url : http://huijiefinance.net/zlhj/app/RRD/debug/Android/app-release.apk
     * versionCode : 201
     */

    private boolean forceUpdates;
    private String versionName;
    private String updateMsg;
    private String url;
    private String versionCode;

    public boolean isForceUpdates() {
        return forceUpdates;
    }

    public void setForceUpdates(boolean forceUpdates) {
        this.forceUpdates = forceUpdates;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
