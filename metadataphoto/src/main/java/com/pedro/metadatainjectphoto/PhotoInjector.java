package com.pedro.metadatainjectphoto;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.w3c.dom.Document;
import pixy.meta.Metadata;
import pixy.meta.MetadataType;
import pixy.meta.exif.Exif;
import pixy.meta.jpeg.JPEGMeta;
import pixy.meta.xmp.XMP;
import pixy.string.XMLUtils;

/**
 * Created by pedro on 13/07/16.
 */
public class PhotoInjector {

  private final String TAG = "PhotoInjector";
  private Activity activity;
  private File fileOutClear, fileExif, fileMeta;

  public PhotoInjector(Activity activity){
    this.activity = activity;
  }

  /**need set file in format jpg*/
  public void putMetadata(File fileTarget, File destinyFile) throws Exception {
    ReadFile readFile = new ReadFile(activity);
    fileMeta = readFile.getRawFile("filewithmeta");
    File clear = removeMeta(fileTarget);
    setMetadata(fileMeta, clear, destinyFile);
    clearTempFiles();
  }

  private File removeMeta(File fileIn) throws IOException {
    fileOutClear = new File(Environment.getExternalStorageDirectory(), "fileClear.jpg");

    FileInputStream is = new FileInputStream(fileIn);
    FileOutputStream os = new FileOutputStream(fileOutClear);

    Metadata.removeMetadata(is, os, MetadataType.EXIF, MetadataType.IMAGE, MetadataType.XMP,
        MetadataType.JPG_JFIF);
    Log.d(TAG, "metadata clear");
    return fileOutClear;
  }

  private void setMetadata(File fileMeta, File fileTarget, File destinyFile) throws Exception {
    fileExif = new File(Environment.getExternalStorageDirectory(), "fileExif.jpg");

    Map<MetadataType, Metadata> metadataMap = Metadata.readMetadata(fileMeta);

    /**insert exif*/
    if(metadataMap.get(MetadataType.EXIF) != null){

      Exif exif = (Exif) metadataMap.get(MetadataType.EXIF);
      FileInputStream in = new FileInputStream(fileTarget);
      FileOutputStream out = new FileOutputStream(fileExif);
      Metadata.insertExif(in, out, exif);
      in.close();
      out.close();
      Log.d(TAG, "metadata exif injected");
    }
    else{
      Log.e(TAG, "no metadata exif in this file, set file with metadata");
    }

    /**insert xmp*/
    if(metadataMap.get(MetadataType.XMP) != null){
      XMP xmp = (XMP) metadataMap.get(MetadataType.XMP);
      Document xmpDoc = xmp.getXmpDocument();
      FileInputStream in = new FileInputStream(fileExif);
      FileOutputStream out = new FileOutputStream(destinyFile);
      if(!xmp.hasExtendedXmp()){
        JPEGMeta.insertXMP(in, out, xmp);
      }
      else {
        Document extendedXmpDoc = xmp.getExtendedXmpDocument();
        JPEGMeta.insertXMP(in, out, XMLUtils.serializeToString(xmpDoc.getDocumentElement(), "UTF-8"), XMLUtils.serializeToString(extendedXmpDoc));
      }
      in.close();
      out.close();
      Log.d(TAG, "metadata xmp injected");
    }
    else{
      Log.e(TAG, "no metadata xmp in this file, set file with metadata");
    }
  }

  private void clearTempFiles(){
    fileOutClear.delete();
    fileExif.delete();
    fileMeta.delete();
  }
}
