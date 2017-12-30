package com.hakodev.androiditunesapi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {
    public static String formatStringForURL(String text) {
        String encodedText = "";
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }
}
