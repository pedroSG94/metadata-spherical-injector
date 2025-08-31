#include <iostream>
#include <stdlib.h>
#include "parser.h"
#include "metadata_utils.h"
using namespace std;

int main ( int argc, char *argv[] )
{
SpatialMedia::Parser parser;
  parser.getInFile = "./testmeta.mp4";
    parser.getOutFile = "./metadata.mp4";
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
  
  return 0;
}