#include "mbed.h"
#include <TCPSocket.h>
#include <EthInterface.h>
#include "EthernetInterface.h"
#include "string.h"
#include "ESP8266Interface.h"
ESP8266Interface wifi(D1, D0);
void ClearBuffer();
void measureEMG();
char rbuffer[300];

char vals[]={};
int rcount;

AnalogIn analog_value(A1);

float meas[50] = {};
char finial[800];


int main() {
    printf("Main\n\r"); 
    
    char userName[] = "It sent";
    char userName2[] = "This Sent";
    
    strcat(userName, "\n");
    strcat(userName2, "\n");
 
   printf("\r\nUser: %s \tSize1: %d", userName, sizeof(userName));
    
    
    printf("\r\nConnecting...\n\r");
    int ret = wifi.connect("AndroidAP","ynja9248", NSAPI_SECURITY_WPA_WPA2);
    if (ret != 0) {
        printf("\r\n*** WiFi Connection error ***\r\n");
        return -1;
    }
    printf("IP Address is %s\r\n", wifi.get_ip_address());
    
    
    //create socket
    TCPSocket sock;
    sock.open(&wifi);
    int ret2 = sock.connect("192.168.43.145", 8082);
    if(ret2 != 0)
    {
       printf("*** Socket connection error ***\r\n");
       return -1;
    }
    sock.set_blocking(false);
    
      
   



////////////////////////////////////////////////////////////////////////////
   // int scount = sock.send(userName, sizeof userName);
    
   // int scount1 = sock.send(userName2, sizeof userName2);
    
    // printf("sent %d [%.*s]\r\n", scount, strstr(userName, "\r\n")-userName, userName);
    // printf("sent %d [%.*s]\r\n", scount1, strstr(userName2, "\r\n")-userName2, userName2);//ORG
   
    while(1)
    {
         //recieve from java server
   // while(1) {//waits for something to be in the buffer  (timing issues)
        int32_t val =sock.recv(rbuffer, sizeof(rbuffer) -1);
        
         printf("%s\n",rbuffer);
        
        // printf("This is the rbuffer %d",val);
        
         char buf [] = "Server Says : John\r\n";
         if (strcmp(rbuffer, "Server Says : John\r\n") == 0){
             printf("Done name 1\r\n");
              ClearBuffer();
              measureEMG();
              int scount1 = sock.send(finial, sizeof finial);
              //printf("sent %d [%.*s]\r\n", scount1, strstr(finial, "\r\n")-finial, finial);//ORG
        }
        
         if (strcmp(rbuffer, "We could be on to a winner lads\r\n") == 0){
           printf("yop the boys\r\n");
           int scount1 = sock.send(userName, sizeof userName);
        }
        
          if (strcmp(rbuffer, "We could be on to a winner lads slightly closer\r\n") == 0){
           printf("yop the boys closer\r\n");
           int scount1 = sock.send(userName2, sizeof userName2);
        }
           
       // printf("%s",rbuffer);
        
        // if(strcmp(rbuffer,"Server Says : Mnald\r\n")==0){
//            printf("it worked ?????\n");
//
//            ClearBuffer();
//          
//            int scount3 = sock.send(userName2, sizeof userName2);
//            printf("sent %d [%.*s]\r\n", scount3, strstr(userName, "\r\n")-userName, userName);
//            printf("%s\n",rbuffer);
//        }
    }
    
    //recieve from java server
   // while( 1) {//waits for something to be in the buffer  (timing issues)
//        int32_t val = sock.recv(rbuffer, sizeof(rbuffer));
//       
//        if(val >0)
//            break;
//    }
//    printf("recv %d [%.*s]\r\n", rcount, strstr(rbuffer, "\r\n")-rbuffer, rbuffer);
//    printf("%s",rbuffer);
//    
//    char buf [] = "Server Says : John\r\n";
//    printf("%s\n",buf);
//     if (strcmp(rbuffer, buf) == 0) 
//        printf("Done name 1\r\n");
//    
//     printf("not name 1\r\n");
    sock.close();
    
    wifi.disconnect();
}

void ClearBuffer(){
    for(int i = 0; i <= rcount; i++)
        rbuffer[i] = '\0';
}

void measureEMG(){ 
   
    finial[0] = '\0';   
     for(int x; x<10;x++){
        
        //printf("the value is here\n\r");
         meas[x] = analog_value.read();
         printf("the value %f \n\r",meas[x]);
        float tempFloat = meas[x];
        snprintf (vals, 7,"%f", tempFloat);
        strcat(finial, vals);
        strcat(finial, " ");
      //  printf("the char val %s\n", vals);
     }
     strcat(finial, "\n");
     printf("the char val %s\n\r\n", finial);
}