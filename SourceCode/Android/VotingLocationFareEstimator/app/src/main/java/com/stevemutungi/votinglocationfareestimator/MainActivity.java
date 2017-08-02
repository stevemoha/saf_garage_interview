package com.stevemutungi.votinglocationfareestimator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.stevemutungi.votinglocationfareestimator.lib.exceptions.LocationNotFoundException;
import com.stevemutungi.votinglocationfareestimator.lib.libs.fare.UberFareEstimates;
import com.stevemutungi.votinglocationfareestimator.lib.libs.fare.UberUtils;
import com.stevemutungi.votinglocationfareestimator.lib.libs.maps.google.GoogleMapsUtils;
import com.stevemutungi.votinglocationfareestimator.lib.maps.GoogleMapLocation;

import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText mAEtFromLocation;
    private AppCompatEditText mAEtToLocation;
    private AppCompatEditText mABtCalculateEstimate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        mAEtFromLocation = (AppCompatEditText) findViewById(R.id.from_location);
        mAEtToLocation = (AppCompatEditText) findViewById(R.id.to_location);
        mABtCalculateEstimate = (AppCompatEditText) findViewById(R.id.estimate_fare);

        mABtCalculateEstimate.setOnClickListener(estimateFareClickListener);

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

    View.OnClickListener estimateFareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String toLocation = mAEtToLocation.getText().toString();
            String fromLocaton = mAEtFromLocation.getText().toString();

            if (isValidAddress(toLocation, fromLocaton)) {
                Snackbar.make(view, "Getting fare estimate", Snackbar.LENGTH_LONG).show();
            }
        }
    };

    private boolean isValidAddress(String toLocation, String fromLocation) {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        int errorCount = 0;


        if (fromLocation.equals("")) {
            isValid = false;
            errorMessage.append(String.format(Locale.getDefault(), "%d. Missing origin\n", ++errorCount));
        }
        if (toLocation.equals("")) {
            isValid = false;
            errorMessage.append(String.format(Locale.getDefault(), "%d. Missing destination\n", ++errorCount));
        }
        if (toLocation.equals(fromLocation)) {
            isValid = false;
            errorMessage.append(String.format(Locale.getDefault(), "%d. Cyclic voyage!\n", ++errorCount));
        }

        if (!isValid) {
            showAlertDialog(MainActivity.this, "Error", errorMessage.toString(), "Resolve", null, null, null);
        }
        return isValid;
    }

    /**
     * Show an alert dialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtonLabel
     * @param positiveIntent
     * @param negativeButtonLabel
     * @param negativeIntent
     */
    public void showAlertDialog(Context context, String title, String message, String positiveButtonLabel,
                                Intent positiveIntent, String negativeButtonLabel, Intent negativeIntent) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        if (positiveButtonLabel != null) {
            alert.setPositiveButton(positiveButtonLabel, ((dialogInterface, i) -> {
                if (positiveIntent != null) {
                    sendBroadcast(positiveIntent);
                }
            }));
        }

        if (negativeButtonLabel != null) {
            alert.setNegativeButton(negativeButtonLabel, ((dialogInterface, i) -> {
                if (negativeIntent != null) {
                    sendBroadcast(negativeIntent);
                }
            }));
        }

        alert.show();
    }
}
