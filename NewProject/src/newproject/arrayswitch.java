/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newproject;

/**
 *
 * @author user
 */
public class arrayswitch {
    public static  Connection[] sw=new Connection[100];
    public static int swobjectiveIndex=0;
    public static boolean Findpcobjective=false;
    public String [][]swassemble=new String[100][4];
     public arrayswitch (){
     for(int i=0;i<sw.length;i++){
         sw[i]=null;
     }
    swassemble[0][0]="IP"; 
    swassemble[0][1]="SubnetMask";   
    swassemble[0][2]="MAC";
    swassemble[0][3]="HostName";  
    }
    public static void ADD_objective(Connection pcobjective){
        sw[swobjectiveIndex]=pcobjective;
        swobjectiveIndex++;
    }
    public void PrintpcobjectTable(){

          System.out.println(swassemble[0][0]+"\t\t"+swassemble[0][1]+"\t\t\t\t"+swassemble[0][2]+"\t\t\t\t"+swassemble[0][3]);
              for(int h=0;h<sw.length;h++){
              if(!(sw[h]==null)){
                    
                    swassemble[h+1][0]=sw[h].IPaddress;
                    swassemble[h+1][1]=sw[h].Subnetmask;
                    swassemble[h+1][2]=sw[h].Macaddress;
                    swassemble[h+1][3]=sw[h].HostName;
                    System.out.println(swassemble[h+1][0]+"\t\t\t"+swassemble[h+1][1]+"\t\t\t"+swassemble[h+1][2]+"\t\t"+swassemble[h+1][3]);
              }
          }
      }
      public static int  search_arrayswitch(String PingedIP){
       int h=0;
       while(!(sw[h]==null)&&!(sw[h].swPort.ipAddress.equals(PingedIP))){
           h++;
       }      
       if(sw[h]==null){
           Findpcobjective=false;
           return -1;
       }
       else{
           Findpcobjective =true;
           return h;
        }
    } 
}

