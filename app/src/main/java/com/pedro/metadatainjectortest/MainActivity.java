package com.pedro.metadatainjectortest;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import com.pedro.metadatainjectphoto.PhotoInjector;
import com.pedro.metadatavideo.VideoInjector;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    VideoInjector videoInjector = new VideoInjector();
    File fileIn = new File(Environment.getExternalStorageDirectory(), "testVideo.mp4");
    File fileOut = new File(Environment.getExternalStorageDirectory(), "resultVideo.mp4");
    videoInjector.injectVideo(fileIn.getAbsolutePath(), fileOut.getAbsolutePath());

    File fileIn2 = new File(Environment.getExternalStorageDirectory(), "testPhoto.jpg");
    File fileOut2 = new File(Environment.getExternalStorageDirectory(), "resultPhoto.jpg");
    PhotoInjector photoInjector = new PhotoInjector(this);
    try {
      photoInjector.putMetadata(fileIn2, fileOut2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
