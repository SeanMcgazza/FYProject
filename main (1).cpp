#include "mbed.h"

 
AnalogIn analog_value(A0);
 
DigitalOut led(LED1);
Serial pc(USBTX, USBRX); // tx, rx
int main() {
float meas;
float temp= 0;

    
    
    while(1) {
        meas = analog_value.read(); // Converts and read the analog input value (value from 0.0 to 1.0)
        //meas = meas * 3300; // Change the value to be in the 0 to 3300 range
        //printf("measure = %.0f mV\n\r", meas);
        // printf("%.0f\n\r",meas);
          printf("%f\n\r",meas);
         
        if (meas > 1000) { // If the value is greater than 2V then switch the LED on
          led = 0;
        }
        else {
          led = 1;
        }
        
        if(temp<meas/10) {
           temp=meas;
           printf("The peak value is %f\n\r",temp);
        }
        wait(.5); // 200 ms
    }
}
