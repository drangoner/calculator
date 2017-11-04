package com.jamespace.calculator;

import android.graphics.Color;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.jamespace.calculator.calculate.Calculate;
import com.jamespace.calculator.calculate.Operator;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;
    TextView textView;
    String operator = null;
    boolean isOpt = false;
    boolean hasDot = false;
    double  num = 0;
    double  result = 0;
    String[] chars = new String[]
            {
                    "7","8","9","÷",
                    "4","5","6","×",
                    "1","2","3","-",
                    ".","0","=","+"
            };
    int color1[][] = {
            {220,220,220},
            {255,222,173},
            {230,230,250},
            {255,193,193},
            {255,127,80},
            {255,114,86},
            {255,52,179},
            {255,215,0},
            {255,228,225},
            {205,92,92},
            {124,252,0},
            {0,255,0},
            {210,105,30},
            {250,128,114},
            {255,69,0},
            {255,192,203},
            {238,59,59},
            {238,154,0},
            {238,64,0},
            {238,169,184}};
    int color2[][] = {
            {138,43,226},
            {105,86,205},
            {154,50,205},
            {125,38,205},
            {0,104,139},
            {0,0,238},
            {0,255,255},
            {95,158,160},
            {138,43,226},
            {0,0,139},
            {139,34,82},
            {70,130,180}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_view);//屏幕显示
        gridLayout = (GridLayout) findViewById(R.id.activity_main);//添加数组按键
        for(int i=0;i<chars.length;i++){
            final Button bn = new Button(this);
            bn.setText(chars[i]);
            bn.setTextSize(40);
            bn.setWidth(180);
            bn.setHeight(160);
            GridLayout.Spec rowSpec= GridLayout.spec(i/4+2);

            GridLayout.Spec columnSpec = GridLayout.spec(i%4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.setGravity(Gravity.FILL);
            gridLayout.addView(bn, params);
            bn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button bt = (Button)view;
                    char c = bt.getText().charAt(0);
                    if (c >= '0'&& c<='9'){//0-9
                        int j = (int)(Math.random()*color1.length);
                        view.setBackgroundColor(Color.argb(120,color1[j][0],color1[j][1],color1[j][2]));
                        if (isOpt){//是否是输入操作码之后
                            textView.setText(bt.getText());
                            isOpt = false;
                        }else if(textView.getText().toString() == "0"){//初始显示为0的情况
                            textView.setText(bt.getText());
                        }else {
                            textView.setText(textView.getText() + bt.getText().toString());
                        }
                    }else if (c == '.'){
                        int j = (int)(Math.random()*color1.length);
                        view.setBackgroundColor(Color.argb(120,color1[j][0],color1[j][1],color1[j][2]));
                        if(isOpt){
                            textView.setText("0.");
                            isOpt = false;
                        }else if(!hasDot){
                            textView.setText(textView.getText().toString() + ".");
                        }
                        hasDot = true;

                    }else if (c == '+' || c == '-' || c== '×' || c =='÷'){
                        int j = (int)(Math.random()*color2.length);
                        view.setBackgroundColor(Color.argb(120,color2[j][0],color2[j][1],color2[j][2]));
                        if(operator == null){//第一次输入操作码
                            result = Double.valueOf(textView.getText().toString());
                            operator = bt.getText().toString();
                            textView.setText(operator);
                        }else{
                            if (isOpt){//连续输入操作符
                                operator = bt.getText().toString();
                                textView.setText(operator);
                            }else{//输入数字之后输入操作符（至少第二个操作符）
                                int opt = -1;
                                switch (operator.charAt(0)){
                                    case '+':
                                        opt = Operator.PLUS;break;
                                    case '-':
                                        opt = Operator.MINUS;break;
                                    case '×':
                                        opt = Operator.TIME;break;
                                    case '÷':
                                        opt = Operator.DIVIDE;
                                }
                                Calculate cal = new Calculate(result, opt, Double.parseDouble(textView.getText().toString()));
                                result = cal.result;
                                operator = bt.getText().toString();
                                textView.setText(operator);
                            }
                        }
                        isOpt = true;//置位操作码标志位
                        hasDot = false;
                    }else if (c == '='){
                        int j = (int)(Math.random()*color2.length);
                        view.setBackgroundColor(Color.argb(120,color2[j][0],color2[j][1],color2[j][2]));
                        if(operator == null){
                            textView.setText(textView.getText());
                        }else if(isOpt){
                            textView.setText("" + result);
                        }else {
                            int opt = -1;
                            switch (operator.charAt(0)){
                                case '+':
                                    opt = Operator.PLUS;break;
                                case '-':
                                    opt = Operator.MINUS;break;
                                case '×':
                                    opt = Operator.TIME;break;
                                case '÷':
                                    opt = Operator.DIVIDE;
                            }
                            Calculate cal = new Calculate(result, opt, Double.parseDouble(textView.getText().toString()));
                            result = cal.result;
                            textView.setText(""+result);
                        }
                        operator = null;
                        isOpt = false;
                    }
                }
            });
        }

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清屏
                textView.setText("0");
                //清除运算符
                operator = null;
                //结果置0
                result = 0;
                isOpt = false;
                hasDot = false;
            }
        });

    }
}
