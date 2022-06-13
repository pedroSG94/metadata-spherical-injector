# Metadata Spherical Injector

Library for inject spherical metadata in photos and videos on Android. Only MP4 and JPEG formats supported

This library is a wrapper from two librarys.

Photo: https://github.com/dragon66/pixymeta-android

Video: https://github.com/cievon/spatial-media/tree/master/spatialmedia

Photo library should support all arquitecture and video library is only compiled for armeabi-v7a and x86 but you can compile it for others arquitectures.

# Permission

Only write storage permission needed:

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

# Code Example

Video Library:

```java
VideoInjector videoInjector = new VideoInjector();
videoInjector.injectVideo("your_file_origin_path", "yout_file_destiny_path");
```

Photo Library:

```java
PhotoInjector photoInjector = new PhotoInjector(context);
try {
  photoInjector.putMetadata("your_file_origin_path", "yout_file_destiny_path");
} catch (IOException e) {
  e.printStackTrace();
}
```

# Compile

Video Library:

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.pedroSG94.metadata-spherical-injector:metadatavideo:1.1'
}
```
Photo Library:

```gradle
android {
  repositories{
    maven{
      url "https://oss.sonatype.org/content/repositories/snapshots"
    }
  }
}

allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.pedroSG94.metadata-spherical-injector:metadataphoto:1.1'
}
```
