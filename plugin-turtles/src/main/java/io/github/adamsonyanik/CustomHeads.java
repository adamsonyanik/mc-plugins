package io.github.adamsonyanik;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomHeads {

    public static final URL TURTLE = getURL("http://textures.minecraft.net/texture/d2f6c07a326def984e72f772ed645449f5ec96c6ca256499b5d2b84a8dce");
    public static final URL MINING_TURTLE = getURL("http://textures.minecraft.net/texture/c0c7eeb572e0c12271be00dd0740d31ec05bab6acc43a56048f1822e1c3acf85");

    private static URL getURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
