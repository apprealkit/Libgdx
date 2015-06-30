/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testserverkits;

import com.apprealkit.*;
import java.util.HashMap;

/**
 *
 * @author ApprealKit
 */
public class TestAppRealKit {
    // API information
    private static  boolean bDebug = true;    
    private static  String sHost = "192.168.10.102";
    private static int iPort = 7777;
    private static String sAppID = "080693ab-7123-4aee-b560-9f6a8a332a43";
    private static String sAppsecret = "VORAI5D6yz";
    //Test data
    private static String loginID = "1234";
    private static String memberID = "5678";
    private static String roomID = "";

    private static void testAppRealKit() {
        // Setup the balancer
        Balancer.getInstance().setVerbose(bDebug);
        Balancer.getInstance().setProductID(sAppID, sAppsecret);

        //Add KitNode to handle the notification messages
        Balancer.getInstance().setKitNode(new KitNode() {
            @Override
            public void onKitOpen(String arg0, int arg1) {
                System.out.println("onKitOpen:" + arg0);

                //Test authenticate
                Balancer.getInstance().authenticate(loginID, new KitEvent() {
                    @Override
                    public void onMessage(String arg0, int arg1) {
                        System.out.println("authenticate response:" + arg0);
                    }
                });

                //Test create room
                HashMap<String, String> properties = new HashMap<String, String>();
                properties.put("name", "ROOM_TEST_ANDROID");
                int iCapacity = 0;
                Balancer.getInstance().createRoom(memberID, properties, iCapacity, new KitEvent() {
                    @Override
                    public void onMessage(String arg0, int arg1) {
                        System.out.println("createRoom response:" + arg0);
                    }
                });
            }

            @Override
            public void onKitSent(String arg0, int arg1) {
                System.out.println("onKitSent:" + arg0);
            }

            @Override
            public void onKitMessage(String arg0, int arg1) {
                System.out.println("onKitMessage:" + arg0);
            }

            @Override
            public void onKitClose(String arg0, int arg1) {
                System.out.println("onKitClose:" + arg0);
            }
        });

        //Connect to API server
        Balancer.getInstance().connect(sHost, iPort);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testAppRealKit();
    }
}
