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

    public static class AddressRequest {
        private String location;
        private double longitude;
        private double latitude;
        private String osmTag;
        private int limit;

        public String getLocation() {
            return location;
        }

        public AddressRequest setLocation(String location) {
            this.location = location;
            return this;
        }

        public double getLongitude() {
            return longitude;
        }

        public AddressRequest setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public double getLatitude() {
            return latitude;
        }

        public AddressRequest setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public String getOsmTag() {
            return osmTag;
        }

        public AddressRequest setOsmTag(String osmTag) {
            this.osmTag = osmTag;
            return this;
        }

        public int getLimit() {
            return limit;
        }

        public AddressRequest setLimit(int limit) {
            this.limit = limit;
            return this;
        }
    }

    public static class IPRequest {
        private String ip;

        public String getIp() {
            return ip;
        }

        public IPRequest setIp(String ip) {
            this.ip = ip;
            return this;
        }
    }

    public static class ReverseRequest {
        private double longitude;
        private double latitude;
        private int limit;

        public double getLongitude() {
            return longitude;
        }

        public ReverseRequest setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public double getLatitude() {
            return latitude;
        }

        public ReverseRequest setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public int getLimit() {
            return limit;
        }

        public ReverseRequest setLimit(int limit) {
            this.limit = limit;
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

    public Response address(AddressRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/address").newBuilder();
        httpBuider.addQueryParameter("location", request.getLocation());
        httpBuider.addQueryParameter("longitude", String.valueOf(request.getLongitude()));
        httpBuider.addQueryParameter("latitude", String.valueOf(request.getLatitude()));
        httpBuider.addQueryParameter("osm_tag", request.getOsmTag());
        httpBuider.addQueryParameter("limit", String.valueOf(request.getLimit()));
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

    public Response ip(IPRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/ip").newBuilder();
        httpBuider.addQueryParameter("ip", request.getIp());
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

    public Response reverse(ReverseRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/geocode/address").newBuilder();
        httpBuider.addQueryParameter("longitude", String.valueOf(request.getLongitude()));
        httpBuider.addQueryParameter("latitude", String.valueOf(request.getLatitude()));
        httpBuider.addQueryParameter("limit", String.valueOf(request.getLimit()));
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

