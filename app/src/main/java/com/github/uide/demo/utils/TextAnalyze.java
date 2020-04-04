package com.github.uide.demo.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TextAnalyze {

    // java中的50个关键字；
    static final List<String> keyWord = new ArrayList<>(Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"));
    // 算术运算符
    public static String[] arithmeticOperation = {"+", "-", "*", "/", "%", "++", "--"};
    // 关系运算符
    public static String[] relationOperation = {"<", ">", "<=", ">=", "==", "!="};
    // 位运算符
    public static String[] bitOperation = {"&", "|", "^", "~", "<<", ">>", ">>>"};
    // 逻辑运算符
    public static String[] logicOperation = {"&&", "||", "!"};
    // 赋值运算符
    public static String[] assignmentOperation = {"=", "+=", "-=", "/=", "*=", "%=", "<<=", ">>=", "&=", "^=", "|="};
    //标识符由不是数字开头，$、_、字母（区分大小写）和数字组成（true、false、null和关键字不是标识符）不限长度的字符串。
    public static String[] noIdentifier = {"true", "false", "null"};
    // 分界符 (\"在此指的是"，java中用转义字符输出原字符)
    public static String[] limiterWords = {".", ",", ";", "[", "]", "{", "}", "(", ")", "\"", "'", ":"};
    // 单元运算符
    public static String[] unaryOperation = {"+", "-", "*", "/", "%", "<", ">", "&", "|", "^", "~", "!", "="};
    // 双元运算符
    public static String[] binaryOperation = {"++", "--", "<=", ">=", "==", "!=", "<<", ">>", "&&", "||", "+=", "-=",
            "/=", "*=", "%=", "&=", "^=", "|="};
    // 三元运算符
    public static String[] ternaryOperation = {">>>", "<<=", ">>="};

    public static void main(String[] args) {
        InputStreamReader inputStreamReader;
        File file;
        int line = 1;
        int column = 0;
        int startLine = 0;
        int startColumn = 0;
        boolean isInDocument = false;
        boolean isInString = false;
        boolean isCheckWord = false;
        char lastCharacter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        int currentChar = 0;
        int fileLength = 0;
        int currentPoint = 0;
        try {

            inputStreamReader = new InputStreamReader(new FileInputStream(new File("D:\\Project\\IdeaProjects\\JavaSqlite\\src\\android_utils\\TextAnalyze.java")));
            while ((currentChar = inputStreamReader.read()) != -1) {
                column++;
                if (currentChar == '\n') {
                    line++;
                    column = 0;
                }
//              判定注释开始
                if (currentChar == '/') {
                    if (lastCharacter == '/') {
                        isInDocument = true;
//                            开始记录注释开始位置
                        startColumn = column - 1;
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
//              当处于注释记录模式的时候，屏蔽识别其他类型字符
                if (isInDocument) {
                    if (currentChar == '\n') {
                        System.out.println(line + ":" + (startColumn) + "\t" + "Document :" + stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        isInDocument = false;
                    } else {
                        stringBuilder.append((char) currentChar);
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
//              判定字符串开始
                if (currentChar == '\"') {
                    if (!isInString) {
                        if (lastCharacter == '\\') {
                            lastCharacter = (char) currentChar;
                            continue;
                        }
                        isInString = true;
//                            开始记录字符串开始位置
                        startColumn = column - 2;
                        lastCharacter = (char) currentChar;
                        continue;
                    }
                    if (lastCharacter == '\\') {
                        stringBuilder.append((char) currentChar);
                        lastCharacter = (char) currentChar;
                        continue;
                    }
                }
//              当处于字符串记录模式的时候，屏蔽识别其他类型字符
                if (isInString) {
                    if (currentChar == '\"') {
                        isInString = false;
                        System.out.println(line + ":" + (startColumn) + "\t" + "String :" + stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    } else {
                        stringBuilder.append((char) currentChar);
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
                boolean isWord = (currentChar <= 90 && currentChar >= 65) || (currentChar <= 122 && currentChar >= 97) || (currentChar == 95);
//              判断当前是否为普通字符开头
                if (isWord) {
//                  记录开始位置
                    startColumn = column;
                    if (!isCheckWord) {
                        isCheckWord = true;
                        stringBuilder.append((char) currentChar);
                    } else {
                        stringBuilder.append((char) currentChar);
                    }
                }
//              记录截止点
                if (isCheckWord && (!(isWord))) {
                    isCheckWord = false;
                    if (keyWord.contains(stringBuilder.toString())) {
                        System.out.println(line + ":" + (startColumn) + "\t" + "keyWord :" + stringBuilder.toString());
                    } else {
                        System.out.println(line + ":" + (startColumn) + "\t" + "Word :" + stringBuilder.toString());
                    }
                    stringBuilder = new StringBuilder();
                    lastCharacter = (char) currentChar;
                    continue;
                }
                lastCharacter = (char) currentChar;
            }
            if (isInDocument) {

                System.out.println("Document :" + stringBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

