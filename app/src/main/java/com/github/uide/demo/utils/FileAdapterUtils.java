package com.github.uide.demo.utils;

import java.io.File;

public class FileAdapterUtils {
    public static class ItemContain {
        private int fileIcon;
        private File fileObject;

        public ItemContain(int fileIcon, File fileObject) {
            this.fileIcon = fileIcon;
            this.fileObject = fileObject;
        }

        public int getFileIcon() {
            return fileIcon;
        }

        public File getFileObject() {
            return fileObject;
        }

    }
}
