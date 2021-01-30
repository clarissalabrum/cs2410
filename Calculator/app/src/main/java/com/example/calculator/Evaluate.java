package com.example.calculator;

import static java.lang.Double.NaN;

public class Evaluate {

    public static String evaluate(String expression){
        String[] tokens = expression.split(" ");
        if (tokens.length == 0){
            return "";
        }
        if (tokens.length == 1){
            return String.valueOf(Double.parseDouble(tokens[0]));
        }
        if (tokens.length == 2){
            return "";
        }
        String mode = "value";
        double currentValue = 1;
        int i = 2;

        try {
            currentValue = Double.parseDouble(tokens[0]);
        } catch (Exception e){
            if (tokens[0].equals("") && tokens[1].equals("-")) {
                currentValue = -1 * Double.parseDouble(tokens[2]);
                mode = "operator";
                i = 3;
            }
            else return "";
        }

        String operation = tokens[1];

        for (; i<tokens.length; i++){
            if(mode.equals("value")){
                double foundValue;
                try {
                    foundValue = Double.parseDouble(tokens[i]);
                } catch (Exception e) {
                    if (tokens[i].equals("")) {
                        foundValue = -1 * Double.parseDouble(tokens[i+2]);
                        i += 3;
                    }
                    else return "";
                }
                if (operation.equals("+")){
                    currentValue = currentValue + foundValue;
                }
                if (operation.equals("-")){
                    currentValue = currentValue - foundValue;
                }
                if (operation.equals("*")){
                    currentValue = currentValue * foundValue;
                }
                if (operation.equals("/")){
                    currentValue = currentValue / foundValue;
                }
                mode = "operator";
            } else {
                operation = tokens[i];
                mode = "value";
            }
        }
        return String.valueOf(currentValue);
    }
}
