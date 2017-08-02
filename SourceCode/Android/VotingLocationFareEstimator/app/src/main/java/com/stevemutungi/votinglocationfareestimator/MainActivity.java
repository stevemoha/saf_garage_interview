package com.stevemutungi.votinglocationfareestimator;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.stevemutungi.votinglocationfareestimator.app.intents.AppIntents;
import com.stevemutungi.votinglocationfareestimator.datamodel.maps.GoogleMapLocation;
import com.stevemutungi.votinglocationfareestimator.datamodel.maps.uber.fare.UberFareEstimates;
import com.stevemutungi.votinglocationfareestimator.exceptions.LocationNotFoundException;
import com.stevemutungi.votinglocationfareestimator.utils.fare.UberUtils;
import com.stevemutungi.votinglocationfareestimator.utils.maps.GoogleMapsUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String GOOGLE_MAP_KEY = "AIzaSyBaxFNzeT-EGC02s42HCMmO2jS39RbfBf4";

    private ProgressDialog mProgress;

    private AppCompatEditText mAEtFromLocation;
    private AppCompatEditText mAEtToLocation;
    private AppCompatButton mABtCalculateEstimate;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppIntents.ACTION_GETTING_LOCATION)) {
                showProgressDialog("Getting location", "Checking existence of your location", false, false);
            }
            if (intent.getAction().equals(AppIntents.ACTION_GOT_LOCATION)) {
                tryCloseProgressDialog();

            }
            if (intent.getAction().equals(AppIntents.ACTION_LOCATION_NOT_FOUND)) {
                tryCloseProgressDialog();
                showAlertDialog(MainActivity.this, "Error", intent.getStringExtra("message"), null, null, null, null);
            }
            if (intent.getAction().equals(AppIntents.ACTION_HTTP_ERROR)) {
                tryCloseProgressDialog();
                showAlertDialog(MainActivity.this, "Error", intent.getStringExtra("message"), null, null, null, null);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        registerReceiver(receiver, new IntentFilter(AppIntents.ACTION_GETTING_LOCATION));
        registerReceiver(receiver, new IntentFilter(AppIntents.ACTION_GOT_LOCATION));
        registerReceiver(receiver, new IntentFilter(AppIntents.ACTION_LOCATION_NOT_FOUND));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initViews() {

        mAEtFromLocation = (AppCompatEditText) findViewById(R.id.from_location);
        mAEtToLocation = (AppCompatEditText) findViewById(R.id.to_location);
        mABtCalculateEstimate = (AppCompatButton) findViewById(R.id.estimate_fare);

        mABtCalculateEstimate.setOnClickListener(estimateFareClickListener);

    }

    public GoogleMapLocation getGoogleMapLocationDetails(String locationName) {
        try {
            return new GoogleMapsUtils(MainActivity.this).getLocationDetails(locationName, GOOGLE_MAP_KEY);

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
        return new UberUtils(MainActivity.this, "sadBp3notgJiDIQRd8HnzB8Bb7kEo3Pzksu_hYbz").getFareEstimate(fromLat, fromLong, toLat, toLong);
    }

    View.OnClickListener estimateFareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String toLocation = mAEtToLocation.getText().toString();
            String fromLocaton = mAEtFromLocation.getText().toString();

            if (isValidAddress(toLocation, fromLocaton)) {

                //Multi-Thread!
                ExecutorService executors = Executors.newFixedThreadPool(2);
                executors.submit( ()-> getGoogleMapLocationDetails(toLocation));
                executors.submit( ()-> getGoogleMapLocationDetails(fromLocaton));
                executors.shutdown();

            } else {
                Snackbar.make(view, "Resolve error to get fare estimate", Snackbar.LENGTH_LONG).show();
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


    public ProgressDialog getProgressDialog() {
        if (mProgress == null) {
            mProgress = new ProgressDialog(MainActivity.this);
        }
        return mProgress;
    }

    /**
     * Show a progress dialog
     *
     * @param title
     * @param message
     * @param cancellable
     * @param cancellableOnTouchOutside
     */

    public void showProgressDialog(String title, String message, boolean cancellable,
                                   boolean cancellableOnTouchOutside) {

        if (getProgressDialog().isShowing()) {
            getProgressDialog().dismiss();
        }

        getProgressDialog().setTitle(title);
        getProgressDialog().setMessage(message);

        getProgressDialog().setCancelable(cancellable);
        getProgressDialog().setCanceledOnTouchOutside(cancellableOnTouchOutside);

        getProgressDialog().show();
    }

    /**
     * Try to close the progress dialog associated with the app main activity
     */

    public void tryCloseProgressDialog() {
        if (getProgressDialog().isShowing()) {
            getProgressDialog().dismiss();
        }
    }
}
