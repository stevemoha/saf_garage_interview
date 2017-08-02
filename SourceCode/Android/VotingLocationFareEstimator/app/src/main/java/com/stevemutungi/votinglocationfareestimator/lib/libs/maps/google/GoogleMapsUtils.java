package com.stevemutungi.votinglocationfareestimator.lib.libs.maps.google;


import com.stevemutungi.votinglocationfareestimator.lib.exceptions.LocationNotFoundException;
import com.stevemutungi.votinglocationfareestimator.lib.libs.net.GoogleMapsHttpExecutor;
import com.stevemutungi.votinglocationfareestimator.lib.libs.net.URLBuilder;
import com.stevemutungi.votinglocationfareestimator.lib.maps.GoogleMapLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GoogleMapsUtils {

    public static final String STATUS_OK = "OK";
    public static final String ZERO_RESULTS = "ZERO_RESULTS";

    /**
     * Returns a GoogleMapLocation
     *
     * @param placeName       name of the place
     * @param googleMapAPIKey your google map api key
     * @return
     * @throws LocationNotFoundException
     * @throws IOException
     */
    public GoogleMapLocation getLocationDetails(String placeName, String googleMapAPIKey)
            throws LocationNotFoundException, IOException, JSONException {
        String url = "https://maps.googleapis.com/maps/api/geocode/json";
        URLBuilder urlBuilder = new URLBuilder(url);
        String builtGetUrl = urlBuilder.append("address", placeName).append("key", googleMapAPIKey).buildUpon();


        String rawJSON = new GoogleMapsHttpExecutor(builtGetUrl, "").doGetHttpRequest();
        JSONObject responseJSON = new JSONObject(rawJSON);

        String status = responseJSON.getString("status");

        if (status.equalsIgnoreCase(STATUS_OK)) {
            JSONArray results = responseJSON.getJSONArray("results");

            if (results.length() > 0) {
                JSONObject locationDetails = (JSONObject) results.get(0);
                String formattedAddress = (String) locationDetails.get("formatted_address");
                JSONObject geometry = (JSONObject) locationDetails.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                Double latitude = (Double) location.get("lat");
                Double longitude = (Double) location.get("lng");
                return new GoogleMapLocation(formattedAddress, latitude, longitude);
            } else {
                throw new LocationNotFoundException(String.format("Cannot retrieve your location details %s", placeName));
            }
        } else {
            if (status.equalsIgnoreCase(ZERO_RESULTS)) {
                throw new LocationNotFoundException(String.format("Cannot find location %s", placeName));
            } else throw new IllegalStateException("Cannot handle status : " + status);
        }
    }
}
