package fun.flyee.sunshine4u.android.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fun.flyee.sunshine4u.android.BuildConfig;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class InstallReceiver extends BroadcastReceiver {

    // 安装下载接收器
    @Override
    public void onReceive(Context context, Intent intent) {



        // TODO Auto-generated method stub
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
            Log.e("安装","升级了一个安装包，重新启动此程序");
            Intent intents = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            context.startActivity(intents);
            System.exit(0);
        }
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.e("安装","安装了:" +packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("卸载了:"  + packageName + "包名的程序");
        }

        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Toast.makeText(context, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadFileUri = dm.getUriForDownloadedFile(id);

            installApk(context, downloadFileUri);
        }

  }


    // 安装Apk
    private void installApk(Context context, Uri filePath) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        File file = uriToFile(filePath,context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, "${applicationId}.dataProvider", file);
            i.setDataAndType(filePath, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(i);



    }


    public static File uriToFile(Uri uri,Context context) {
            String path = null;
            if ("file".equals(uri.getScheme())) {
                path = uri.getEncodedPath();
                if (path != null) {
                    path = Uri.decode(path);
                    ContentResolver cr = context.getContentResolver();
                    StringBuffer buff = new StringBuffer();
                    buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                    Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
                    int index = 0;
                    int dataIdx = 0;
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                        index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                        index = cur.getInt(index);
                        dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        path = cur.getString(dataIdx);
                    }
                    cur.close();
                    if (index == 0) {
                    } else {
                        Uri u = Uri.parse("content://media/external/images/media/" + index);
                        System.out.println("temp uri is :" + u);
                    }
                }
                if (path != null) {
                    return new File(path);
                }
            } else if ("content".equals(uri.getScheme())) {
                // 4.2.2以后
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(columnIndex);
                }
                cursor.close();

                return new File(path);
            } else {
                //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
            }
            return null;
        }

}
