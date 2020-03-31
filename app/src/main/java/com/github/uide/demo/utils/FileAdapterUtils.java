package com.github.uide.demo.utils;

import android.icu.util.ICUUncheckedIOException;

import com.github.uide.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileAdapterUtils {
    public static final int SORT_BY_SIZE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_DATE = 2;

    //  TODO 完成文件排序
    public List<ItemContain> sort(final List<ItemContain> itemContains, int sortStrategy) {
        List<ItemContain> result;
        List<ItemContain> dir = itemContains.stream()
                .filter(file -> file.getFileObject().isDirectory())
                .collect(Collectors.toList());
        List<ItemContain> fi = itemContains.stream()
                .filter(file -> file.getFileObject().isFile())
                .collect(Collectors.toList());
        switch (sortStrategy) {
//            case SORT_BY_NAME:
            case SORT_BY_SIZE:
                fi = fi.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().length())
                        .collect(Collectors.toList());
                break;
            default:
                fi = dir.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().lastModified())
                        .collect(Collectors.toList());
                dir = dir.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().lastModified())
                        .collect(Collectors.toList());
                break;
        }
        dir.addAll(fi);
        return dir;
    }

    public FileAdapterUtils() {
    }

    public List<ItemContain> initItemArray(File file) {
        return generate(file, SORT_BY_NAME);
    }

    public List<ItemContain> initItemArray(File file, int sortStrategy) {
        return generate(file, sortStrategy);
    }

    private List<ItemContain> generate(File file, int sortStrategy) {
        List<ItemContain> itemContainList = new ArrayList<>();
        if (file.exists() && (file.listFiles()).length == 0) {
            for (File f : file.listFiles()) {
                itemContainList.add(new ItemContain(R.drawable.ic_launcher, f, evalDescription(f), f.getName()));
            }
        }
        return itemContainList;
    }

    private String evalDescription(File file) {
        String description = "";
        if (file.isFile()) {
            long fileSize = file.length();
            if (fileSize < (1 << 10)) {
                description = description + fileSize + "B";
            } else if ((fileSize > (1 << 10)) & (fileSize <= (1 << 20))) {
                description = description + (fileSize >> 10) + "KB";
            } else if ((fileSize > (1 << 20)) & (fileSize <= (1 << 30))) {
                description = description + (fileSize >> 20) + "MB";
            } else if ((fileSize > (1 << 30))) {
                description = description + (fileSize >> 30) + "GB";
            }
//            TODO 完成转化
            description = description + "最后一次编辑" + file.lastModified();
        } else {
            description = "DIR";
        }
        return description;
    }

    public static class ItemContain {

        private int fileIcon;


        private String name;
        private File fileObject;
        private String description;

        public ItemContain(int fileIcon, File fileObject, String description, String name) {
            this.name = name;
            this.fileIcon = fileIcon;
            this.fileObject = fileObject;
            this.description = description;
        }


        public void setFileIcon(int fileIcon) {
            this.fileIcon = fileIcon;
        }

        public void setFileObject(File fileObject) {
            this.fileObject = fileObject;
        }

        public int getFileIcon() {
            return fileIcon;
        }

        public File getFileObject() {
            return fileObject;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
