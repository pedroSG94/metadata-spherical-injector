package com.pedro.metadatainjectortest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.pedro.metadatavideo.VideoInjector;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    VideoInjector videoInjector = new VideoInjector();
    File fileIn = new File(Environment.getExternalStorageDirectory(), "testVideo.mp4");
    File fileOut = new File(Environment.getExternalStorageDirectory(), "result.mp4");
    videoInjector.injectVideo(fileIn.getAbsolutePath(), fileOut.getAbsolutePath());
  }
}
