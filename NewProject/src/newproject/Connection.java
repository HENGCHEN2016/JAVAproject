package newproject;

public class Connection {

    public common common = new common();
    public String IPaddress;
    public String Subnetmask;
    public String Macaddress;
    public String DefaultGateway;
    public String destinationipaddress;
    public String dgMacaddress;
    public String packet;
    public String destinationaddress;
    public String sourceaddress;
    public String HostName;
    public String data;
    
   
    public String[] Add_IP = new String[100];
    public String[] Add_Mac = new String[100];
    public String[] Smask = new String[100];
    public String[] gatew = new String[100];
    public int size = 0;
    public String[][] ARPTable = new String[100][2];
    public String[] PCArrays[] = new String[200][2];
    public boolean pcInNetwork = false;
    public int arpIndex = 1;
    public arrayspc arrayspc;
    public port pcPort; 
    public port swPort; 
 
    public Connection(String Host) {
        HostName=Host;
        ARPTable[0][0] = "IP Address";
        ARPTable[0][1] = "MAC Address";
    }

    public void createPCPort(String ip, String sm, String ma, String dg){
               
        pcPort = new port(ip, sm, ma, dg);
        
    }

    public void createSWPORT(String ip,String sm,String ma,String dg){
        swPort= new port(ip,sm,ma,dg);
    }
    
    
    public String get_HN() {

        return HostName;
    }

    public void print() {
        System.out.print("MAC Address:     " + Macaddress + "\n"
                        + "IP Address:      " + IPaddress + "\n"
                        + "Subnet Mask:     " + Subnetmask + "\n"
                        + "Default Gateway: " + DefaultGateway + "\n");
       
    }

    public String get_IP() {

        return IPaddress;
    }

    public String get_SubnetMask() {

        return Subnetmask;
    }

    public String get_MAC() {

        return Macaddress;
    }

    public String get_DG() {
        return DefaultGateway;
    }

    public String get_NetworkAdd(String temp1) {

        String PcIpB = common.IpToBinaryStr(temp1);  //to convert binary String
        String PcSmB = common.IpToBinaryStr(this.pcPort.subnetMask);  //PcSmB:the SubnetMask of PC in Binary

        String PcIpB_Array[] = new String[32];
        String[] PcSmB_Array = new String[32];
        String[] AndResult_Array = new String[32];
        String AndResult = "";

        for (int i = 0; i < PcSmB.length(); i++) {

            PcIpB_Array[i] = PcIpB.substring(i, i + 1);
            PcSmB_Array[i] = PcSmB.substring(i, i + 1);

            if ("1".equals(PcIpB_Array[i]) && "1".equals(PcSmB_Array[i])) {
                AndResult_Array[i] = "1";
                AndResult = AndResult + AndResult_Array[i];
            } else {
                AndResult_Array[i] = "0";
                AndResult = AndResult + AndResult_Array[i];
            }
        }

        return AndResult;

    }


    public boolean search_ARPTable(String secondIPAddr) {

        boolean searchReturn = false;
        int i = 1;
        while (!(ARPTable[i][0]==null) && !(ARPTable[i][0].equals(secondIPAddr))) {

            i++;
        }
        if (ARPTable[i][0]==null) {//cannot fing this item in the ARP table
            
            System.out.println("************************");
            //Add to the ARP Table
            
                        
        } else {//find this item in the ARP table
            searchReturn = true;
            System.out.println("+++++++++++++++++++++++++");
        }
        
        return searchReturn;

    }

    public void Add_ARPTable(String AddIP, String AddMAC) {
        
        ARPTable[this.arpIndex][0] = AddIP;
        ARPTable[this.arpIndex][1] = AddMAC;
        
        //New PC Attributes included in ARP Table
        //Need to increment ARP Table Index
        this.incrementArpIndex();
    }
    
    public void incrementArpIndex(){ this.arpIndex++; }

    public void print_ARPTable() {

        System.out.println(ARPTable[0][0] + "\t\t\t" + ARPTable[0][1]);
        for (int k = 1; k < this.arpIndex; k++) {
                //System.out.println(ARPTable[k][j]);
                System.out.println(ARPTable[k][0] + "\t\t\t" + ARPTable[k][1]);
            }
    }
    public void send( String DA, String SA, String Ty,String SorR, String SIP, String DIP, String Data,String HNTemp) {
         String da =DA.replaceAll(":", "");
         String sa =SA.replaceAll(":", "");
         String SendFrame = da +sa + Ty + SorR + SIP + DIP + Data;
        
        if ("01".equals(SorR)) {
           
            System.out.println("\n_________________________Frame is sent from"+HNTemp+"______________________");
            System.out.println("Frame Send(request):" + SendFrame);
            common.Frame = SendFrame;                                                                            
        } else {
            System.out.println("\n_________________________Frame is sent from"+HNTemp+"______________________");
            System.out.println("Frame Send(reply):" + SendFrame);
            common.Frame = SendFrame;
        }
    }
    public void ReceiveFrame(String HNTemp) {
        String ReceiveFrame = common.Frame;
        if ("01".equals(common.fishcode)) {

            System.out.println("\n__________________________Frame is received by"+HNTemp+" ____________________");
            System.out.println("request(receieved):" + ReceiveFrame);
        } else {
            System.out.println("\n__________________________Frame is received by "+HNTemp+" ____________________");
            System.out.println("reply(receieved):" + ReceiveFrame);
        }
    }
    
    public void ping(Connection checkPC) {
        String DesMAC;
        String firstPCHexMACAdd;
        String firstPCIPAddHEX;
        String firstPCNetAddr;
        String secondPCNetAddr;
        firstPCNetAddr = get_NetworkAdd(this.pcPort.ipAddress);
        secondPCNetAddr = get_NetworkAdd(checkPC.pcPort.ipAddress);

        if (firstPCNetAddr.equals(secondPCNetAddr)) {
            //Judge if they are in the same Network 
            System.out.println("destination ipAddress:" + checkPC.pcPort.ipAddress+" is present within the same network!");
            if (search_ARPTable(checkPC.pcPort.ipAddress)) {//same network in ARP table
                int i = 0;
                while (!ARPTable[i][0].equals(checkPC.pcPort.ipAddress)) {

                    i++;
                }
                DesMAC = ARPTable[i][1];
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                firstPCHexMACAdd = common.MACToHEXStr(this.pcPort.macAddress);
                firstPCIPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(checkPC.pcPort.ipAddress);
                //Send IP Frame
                common.Type = "0800";
                common.fishcode = "01";
                System.out.println("*****----display PC1 ARP table----*****");
                System.out.println("IP Address | MAC Address ");
                System.out.println(checkPC.pcPort.ipAddress+"|"+checkPC.pcPort.macAddress);
                System.out.println("*****----display PC2 ARP table----*****");
                System.out.println("IP Address | MAC Address ");
                System.out.println(this.pcPort.ipAddress+"|"+this.pcPort.macAddress); 
               send(DesMacHEX, firstPCHexMACAdd, common.Type, common.fishcode, firstPCIPAddHEX, otherIpHEX, "good", HostName);
                int index1 = arrayspc.search_arrayspc(checkPC.pcPort.ipAddress);
                String normal= "good";
                common.fishcode = "02";
                String MACTemp1echonormal = common.MACToHEXStr(arrayspc.pc[index1].pcPort.macAddress);
                String IpHEXTempechonormal = common.IpToHEXStr(arrayspc.pc[index1].pcPort.ipAddress);
                arrayspc.pc[index1].send(firstPCHexMACAdd, MACTemp1echonormal, common.Type, common.fishcode,
 IpHEXTempechonormal, firstPCIPAddHEX, normal, arrayspc.pc[index1].HostName);
                 ReceiveFrame(HostName);
            } else {// not in the ARP table then send request
                System.out.println("not in the ARP Table");
                DesMAC = "FFFFFFFFFFFF";
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(checkPC.pcPort.ipAddress);
                common.Type = "0806";
                common.fishcode = "01";
                String ARPDataReq = "ARP request";//ARP Request
                //send ARP request  
                send(DesMacHEX, MACAddHEX, common.Type, common.fishcode, IPAddHEX, otherIpHEX, "ARP request", HostName);
                int index1 = arrayspc.search_arrayspc(checkPC.pcPort.ipAddress);
                if (arrayspc.Findpcobjective) {
                    //receiceve the request
                    arrayspc.pc[index1].ReceiveFrame(arrayspc.pc[index1].HostName);
                    common.fishcode = "02";
                    arrayspc.pc[index1].Add_ARPTable(this.pcPort.ipAddress,this.pcPort.macAddress);
                    System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                     String tempNEW = "The destination PC1 exists in the PC2 network.";
                     System.out.println(tempNEW);
                     System.out.println("*****-----Create entry ARP Table-----*****");
                     System.out.println("*****-----Display entry ARP Table-----*****");
                     System.out.println("IP Address | MAC Address ");
                     System.out.println(this.pcPort.ipAddress+"|"+this.pcPort.macAddress);
                    //System.out.println(index+"\n");
                    //receiver sends reply
                    String ARPDataReply = "ARP Reply";
                    String MACTemp1 = common.MACToHEXStr(arrayspc.pc[index1].pcPort.macAddress);
                    String IpHEXTemp = common.IpToHEXStr(arrayspc.pc[index1].pcPort.ipAddress);
                    arrayspc.pc[index1].send(MACAddHEX, MACTemp1, common.Type, common.fishcode,
                            IpHEXTemp, IPAddHEX, ARPDataReply, arrayspc.pc[index1].HostName);
                    //sender receieves the reply
                    ReceiveFrame(HostName);
                    Add_ARPTable(arrayspc.pc[index1].pcPort.ipAddress, arrayspc.pc[index1].pcPort.macAddress);
                    arrayspc.pc[index1].Add_ARPTable(this.pcPort.ipAddress,this.pcPort.macAddress);
                    System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                    String temp2 = "The destination PC2 exists in the PC1 network.";
                    System.out.println(temp2);
                     System.out.println("*****-----Create entry ARP Table-----*****");
                     System.out.println("*****-----Display entry ARP Table-----*****");
                     System.out.println("IP Address | MAC Address ");
                     System.out.println(checkPC.pcPort.ipAddress+"|"+checkPC.pcPort.macAddress);
                    int i = 0;
                    while (!ARPTable[i][0].equals(checkPC.pcPort.ipAddress)) {

                    i++;
                   }
                    DesMAC = ARPTable[i][1];
                   String DesMacHEXecho = common.MACToHEXStr(DesMAC);
                   firstPCHexMACAdd = common.MACToHEXStr(this.pcPort.macAddress);
                   firstPCIPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                   String otherIpHEXecho = common.IpToHEXStr(checkPC.pcPort.ipAddress);
                //Send IP Frame
                   common.Type = "0800";
                   common.fishcode = "01";
                 
            send(DesMacHEXecho, firstPCHexMACAdd, common.Type, common.fishcode, firstPCIPAddHEX, otherIpHEXecho, "echo request", HostName);
                  
                   System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                    String tempecho = "echo  exists.";
                    System.out.println(tempecho);

                    //receiceve the request
                    arrayspc.pc[index1].ReceiveFrame(arrayspc.pc[index1].HostName);
                    common.fishcode = "02";
                    //System.out.println(index+"\n");

                    //receiver sends reply
                    String ARPDataecho = "echo Reply";
                    String MACTemp1echo = common.MACToHEXStr(arrayspc.pc[index1].pcPort.macAddress);
                    String IpHEXTempecho = common.IpToHEXStr(arrayspc.pc[index1].pcPort.ipAddress);
                    arrayspc.pc[index1].send(MACAddHEX, MACTemp1echo, common.Type, common.fishcode,
                            IpHEXTempecho, IPAddHEX, ARPDataecho, arrayspc.pc[index1].HostName);

                    //sender receieves the reply
                    ReceiveFrame(HostName);

                   
                } else {
                    System.out.println("Request timed out");
                    String temp = "The destination pc does not exits in your netork.";
                    System.out.println(temp);
                }
            }

        } 
        else {     //Not in the same Network send to default gateway
             System.out.println("The desdination Network address is not on the same network.");
            if (search_ARPTable(this.pcPort.defaultGateway)) { //In ARP table
                int i = 0;
                while (!ARPTable[i][0].equals(this.pcPort.defaultGateway)) {

                    i++;
                }
                DesMAC = ARPTable[i][1];
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(this.pcPort.defaultGateway);

                //Send IP Frame
                common.Type = "0800";
                common.fishcode = "01";

                send(DesMacHEX, MACAddHEX, common.Type, common.fishcode, IPAddHEX, otherIpHEX, "Cao!", HostName);

            } else {//not in the ARP table
                DesMAC = "FFFFFFFFFFFF";
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(this.pcPort.defaultGateway);
                common.Type = "0806";
                common.fishcode = "01";
                String ARPDataReq = "ARP request";//ARP Request
                //send ARP request
                 System.out.println("PC1 will sends a request to its default Gateway");    
                 System.out.println("PC1 Default Gateway is:" + this.pcPort.defaultGateway);
                send(DesMacHEX, MACAddHEX, common.Type, common.fishcode, IPAddHEX, otherIpHEX, ARPDataReq, HostName);
                 System.out.println("No reply !");
            }
        }
    }
    public void pingtosw(Connection checkSW) {
        String DesMAC;
        String firstPCHexMACAdd;
        String firstPCIPAddHEX;
        String firstPCNetAddr;
        String SwitchNetAddr;
        firstPCNetAddr = get_NetworkAdd(this.pcPort.ipAddress);
        SwitchNetAddr = get_NetworkAdd(checkSW.swPort.ipAddress);

        if (firstPCNetAddr.equals(SwitchNetAddr)) {
            //Judge if they are in the same Network 
            System.out.println("destination ipAddress:" + checkSW.swPort.ipAddress+" is present within the same network!");
            if (search_ARPTable(checkSW.swPort.ipAddress)) {//same network in ARP table
                int i = 0;
                while (!ARPTable[i][0].equals(checkSW.swPort.ipAddress)) {

                    i++;
                }
                DesMAC = ARPTable[i][1];
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                firstPCHexMACAdd = common.MACToHEXStr(this.pcPort.macAddress);
                firstPCIPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(checkSW.swPort.ipAddress);
                //Send IP Frame
                common.Type = "0800";
                common.fishcode = "01";
                System.out.println("*****----display PC1 ARP table----*****");
                System.out.println("IP Address | MAC Address ");
                System.out.println(checkSW.swPort.ipAddress+"|"+checkSW.swPort.macAddress);
                System.out.println("*****----display PC2 ARP table----*****");
                System.out.println("IP Address | MAC Address ");
                System.out.println(this.pcPort.ipAddress+"|"+this.pcPort.macAddress); 
               send(DesMacHEX, firstPCHexMACAdd, common.Type, common.fishcode, firstPCIPAddHEX, otherIpHEX, "good", HostName);
                int index1 = arrayswitch.search_arrayswitch(checkSW.swPort.ipAddress);
                String normal= "good";
                common.fishcode = "02";
                String MACTemp1echonormal = common.MACToHEXStr(arrayswitch.sw[index1].swPort.macAddress);
                String IpHEXTempechonormal = common.IpToHEXStr(arrayswitch.sw[index1].swPort.ipAddress);
                arrayswitch.sw[index1].send(firstPCHexMACAdd, MACTemp1echonormal, common.Type, common.fishcode,
 IpHEXTempechonormal, firstPCIPAddHEX, normal, arrayswitch.sw[index1].HostName);
                 ReceiveFrame(HostName);
            } else {// not in the ARP table then send request
                System.out.println("not in the ARP Table");
                DesMAC = "FFFFFFFFFFFF";
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(checkSW.swPort.ipAddress);
                common.Type = "0806";
                common.fishcode = "01";
                String ARPDataReq = "ARP request";//ARP Request
                //send ARP request  
                send(DesMacHEX, MACAddHEX, common.Type, common.fishcode, IPAddHEX, otherIpHEX, "ARP request", HostName);
                int index1 = arrayswitch.search_arrayswitch(checkSW.swPort.ipAddress);
                if (arrayswitch.Findpcobjective) {
                    //receiceve the request
                    arrayswitch.sw[index1].ReceiveFrame(arrayswitch.sw[index1].HostName);
                    common.fishcode = "02";
                    arrayswitch.sw[index1].Add_ARPTable(this.pcPort.ipAddress,this.pcPort.macAddress);
                    System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                     String tempNEW = "The destination PC1 exists in the PC2 network.";
                     System.out.println(tempNEW);
                     System.out.println("*****-----Create entry ARP Table-----*****");
                     System.out.println("*****-----Display entry ARP Table-----*****");
                     System.out.println("IP Address | MAC Address ");
                     System.out.println(this.pcPort.ipAddress+"|"+this.pcPort.macAddress);
                    //System.out.println(index+"\n");
                    //receiver sends reply
                    String ARPDataReply = "ARP Reply";
                    String MACTemp1 = common.MACToHEXStr(arrayswitch.sw[index1].swPort.macAddress);
                    String IpHEXTemp = common.IpToHEXStr(arrayswitch.sw[index1].swPort.ipAddress);
                    arrayswitch.sw[index1].send(MACAddHEX, MACTemp1, common.Type, common.fishcode,
                            IpHEXTemp, IPAddHEX, ARPDataReply, arrayswitch.sw[index1].HostName);
                    //sender receieves the reply
                    ReceiveFrame(HostName);
                    Add_ARPTable(arrayswitch.sw[index1].swPort.ipAddress, arrayswitch.sw[index1].swPort.macAddress);
                    arrayswitch.sw[index1].Add_ARPTable(this.pcPort.ipAddress,this.pcPort.macAddress);
                    System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                    String temp2 = "The destination PC2 exists in the PC1 network.";
                    System.out.println(temp2);
                     System.out.println("*****-----Create entry ARP Table-----*****");
                     System.out.println("*****-----Display entry ARP Table-----*****");
                     System.out.println("IP Address | MAC Address ");
                     System.out.println(checkSW.swPort.ipAddress+"|"+checkSW.swPort.macAddress);
                    int i = 0;
                    while (!ARPTable[i][0].equals(checkSW.swPort.ipAddress)) {

                    i++;
                   }
                    DesMAC = ARPTable[i][1];
                   String DesMacHEXecho = common.MACToHEXStr(DesMAC);
                   firstPCHexMACAdd = common.MACToHEXStr(this.pcPort.macAddress);
                   firstPCIPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                   String otherIpHEXecho = common.IpToHEXStr(checkSW.swPort.ipAddress);
                //Send IP Frame
                   common.Type = "0800";
                   common.fishcode = "01";
                 
            send(DesMacHEXecho, firstPCHexMACAdd, common.Type, common.fishcode, firstPCIPAddHEX, otherIpHEXecho, "echo request", HostName);
                  
                   System.out.println("Find pc objects   =   " + arrayspc.Findpcobjective);
                    String tempecho = "echo  exists.";
                    System.out.println(tempecho);

                    //receiceve the request
                    arrayspc.pc[index1].ReceiveFrame(arrayspc.pc[index1].HostName);
                    common.fishcode = "02";
                    //System.out.println(index+"\n");

                    //receiver sends reply
                    String ARPDataecho = "echo Reply";
                    String MACTemp1echo = common.MACToHEXStr(arrayspc.pc[index1].pcPort.macAddress);
                    String IpHEXTempecho = common.IpToHEXStr(arrayspc.pc[index1].pcPort.ipAddress);
                    arrayspc.pc[index1].send(MACAddHEX, MACTemp1echo, common.Type, common.fishcode,
                            IpHEXTempecho, IPAddHEX, ARPDataecho, arrayspc.pc[index1].HostName);

                    //sender receieves the reply
                    ReceiveFrame(HostName);

                   
                } else {
                    System.out.println("Request timed out");
                    String temp = "The destination pc does not exits in your netork.";
                    System.out.println(temp);
                }
            }

        } 
        else {     //Not in the same Network send to default gateway
             System.out.println("The desdination Network address is not on the same network.");
            if (search_ARPTable(this.pcPort.defaultGateway)) { //In ARP table
                int i = 0;
                while (!ARPTable[i][0].equals(this.pcPort.defaultGateway)) {

                    i++;
                }
                DesMAC = ARPTable[i][1];
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(this.pcPort.defaultGateway);

                //Send IP Frame
                common.Type = "0800";
                common.fishcode = "01";

                send(DesMacHEX, MACAddHEX, common.Type, common.fishcode, IPAddHEX, otherIpHEX, "Cao!", HostName);

            } else {//not in the ARP table
                DesMAC = "FFFFFFFFFFFF";
                String DesMacHEX = common.MACToHEXStr(DesMAC);
                String MACAddHEX = common.MACToHEXStr(this.pcPort.macAddress);
                String IPAddHEX = common.IpToHEXStr(this.pcPort.ipAddress);
                String otherIpHEX = common.IpToHEXStr(this.pcPort.defaultGateway);
                common.Type = "0806";
                common.fishcode = "01";
                String ARPDataReq = "ARP request";//ARP Request
                //send ARP request
                 System.out.println("PC1 will sends a request to its default Gateway");    
                 System.out.println("PC1 Default Gateway is:" + this.pcPort.defaultGateway);
                send(DesMacHEX,MACAddHEX, common.Type, common.fishcode, IPAddHEX,otherIpHEX, ARPDataReq, HostName);
                 System.out.println("No reply !");
            }
        }
    }
  
}
