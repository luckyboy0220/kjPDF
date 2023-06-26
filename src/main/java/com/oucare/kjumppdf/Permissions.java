package com.oucare.kjumppdf;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class Permissions {

    public static void showPermissionsSettingDialog(Context context, String permission) {
        String msg = "";
        if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            msg = "You need allow permissions !";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showSettings(context);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public static void showSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


//    ※ 引述《e900828 (小福)》之銘言：
//            : 身為一個雙魚座，我覺得有
//: 常常有人格分離的感覺
//: ※ 引述《Maaa (怕的軀骨顛倒用)》之銘言：
//            : : 那小弟我希望有幫到妳~
//            : : 對了我問妳一個問題喔，妳認為雙魚座是不是有雙重人格的個性



//    OUcare:
//    KP系列 (血壓計):
//    KP-7672		KP-7020		KP-7172		KP-7021		KP-6027
//    KP-7590		KP-6527		KP-7830		KP-7721		KP-7531
//    KP-7950		KP-6520		KP-6261		KP-6021		KP-7122
//    KP-7536		KP-7090		KP-6760		KP-6521		KP-7130
//    KP-7532		KP-6020		KP-7660		KP-6026		KP-6528
//    KP-6028		KP-7860		KP-6525		KP-6650		KP-6750
//    KP-6255		KP-6860		KP-7620		KP-7031		KP-6630
//    KP-6260		KP-6526		KP-7530		KP-7922		KP-6631
//    KP-6160		KP-7120		KP-7030
//
//    KD系列 (溫度計):
//    KD-2105		KD-2070		KD-2115		KD-3150		KD-2270
//    KD-2186		KD-2072		KD-2203		KD-2481		KD-2162
//    KD-2161		KD-2220		KD-2202		KD-2160		KD-3040
//
//    KI系列 (溫度計):
//    KI-8186			KI-8193			KI-8271			KI-8179			KI-8180
//    KI-2880			KI-8270			KI-8178
//
//    KB系列 (體重計):
//    KS-4110
//
//    KG系列 (血糖量測):
//    KG-5170
//
//    特殊機種:
//    KP-7800
//
//    陳博:
//    KD-1481		KD-2207		KD-2202		KD-2270

//    MomiSure:
//    額頭貼體溫計: KD-3120
//    腋下體溫計: KD-3120
//    奶瓶溫度計: KD-3020
//    奶嘴體溫計: KD-3130
//    熱水量測溫度計: KD-3030
//    耳朵體溫計: KD-3170
//    婦女體溫計: KD-2162
//
//// 有 BLE功能
//    有綁帶的體溫計: KD-3160
//    長得像飛碟的: KS-4310 (有sensor功能)
//    尿濕與溫度偵測:  KS-4320 (跟KS-4310一組)
//    尿濕與溫度偵測:  KS-4390

}
