package com.dit.himachal.ecabinet.network;

import android.util.Log;

import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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


}
