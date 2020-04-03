package com.github.uide.demo.utils;

import com.github.uide.demo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author MeteorImpact/ADP-P-P
 */
public class FileAdapterUtils {

    public static final int SORT_BY_SIZE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_DATE = 2;
    public static final int ORDER_DESCEND = -1;
    public static final int ORDER_ASCENDING = 0;

    //    String pat= "(\\.+?).*";
    private itemDescription item_description = f -> {
        String description = "";
        if (f.isFile()) {
            long fileSize = f.length();
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
            description = description + "  最后一次编辑" + f.lastModified();
        } else {
            description = "DIR";
        }
        return description;
    };
    private itemIcon itemIcon = file -> {
        String extraName = "(\\.+?).*";
        int icon = 0;
        Matcher matcher = Pattern.compile(extraName).matcher(file.getName());
        if (matcher.find()) {
            switch (matcher.group()) {
                case "doc":
                case "docx":
                    icon = R.drawable.ic_icon_word;
                    break;
                case "xml":
                case "html":
                    icon = R.drawable.ic_icon_xml;
                    break;
                default:
                    icon = R.drawable.ic_launcher;
            }
            return icon;
        } else {
            return R.drawable.ic_launcher;
        }

    };


    private List<ItemContain> sort(final List<ItemContain> itemContains, int sortStrategy, int orderStrategy) {
        List<ItemContain> result;
        List<ItemContain> dir = itemContains.stream()
                .filter(file -> file.getFileObject().isDirectory())
                .sorted((item, t1) -> {
                    return item.getFileObject().getName().compareTo(t1.getFileObject().getName());
                })
                .collect(Collectors.toList());
        List<ItemContain> fi = itemContains.stream()
                .filter(file -> file.getFileObject().isFile())
                .collect(Collectors.toList());
        switch (sortStrategy) {
            case SORT_BY_NAME:
                fi = fi.stream()
                        .sorted((item, t1) -> item.getFileObject().getName().compareTo(t1.getFileObject().getName()))
                        .collect(Collectors.toList());

            case SORT_BY_SIZE:
                fi = fi.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().length())
                        .collect(Collectors.toList());
                break;
            default:
                fi = fi.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().lastModified())
                        .collect(Collectors.toList());
                dir = dir.stream()
                        .sorted((item, t1) -> (int) item.getFileObject().lastModified())
                        .collect(Collectors.toList());
                break;
        }
        if (orderStrategy != ORDER_ASCENDING) {
            List<ItemContain> itemContainList = new ArrayList<>();
            for (int i = dir.size() - 1; i > -1; i--) {
                itemContainList.add(dir.get(i));
            }
            for (int i = fi.size() - 1; i > -1; i--) {
                itemContainList.add(fi.get(i));
            }
            dir = itemContainList;
        } else {
            dir.addAll(fi);
        }
        return dir;
    }

    public FileAdapterUtils() {
    }

    /**
     * 默认：按名称升序排列
     *
     * @param file 传入文件实例，用来当做父文件夹
     * @return 整理好的item
     */
    public List<ItemContain> initItemArray(File file) {
        return generate(file, SORT_BY_NAME, ORDER_ASCENDING);
    }

    /**
     * @param file          传入的文件实例
     * @param sortStrategy  排序规则
     * @param orderStrategy 升降序规则
     * @return 返回整理好的item
     */
    public List<ItemContain> initItemArray(File file, int sortStrategy, int orderStrategy) {
        return generate(file, sortStrategy, orderStrategy);
    }

    private List<ItemContain> generate(File file, int sortStrategy, int orderStrategy) {
        File[] subDIr = file.listFiles();
        List<ItemContain> itemContainList = new ArrayList<>();
        if (subDIr == null) {
            return itemContainList;
        }
        if (file.exists() && subDIr.length != 0) {
            for (File f : subDIr) {
                itemContainList.add(new ItemContain(itemIcon.icon(f)
                        , f, item_description.description(f), f.getName()));
            }
        }
        return sort(itemContainList, sortStrategy, orderStrategy);
    }

    private interface itemDescription {
        String description(File file);

    }

    private interface itemIcon {
        int icon(File file);
    }

    public static class ItemContain {

        private int fileIcon;

        private String name;
        private File fileObject;
        private String description;

        /**
         * 作为储存每个recycleView item的工具类
         *
         * @param fileIcon    储存图标
         * @param fileObject  文件实例，可以直接拿来调用
         * @param description 文件描述
         * @param name        文件名
         */
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
