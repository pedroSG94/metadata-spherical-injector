//
// Created by pedro on 9/11/16.
//

#include "com_pedro_metadatavideo_VideoInjector.h"
#include <iostream>
#include <stdlib.h>
#include "spatialmedia/parser.h"
#include "spatialmedia/metadata_utils.h"
#include "android/log.h"

using namespace std;
using namespace SpatialMedia;

JNIEXPORT jboolean JNICALL Java_com_pedro_metadatavideo_VideoInjector_injectVideo
        (JNIEnv *env, jobject, jstring fileIn, jstring fileOut) {
    Parser parser;
    const char *nativeStringIn = env->GetStringUTFChars(fileIn, 0);
    const char *nativeStringOut = env->GetStringUTFChars(fileOut, 0);
    parser.m_strInFile = nativeStringIn;
    parser.m_strOutFile = nativeStringOut;
    parser.getInFile();
    parser.getOutFile();
    if (parser.getInFile() == "") {
        __android_log_print(ANDROID_LOG_INFO, "metaLibrary", "Please provide an input file.");
        exit(-1);
    }

    Utils utils;
    if (parser.getInject()) {
        if (parser.getOutFile() == "") {
            __android_log_print(ANDROID_LOG_INFO, "metaLibrary",
                                "Injecting metadata requires both input and output file.");
            exit(-2);
        }
        Metadata md;
        std::string &strVideoXML = utils.generate_spherical_xml(parser.getStereoMode(),
                                                                parser.getCrop());
        md.setVideoXML(strVideoXML);
        __android_log_print(ANDROID_LOG_INFO, "metaLibrary", "xml created");
        if (parser.getSpatialAudio()) {
            md.setAudio(&g_DefAudioMetadata);
            __android_log_print(ANDROID_LOG_INFO, "metaLibrary", "meta audio setted");
        }
        if (strVideoXML.length() > 1) {
            utils.inject_metadata(parser.getInFile(), parser.getOutFile(), &md);
            __android_log_print(ANDROID_LOG_INFO, "metaLibrary", "metadata injected");
        }
        else
            __android_log_print(ANDROID_LOG_INFO, "metaLibrary", "Failed to generate metadata.");
    }

    return 0;

}