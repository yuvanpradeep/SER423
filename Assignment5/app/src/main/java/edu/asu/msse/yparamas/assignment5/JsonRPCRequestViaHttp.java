/* JsonRPCRequestViaHttp.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: Example Android application that uses an AsyncTask to accomplish the same effect
 * as using a Thread and android.os.Handler
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 23, 2020
 */

package edu.asu.msse.yparamas.assignment5;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;


public class JsonRPCRequestViaHttp {

    private final Map<String, String> headers;
    private URL url;
    private String requestData;
    private Activity parent;

    public JsonRPCRequestViaHttp(URL url, Activity parent) {
        this.url = url;
        this.parent = parent;
        this.headers = new HashMap<String, String>();
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String call(String requestData) throws Exception {
        android.util.Log.d(this.getClass().getSimpleName(),"in call, url: "+url.toString()+" requestData: "+requestData);
        String respData = post(url, headers, requestData);
        return respData;
    }

    private String post(URL url, Map<String, String> headers, String data) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        this.requestData = data;
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.addRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream out = null;
        try {
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception(
                        "Unexpected status from post: " + statusCode);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
        String responseEncoding = connection.getHeaderField("Content-Encoding");
        responseEncoding = (responseEncoding == null ? "" : responseEncoding.trim());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();
        try {
            in = connection.getInputStream();
            if ("gzip".equalsIgnoreCase(responseEncoding)) {
                in = new GZIPInputStream(in);
            }
            in = new BufferedInputStream(in);
            byte[] buff = new byte[1024];
            int n;
            while ((n = in.read(buff)) > 0) {
                bos.write(buff, 0, n);
            }
            bos.flush();
            bos.close();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        android.util.Log.d(this.getClass().getSimpleName(),"json rpc request via http returned string "+bos.toString());
        return bos.toString();
    }
}

