package com.pedro.metadatainjectphoto;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import pixy.image.tiff.FieldType;
import pixy.image.tiff.TiffTag;
import pixy.meta.Metadata;
import pixy.meta.MetadataType;
import pixy.meta.exif.Exif;
import pixy.meta.exif.ExifTag;
import pixy.meta.exif.JpegExif;

/**
 * Created by pedro on 28/11/16.
 */

public class Utils {
  public static String readRawTextFile(Context context, int resId) {
    InputStream is = context.getResources().openRawResource(resId);
    InputStreamReader reader = new InputStreamReader(is);
    BufferedReader buf = new BufferedReader(reader);
    StringBuilder text = new StringBuilder();
    try {
      String line;
      while ((line = buf.readLine()) != null) {
        text.append(line).append('\n');
      }
    } catch (IOException e) {
      return null;
    }
    return text.toString();
  }

  public static Exif createExif(Map<MetadataType, Metadata> metadataMap) {
    Exif exif = (Exif) metadataMap.get(MetadataType.EXIF);
    DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    Date date = new Date();
    if(exif == null){
      Log.i("ImageMeta", "No exif found, creating new exif");
      exif = new JpegExif();
    }
    exif.addExifField(ExifTag.DATE_TIME_ORIGINAL, FieldType.ASCII, dateFormat.format(date));
    exif.addExifField(ExifTag.DATE_TIME_DIGITIZED, FieldType.ASCII, dateFormat.format(date));
    exif.addImageField(TiffTag.SOFTWARE, FieldType.ASCII, "pedroSG94");
    return exif;
  }
}
