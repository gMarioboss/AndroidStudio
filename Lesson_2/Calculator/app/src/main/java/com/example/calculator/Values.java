package com.example.calculator;

public enum Values {
    NUMBER_1 ("1"),
    NUMBER_2 ("2"),
    NUMBER_3 ("3"),
    NUMBER_4 ("4"),
    NUMBER_5 ("5"),
    NUMBER_6 ("6"),
    NUMBER_7 ("7"),
    NUMBER_8 ("8"),
    NUMBER_9 ("9"),
    NUMBER_0 ("0"),
    POINT ("."),
    OPER_PLUS ("+"),
    OPER_MINUS ("-"),
    OPER_DIV ("/"),
    OPER_MULT ("*"),
    OPER_PERCENT ("%%"),
    OPER_EQ ("=");

    private String value;

    Values (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
