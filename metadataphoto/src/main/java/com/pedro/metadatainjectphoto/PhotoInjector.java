package com.pedro.metadatainjectphoto;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.w3c.dom.Document;
import pixy.meta.Metadata;
import pixy.meta.MetadataType;
import pixy.meta.exif.Exif;
import pixy.meta.jpeg.JPEGMeta;
import pixy.meta.jpeg.JpegXMP;
import pixy.meta.xmp.XMP;
import pixy.string.XMLUtils;

/**
 * Created by pedro on 13/07/16.
 */
public class PhotoInjector {

  private final String TAG = "PhotoInjector";
  private Context context;
  private File fileOutClear, fileExif;

  public PhotoInjector(Context context) {
    this.context = context;
  }

  /** need set file in format jpg */
  public void putMetadata(File fileTarget, File destinyFile) throws IOException {
    File clear = removeMeta(fileTarget);
    setMetadata(clear, destinyFile);
    clearTempFiles();
  }

  private File removeMeta(File fileIn) throws IOException {
    fileOutClear = new File(Environment.getExternalStorageDirectory(), "fileClear.jpg");
    FileInputStream is = new FileInputStream(fileIn);
    FileOutputStream os = new FileOutputStream(fileOutClear);
    Metadata.removeMetadata(is, os, MetadataType.IMAGE, MetadataType.XMP, MetadataType.JPG_JFIF);
    Log.i(TAG, "metadata clear");
    return fileOutClear;
  }

  private void setMetadata(File fileTarget, File destinyFile) throws IOException {
    fileExif = new File(Environment.getExternalStorageDirectory(), "fileExif.jpg");
    Map<MetadataType, Metadata> metadataMap = Metadata.readMetadata(fileTarget);
    /**insert exif*/
    Exif exif = Utils.createExif(metadataMap);
    FileInputStream in = new FileInputStream(fileTarget);
    FileOutputStream out = new FileOutputStream(fileExif);
    Metadata.insertExif(in, out, exif);
    in.close();
    out.close();
    Log.i(TAG, "metadata exif injected");

    /**insert xmp*/
    XMP xmp = new JpegXMP(Utils.readRawTextFile(context, R.raw.xmp_meta));
    Document xmpDoc = xmp.getXmpDocument();

    FileInputStream inXmp = new FileInputStream(fileExif);
    FileOutputStream outXmp = new FileOutputStream(destinyFile);

    if (!xmp.hasExtendedXmp()) {
      JPEGMeta.insertXMP(inXmp, outXmp, xmp);
    } else {
      Document extendedXmpDoc = xmp.getExtendedXmpDocument();
      JPEGMeta.insertXMP(inXmp, outXmp,
          XMLUtils.serializeToString(xmpDoc.getDocumentElement(), "UTF-8"),
          XMLUtils.serializeToString(extendedXmpDoc));
    }
    in.close();
    out.close();
    Log.i(TAG, "metadata xmp injected");
  }

  private void clearTempFiles() {
    fileOutClear.delete();
    fileExif.delete();
  }
}
