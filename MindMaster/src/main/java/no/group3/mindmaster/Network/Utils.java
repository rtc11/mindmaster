package no.group3.mindmaster.Network;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

import java.util.HashMap;

/**
 * Created by tordly on 10.03.14.
 */
public class Utils {

    private Context ctxt;

    //WIFI
    private DhcpInfo d;
    private WifiManager wifii;

    //TODO: bruk singleton på dette objektet
    public Utils(Context ctxt){
        this.ctxt = ctxt;
    }

    /**
     * Extracts all network information and puts it in a HashMap.
     *
     * @return A HashMap with all network information.
     * To extract info: HashMap.get(Connection.SUBNET_MASK) or Connection.IP_ADDRESS etc...
     */
    public HashMap<String, String> getNetworkInfo() {
        HashMap<String, String> map = new HashMap<String, String>();

        wifii = (WifiManager) ctxt.getSystemService(ctxt.WIFI_SERVICE);
        d = wifii.getDhcpInfo();

        String DNS1 = intToIp(d.dns1);
        String DNS2 = intToIp(d.dns2);
        String DEFAULT_GATEWAY = intToIp(d.gateway);
        String IP_ADDRESS = intToIp(d.ipAddress);
        String SUBNET_MASK = intToIp(d.netmask);
        String SERVER_IP = intToIp(d.serverAddress);

        map.put(Connection.DNS1, DNS1);
        map.put(Connection.DNS2, DNS2);
        map.put(Connection.DEFAULT_GATEWAY, DEFAULT_GATEWAY);
        map.put(Connection.IP_ADDRESSS, IP_ADDRESS);
        map.put(Connection.SUBNET_MASK, SUBNET_MASK);
        map.put(Connection.SERVER_IP, SERVER_IP);

        String TO_STRING = "Network Info"
                + "\nDNS 1: " + DNS1
                + "\nDNS 2: " + DNS2
                + "\nDefault Gateway: " + DEFAULT_GATEWAY
                + "\nIP Address: " + IP_ADDRESS
                + "\nSubnet Mask: " + SUBNET_MASK
                + "\nServer IP: " + SERVER_IP;

        map.put(Connection.TO_STRING, TO_STRING);

        return map;
    }

    private String intToIp(int addr) {
        return ((addr & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF));
    }
}
