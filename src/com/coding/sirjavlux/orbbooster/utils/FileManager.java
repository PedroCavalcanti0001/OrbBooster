package com.coding.sirjavlux.orbbooster.utils;

import org.bukkit.plugin.Plugin;

import java.io.*;

public class FileManager {

    public static void writeFileFromResources(Plugin plugin, String path, String resource) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //create file
            File auto = new File(path);
            // read this file into InputStream
            inputStream = plugin.getResource(resource);
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(auto);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
