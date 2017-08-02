package com.stevemutungi.votinglocationfareestimator;

import android.support.test.InstrumentationRegistry;

import com.stevemutungi.votinglocationfareestimator.datamodel.maps.GoogleMapLocation;
import com.stevemutungi.votinglocationfareestimator.utils.maps.GoogleMapsUtils;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;


public class GoogleMapIntegrationTests {

    @Test
    public void assetCanGetNairobiLocationDetails() {

        GoogleMapsUtils googleMapsUtils = new GoogleMapsUtils(InstrumentationRegistry.getInstrumentation().getContext());

        try {
            GoogleMapLocation nairobi = googleMapsUtils.getLocationDetails("Nairobi", "AIzaSyBaxFNzeT-EGC02s42HCMmO2jS39RbfBf4");

            assertEquals(nairobi.getLatitude(), -1.2920659 );
            assertEquals(nairobi.getLongitude(),36.8219462);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canGetMombasaLocationDetails() {

        GoogleMapsUtils googleMapsUtils = new GoogleMapsUtils(InstrumentationRegistry.getInstrumentation().getContext());

        try {
            GoogleMapLocation mombasa = googleMapsUtils.getLocationDetails("Mombasa", "AIzaSyBaxFNzeT-EGC02s42HCMmO2jS39RbfBf4");

            assertEquals(mombasa.getLatitude(), -4.0434771);
            assertEquals(mombasa.getLongitude(),39.6682065 );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
