package io.ryankshah.util.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScriptExecutor
{
    public static boolean executePythonScriptWithArgs(File file, String args) {
        try {
            Process p = Runtime.getRuntime().exec("python3 " + file.getPath() + " " + args);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            // Read the output from the command
            String s;
            while ((s = stdInput.readLine()) != null) {
                if(s.equals("success")) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}