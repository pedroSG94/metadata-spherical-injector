package com.pedro.metadatainjectphoto;

import android.app.Activity;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

/**
 * Created by pedro on 13/07/16.
 */
public class ReadFile {

  private Activity activity;

  public ReadFile(Activity activity) {
    this.activity = activity;
  }

  /**set name without extension*/
  public File getRawFile(String name) throws IOException {
    InputStream ins = activity.getResources().openRawResource(
        activity.getResources().getIdentifier(name,
            "raw", activity.getPackageName()));
    File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
    OutputStream os = new FileOutputStream(file);
    IOUtils.copy(ins, os);
    return file;
  }
}
