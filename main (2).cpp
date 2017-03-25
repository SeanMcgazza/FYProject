#include "mbed.h"
#include "FXOS8700Q.h"
#include "SerialRPCInterface.h"
 
//I2C lines for FXOS8700Q accelerometer/magnetometer
FXOS8700Q_acc acc( PTE25, PTE24, FXOS8700CQ_SLAVE_ADDR1);
FXOS8700Q_mag mag( PTE25, PTE24, FXOS8700CQ_SLAVE_ADDR1);

//Create the interface on the USB Serial Port
//SerialRPCInterface SerialInterface(USBTX, USBRX);
//void ReadRange(char * input, char * output);

//SRF08 srf08(p9, p10, 0xE0);      // Define SDA, SCL pin and I2C address 
 
//Temrinal enable 
Serial pc(USBTX, USBRX);
 
MotionSensorDataUnits mag_data;
MotionSensorDataUnits acc_data;
 
 DigitalOut myled(LED2);
 DigitalOut myled1(LED1);
 
 DigitalIn mySW(SW2);
 DigitalIn mySW1(SW3);
 
 float myXValue,myYvalue;
 
RPCVariable<float> RPCmyXValue(&myXValue, "myXValue");
RPCVariable<float> RPCmyYvalue(&myYvalue, "myYvalue");


int main() 
{
    while(1){
    float faX, faY, faZ;
    float fmX, fmY, fmZ;
    
    float MyAccelX = 0 ,MyAccelY = 0,MyAccelZ = 0;
    
    float SumOfX =0,SumOfY=0;
    float SumOfXa =0,SumOfYa=0,SumOfZa;
   // float SumOfXa=0,SumOfYa=0;,SumOfZa=0;

    acc.enable();
   // printf("\r\n\nFXOS8700Q Who Am I= %X\r\n", acc.whoAmI());
   // printf("\r\n\n the level os SW...",mySW);

    while (1){
        
        acc.getAxis(acc_data);
        mag.getAxis(mag_data);
       // printf("FXOS8700Q ACC: X=%1.4f Y=%1.4f Z=%1.4f  ", acc_data.x, acc_data.y, acc_data.z);
//        printf("MAG: X=%4.1f Y=%4.1f Z=%4.1f\r\n", mag_data.x, mag_data.y, mag_data.z);
        acc.getX(&faX);
        acc.getY(&faY);
        acc.getZ(&faZ);
        mag.getX(&fmX);
        mag.getY(&fmY);
        mag.getZ(&fmZ);
        printf("FXOS8700Q ACC: X=%1.4f Y=%1.4f Z=%1.4f  ", faX, faY, faZ);
        printf("    MAG: X=%4.1f Y=%4.1f Z=%4.1f\r\n", fmX, fmY, fmZ);
        
        wait(1.0);
        
        
        //
//        printf("\rThe value of X is = %1.2f and the value of Y is = %1.2f the value of FMY = %1.2f the value of FMX = %1.2f",myXValue,myYvalue,fmY,fmX);
        
        
        //To calibrate the postion of the knee
    if (mySW == 0) 
    {
        myled = 1; // LED is ON
        wait(0.2); // 200 ms
        myled = 0; // LED is OFF
        wait(1.0); // 1 sec
        
        myXValue = fmX;
        myYvalue = fmY;
        
        MyAccelX = faX;
        MyAccelY = faY;
        MyAccelZ = faZ;
        
    //    printf("\r\n\nThe value of X is = %1.2f and the value of Y is = %1.2f",myXValue,myYvalue);
//        printf("\r\n\n The value of Xa = %1.2f and the value of Ya = %1.2f and Za = %1.2f",MyAccelX,MyAccelY,MyAccelZ);
        
    }
    
    /*
    The MAG y value is the value is the data to tell me if the knee is buckiling in or out
    
    The Accel y value teels me the Gforce applyied during a jump
    */
    else if ( mySW1 == 0 )
    {
        myled1 = 1; // LED is ON
        wait(0.2); // 200 ms
        myled1 = 0; // LED is OFF
        wait(1.0); // 1 sec
 
        
        SumOfX=myXValue-SumOfX;
        SumOfY=myYvalue-SumOfY;
        
        SumOfX=MyAccelX-SumOfXa;
        SumOfY=MyAccelY-SumOfYa;
        SumOfY=MyAccelZ-SumOfZa;
        
        
      //  printf("\r\n\n\nThe value of SumOfX is = %1.2f and the value of SumOfY is = %1.2f\n\n",SumOfX,SumOfY);
//        printf("\r\n\n\nThe value of SumOfX is = %1.2f \r\nand the value of SumOfY is = %1.2f\r\n the value of SumOfZ is = %1.2f\n",SumOfXa,SumOfYa,SumOfZa);
        }
        
        //To determine if the knee is buckling in or out//
       if(myYvalue > fmY+4 && myXValue < fmX + 3 )
       {
          // printf("\The value of MyYVal = 1.2%f FMY = 1.2%f \r\nMyXval = 1.2%f FM
           //printf("\n\n\n\rThe sum of Y vale is = 1.2%f \r\nthe value of fmY = %f \n\n\r" , myYvalue,fmY );
            printf("\n\n\rbuckling in\n\r");
            pc.printf("1\n\r");
       }  
       
        if(myYvalue < fmY-3 && myXValue < fmX -3)
       {
           //printf("\n\n\n\rThe sum of Y vale is = 1.2%f the value of fmY = %f\n\n\r" , myYvalue);
             printf("\n\n\rbuckling out\n\r");
             pc.printf("2\n\r");
            
       } 
       
       else{
            pc.printf("2\n\r");
           }
       
     
    }
 
 
 }
//      void RangeFinder(char * input, char * output){
// Range = srf08.read();
// }
}
 
