package com.tiger.quanlylop.Library;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class BackUpSQLite {
    public static boolean backupDatabase(Context context, String databaseName) {
        boolean ok =false;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            String packageName = context.getApplicationInfo().packageName;
            String currentDBPath = String.format("//data//%s//databases//%s", packageName, databaseName);
            String backupDBPath = String.format("backupQuanLyLop");

            if (sd.canWrite()) {
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);



                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    ok=true;
                }
            }
        } catch (Exception e) {
            throw new Error(e);
        }

        return ok;
    }
}
