package com.example.calculator;

public class ButtonData {

    public String text;
    public int row;
    public int col;
    public int colSpan;
    public ButtonType type;

    public enum ButtonType{
        OPERATOR,
        NUMBER,
        EVALUATE,
        CLEAR
    }

    ButtonData(String text, int row, int col, int colSpan, ButtonType type){
        this.text = text;
        this.row = row;
        this.col = col;
        this.colSpan = colSpan;
        this.type = type;
    }

    ButtonData(String text, int row, int col, int colSpan) {
        this.text = text;
        this.row = row;
        this.col = col;
        this.colSpan = colSpan;
        this.type = ButtonType.NUMBER;
    }
}
