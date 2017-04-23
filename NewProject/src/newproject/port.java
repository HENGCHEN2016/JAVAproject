///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
package newproject;


public class port{
    
    public String ipAddress;
    public String macAddress;
    public String subnetMask;
    public String defaultGateway;
    public String[][] ARPTable = new String[100][2];
    
    public port(String ip, String sm, String ma, String dg){
        
        ipAddress = ip;
        macAddress = ma;
        subnetMask = sm;
        defaultGateway = dg;
        ARPTable[0][0] = "IP Address";
        ARPTable[0][1] = "MAC Address"; 
    } 
}