package io.ryankshah.util.database;

import javax.swing.*;
import java.io.File;

public class ImageLoader
{
    public static ImageIcon getImageIconResource(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("img/" + fileName).getFile());
        return new ImageIcon(file.getPath());
    }
}