LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog
LOCAL_MODULE := libMetaVideo
LOCAL_SRC_FILES := libMetaVideo.cpp spatialmedia/parser.cpp spatialmedia/metadata_utils.cpp spatialmedia/mpeg/mpeg4_container.cpp spatialmedia/mpeg/sa3d.cpp spatialmedia/mpeg/box.cpp spatialmedia/mpeg/container.cpp spatialmedia/mxml-2.10/mxml-attr.c spatialmedia/mxml-2.10/mxml-entity.c spatialmedia/mxml-2.10/mxml-file.c spatialmedia/mxml-2.10/mxml-get.c spatialmedia/mxml-2.10/mxml-index.c spatialmedia/mxml-2.10/mxml-node.c spatialmedia/mxml-2.10/mxml-private.c spatialmedia/mxml-2.10/mxml-search.c spatialmedia/mxml-2.10/mxml-set.c spatialmedia/mxml-2.10/mxml-string.c
LOCAL_C_INCLUDES := spatialmedia/parser.h spatialmedia/metadata_utils.h spatialmedia/mpeg/mpeg4_container.h spatialmedia/mpeg/sa3d.h spatialmedia/mpeg/box.h spatialmedia/mpeg/container.h spatialmedia/mpeg/constants.h spatialmedia/portable_endian.h spatialmedia/mpeg/portable_endian.h spatialmedia/mxml-2.10/mxml.h spatialmedia/mxml-2.10/mxml-private.h spatialmedia/mxml-2.10/config.h
include $(BUILD_SHARED_LIBRARY)