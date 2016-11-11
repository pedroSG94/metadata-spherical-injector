# Spatial Media Metadata Injector

A tool for manipulating spatial media ([spherical video])
This is a port from the python scripts provided by google to C++ by Varol Okan
and support iOS、Android by cievon

It can be used to inject spatial media metadata into a file or validate 
metadata in an existing file.

## Usage

    SpatialMedia::Parser parser;
      parser.getInFile = "input file";
        parser.getOutFile = "output file";
      if ( parser.getInFile ( ) == "" )  {
        cout << "Please provide an input file." << endl;
        exit ( -1 );
      }
      
      Utils utils;
      if ( parser.getInject ( ) )  {
        if ( parser.getOutFile ( ) == "" )  {
          cout << "Injecting metadata requires both input and output file." << endl;
          exit ( -2 );
        }
        Metadata md;
        std::string &strVideoXML = utils.generate_spherical_xml ( parser.getStereoMode ( ), parser.getCrop ( ) );
        md.setVideoXML ( strVideoXML );
        if ( parser.getSpatialAudio ( ) )
          md.setAudio ( &g_DefAudioMetadata );
        if ( strVideoXML.length ( ) > 1 )
          utils.inject_metadata ( parser.getInFile ( ), parser.getOutFile ( ), &md );
        else
          cout << "Failed to generate metadata." << endl;
      }

##### --stereo

Selects the left/right eye frame layout; see the `StereoMode` element in the
[Spherical Video RFC](https://github.com/google/spatial-media/tree/master/docs/spherical-video-rfc.md) for more information.

Options:

- `none`: Mono frame layout.

- `top-bottom`: Top half contains the left eye and bottom half contains the
right eye.

- `left-right`: Left half contains the left eye and right half contains the
right eye.

##### --spatial-audio

Enables injection of spatial audio metadata. If enabled, the file must contain a
4-channel first-order ambisonics audio track with ACN channel ordering and SN3D
normalization; see the [Spatial Audio RFC](../docs/spatial-audio-rfc.md) for
more information.

