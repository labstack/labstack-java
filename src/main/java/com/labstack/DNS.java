package com.labstack;

import com.squareup.moshi.Json;

import java.util.List;

public class DNS {
    public static class LookupRequest {
        private String domain;
        private String type;

        public LookupRequest setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public LookupRequest setType(String type) {
            this.type = type;
            return this;
        }
    }

    public static class LookupResponse {
        private List<Record> records;
    }

    public static class Record {
        private String type;
        private String name;
        // Values - start
        private String a;
        private String aaaa;
        private String cname;
        private String mx;
        private String ns;
        private String ptr;
        private int serial;
        private int refresh;
        private int retry;
        private int expire;
        private int priority;
        private int weight;
        private int port;
        private String target;
        private List<String> txt;
        // Values - end
        private int ttl;
        @Json(name = "class")
        private String clazz;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getA() {
            return a;
        }

        public String getAaaa() {
            return aaaa;
        }

        public String getCname() {
            return cname;
        }

        public String getMx() {
            return mx;
        }

        public String getNs() {
            return ns;
        }

        public String getPtr() {
            return ptr;
        }

        public int getSerial() {
            return serial;
        }

        public int getRefresh() {
            return refresh;
        }

        public int getRetry() {
            return retry;
        }

        public int getExpire() {
            return expire;
        }

        public int getPriority() {
            return priority;
        }

        public int getWeight() {
            return weight;
        }

        public int getPort() {
            return port;
        }

        public String getTarget() {
            return target;
        }

        public List<String> getTxt() {
            return txt;
        }

        public int getTtl() {
            return ttl;
        }

        public String getClazz() {
            return clazz;
        }
    }
}
