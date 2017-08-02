package com.stevemutungi.votinglocationfareestimator.lib.libs.fare;

public class UberFareEstimates {

    private double distance;
    private String lowEstimate;
    private String highEstimate;
    private String duration;
    private String estimate;
    private String currencyCode;

    public UberFareEstimates(double distance, String lowEstimate, String highEstimate, String duration, String estimate, String currencyCode) {
        setDistance(distance);
        setLowEstimate(lowEstimate);
        setHighEstimate(highEstimate);
        setDuration(duration);
        setEstimate(estimate);
        setCurrencyCode(currencyCode);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLowEstimate() {
        return lowEstimate;
    }

    public void setLowEstimate(String lowEstimate) {
        this.lowEstimate = lowEstimate;
    }

    public String getHighEstimate() {
        return highEstimate;
    }

    public void setHighEstimate(String highEstimate) {
        this.highEstimate = highEstimate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
