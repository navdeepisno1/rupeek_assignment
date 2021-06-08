package com.suvidha.rupeekhiring;

public class POI {
    private int id;
    private String name;
    private String imagePath;
    private String latitude;
    private String longitude;
    private String address;

    public POI()
    {

    }

    public POI(int id, String name, String imagePath, String latitude, String longitude, String address) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
