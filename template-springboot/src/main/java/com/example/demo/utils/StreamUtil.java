package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import com.example.demo.utils.JsonUtil;

import javax.annotation.Resource;


public class StreamUtil {
    private static final Logger log = LoggerFactory.getLogger(StreamUtil.class);
    private static final String DEFAULT_UNAUTHORIZED_MSG = "Unauthorized";
    private static final String DEFAULT_SERVERERROR_MSG = "Internal server error";
    private static final int BUFFER_SIZE = 4096;

    static volatile JsonUtil jsonUtil;

    static {
        jsonUtil = new JsonUtil();
    }

    public static void writeUnauthorized(String errorMsg, Writer writer) throws IOException {
        doWrite(writer, jsonUtil.toJsonString(new Unauthorized(errorMsg)));
    }

    public static void writeServerError(String errorMsg, Writer writer) throws IOException {
        doWrite(writer, jsonUtil.toJsonString(new ServerError(errorMsg)));
    }

    public static void write(String body, Writer writer) throws IOException {
        doWrite(writer, body);
    }

    public static void writeTokens(Writer writer, String accessToken, long accessTokenTimeoutSeconds, String refreshToken, long refreshTokenTimeoutSeconds) throws IOException {
        JwtTokens jwtTokens = new JwtTokens();
        jwtTokens.setAccessToken(accessToken);
        jwtTokens.setAccessExpire(accessTokenTimeoutSeconds);
        if (StringUtils.hasLength(refreshToken)) {
            jwtTokens.setRefreshToken(refreshToken);
            jwtTokens.setRefreshExpire(refreshTokenTimeoutSeconds);
        }

        write(jsonUtil.toJsonString(jwtTokens), writer);
    }

    public static String readCode(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            String body = doReadBody(in);
            Oauth2LoginEntity loginEntity = (Oauth2LoginEntity)jsonUtil.toBean(body, Oauth2LoginEntity.class);
            return loginEntity.getCode();
        }
    }

    public static Oauth2LoginEntity readOauth2LoginEntity(InputStream in) throws IOException {
        if (in == null) {
            return null;
        } else {
            String body = doReadBody(in);
            Oauth2LoginEntity loginEntity = (Oauth2LoginEntity)jsonUtil.toBean(body, Oauth2LoginEntity.class);
            return loginEntity;
        }
    }

    public static String readRedirectUrl(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            String body = doReadBody(in);
            Oauth2LoginEntity loginEntity = (Oauth2LoginEntity)jsonUtil.toBean(body, Oauth2LoginEntity.class);
            return loginEntity.getRedirectUrl();
        }
    }

    private static String doReadBody(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder(4096);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        char[] buffer = new char[4096];

        int charsRead;
        while((charsRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, charsRead);
        }

        String body = out.toString();
        return body;
    }

    public static String readRefreshToken(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            String body = doReadBody(in);
            JwtTokens tokens = (JwtTokens)jsonUtil.toBean(body, JwtTokens.class);
            return tokens.getRefreshToken();
        }
    }

    public static String readAccessToken(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            String body = doReadBody(in);
            JwtTokens tokens = (JwtTokens)jsonUtil.toBean(body, JwtTokens.class);
            return tokens.getAccessToken();
        }
    }

    private static void doWrite(Writer writer, String msg) throws IOException {
        writer.write(msg, 0, msg.length());
        writer.flush();
        writer.close();
    }

    public static void setJsonUtil(JsonUtil jsonUtil) {
        StreamUtil.jsonUtil = jsonUtil;
    }

    static class JwtTokens {
        private String accessToken;
        private long accessExpire;
        private String refreshToken;
        private long refreshExpire;

        public JwtTokens() {
        }

        public String getAccessToken() {
            return this.accessToken;
        }

        public long getAccessExpire() {
            return this.accessExpire;
        }

        public String getRefreshToken() {
            return this.refreshToken;
        }

        public long getRefreshExpire() {
            return this.refreshExpire;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setAccessExpire(long accessExpire) {
            this.accessExpire = accessExpire;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public void setRefreshExpire(long refreshExpire) {
            this.refreshExpire = refreshExpire;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof JwtTokens)) {
                return false;
            } else {
                JwtTokens other = (JwtTokens)o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getAccessExpire() != other.getAccessExpire()) {
                    return false;
                } else if (this.getRefreshExpire() != other.getRefreshExpire()) {
                    return false;
                } else {
                    label40: {
                        Object this$accessToken = this.getAccessToken();
                        Object other$accessToken = other.getAccessToken();
                        if (this$accessToken == null) {
                            if (other$accessToken == null) {
                                break label40;
                            }
                        } else if (this$accessToken.equals(other$accessToken)) {
                            break label40;
                        }

                        return false;
                    }

                    Object this$refreshToken = this.getRefreshToken();
                    Object other$refreshToken = other.getRefreshToken();
                    if (this$refreshToken == null) {
                        if (other$refreshToken != null) {
                            return false;
                        }
                    } else if (!this$refreshToken.equals(other$refreshToken)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof JwtTokens;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            long $accessExpire = this.getAccessExpire();
            result = result * 59 + (int)($accessExpire >>> 32 ^ $accessExpire);
            long $refreshExpire = this.getRefreshExpire();
            result = result * 59 + (int)($refreshExpire >>> 32 ^ $refreshExpire);
            Object $accessToken = this.getAccessToken();
            result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
            Object $refreshToken = this.getRefreshToken();
            result = result * 59 + ($refreshToken == null ? 43 : $refreshToken.hashCode());
            return result;
        }

        public String toString() {
            return "StreamUtil.JwtTokens(accessToken=" + this.getAccessToken() + ", accessExpire=" + this.getAccessExpire() + ", refreshToken=" + this.getRefreshToken() + ", refreshExpire=" + this.getRefreshExpire() + ")";
        }
    }

    public static class Oauth2LoginEntity {
        private String code;
        private String state;
        private String redirectUrl;

        public Oauth2LoginEntity() {
        }

        public String getCode() {
            return this.code;
        }

        public String getState() {
            return this.state;
        }

        public String getRedirectUrl() {
            return this.redirectUrl;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Oauth2LoginEntity)) {
                return false;
            } else {
                Oauth2LoginEntity other = (Oauth2LoginEntity)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    label47: {
                        Object this$code = this.getCode();
                        Object other$code = other.getCode();
                        if (this$code == null) {
                            if (other$code == null) {
                                break label47;
                            }
                        } else if (this$code.equals(other$code)) {
                            break label47;
                        }

                        return false;
                    }

                    Object this$state = this.getState();
                    Object other$state = other.getState();
                    if (this$state == null) {
                        if (other$state != null) {
                            return false;
                        }
                    } else if (!this$state.equals(other$state)) {
                        return false;
                    }

                    Object this$redirectUrl = this.getRedirectUrl();
                    Object other$redirectUrl = other.getRedirectUrl();
                    if (this$redirectUrl == null) {
                        if (other$redirectUrl != null) {
                            return false;
                        }
                    } else if (!this$redirectUrl.equals(other$redirectUrl)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof Oauth2LoginEntity;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            Object $code = this.getCode();
            result = result * 59 + ($code == null ? 43 : $code.hashCode());
            Object $state = this.getState();
            result = result * 59 + ($state == null ? 43 : $state.hashCode());
            Object $redirectUrl = this.getRedirectUrl();
            result = result * 59 + ($redirectUrl == null ? 43 : $redirectUrl.hashCode());
            return result;
        }

        public String toString() {
            return "StreamUtil.Oauth2LoginEntity(code=" + this.getCode() + ", state=" + this.getState() + ", redirectUrl=" + this.getRedirectUrl() + ")";
        }
    }

    static class ServerError {
        private long timestamp = System.currentTimeMillis();
        private int status;
        private String error;

        public ServerError() {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            this.error = "Internal server error";
        }

        public ServerError(String error) {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            this.error = "Internal server error";
            this.error = error;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public int getStatus() {
            return this.status;
        }

        public String getError() {
            return this.error;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setError(String error) {
            this.error = error;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof ServerError)) {
                return false;
            } else {
                ServerError other = (ServerError)o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getTimestamp() != other.getTimestamp()) {
                    return false;
                } else if (this.getStatus() != other.getStatus()) {
                    return false;
                } else {
                    Object this$error = this.getError();
                    Object other$error = other.getError();
                    if (this$error == null) {
                        if (other$error == null) {
                            return true;
                        }
                    } else if (this$error.equals(other$error)) {
                        return true;
                    }

                    return false;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof ServerError;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            long $timestamp = this.getTimestamp();
            result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
            result = result * 59 + this.getStatus();
            Object $error = this.getError();
            result = result * 59 + ($error == null ? 43 : $error.hashCode());
            return result;
        }

        public String toString() {
            return "StreamUtil.ServerError(timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", error=" + this.getError() + ")";
        }
    }

    static class Unauthorized {
        private long timestamp = System.currentTimeMillis();
        private int status;
        private String error;

        public Unauthorized() {
            this.status = HttpStatus.UNAUTHORIZED.value();
            this.error = "Unauthorized";
        }

        public Unauthorized(String error) {
            this.status = HttpStatus.UNAUTHORIZED.value();
            this.error = "Unauthorized";
            this.error = error;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public int getStatus() {
            return this.status;
        }

        public String getError() {
            return this.error;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setError(String error) {
            this.error = error;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Unauthorized)) {
                return false;
            } else {
                Unauthorized other = (Unauthorized)o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getTimestamp() != other.getTimestamp()) {
                    return false;
                } else if (this.getStatus() != other.getStatus()) {
                    return false;
                } else {
                    Object this$error = this.getError();
                    Object other$error = other.getError();
                    if (this$error == null) {
                        if (other$error == null) {
                            return true;
                        }
                    } else if (this$error.equals(other$error)) {
                        return true;
                    }

                    return false;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof Unauthorized;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            long $timestamp = this.getTimestamp();
            result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
            result = result * 59 + this.getStatus();
            Object $error = this.getError();
            result = result * 59 + ($error == null ? 43 : $error.hashCode());
            return result;
        }

        public String toString() {
            return "StreamUtil.Unauthorized(timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", error=" + this.getError() + ")";
        }
    }
}
