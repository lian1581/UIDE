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
            "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false", "null"));
    // 分界符 (\"在此指的是"，java中用转义字符输出原字符)
    static final List<String> limiterWords = new ArrayList<>(Arrays.asList(".", ",", ";", "[", "]", "{", "}", "(", ")", "\"", "'", ":"));
    static final List<String> operationWords = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", "&", "|", "!", "=", "<", ">"));

    private static PipedWriter pipedWriter;

    public static void main(String[] args) throws IOException {
        analyze();
    }

    /*
     *多行注释测试
     * line
     */
    public static void analyze() {
        InputStreamReader inputStreamReader;
        File file;
        int bin = 0b1010010;
        int eig = 5096674;
        int hex = 0xfffff;
        int line = 1;
        int column = 0;
        int startLine = 0;
        int startColumn = 0;
        boolean isInDocument = false;
        boolean isInString = false;
        boolean isCheckWord = false;
        boolean isInMultiply = false;
        boolean isInNumber = false;
        boolean isInOperation = false;
        char lastCharacter = 0;
        String PATH = "D:\\Project\\IdeaProjects\\JavaSqlite\\src\\android_utils\\TextAnalyze.java";
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder lastOperation = new StringBuilder();
        int currentChar = 0;
        int fileLength = 0;
        int currentPoint = 0;
        System.gc();
        long startTime = System.currentTimeMillis();
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(new File(PATH)));
            while ((currentChar = inputStreamReader.read()) != -1) {
                column++;
                if (currentChar == '\n') {
                    line++;
                    column = 0;
                }
//              判定注释开始/解除多行注释
                if (currentChar == '/') {
                    if (!isInMultiply) {
                        if (lastCharacter == '/') {
                            isInDocument = true;
//                            开始记录注释开始位置
                            startColumn = column - 1;
                        }
                    } else if (lastCharacter == '*') {
                        isInMultiply = false;
                        print(line, column, "multiply document", stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
//              当处于注释记录模式的时候，屏蔽识别其他类型字符
                if (isInDocument) {
                    if (currentChar == '\n') {
                        print(line, startColumn, "Document", stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        isInDocument = false;
                    } else {
                        stringBuilder.append((char) currentChar);
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
//              判定多行注释
                if (currentChar == '*' && lastCharacter == '/') {
                    isInMultiply = true;
                    lastCharacter = (char) currentChar;
                    continue;
                }
                if (isInMultiply) {
                    stringBuilder.append((char) currentChar);
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
                        print(line, column, "String", stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    } else {
                        stringBuilder.append((char) currentChar);
                    }
                    lastCharacter = (char) currentChar;
                    continue;
                }
//              判断符号
                if (limiterWords.contains(String.valueOf((char) currentChar))) {
                    print(line, column, "limitWord", new String(String.valueOf((char) currentChar)));
                    lastCharacter = (char) currentChar;
                    stringBuilder = new StringBuilder();
                }
                if (operationWords.contains(String.valueOf((char) currentChar))) {
                    isInOperation = true;
                }
                if (isInOperation) {
                    if (operationWords.contains(String.valueOf((char) currentChar))) {
                        stringBuilder.append((char) currentChar);
                    } else {
                        print(line, column, "operationWord", stringBuilder.toString());
                        isInOperation = false;
                        stringBuilder = new StringBuilder();
                    }
                }
//              开始判断数字
                if ((currentChar <= 57) && (currentChar >= 48) && !isInNumber) {
                    isInNumber = true;
                }
                if (isInNumber) {
                    if (((currentChar <= 57) && (currentChar >= 48)) || (currentChar <= 90 && currentChar >= 65) || (currentChar <= 122 && currentChar >= 97) || (currentChar == 95)) {
                        stringBuilder.append((char) currentChar);
                        continue;
                    } else {
                        print(line, column, "number", stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        isInNumber = false;
                    }
                }
                boolean isWord = (currentChar <= 90 && currentChar >= 65) || (currentChar <= 122 && currentChar >= 97) || (currentChar == 95);
//              判断当前是否为普通字符开头
                if (isWord) {
//                  记录开始位置
                    startColumn = column;
                    if (!isCheckWord) {
                        isCheckWord = true;
                    }
                    stringBuilder.append((char) currentChar);
                }
//              记录截止点
                if (isCheckWord && (!(isWord))) {
                    isCheckWord = false;
                    if (keyWord.contains(stringBuilder.toString())) {
                        print(line, startColumn, "keyWord", stringBuilder.toString());
                    } else {
                        print(line, startColumn, "word", stringBuilder.toString());
                    }
                    stringBuilder = new StringBuilder();
                }
                lastCharacter = (char) currentChar;
            }
            if (isInDocument) {
                System.out.println("Document :" + stringBuilder.toString());
            }
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
        System.gc();
        System.out.println("消耗内存：" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }

    /**
     * 以上分析的所有结果都会通过下面的print函数输出，可以通过多线程+pipe开控制惰性分析
     *
     * @param line   这个词出现的行号
     * @param column 这个词出现的位数
     * @param type   词的类型
     * @param result 词的值
     */
    private static void print(int line, int column, String type, String result) throws IOException {
        System.out.println(line + ":" + column + "\t" + type + " :" + result);
    }
}

