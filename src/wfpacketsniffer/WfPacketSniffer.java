/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wfpacketsniffer;

import com.google.common.collect.HashBiMap;
import intro.MainClass;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WfPacketSniffer {

    public static Map<Integer, ArrayList<String>> myfilenames;

    public static void main(String[] args) {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation
        myfilenames = new HashMap();
        Map<Integer, Webpage> myMap = setUpWebpages();
        for (int i = 0; i < 5; i++) {
            for (Integer id : myMap.keySet()) {
                Webpage wb = myMap.get(id);

                try {
                    String name = generateFileName(i + "__" + id, wb) + ".pcap";
                    myfilenames.get(id).add(name);
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.command("/bin/bash", "-c",
                            "/usr/sbin/tcpdump -i lo port ssh -w "
                            + name);
                    Process process = pb.start();
                    WebDriver driver = new FirefoxDriver();

                    // And now use this to visit Google
                    driver.get(wb.getName());

                    driver.quit();
                    Thread.sleep(1000);
                    pb.command("/bin/bash", "-c",
                            "/usr/sbin/killall tcpdump");
                    Process process1 = pb.start();

                } catch (Exception ex) {

                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        ///call spark
        try {
             MainClass.process(null);

            Thread.sleep(20000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        myfilenames.clear();

    }

    public static Map<Integer, Webpage> setUpWebpages() {

        Map<Integer, Webpage> myMap = new HashMap<Integer, Webpage>();

        ArrayList<Webpage> myList = new ArrayList<Webpage>();
        myList.add(new Webpage("http://www.google.com"));
        myList.add(new Webpage("http://www.facebook.com"));
        myList.add(new Webpage("http://www.yahoo.com"));
        myList.add(new Webpage("http://www.youtube.com"));

        int count = 0;
        for (Webpage w : myList) {
            myMap.put(count, w);
            ArrayList<String> list = new ArrayList();
            myfilenames.put(count, list);
            count++;
        }
        return myMap;

    }

    public static String generateFileName(String webid, Webpage wp) {
        System.out.println(wp.getName());
        return wp.getName().substring(7) + webid + new SimpleDateFormat("MMdd.HHmm").format(Calendar.getInstance().getTime());
    }
}
