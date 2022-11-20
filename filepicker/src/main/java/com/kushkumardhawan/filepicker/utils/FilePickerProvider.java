
package com.kushkumardhawan.filepicker.utils;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

public class FilePickerProvider extends FileProvider {
    private static final String FILE_PROVIDER = ".filepicker.provider";

    public static String getAuthority(@NonNull Context context) {
        return context.getPackageName() + FILE_PROVIDER;
    }

    public static Uri getUriForFile(@NonNull Context context, @NonNull File file) {
        return getUriForFile(context, getAuthority(context), file);
    }
}
