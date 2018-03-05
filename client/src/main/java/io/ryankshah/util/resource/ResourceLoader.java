package io.ryankshah.util.resource;

import javax.swing.*;
import java.io.File;

public class ResourceLoader
{
    public static ImageIcon getImageIconResource(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("img/" + fileName).getFile());
        return new ImageIcon(file.getPath());
    }

    public static File getScriptResource(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return new File(classLoader.getResource("scripts/" + fileName).getFile());
    }
}