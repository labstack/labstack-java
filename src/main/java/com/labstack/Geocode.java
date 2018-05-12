package com.labstack;

import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.io.IOException;
import java.util.List;

import static com.labstack.Client.API_URL;
import static com.labstack.Client.MOSHI;

@SuppressWarnings("Duplicates")
public class Geocode {
    private Client client;
    private JsonAdapter<Response> responseJsonAdapter = MOSHI.adapter(Response.class);

    public Geocode(Client client) {
        this.client = client;
    }

    public static class AddressOptions {
        private double latitude;
        private double longitude;
        private String osmTag;
        private String format;
        private int limit;

        public AddressOptions setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public AddressOptions setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public AddressOptions setOsmTag(String osmTag) {
            this.osmTag = osmTag;
            return this;
        }

        public AddressOptions setFormat(String format) {
            this.format = format;
            return this;
        }

        public AddressOptions setLimit(int limit) {
            this.limit = limit;
            return this;
        }
    }

    public static class ReverseOptions {
        private String format;
        private int limit;

        public ReverseOptions setFormat(String format) {
            this.format = format;
            return this;
        }

        public ReverseOptions setLimit(int limit) {
            this.limit = limit;
            return this;
        }
    }

    public static class IPOptions {
        private String format;

        public IPOptions setFormat(String format) {
            this.format = format;
            return this;
        }
    }

    public static class Geometry {
        private String type;
        private List<Double> coordinates;

        public String getType() {
            return type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }
    }

    public static class Feature {
        private String type;
        private Geometry geometry;
        private Properties properties;

        public String getType() {
            return type;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Properties getProperties() {
            return properties;
        }
    }


    public static class Response {
        private String type;
        private List<Feature> features;

        public String getType() {
            return type;
        }

        public List<Feature> getFeatures() {
            return features;
        }
    }

    public Response address(String location) {
        return address(location, new AddressOptions());
    }

    public Response address(String location, AddressOptions options) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/address").newBuilder();
        httpBuider.addQueryParameter("location", location);
        httpBuider.addQueryParameter("latitude", String.valueOf(options.latitude));
        httpBuider.addQueryParameter("longitude", String.valueOf(options.longitude));
        httpBuider.addQueryParameter("osm_tag", options.osmTag);
        httpBuider.addQueryParameter("format", options.format);
        httpBuider.addQueryParameter("limit", String.valueOf(options.limit));
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            okhttp3.Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return responseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Response ip(String ip) {
        return ip(ip, new IPOptions());
    }

    public Response ip(String ip, IPOptions options) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/ip").newBuilder();
        httpBuider.addQueryParameter("ip", ip);
        httpBuider.addQueryParameter("format", options.format);
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            okhttp3.Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return responseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Response reverse(double latitude, double longitude) {
       return reverse(latitude, longitude, new ReverseOptions());
    }

    public Response reverse(double latitude, double longitude, ReverseOptions options) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/address").newBuilder();
        httpBuider.addQueryParameter("latitude", String.valueOf(latitude));
        httpBuider.addQueryParameter("longitude", String.valueOf(longitude));
        httpBuider.addQueryParameter("format", options.format);
        httpBuider.addQueryParameter("limit", String.valueOf(options.limit));
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            okhttp3.Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return responseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

}

