package com.yl.technician.util;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.yl.technician.YLApplication;

import java.io.File;

/**
 * Created by zm on 2018/10/18.
 */
public class UriHelper {

    public static Uri fromFile(File file){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(YLApplication.getContext(), "com.yiyue.technician.fileProvider", file);
        }else {
            return Uri.fromFile(file);
        }
    }
}
