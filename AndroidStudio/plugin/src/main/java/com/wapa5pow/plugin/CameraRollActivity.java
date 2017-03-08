package com.wapa5pow.plugin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CameraRollActivity extends Activity {

    private final static int REQUEST_GALLERY = 0;
    private static String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPhoto();
    }

    public void setPhoto() {
        // ギャラリー呼び出し
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (REQUEST_GALLERY):
            {
                if (resultCode != RESULT_OK) {
                    return;
                }

                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    // 選択した画像のcrop
                    img = crop(img);
                    img = Bitmap.createScaledBitmap(img, 500, 500, false);

                    // 選択した画像を表示
                    final String filePath = getIntent().getStringExtra("FILEPATH");
                    Log.d("photo: ", filePath);
                    FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                    img.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    UnityPlayer.UnitySendMessage("GameController", "SetImage", filePath);
                } catch (Exception e) {
                    Log.d("error: ", e.getLocalizedMessage());
                }
            }
        }

        finish();
    }

    private Bitmap crop(Bitmap src) {
        if (src.getWidth() >= src.getHeight()){
            return Bitmap.createBitmap(
                    src,
                    src.getWidth()/2 - src.getHeight()/2,
                    0,
                    src.getHeight(),
                    src.getHeight()
            );
        } else {
            return Bitmap.createBitmap(
                    src,
                    0,
                    src.getHeight()/2 - src.getWidth()/2,
                    src.getWidth(),
                    src.getWidth()
            );
        }
    }
}
