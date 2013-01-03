package me.almalkawi.ListViewDemo;

public class City {
    private final String mName;
    private final String mImage;
    private final String mUrl;

    public City(String name, String image, String url) {
        mName = name;
        mImage = image;
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }

    public String getUrl() {
        return mUrl;
    }
}
