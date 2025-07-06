package com.epmedia.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;

public class MainActivity extends AppCompatActivity implements OnEditorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_process);
        btn.setOnClickListener(this::startProcess);

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void startProcess(View view) {
        String input = Environment.getExternalStorageDirectory() + "/input.mp4";
        String output = getExternalFilesDir(null).getAbsolutePath() + "/output.mp4";

        EpVideo epVideo = new EpVideo(input);
        EpEditor.OutputOption option = new EpEditor.OutputOption(output);
        EpEditor.exec(epVideo, option, this);
    }

    @Override
    public void onSuccess() {
        runOnUiThread(() -> Toast.makeText(this, "Process success", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onFailure() {
        runOnUiThread(() -> Toast.makeText(this, "Process failed", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onProgress(final float progress) {
        // Progress updates could be shown here
    }
}
