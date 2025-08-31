/* *******************************************************************************
** usage: spatialmedia [options] [files...]
**
** By default prints out spatial media metadata from specified files.
**
** positional arguments:
**   file                  input/output files
**
** optional arguments:
**   -h, --help            show this help message and exit
**   -i, --inject          injects spatial media metadata into the first file
**                         specified (.mp4 or .mov) and saves the result to the
**                         second file specified
**
** Spherical Video:
**   -s STEREO-MODE, --stereo STEREO-MODE
**                         stereo mode (none | top-bottom | left-right)
**   -c CROP, --crop CROP  crop region. Must specify 6 integers in the form of
**                         "w:h:f_w:f_h:x:y" where w=CroppedAreaImageWidthPixels
**                         h=CroppedAreaImageHeightPixels f_w=FullPanoWidthPixels
**                         f_h=FullPanoHeightPixels x=CroppedAreaLeftPixels
**                         y=CroppedAreaTopPixels
**
** Spatial Audio:
**   -a, --spatial-audio   spatial audio. First-order periphonic ambisonics with
**                        ACN channel ordering and SN3D normalization
**
*********************************************************************************/

#include <algorithm>
#include <iostream>

#include <getopt.h>

#include "parser.h"

using namespace std;

namespace SpatialMedia
{

Parser::Parser ( )
{
  m_bInject       = true;
  m_StereoMode    = SM_NONE;
  for ( int t=0; t<6; t++ )
    m_crop[t]     = 0;
  m_bSpatialAudio = false;
}

Parser::~Parser ( )
{
}

void Parser::printHelp ( )
{
  cout << "usage: spatialmedia [options] [files...]" << endl;
  cout << endl;
  cout << "By default prints out spatial media metadata from specified files." << endl;
  cout << endl;
  cout << "positional arguments:" << endl;
  cout << "  file                  input/output files" << endl;
  cout << endl;
  cout << "optional arguments:" << endl;
  cout << "  -h, --help            show this help message and exit" << endl;
  cout << "  -i, --inject          injects spatial media metadata into the first file" << endl;
  cout << "                        specified (.mp4 or .mov) and saves the result to the" << endl;
  cout << "                        second file specified" << endl;
  cout << endl;
  cout << "Spherical Video:" << endl;
  cout << "  -s STEREO-MODE, --stereo STEREO-MODE" << endl;
  cout << "                        stereo mode (none | top-bottom | left-right)" << endl;
  cout << "                        \"none\": Mono frame layout." << endl;
  cout << "                        \"top-bottom\": Top half contains the left eye and bottom half contains the right eye." << endl;
  cout << "                        \"left-right\": Left half contains the left eye and right half contains the right eye." << endl;
  cout << "                        ( RFC: https://github.com/google/spatial-media/tree/master/docs/spherical-video-rfc.md )" << endl;
  cout << endl;
  cout << "  -c CROP, --crop CROP  crop region. Must specify 6 integers in the form of" << endl;
  cout << "                        \"w:h:f_w:f_h:x:y\" where w=CroppedAreaImageWidthPixels" << endl;
  cout << "                        h=CroppedAreaImageHeightPixels f_w=FullPanoWidthPixels" << endl;
  cout << "                        f_h=FullPanoHeightPixels x=CroppedAreaLeftPixels" << endl;
  cout << "                        y=CroppedAreaTopPixels" << endl;
  cout << endl;
  cout << "Spatial Audio:" << endl;
  cout << "  -a, --spatial-audio   spatial audio. First-order periphonic ambisonics with" << endl;
  cout << "                        ACN channel ordering and SN3D normalization" << endl;
  cout << "                        Enables injection of spatial audio metadata. If enabled, the file must contain a" << endl;
  cout << "                        4-channel first-order ambisonics audio track with ACN channel ordering and SN3D" << endl;
  cout << "                        normalization; see the [Spatial Audio RFC](../docs/spatial-audio-rfc.md) for" << endl;
  cout << "                        more information." << endl;

}

std::string &Parser::getInFile ( )
{
  return m_strInFile;
}

std::string &Parser::getOutFile ( )
{
  return m_strOutFile;
}

bool Parser::getInject ( )
{
  return m_bInject;
}

Parser::enMode Parser::getStereoMode ( )
{
  return m_StereoMode;
}

int *Parser::getCrop ( )
{
  // return NULL if no croping was specified.
  for ( int t=0; t<6; t++ )  {
    if ( m_crop[t] != 0 )
      return m_crop;
  }
  return NULL;
}

bool Parser::getSpatialAudio ( )
{
  return m_bSpatialAudio;
}

};  // nd of namespace SpatialMedia

