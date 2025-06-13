package com.example.demo.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ValidatorUtil {
    public static final String[] PROTOCOL_BLACK_LIST = new String[]{"file", "dict", "sftp", "ldap", "gopher", "telnet", "mailto", "news"};
    public static final String[] INTERNAL_DOMAINS = new String[]{"localhost", "127.0.0.1", "0.0.0.0", "10.0.0.0/8", "172.16.0.0/12", "192.168.0.0/16"};
    public static final String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final String MAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$";
    public static final String PHONE_REGEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public ValidatorUtil() {
    }

    public static boolean isIPValid(String ip) {
        String regex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(regex);
    }

    public static boolean isEmailValid(String email) {
        return StringUtils.isBlank(email) ? false : email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$");
    }

    public boolean isPhoneValid(String phoneNumber) {
        return StringUtils.isBlank(phoneNumber) ? false : phoneNumber.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");
    }

    public static boolean checkSSRF(String url) {
        return checkSSRF(url, (List)null);
    }

    public static boolean checkSSRF(String url, List<String> domainWhiteList) {
        String[] urlSplit = url.split("://");
        if (urlSplit.length < 2) {
            return false;
        } else {
            String protocol = urlSplit[0];
            String domain = urlSplit[1].split("/")[0];
            if (Arrays.asList(PROTOCOL_BLACK_LIST).contains(protocol)) {
                return false;
            } else if (domainWhiteList != null && domainWhiteList.contains(domain)) {
                return true;
            } else {
                return !isInternalDomain(domain);
            }
        }
    }

    private static boolean isInternalDomain(String domain) {
        String[] var1 = INTERNAL_DOMAINS;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String internalDomain = var1[var3];
            if (internalDomain.contains("/")) {
                String[] parts = internalDomain.split("/");
                String ip = parts[0];
                int mask = Integer.parseInt(parts[1]);

                try {
                    if (isIPInRange(domain, ip, maskIntToString(mask))) {
                        return true;
                    }
                } catch (Exception var9) {
                    return false;
                }
            } else if (domain.equals(internalDomain)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isIPInRange(String ipAddress, String subnetAddress, String subnetMask) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        InetAddress subnet = InetAddress.getByName(subnetAddress);
        InetAddress mask = InetAddress.getByName(subnetMask);
        byte[] inetAddressBytes = inetAddress.getAddress();
        byte[] subnetBytes = subnet.getAddress();
        byte[] maskBytes = mask.getAddress();

        for(int i = 0; i < inetAddressBytes.length; ++i) {
            int addressByte = inetAddressBytes[i] & 255;
            int subnetByte = subnetBytes[i] & 255;
            int maskByte = maskBytes[i] & 255;
            if ((addressByte & maskByte) != (subnetByte & maskByte)) {
                return false;
            }
        }

        return true;
    }

    private static String maskIntToString(int mask) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 4; ++i) {
            int octet = mask >> i * 8 & 255;
            sb.append(octet);
            if (i < 3) {
                sb.append(".");
            }
        }

        return sb.toString();
    }
}
