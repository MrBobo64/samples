#include <stdlib.h>
#include <iostream>
#include "bmp\EasyBMP.h"

using namespace std;

struct target {
	ebmpBYTE red;
	ebmpBYTE green;
	ebmpBYTE blue;
	double radius;
};

void rgb2hsl( int* hue, int* sat, int* lum, int r, int g, int b ) {
	double var_R;
	double var_G;
	double var_B;
	
	double var_Min;
	double var_Max;
	double del_Max;
	
	double H;
	double S;
	double L;
	
	double del_R;
	double del_G;
	double del_B;
	
	var_R = ( r / 255.0 );
	var_G = ( g / 255.0 );
	var_B = ( b / 255.0 );
	
	if(var_R > var_G) {
		var_Min = var_G;
		var_Max = var_R;
	}
	else {
		var_Min = var_R;
		var_Max = var_G;
	}

	if (var_B > var_Max) {
		var_Max = var_B;
	}
	if (var_B < var_Min) {
		var_Min = var_B;
	}
	
	del_Max = var_Max - var_Min;
	
	H = 0;
	L = ( var_Max + var_Min ) / 2.0;
	
	if( del_Max == 0 ) {
		H = 0;
		S = 0;
	}
	else {
		if( L > 0.5 ) {
			S = del_Max / ( var_Max + var_Min );
		}
		else {
			S = del_Max / ( 2.0 - var_Max - var_Min );
		}
		
		del_R = ( ( ( var_Max - var_R ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		del_G = ( ( ( var_Max - var_G ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		del_B = ( ( ( var_Max - var_B ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		
		if( var_R == var_Max ) {
			H = del_B - del_G;
		}
		else if( var_G == var_Max ) { 
			H = ( 1.0 / 3.0 ) + del_R - del_B;
		}
		else if ( var_B == var_Max ) {
			H = ( 2.0 / 3.0 ) + del_G - del_R;
		}
		
		if( H < 0 ) {
			H += 1;
		}
		if( H > 1 ) {
			H -= 1;
		}
	}
	
	*hue = (int) 360 * H;
	*sat = (int) S * 100;
	*lum = (int) L * 100;
}

bool checkTarget( int h, int s, int l, struct target* t ) {
	double hueRadius;
	double satRadius;
	double lumRadius;
	
	int targetHue;
	int targetSat;
	int targetLum;
	
	int diffHue;
	int diffSat;
	int diffLum;
	
	hueRadius = t->radius * 360;
	satRadius = t->radius * 100;
	lumRadius = t->radius * 100;
	
	rgb2hsl( &targetHue, &targetSat, &targetLum, (int) t->red, (int) t->green, (int) t->blue );
	
	diffHue = targetHue - h;
	diffSat = targetSat - s;
	diffLum = targetLum - l;
	
	if( diffHue < 0 ) diffHue = diffHue * -1;
	if( diffSat < 0 ) diffSat = diffSat * -1;
	if( diffLum < 0 ) diffLum = diffLum * -1;
	
	if( diffHue > hueRadius ) return false;
	if( diffSat > satRadius ) return false;
	if( diffLum > lumRadius ) return false;
	
	return true;
}

int WinMain( int argc, char* argv[] ) {
	char* fileInName;
	char* fileOutName;
	struct target* listOfTargets;
	int numberOfTargets;
	int i;
	int j;
	int k;
	BMP working;
	int hue;
	int sat;
	int lum;
	int red;
	int green;
	int blue;
	int gray;
	int Temp;
	ebmpBYTE TempBYTE;
	
	if( argc < 7 ) {
		cout << "Usage: inFile, outFile, [red, green, blue, radius]+" << endl;
	}
	
	fileInName = argv[1];
	fileOutName = argv[2];
	
	numberOfTargets = (argc - 3) / 4;
	
	listOfTargets = (struct target*) malloc( numberOfTargets * sizeof( struct target) );
	
	for( i = 0; i < numberOfTargets; i++ ) {
		listOfTargets[i].red	= (ebmpBYTE) atoi( argv[3 + i*4 + 0] );
		listOfTargets[i].green	= (ebmpBYTE) atoi( argv[3 + i*4 + 1] );
		listOfTargets[i].blue	= (ebmpBYTE) atoi( argv[3 + i*4 + 2] );
		listOfTargets[i].radius	= (double) atoi( argv[3 + i*4 + 3] ) / 100.0;	
	}
	
	working.ReadFromFile( fileInName );
	
	for( i = 0; i < working.TellWidth(); i++ ) {
		for( j = 0; j < working.TellHeight(); j++ ) {
			red		= (int) working(i,j)->Red;
			green	= (int) working(i,j)->Green;
			blue	= (int) working(i,j)->Blue;
			rgb2hsl( &hue, &sat, &lum, red, green, blue );

			gray = 1;
			for( k = 0; k < numberOfTargets; k++ ) {
				if( checkTarget( hue, sat, lum, &listOfTargets[k] ) ) {
					gray = 0;
					break;
				}
			}
			
			if( gray ) {
				Temp = (int) floor( 0.299 * red + 0.587 * green + 0.114 * blue );
				TempBYTE = (ebmpBYTE) Temp;
				working(i,j)->Red	= TempBYTE;
				working(i,j)->Green	= TempBYTE;
				working(i,j)->Blue	= TempBYTE;
			}
		}
	}
	
	working.WriteToFile( fileOutName );
	
	return 0;	
}
