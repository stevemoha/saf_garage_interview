package com.stevemutungi.votinglocationfareestimator.lib.maps;

public class GoogleMapLocation {

    private String address;
    private double longitude;
    private double latitude;

    public GoogleMapLocation(String address, double longitude, double latitude) {
        setAddress(address);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
