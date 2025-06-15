package com.example.demo.utils;


import org.owasp.encoder.Encode;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.codecs.MySQLCodec.Mode;
import org.owasp.esapi.reference.DefaultEncoder;

public class EncodeUtil {
    public static final UnixCodec UNIX_CODEC = new UnixCodec();
    public static final WindowsCodec WINDOWS_CODEC = new WindowsCodec();
    public static final MySQLCodec MYSQL_CODEC;
    public static final OracleCodec ORACLE_CODEC;

    public EncodeUtil() {
    }

    public enum OSType {
        WINDOWS("windows"),
        UNIX("unix");

        private final String osType;

        private OSType(String osType) {
            this.osType = osType;
        }

        public String osType() {
            return this.osType;
        }

        public static OSType match(String dbType) {
            if (dbType != null) {
                OSType[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    OSType item = var1[var3];
                    if (item.osType().equalsIgnoreCase(dbType)) {
                        return item;
                    }
                }
            }

            return UNIX;
        }
    }


    public static String encodeHtml(String input) {
        return Encode.forHtml(input);
    }

    public static String encodeHtmlAttribute(String input) {
        return Encode.forHtmlAttribute(input);
    }

    public static String encodeJavaScript(String input) {
        return Encode.forJavaScript(input);
    }

    public static String encodeCss(String input) {
        return Encode.forCssString(input);
    }

    public static String encodeUrl(String input) {
        return Encode.forUriComponent(input);
    }

    public static String encodeSqlForMySQL(String input) {
        Codec codec = MYSQL_CODEC;
        return DefaultEncoder.getInstance().encodeForSQL(codec, input);
    }

    public static String encodeSqlForOracle(String input) {
        Codec codec = ORACLE_CODEC;
        return DefaultEncoder.getInstance().encodeForSQL(codec, input);
    }

    public static String encodeForSQL(String input, Codec codec) {
        return DefaultEncoder.getInstance().encodeForSQL(codec, input);
    }

    public static String encodeXPath(String input) {
        return DefaultEncoder.getInstance().encodeForXPath(input);
    }

    public static String encodeXml(String input) {
        return Encode.forXml(input);
    }

    public static String encodeLdap(String input) {
        return DefaultEncoder.getInstance().encodeForLDAP(input);
    }

    public static String encodeOS(String input, OSType osType) {
        Codec<Character> codec = UNIX_CODEC;
        if (osType == OSType.WINDOWS) {
            codec = WINDOWS_CODEC;
        }

        return DefaultEncoder.getInstance().encodeForOS((Codec)codec, input);
    }

    public static String decodeOS(String input, OSType osType) {
        Codec<Character> codec = UNIX_CODEC;
        if (osType == OSType.WINDOWS) {
            codec = WINDOWS_CODEC;
        }

        return ((Codec)codec).decode(input);
    }

    static {
        MYSQL_CODEC = new MySQLCodec(Mode.STANDARD);
        ORACLE_CODEC = new OracleCodec();
    }
}
