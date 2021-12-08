package main.java.Common;

import java.io.InputStream;

public abstract class AbstractDay {

    public static String getResourcePath() {
        return "input.txt";
    }

    public InputStream getInputStream() {
        return getClass().getResourceAsStream(getResourcePath());
    }
}
