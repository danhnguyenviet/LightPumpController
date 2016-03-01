package com.danh.iot;

import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Danh on 2/22/2016.
 */
public class Connection {

    public static String message = "";

    /**
     * Open a connection
     * @param urlStr
     * @param method
     * @return InputStream of the connection
     */
    public static InputStream openHttpConnection(String urlStr, String method) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod(method);
            httpConn.connect();

            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static void sendGETRequest(String urlStr) {

        final String url = urlStr;

        new Thread() {
            public void run() {
                InputStream in = null;

                try {
                    in = openHttpConnection(url, "POST");

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while((line = bufferedReader.readLine()) != null) {
                        message += line;
                    }

                    if (message == "")
                        System.out.println("Test 1 - null");

                    in.close();
                }

                catch (IOException e1) {
                    e1.printStackTrace();
                }
//                messageHandler.sendMessage(msg);
            }
        }.start();

    }

}
