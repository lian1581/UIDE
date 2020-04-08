package com.github.uide.demo.utils;

public class KeywordConstant {
    // java中的50个关键字；
    public static String[] keyWords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};
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
}
