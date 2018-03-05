package io.ryankshah.client.gui;

import io.ryankshah.util.resource.ResourceLoader;
import javax.swing.*;

public class ScanEntry
{
    protected String timestamp;

    public ScanEntry(String timestamp) {
        this.timestamp = timestamp;
    }

    public ImageIcon getIcon() {
        return ResourceLoader.getImageIconResource("disabled_icon.png");
    }

    @Override
    public String toString() {
        return timestamp;
    }
}