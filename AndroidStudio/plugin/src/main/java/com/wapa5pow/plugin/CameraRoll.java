package com.wapa5pow.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;

public class CameraRoll {
    static public void openCameraRoll(Context context, String filePath) {
        final Activity activity = UnityPlayer.currentActivity;

        Intent intent = new Intent();
        intent.putExtra("FILEPATH", filePath);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClassName(activity, "com.wapa5pow.plugin.CameraRollActivity");
        activity.startActivity(intent);
    }

}
