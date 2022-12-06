package com.dit.himachal.rms.utilities;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 09, 07 , 2020
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Locale;

public class HardwareDetails {

    public HardwareDetails(){}

    public static String getRootDirPath(Context context) {
        String path = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),  null)[0];


            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                path =  file.getAbsolutePath();
            }

            return path;

        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    public  Boolean isDeviceRooted(Context context){
        boolean isRooted = isrooted1(); // && isrooted2();
        Log.e("isrooted1",Boolean.toString(isrooted1()));
        Log.e("isrooted2",Boolean.toString(isrooted2()));
        return isRooted;
    }

    private boolean isrooted1() {

        File file = new File("/system/app/Superuser.apk");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    // try executing commands
    private boolean isrooted2() {
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su")
                || canExecuteCommand("which su");
    }

    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }
}
