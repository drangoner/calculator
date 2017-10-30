package com.jamespace.calculator.calculate;



/**
 * Created by James on 2017/10/30.
 */

public class Calculate {
    private int operator;
    private double num1;
    private double num2;
    private double result;
    private int infinite = 0;//1为正，-1为负

    Calculate(double num1, int operator, double num2){
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = calculate(num1,operator,num2);
    }
    Calculate(double num1, int operator){
        this.num1 = num1;
        this.operator = operator;
        this.result = calculate(num1,operator);
    }
    private double calculate(double num1, int operator, double num2){
        double result = 0;
        switch (operator){
            case Operator.PLUS:  result = num1 + num2;break;
            case Operator.MINUS: result = num1 - num2;break;
            case Operator.TIME:  result = num1 * num2;break;
            case Operator.DIVIDE:
                if (num2!=0){
                    result = num1 / num2;
                }else if(num1 < 0){
                    infinite = -1;
                }else if(num1 > 0){
                    infinite = 1;
                }
                break;
            case Operator.POWER: result = Math.pow(num1,num2);break;
        }
        return result;
    }
    private double calculate(double num1, int operator){
        double result = 0;
        switch (operator){
            case Operator.SQUARE: result = num1 * num1;break;
            case Operator.CUBE:   result = num1 * num1 * num1;break;
            case Operator.CUBEROOt: result = Math.pow(num1,1.0/num2);break;
            case Operator.SQUAREROOT:result = Math.sqrt(num1);break;
            case Operator.FACTORIAL:
                result = 1;
                while (num1>0){
                    result *=num1;
                    num1 --;
                }break;
        }
        return result;
    }
}
