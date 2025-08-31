cmake_minimum_required(VERSION 3.10.2)
project("MetaVideo")

# ========================
# Definición de la librería
# ========================
add_library(
        MetaVideo                # Nombre de la librería sin "lib" (Gradle lo empaqueta como libMetaVideo.so)
        SHARED
        libMetaVideo.cpp
        spatialmedia/parser.cpp
        spatialmedia/metadata_utils.cpp
        spatialmedia/mpeg/mpeg4_container.cpp
        spatialmedia/mpeg/sa3d.cpp
        spatialmedia/mpeg/box.cpp
        spatialmedia/mpeg/container.cpp
        spatialmedia/mxml-2.10/mxml-attr.c
        spatialmedia/mxml-2.10/mxml-entity.c
        spatialmedia/mxml-2.10/mxml-file.c
        spatialmedia/mxml-2.10/mxml-get.c
        spatialmedia/mxml-2.10/mxml-index.c
        spatialmedia/mxml-2.10/mxml-node.c
        spatialmedia/mxml-2.10/mxml-private.c
        spatialmedia/mxml-2.10/mxml-search.c
        spatialmedia/mxml-2.10/mxml-set.c
        spatialmedia/mxml-2.10/mxml-string.c
)

# ========================
# Rutas de cabeceras (include)
# ========================
target_include_directories(
        MetaVideo
        PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}/spatialmedia
        ${CMAKE_CURRENT_SOURCE_DIR}/spatialmedia/mpeg
        ${CMAKE_CURRENT_SOURCE_DIR}/spatialmedia/mxml-2.10
)

# ========================
# Opciones de compilación
# ========================
target_compile_options(
        MetaVideo
        PRIVATE
        -Wno-error=format-security
)

# ========================
# Librerías del sistema (equiv. a LOCAL_LDLIBS := -llog)
# ========================
find_library(
        log-lib
        log
)

target_link_libraries(
        MetaVideo
        ${log-lib}
)

# Forzar page size 16 KB
set_target_properties(
        MetaVideo PROPERTIES
        LINK_FLAGS "-Wl,-z,max-page-size=16384"
)