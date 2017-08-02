package com.stevemutungi.votinglocationfareestimator.net;


import android.content.Context;
import android.content.Intent;

import com.stevemutungi.votinglocationfareestimator.app.intents.AppIntents;

public class GoogleMapsHttpExecutor extends WebServicesProvider {

    /**
     * Web services provide class that provides apis
     * for sending requests to the server and has call
     * backs to handle errors and provide information that is incoming from the server
     *
     * @param context
     * @param url
     * @param query
     */
    public GoogleMapsHttpExecutor(Context context, String url, String query) {
        super(context, url, query);
    }

    /**
     * Called when the url is set
     *
     * @return the set url
     */
    @Override
    public String onSetURL() {
        return null;
    }

    /**
     * Called when the server communication starts
     */
    @Override
    protected void onStartCommunication() {
        sendIntent(new Intent(AppIntents.ACTION_GETTING_LOCATION));
    }

    /**
     * Called when a successful connection has been created to the server
     *
     * @param statusCode
     */
    @Override
    public void onConnect(int statusCode) {

    }

    /**
     * Called when the query to send to the server has been set
     *
     * @return the query that had been set
     */
    @Override
    public String onSetQuery() {
        return null;
    }

    /**
     * Called when the set query has been sent to the server
     *
     * @return the sent query which is now appended to the url
     */
    @Override
    public String onSendQuery() {
        return null;
    }

    /**
     * Called when a response has been received from the server
     */
    @Override
    public void onReceiveResponse() {

    }

    /**
     * Called when the server response has started to be read the received response
     *
     * @param readResponse
     */
    @Override
    public void onReadResponse(String readResponse) {

    }

    /**
     * Called when the current read response has been appended to the previously read response
     *
     * @param appendedResponse
     */
    @Override
    public void onAppendResponse(String appendedResponse) {

    }

    /**
     * Called when the reader has finished reading the response sent from the server
     *
     * @param readResponse
     */
    @Override
    public void onFinishedReadingResponse(String readResponse) {

    }

    /**
     * Called when an error has occurred making a HTTP_CONNECTION
     *
     * @param errorCode
     */
    @Override
    public void onConnectionError(int errorCode) {

    }

    /**
     * WebServicesProvider#onHTTPResultsFailed
     * <p>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    @Override
    public void onHttpResultsFailed(String resultText, String client, String clientAction, String clientIntent, String build) {

    }

    /**
     * WebServicesProvider#onHTTPResultsSuccessful
     * <p>
     * Called when a the http results are successful
     *
     * @param resultText
     * @param client
     * @param clientAction
     * @param clientIntent
     * @param build
     */
    @Override
    public void onHttpResultsSuccessful(String resultText, String client, String clientAction, String clientIntent, String build) {

    }

    /**
     * Called when the server communication ends
     */
    @Override
    protected void onEndCommunication() {
        sendIntent(new Intent(AppIntents.ACTION_GOT_LOCATION));
    }
}
