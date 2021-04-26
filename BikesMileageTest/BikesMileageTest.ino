/* TSL2591 Digital Light Sensor */
/* Dynamic Range: 600M:1 */
/* Maximum Lux: 88K */

#include <Wire.h>
#include <Adafruit_Sensor.h>
#include "Adafruit_TSL2591.h"
#include "DEV_Config.h"
#include "EPD_2in13.h"
#include "GUI_Paint.h"
#include "imagedata.h"


// macros from DateTime.h
/* Useful Constants */
#define SECS_PER_MIN  (60UL)
#define SECS_PER_HOUR (3600UL)
#define SECS_PER_DAY  (SECS_PER_HOUR * 24L)

/* Useful Macros for getting elapsed time */
#define numberOfSeconds(_time_) (_time_ % SECS_PER_MIN)
#define numberOfMinutes(_time_) ((_time_ / SECS_PER_MIN) % SECS_PER_MIN)
#define numberOfHours(_time_) (( _time_% SECS_PER_DAY) / SECS_PER_HOUR)
#define elapsedDays(_time_) ( _time_ / SECS_PER_DAY)


//for display
unsigned char BlackImage[((EPD_WIDTH % 8 == 0) ? (EPD_WIDTH / 8 ) : (EPD_WIDTH / 8 + 1)) * 50]; //50 line  Screen dimension : x:122 y:280(?)
PAINT_TIME sPaint_time;



// for TLS
 unsigned long time0 = 0; 
 int nbTour = 1;
 int count = 0; 

 float kmh_disp = 0;
 float nbkm_disp = 0;


// Example for demonstrating the TSL2591 library - public domain!

// connect SCL to I2C Clock
// connect SDA to I2C Data
// connect Vin to 3.3-5V DC
// connect GROUND to common ground

Adafruit_TSL2591 tsl = Adafruit_TSL2591(2591); // pass in a number for the sensor identifier (for your use later)

/**************************************************************************/
/*
    Displays some basic information on this sensor from the unified
    sensor API sensor_t type (see Adafruit_Sensor for more information)
*/
/**************************************************************************/
void displaySensorDetails(void)
{
  sensor_t sensor;
  tsl.getSensor(&sensor);
  Serial.println(F("------------------------------------"));
  Serial.print  (F("Sensor:       ")); Serial.println(sensor.name);
  Serial.print  (F("Driver Ver:   ")); Serial.println(sensor.version);
  Serial.print  (F("Unique ID:    ")); Serial.println(sensor.sensor_id);
  Serial.print  (F("Max Value:    ")); Serial.print(sensor.max_value); Serial.println(F(" lux"));
  Serial.print  (F("Min Value:    ")); Serial.print(sensor.min_value); Serial.println(F(" lux"));
  Serial.print  (F("Resolution:   ")); Serial.print(sensor.resolution, 4); Serial.println(F(" lux"));  
  Serial.println(F("------------------------------------"));
  Serial.println(F(""));
  delay(500);
}

/**************************************************************************/
/*
    Configures the gain and integration time for the TSL2591
*/
/**************************************************************************/
void configureSensor(void)
{
  // You can change the gain on the fly, to adapt to brighter/dimmer light situations
  //tsl.setGain(TSL2591_GAIN_LOW);    // 1x gain (bright light)
  tsl.setGain(TSL2591_GAIN_MED);      // 25x gain
  //tsl.setGain(TSL2591_GAIN_HIGH);   // 428x gain
  
  // Changing the integration time gives you a longer time over which to sense light
  // longer timelines are slower, but are good in very low light situtations!
  //tsl.setTiming(TSL2591_INTEGRATIONTIME_100MS);  // shortest integration time (bright light)
  // tsl.setTiming(TSL2591_INTEGRATIONTIME_200MS);
  tsl.setTiming(TSL2591_INTEGRATIONTIME_300MS);
  // tsl.setTiming(TSL2591_INTEGRATIONTIME_400MS);
  // tsl.setTiming(TSL2591_INTEGRATIONTIME_500MS);
  // tsl.setTiming(TSL2591_INTEGRATIONTIME_600MS);  // longest integration time (dim light)

  /* Display the gain and integration time for reference sake */  
  //Serial.println(F("------------------------------------"));
  //Serial.print  (F("Gain:         "));
  tsl2591Gain_t gain = tsl.getGain();
 /* switch(gain)
  {
    case TSL2591_GAIN_LOW:
      Serial.println(F("1x (Low)"));
      break;
    case TSL2591_GAIN_MED:
      Serial.println(F("25x (Medium)"));
      break;
    case TSL2591_GAIN_HIGH:
      Serial.println(F("428x (High)"));
      break;
    case TSL2591_GAIN_MAX:
      Serial.println(F("9876x (Max)"));
      break;
  }
  Serial.print("okok\r\n");
  Serial.print  (F("Timing:       "));
  Serial.print((tsl.getTiming() + 1) * 100, DEC); 
  Serial.println(F(" ms"));
  Serial.println(F("------------------------------------"));
  Serial.println(F(""));*/
}


/**************************************************************************/
/*
    Program entry point for the Arduino sketch
*/
/**************************************************************************/
void setup(void) 
{
  Serial.begin(9600);
  
  Serial.println(F("Starting Adafruit TSL2591 Test!"));
  
  if (tsl.begin()) 
  {
    Serial.println(F("Found a TSL2591 sensor"));
  } 
  else 
  {
    Serial.println(F("No sensor found ... check your wiring?"));
    while (1);
  }
    
  /* Display some basic information on this sensor */
  displaySensorDetails();
  
  /* Configure the sensor */
  configureSensor();

  // Now we're ready to get readings ... move on to loop()!


    DEV_ModuleInit();
    if (EPD_Init(FULL_UPDATE) != 0) {
        Serial.print("e-Paper init failed\r\n");
    }
     Serial.print("e-Paper init work\r\n");
    EPD_Clear();
    //DEV_Delay_ms(500);

    //Image to display
    Paint_NewImage(BlackImage, EPD_WIDTH, 50, 0, WHITE);
  Paint_SelectImage(BlackImage);
  //Paint_SetMirroring(MIRROR_VERTICAL);
  //Paint_SetRotate(ROTATE_90);
  Paint_Clear(0xff);

  Paint_Clear(0xff);
  Paint_DrawRectangle(0, 0, 122, 50, BLACK, DRAW_FILL_FULL, DOT_PIXEL_1X1);
  Paint_DrawString_EN(0, 5, "Bike's", &Font20, BLACK, WHITE);
  Paint_DrawString_EN(0, 25, "Mileage", &Font20, BLACK, WHITE);
  EPD_DisplayWindows(BlackImage, 0, 0, 122, 50);

  Paint_Clear(0xff);
  Paint_DrawRectangle(0, 0, 122, 50, BLACK, DRAW_FILL_FULL, DOT_PIXEL_1X1);
  EPD_DisplayWindows(BlackImage, 0, 200, 122, 250);

  EPD_TurnOnDisplay();
  DEV_Delay_ms(500);//Analog clock 1s
  if (EPD_Init(PART_UPDATE) != 0) {
    Serial.print("e-Paper init failed\r\n");
  }
  sPaint_time.Hour = 0;
  sPaint_time.Min = 0;
  sPaint_time.Sec = 0;



    
}

/**************************************************************************/
/*
    Shows how to perform a basic read on visible, full spectrum or
    infrared light (returns raw 16-bit ADC values)
*/
/**************************************************************************/
void simpleRead(void)
{
  // Simple data read example. Just read the infrared, fullspecrtrum diode 
  // or 'visible' (difference between the two) channels.
  // This can take 100-600 milliseconds! Uncomment whichever of the following you want to read
  uint16_t x = tsl.getLuminosity(TSL2591_VISIBLE);
  //uint16_t x = tsl.getLuminosity(TSL2591_FULLSPECTRUM);
  //uint16_t x = tsl.getLuminosity(TSL2591_INFRARED);

  Serial.print(F("[ ")); Serial.print(millis()); Serial.print(F(" ms ] "));
  Serial.print(F("Luminosity: "));
  Serial.println(x, DEC);
}

/**************************************************************************/
/*
    Show how to read IR and Full Spectrum at once and convert to lux
*/
/**************************************************************************/
unsigned long nbst;
float kmh;
float ms;
float cir; 

float calculkilometreheure(float cir, float deltaT) {

  ms = (cir*nbTour) / (deltaT / 1000);
  kmh = ms * 3.6;
 // Serial.print(F("cir :")); Serial.println(cir);
 // Serial.print(F("ms :")); Serial.print(ms);Serial.println(F("metre / tour"));
  Serial.print(F("kmh :")); Serial.print(kmh); Serial.println(F("km/h"));
  return kmh;
  
}
float calculnbkilo(int count, int cir) {
  float nbm = count * cir;
  float nbkm = nbm / 1000;
  if(nbm < 1000){
    Serial.print(F("nbm :")); Serial.println(nbm);
    return nbm;
  }else{
    Serial.print(F("nbkm :")); Serial.println(nbkm);
    return nbkm;
  }
  
}


void advancedRead(void)
{
  // More advanced data read example. Read 32 bits with top 16 bits IR, bottom 16 bits full spectrum
  // That way you can do whatever math and comparisons you want!
  uint32_t lum = tsl.getFullLuminosity();
  uint16_t ir, full;
  ir = lum >> 16;
  
  full = lum & 0xFFFF;
 // Serial.print(F("[ ")); Serial.print(millis()); Serial.print(F(" ms ] "));
 // Serial.print(F("IR: ")); Serial.print(ir);  Serial.print(F("  "));
 // Serial.print(F("Full: ")); Serial.print(full); Serial.print(F("  "));
 // Serial.print(F("Visible: ")); Serial.print(full - ir); Serial.print(F("  "));
  Serial.print(F("Lux: ")); Serial.println(tsl.calculateLux(full, ir), 6);
  unsigned long deltaT = 0;
  if(tsl.calculateLux(full, ir)> 300){ //300
   // Serial.println(F("Reflecting tape detected"));
    count ++; 
     //Serial.print(F("Count : ")); Serial.println(count);
    unsigned long myTime = millis();
    //Serial.print(F("myTime: "));Serial.print(myTime);Serial.println(F("millisec "));
   if(count%nbTour == 0){
      deltaT = myTime-time0; 
      time0 = myTime; 
      if(time0 < 0){
        time0 = -time0;
      }
      Serial.print(F("DeltaT: "));Serial.println(deltaT);
      Serial.print(F("time0: "));Serial.println(time0);
      kmh_disp = calculkilometreheure(2,deltaT);
      nbkm_disp = calculnbkilo(count,2);
   }
  }
}

/**************************************************************************/
/*
    Performs a read using the Adafruit Unified Sensor API.
*/
/**************************************************************************/
void unifiedSensorAPIRead(void)
{
  /* Get a new sensor event */ 
  sensors_event_t event;
  tsl.getEvent(&event);
 
  /* Display the results (light is measured in lux) */
  Serial.print(F("[ ")); Serial.print(event.timestamp); Serial.print(F(" ms ] "));
  if ((event.light == 0) |
      (event.light > 4294966000.0) | 
      (event.light <-4294966000.0))
  {
    /* If event.light = 0 lux the sensor is probably saturated */
    /* and no reliable data could be generated! */
    /* if event.light is +/- 4294967040 there was a float over/underflow */
    Serial.println(F("Invalid data (adjust gain or timing)"));
  }
  else
  {
    Serial.print(event.light); Serial.println(F(" lux"));
  }
}


/**************************************************************************/
/*
    Arduino loop function, called once 'setup' is complete (your own code
    should go here)
*/
/**************************************************************************/
void loop(void) 
{ 
  //simpleRead(); 
  advancedRead();
  // unifiedSensorAPIRead();

  //Image

  //Display Km/h
  Paint_Clear(0xff);
  Paint_DrawString_EN(0, 16, "Km/h:", &Font16, WHITE, BLACK);
  Paint_DrawNum(60, 16, kmh_disp, &Font16, WHITE, BLACK);
  Paint_DrawLine(0, 49, 122, 49, BLACK, LINE_STYLE_DOTTED, DOT_PIXEL_2X2);
  EPD_DisplayWindows(BlackImage, 0, 50, 122, 100);



  //Dixplay Total Km
  double y, z;
   uint32_t a,b;

   y = modf(nbkm_disp, &z);
   a = z;
   b = y*1000000.0;
  
  Paint_Clear(0xff);
  Paint_DrawString_EN(0, 16, "Km:", &Font16, WHITE, BLACK);
  Paint_DrawNum(60, 16, a, &Font16, WHITE, BLACK);
  
  Paint_DrawString_EN(80, 16, ",", &Font16, WHITE, BLACK);
  Paint_DrawNum(90, 16, b, &Font16, WHITE, BLACK);
  Paint_DrawLine(0, 49, 122, 49, BLACK, LINE_STYLE_DOTTED, DOT_PIXEL_2X2);
  EPD_DisplayWindows(BlackImage, 0, 100, 122, 150);

  
  unsigned long myTime = millis();
  time(myTime / 1000);

  Paint_Clear(0xff);
  Paint_DrawTime(10, 15, &sPaint_time, &Font20, WHITE, BLACK);
  //Paint_DrawLine(0, 49, 122, 49, BLACK, LINE_STYLE_DOTTED, DOT_PIXEL_2X2);
  EPD_DisplayPartWindows(BlackImage, 0, 150, 122, 200);

  /*Paint_Clear(0xff);
    Paint_DrawString_EN(0, 0, "Cpt", &Font12, WHITE, BLACK);
    Paint_DrawNum(35, 0, cptest, &Font12, WHITE, BLACK);
    cptest = cptest+1;
    EPD_DisplayPartWindows(BlackImage, 0, 220, 122, 280);*/

  EPD_TurnOnDisplay();
  //DEV_Delay_ms(0);//Analog clock 1s
}

void time(long val) {
  int days = elapsedDays(val);
  int hours = numberOfHours(val);
  int minutes = numberOfMinutes(val);
  int seconds = numberOfSeconds(val);

  // digital clock display of current time
  sPaint_time.Hour = hours;
  sPaint_time.Min = minutes;
  sPaint_time.Sec = seconds;
}
