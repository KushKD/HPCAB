package com.dit.himachal.ecabinet.network;

import android.util.Log;

import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.PostDataPojo;
import com.dit.himachal.ecabinet.modal.PostObject;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;

import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public class HttpManager {

    public ResponsObject GetData(GetDataPojo data) throws IOException {
        BufferedReader reader = null;
        URL url_ = null;
        ResponsObject response = null;
        HttpURLConnection con = null;

        try {
            url_ = new URL(CommonUtils.createUrl(data));
            Log.e("url", url_.toString());
            con = (HttpURLConnection) url_.openConnection();
            con.connect();

            if (con.getResponseCode() != 200) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                con.disconnect();
                response = Econstants.getResponseObject(Econstants.failure, sb.toString(), con.getResponseCode(),data.getDepartmentId());

                return response;
            } else {


                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                con.disconnect();
                Log.e("DAta", sb.toString());
                response = Econstants.getResponseObject(Econstants.success, sb.toString(), con.getResponseCode(),data.getDepartmentId());
                return response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = Econstants.getResponseObject(Econstants.failure, e.getLocalizedMessage().toString(), con.getResponseCode(),data.getDepartmentId());
            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    response = Econstants.getResponseObject(Econstants.failure, e.getLocalizedMessage().toString(), con.getResponseCode(), data.getDepartmentId());
                    return response;
                }
            }
        }
    }

    public ResponsObject PostDataScanQRCode(PostDataPojo data) {

        URL url_ = null;
        HttpURLConnection conn_ = null;
        StringBuilder sb = null;
        JSONStringer userJson = null;

        String URL = null;
        ResponsObject response = null;


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
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getErrorStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsObject();
                    response = Econstants.getResponseObject(Econstants.failure, sb.toString(), conn_.getResponseCode(),data.getParameters().getDeptId());
                    return response;


                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn_.getInputStream(), "utf-8"));
                    String line = null;
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println(sb.toString());
                    Log.e("Data from Service", sb.toString());
                    response = new ResponsObject();
                    response = Econstants.getResponseObject(Econstants.success, sb.toString(), conn_.getResponseCode(),data.getParameters().getDeptId());

                }

            } catch (Exception e) {
                response = new ResponsObject();
                response = Econstants.getResponseObject(Econstants.failure, sb.toString(), conn_.getResponseCode(),data.getParameters().getDeptId());
                return response;
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
