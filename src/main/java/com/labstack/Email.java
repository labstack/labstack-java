package com.labstack;

public class Email {
    public static class VerifyRequest {
        private String email;

        public VerifyRequest setEmail(String email) {
            this.email = email;
            return this;
        }
    }

    public static class VerifyResponse extends Response {
        private Boolean syntax;
        private Boolean disposable;
        private Boolean domain;
        private Boolean mailbox;
        private String error;

        public Boolean getSyntax() {
            return syntax;
        }

        public Boolean getDisposable() {
            return disposable;
        }

        public Boolean getDomain() {
            return domain;
        }

        public Boolean getMailbox() {
            return mailbox;
        }

        public String getError() {
            return error;
        }
    }
}
