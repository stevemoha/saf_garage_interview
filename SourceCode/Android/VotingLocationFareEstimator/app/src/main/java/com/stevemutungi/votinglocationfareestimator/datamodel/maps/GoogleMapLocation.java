package com.stevemutungi.votinglocationfareestimator.datamodel.maps;

public class GoogleMapLocation {

    private String address;
    private double longitude;
    private double latitude;

    public GoogleMapLocation(String address, double latitude ,double longitude) {
        setAddress(address);
        setLongitude(longitude);
        setLatitude(latitude);
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
