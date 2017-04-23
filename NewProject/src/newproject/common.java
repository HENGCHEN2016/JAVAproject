/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newproject;

/**
 *
 * @author Jack
 */
public class common {
    public static String Frame="";
    public static String FrameEcho="";
    public static String FrameProtocal="";
    public static String fishcode;
    public static String Type;
    public static String BPDU;
     public static String IpToBinaryStr(String strIpAdd){ 
        long[] Ip = new long[4];
        int point1 = strIpAdd.indexOf(".");
        int point2 = strIpAdd.indexOf(".", point1 + 1);
        int point3 = strIpAdd.indexOf(".", point2 + 1);
        Ip[0] = Long.parseLong(strIpAdd.substring(0, point1));
        Ip[1] = Long.parseLong(strIpAdd.substring(point1+1, point2));
        Ip[2] = Long.parseLong(strIpAdd.substring(point2+1, point3));
        Ip[3] = Long.parseLong(strIpAdd.substring(point3+1));
        long IpD=(Ip[0]<<24)+(Ip[1]<<16)+(Ip[2]<<8)+Ip[3];
        String IpB = Long.toBinaryString(IpD);
        return IpB; 
        } 
    public static String MACToHEXStr(String strMAC){
        return strMAC.replaceAll(":","");   
    }
    public static String IpToHEXStr(String strIpAdd){
        long[] Ip = new long[4];
        int point1 = strIpAdd.indexOf(".");  
        int point2 = strIpAdd.indexOf(".", point1 + 1);
        int point3 = strIpAdd.indexOf(".", point2 + 1);
        Ip[0] = Long.parseLong(strIpAdd.substring(0, point1));
        Ip[1] = Long.parseLong(strIpAdd.substring(point1+1, point2));
        Ip[2] = Long.parseLong(strIpAdd.substring(point2+1, point3));
        Ip[3] = Long.parseLong(strIpAdd.substring(point3+1));
        long IpD=(Ip[0]<<24)+(Ip[1]<<16)+(Ip[2]<<8)+Ip[3];
        String IpB = Long.toHexString(IpD);
        return IpB;
        }  
    public static String switchIDtoHexSrt(String strId){
        long[] Id = new long[3];
        int point1 = strId.indexOf(".");
        int point2 = strId.indexOf(".", point1+1);
        int point3 = strId.indexOf(".", point2+1);
        Id[0]=Long.parseLong(strId.substring(0, point1));
        Id[1]=Long.parseLong(strId.substring(point1+1, point2));
        Id[2]=Long.parseLong(strId.substring(point2+1, point3));
        long Idx=(Id[0]<<16)+(Id[1]<<8)+Id[2];
        String Idy = Long.toHexString(Idx);
        return Idy;
    } 
}
