package com.labstack;

import java.util.List;
import java.util.Map;

public class Geocode {
    public static class AddressRequest {
        private String location;
        private double longitude;
        private double latitude;
        private String osmTag;
        private int limit;

        public AddressRequest setLocation(String location) {
            this.location = location;
            return this;
        }

        public AddressRequest setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public AddressRequest setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public AddressRequest setOsmTag(String osmTag) {
            this.osmTag = osmTag;
            return this;
        }

        public AddressRequest setLimit(int limit) {
            this.limit = limit;
            return this;
        }
    }

    public static class IPRequest {
        private String ip;

        public IPRequest setIp(String ip) {
            this.ip = ip;
            return this;
        }
    }

    public static class ReverseRequest {
        private double longitude;
        private double latitude;
        private int limit;

        public ReverseRequest setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public ReverseRequest setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
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
}

