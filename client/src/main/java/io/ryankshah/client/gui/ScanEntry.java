package io.ryankshah.client.gui;

import io.ryankshah.util.resource.ResourceLoader;
import javax.swing.*;

public class ScanEntry
{
    protected int id;
    protected String timestamp;

    public ScanEntry(int id, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public int getID() {
        return id;
    }

    public ImageIcon getIcon() {
        return ResourceLoader.getImageIconResource("disabled_icon.png");
    }

    @Override
    public String toString() {
        return timestamp;
    }
}