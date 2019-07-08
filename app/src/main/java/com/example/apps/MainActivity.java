package com.example.apps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    private static final String IMAGE_DOWNLOAD_PATH = "http://globalmedicalco.com/photos/globalmedicalco/9/41427.jpg";
    private static final String SONG_DOWNLOAD_PATH = "https://scontent-lga3-1.xx.fbcdn.net/v/t66.18014-6/10000000_1548742065257032_8533859838285840384_n.mp4?_nc_cat=111&efg=eyJ2ZW5jb2RlX3RhZyI6ImRhc2hfb2VwX2hxMV9mcmFnXzJfdmlkZW8ifQ==&_nc_oc=AQn6BfyOkalbKNQDAR1bp270f0P9R6A124epG_jo77OZLRQZMu-Pr1UANNqrVRmGQhQ&_nc_ht=scontent-lga3-1.xx&oh=ac09b107187ffc250cceb07fbed5f744&oe=5DBFCF1A";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        FadingCircle doubleBounce = new FadingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        findViewById(R.id.downloadImageButton).setOnClickListener(this);
        findViewById(R.id.downloadSongButton).setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return;
        }
        DirectoryHelper.createDirectory(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadImageButton: {
                startService(DownloadSongService.getDownloadService(this, IMAGE_DOWNLOAD_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                break;
            }
            case R.id.downloadSongButton: {
                startService(DownloadSongService.getDownloadService(this, SONG_DOWNLOAD_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                DirectoryHelper.createDirectory(this);
        }
    }
}
