package com.stevemutungi.votinglocationfareestimator.utils.fare;


import android.content.Context;

import com.stevemutungi.votinglocationfareestimator.datamodel.maps.uber.fare.UberFareEstimates;
import com.stevemutungi.votinglocationfareestimator.exceptions.UberFareEstimateNotFoundException;
import com.stevemutungi.votinglocationfareestimator.net.UberFareEstimateHttpExecutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

public class UberUtils {

    private String serverToken;

    private Context context;

    public UberUtils(Context context, String serverToken) {
        this.context = context;
        this.serverToken = serverToken;
    }

    public Context getContext() {
        return context;
    }

    public String getServerToken() {
        return serverToken;
    }

    public UberFareEstimates getFareEstimate(double fromLat, double fromLong, double toLat, double toLong) throws IOException, JSONException {
        String url = String.format(Locale.getDefault(),
                "https://api.uber.com/v1.2/estimates/price?start_latitude=%f&start_longitude=%f&end_latitude=%f&end_longitude=%f",
                fromLat, fromLong, toLat, toLong);


        String headers = "Authorization: Token " + getServerToken() +
                "Accept-Language: en_US" +
                "Content-Type: application/json";

        String jsonResponse = new UberFareEstimateHttpExecutor(getContext(), url, headers).doPostHttpRequest();
        JSONObject serverResponse = new JSONObject(jsonResponse);
        JSONArray prices = serverResponse.getJSONArray("prices");
        if (prices.length() > 0) {
            JSONObject price = prices.getJSONObject(0);
            String localized_display_name = price.getString("localized_display_name");
            double distance = price.getDouble("distance");
            String display_name = price.getString("display_name");
            String product_id = price.getString("product_id");
            String high_estimate = price.getString("high_estimate");
            String low_estimate = price.getString("low_estimate");
            String duration = price.getString("duration");
            String estimate = price.getString("estimate");
            String currency_code = price.getString("currency_code");
            return new UberFareEstimates(distance, low_estimate, high_estimate, duration, estimate, currency_code);
        } else {
            throw new UberFareEstimateNotFoundException("Could not get the fare estimate for your location");
        }

    }
}
