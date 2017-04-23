/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newproject;

/**
 *
 * @author Jack
 */
public class arrayspc {
    public static  Connection[] pc=new Connection[100];
    public static int pcobjectiveIndex=0;
    public static boolean Findpcobjective=false;
    public String [][]pcassemble=new String[100][4];
     public arrayspc (){
     for(int i=0;i<pc.length;i++){
         pc[i]=null;
     }
    pcassemble[0][0]="IP"; 
    pcassemble[0][1]="SubnetMask";   
    pcassemble[0][2]="MAC";
    pcassemble[0][3]="HostName";  
    }
    public static void ADD_objective(Connection pcobjective){
        pc[pcobjectiveIndex]=pcobjective;
        pcobjectiveIndex++;
    }
    public void PrintpcobjectTable(){

          System.out.println(pcassemble[0][0]+"\t\t"+pcassemble[0][1]+"\t\t\t\t"+pcassemble[0][2]+"\t\t\t\t"+pcassemble[0][3]);
              for(int h=0;h<pc.length;h++){
              if(!(pc[h]==null)){
                    
                    pcassemble[h+1][0]=pc[h].IPaddress;
                    pcassemble[h+1][1]=pc[h].Subnetmask;
                    pcassemble[h+1][2]=pc[h].Macaddress;
                    pcassemble[h+1][3]=pc[h].HostName;
                    System.out.println(pcassemble[h+1][0]+"\t\t\t"+pcassemble[h+1][1]+"\t\t\t"+pcassemble[h+1][2]+"\t\t"+pcassemble[h+1][3]);
              }
          }
      }
      public static int  search_arrayspc(String PingedIP){
       int h=0;
       while(!(pc[h]==null)&&!(pc[h].pcPort.ipAddress.equals(PingedIP))){
           h++;
       }      
       if(pc[h]==null){
           Findpcobjective=false;
           return -1;
       }
       else{
           Findpcobjective =true;
           return h;
        }
    } 
}
