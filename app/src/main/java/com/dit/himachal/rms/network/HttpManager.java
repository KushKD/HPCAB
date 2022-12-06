package com.dit.himachal.rms.network;

import android.util.Log;

import com.dit.himachal.rms.modal.GetDataPojo;
import com.dit.himachal.rms.modal.OfflineDataModel;
import com.dit.himachal.rms.modal.PostDataPojo;
import com.dit.himachal.rms.utilities.CommonUtils;
import com.dit.himachal.rms.utilities.Econstants;
import com.dit.himachal.rms.utilities.Preferences;

import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class HttpManager {

    private SSLSocketFactory getSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            TrustManager[] tm = {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    public OfflineDataModel GetData(GetDataPojo data) throws IOException {
        BufferedReader reader = null;
        URL url_ = null;
        OfflineDataModel response = null;
        HttpURLConnection con = null;


        try {
            url_ = new URL(CommonUtils.createUrl(data));
            Log.e("url", url_.toString());
            con = (HttpURLConnection) url_.openConnection();

            if (data.getUrl().startsWith("https")) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) con;
                httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
            } else {
                con = (HttpURLConnection) url_.openConnection();

            }

            con.connect();

            if (con.getResponseCode() != 200) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                con.disconnect();
                response = new OfflineDataModel();
                response.setUrl(url_.toString());
                if(data.getParameters()!=null){
                    response.setParams(data.getParameters().toString());
                }else{
                    response.setParams("");
                }

                response.setResponse("Something went wrong. Please re-connect to Internet and try again.");
                response.setHttpFlag(Econstants.failure);
                response.setFunctionName(data.getTaskType().toString());
                response.setUserId(Preferences.getInstance().user_id);
                response.setRoleId(Preferences.getInstance().role_id);
                response.setBifurcation(data.getBifurcation());
                return response;
            } else {


                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                con.disconnect();
                Log.e("Data", sb.toString());
                response = new OfflineDataModel();
                response.setUrl(url_.toString());
                if(data.getParameters()!=null){
                    response.setParams(data.getParameters().toString());
                }else{
                    response.setParams("");
                }
                response.setResponse(sb.toString());
                response.setHttpFlag(Econstants.success);
                response.setFunctionName(data.getTaskType().toString());
                response.setUserId(Preferences.getInstance().user_id);
                response.setRoleId(Preferences.getInstance().role_id);
                response.setBifurcation(data.getBifurcation());
               return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new OfflineDataModel();
            response.setUrl(url_.toString());
            if(data.getParameters() != null){
                response.setParams(data.getParameters().toString());
            }else{
                response.setParams("");
            }
            response.setResponse("Something went wrong. Please re-connect to Internet and try again.");
            response.setHttpFlag(Econstants.failure);
            response.setFunctionName(data.getTaskType().toString());
            response.setUserId(Preferences.getInstance().user_id);
            response.setRoleId(Preferences.getInstance().role_id);
            response.setBifurcation(data.getBifurcation());
            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    response = new OfflineDataModel();
                    response.setUrl(url_.toString());
                    if(data.getParameters()!=null){
                        response.setParams(data.getParameters().toString());
                    }else{
                        response.setParams("");
                    }
                    response.setResponse("Something went wrong. Please re-connect to Internet and try again.");
                    response.setHttpFlag(Econstants.failure);
                    response.setFunctionName(data.getTaskType().toString());
                    response.setUserId(Preferences.getInstance().user_id);
                    response.setRoleId(Preferences.getInstance().role_id);
                    response.setBifurcation(data.getBifurcation());
                    return response;
                }
            }
        }
    }

    public OfflineDataModel PostDataScanQRCode(PostDataPojo data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONStringer userJson = null;

        String URL = null;
        OfflineDataModel response = null;


        try {

            URL = data.getUrl()+"/"+data.getMethord();


            url_ = new URL(URL);
            conn_ = (HttpURLConnection) url_.openConnection();
            conn_.setDoOutput(true);
            conn_.setRequestMethod("POST");
            conn_.setUseCaches(false);
            conn_.setConnectTimeout(10000);
            conn_.setReadTimeout(10000);
            conn_.setRequestProperty("Content-Type", "application/json");
            conn_.connect();

            System.out.println(data.getParameters().toJSON());
            OutputStreamWriter out = new OutputStreamWriter(conn_.getOutputStream());
            out.write(data.getParameters().toJSON());
            out.close();

            try {
                int HttpResult = conn_.getResponseCode();
                if (HttpResult != HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), StandardCharsets.UTF_8));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new OfflineDataModel();
                    response.setUrl(url_.toString());
                    response.setParams(data.getParameters().toString());
                    response.setResponse("Something went wrong. Please re-connect to Internet and try again.");
                    response.setHttpFlag(Econstants.failure);
                    response.setFunctionName(data.getTaskType().toString());
                    response.setUserId(Preferences.getInstance().user_id);
                    response.setRoleId(Preferences.getInstance().role_id);
                    response.setBifurcation("bifercation");


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), StandardCharsets.UTF_8));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    response = new OfflineDataModel();
                    Log.e("Data from Service", sb.toString());
                    response.setUrl(url_.toString());
                    response.setParams(data.getParameters().toString());
                    response.setResponse(sb.toString().trim());
                    response.setHttpFlag(Econstants.success);
                    response.setFunctionName(data.getTaskType().toString());
                    response.setUserId(Preferences.getInstance().user_id);
                    response.setRoleId(Preferences.getInstance().role_id);
                    response.setBifurcation("bifercation");
                }

            } catch (Exception e) {
                Log.e("Data from Service", sb.toString());
                response = new OfflineDataModel();
                response.setUrl(url_.toString());
                response.setParams(data.getParameters().toString());
                response.setResponse("Something went wrong. Please re-connect to Internet and try again.");
                response.setHttpFlag(Econstants.failure);
                response.setFunctionName(data.getTaskType().toString());
                response.setUserId(Preferences.getInstance().user_id);
                response.setRoleId(Preferences.getInstance().role_id);
                response.setBifurcation("bifercation");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn_ != null)
                conn_.disconnect();
        }
        return response;
    }


}
