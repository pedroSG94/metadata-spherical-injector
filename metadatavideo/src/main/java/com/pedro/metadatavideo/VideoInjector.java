package com.pedro.metadatavideo;

/**
 * Created by pedro on 11/11/16.
 */

public class VideoInjector {
  static {
    System.loadLibrary("MetaVideo");
  }

  public native boolean injectVideo(String fileIn, String fileOut);
}
