package com.stevemutungi.votinglocationfareestimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stevemutungi.votinglocationfareestimator.lib.exceptions.LocationNotFoundException;
import com.stevemutungi.votinglocationfareestimator.lib.libs.fare.UberFareEstimates;
import com.stevemutungi.votinglocationfareestimator.lib.libs.fare.UberUtils;
import com.stevemutungi.votinglocationfareestimator.lib.libs.maps.google.GoogleMapsUtils;
import com.stevemutungi.votinglocationfareestimator.lib.maps.GoogleMapLocation;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public GoogleMapLocation getGoogleMapLocationDetails(String locationName) {
        try {

            return new GoogleMapsUtils().getLocationDetails(locationName, "AIzaSyBaxFNzeT-EGC02s42HCMmO2jS39RbfBf4");

        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            System.err.println(String.format("LocationNotFoundException - %s", e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(String.format("IOException - %s", e.getMessage()));
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println(String.format("JSONException - %s", e.getMessage()));
        }
        return null;
    }

    public UberFareEstimates getUberFareEstimates(GoogleMapLocation fromLocation, GoogleMapLocation toLocation) throws IOException, JSONException {
        double fromLat = fromLocation.getLatitude();
        double fromLong = fromLocation.getLongitude();
        double toLat = toLocation.getLatitude();
        double toLong = toLocation.getLongitude();
        return new UberUtils("sadBp3notgJiDIQRd8HnzB8Bb7kEo3Pzksu_hYbz").getFareEstimate(fromLat, fromLong, toLat, toLong);
    }

}
