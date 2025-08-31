package com.pedro.metadatainjectortest

import android.app.Activity
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Bundle
import com.pedro.metadatainjectphoto.PhotoInjector
import com.pedro.metadatavideo.VideoInjector
import java.io.File
import java.io.FileOutputStream

class MainActivity: Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val baseFolder = cacheDir
    val folder = File(baseFolder.absolutePath + "/injector")

    val photoFile = copyFileToDisk(R.raw.test_photo, "test_photo.jpg")
    val videoFile = copyFileToDisk(R.raw.test_video, "test_video.mp4")

    val photoFileOut = File(folder, "resultPhoto.png")
    val videoFileOut = File(folder, "resultVideo.mp4")

    //This test require set test_video.mp4 and test_photo.jpg files inside Movies folder of your device
    val videoInjector = VideoInjector()
    val result = videoInjector.injectVideo(videoFile.absolutePath, videoFileOut.absolutePath)
    if (!result) {
      throw IllegalArgumentException("Inject video file failed")
    } else {
      updateGallery(this, videoFileOut.absolutePath)
      if (!videoFileOut.exists()) throw IllegalArgumentException("Inject video file failed, output not found")
    }

    val photoInjector = PhotoInjector(this)
    photoInjector.putMetadata(photoFile, photoFileOut)
    updateGallery(this, photoFileOut.absolutePath)
    if (!photoFileOut.exists()) throw IllegalArgumentException("Inject photo file failed, output not found")
  }

  fun copyFileToDisk(resource: Int, fileName: String): File {
    val baseFolder = cacheDir
    val folder = File(baseFolder.absolutePath + "/injector")
    if (!folder.exists()) folder.mkdirs()
    val file = File(folder, fileName)
    if (!file.exists()) {
      val photo = resources.openRawResource(resource)
      FileOutputStream(file).use {
        it.write(photo.readBytes())
      }
    }
    return file
  }

  fun updateGallery(context: Context, path: String) {
    MediaScannerConnection.scanFile(context, arrayOf(path), null, null)
  }
}
