package com.oucare.kjumppdf;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class FileUtils {
    public static String getAppPath() {
        File dir = new File(
                android.os.Environment.getExternalStorageDirectory() +
                        File.separator +
                        "PDF" +
                        File.separator
        );
        if (!dir.exists()) dir.mkdirs();


        return dir.getPath() + File.separator;
    }

    public static void openFile(Context context, File url) throws ActivityNotFoundException {
        if (url.exists()) {
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext().getPackageName() + ".fileprovider",
                    url
            );
            String urlString = url.toString().toLowerCase();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
            );

            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(
                        packageName,
                        uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            }

            if (urlString.toLowerCase().contains(".pdf")) {
                intent.setDataAndType(uri, "application/pdf");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            Toast.makeText(context, "Not found !", Toast.LENGTH_LONG).show();
        }
    }

}
