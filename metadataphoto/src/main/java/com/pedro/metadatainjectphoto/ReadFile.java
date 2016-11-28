package com.pedro.metadatainjectphoto;

import android.content.Context;
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

  private Context context;

  public ReadFile(Context context) {
    this.context = context;
  }

  /** set name without extension */
  public File getRawFile(String name) throws IOException {
    InputStream ins = context.getResources()
        .openRawResource(
            context.getResources().getIdentifier(name, "raw", context.getPackageName()));
    File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
    OutputStream os = new FileOutputStream(file);
    IOUtils.copy(ins, os);
    return file;
  }
}
