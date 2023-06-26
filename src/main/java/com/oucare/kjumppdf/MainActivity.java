package com.oucare.kjumppdf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final static String READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final static String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final static int CHECK_PERMISSION_CODE = 101;
    private final static String[] permissionList = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Button createPDFBtn;
    private Button openPDFBtn;
    private String path;
    private Context context;
    private boolean isPermissions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initView();
    }

    private void initView() {
        createPDFBtn = findViewById(R.id.create);
        openPDFBtn = findViewById(R.id.open);

        context = getApplicationContext();
        path = FileUtils.getAppPath() + "test.pdf";
        createPDFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF(path);
            }
        });
        openPDFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF();
            }
        });
    }

    private void createPDF(String path) {
        if (isPermissions) {
            Log.d("Curry", "path:　" + path);
            File file = new File(path);
            if (file.exists()) file.delete();
            file.getParentFile().mkdirs();
            // 創建一個 Paint 對象來繪製內容
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            // 創建一個新的 PdfDocument
            PdfDocument document = new PdfDocument();
            // 創建三個頁面
            for (int i = 0; i < 3; i++) {
                // 創建一個新頁面的 PageInfo
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, i+1).create();

                // 創建一個新頁面
                PdfDocument.Page page = document.startPage(pageInfo);

                // 獲取該頁面的 Canvas
                Canvas canvas = page.getCanvas();

                // 在 Canvas 上進行繪製
                canvas.drawCircle(50, 50, 50, paint);

                // 結束該頁面
                document.finishPage(page);
            }

            // 寫入PDF文件
            try {
                document.writeTo(new FileOutputStream(path));
            } catch (Exception e) {
                Log.d("Curry", "Exception:　" + e);
                e.printStackTrace();
            }
            document.close();
        }
    }

    private void openPDF() {

    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                isPermissions = true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Need permission !");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), CHECK_PERMISSION_CODE);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, READ_PERMISSION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, WRITE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                isPermissions = true;
            } else {
                ActivityCompat.requestPermissions(this, permissionList, CHECK_PERMISSION_CODE);
            }
        } else {
            isPermissions = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHECK_PERMISSION_CODE) {
            if (ActivityCompat.checkSelfPermission(this, READ_PERMISSION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, WRITE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                isPermissions = true;
            } else {
                Permissions.showPermissionsSettingDialog(this, permissions[0]);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHECK_PERMISSION_CODE && Build.VERSION.SDK_INT > -Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                isPermissions = true;
            } else {
                Toast.makeText(this, "Get Permission Fail", Toast.LENGTH_LONG).show();
            }
        }


    }
}