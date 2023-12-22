package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CalculateHelper calculateHelper;

    boolean isBracket;
    boolean isPreview;

    TextView textView;
    TextView textView2;

    int size;
    String result;

    ArrayList num_and_mark_check = new ArrayList();
    int ch;

    Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    Button add, sub, mul, div, clear, bracket, percent, back, dot, equal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateHelper = new CalculateHelper();

        size=0;
        setButton();
        setTextView();

    }

    private void setButton(){
        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        clear = findViewById(R.id.clear);
        bracket = findViewById(R.id.bracket);
        percent = findViewById(R.id.percent);
        back = findViewById(R.id.back);
        dot = findViewById(R.id.dot);

        equal = findViewById(R.id.equal);

        num0.setOnClickListener(numClickListener);
        num1.setOnClickListener(numClickListener);
        num2.setOnClickListener(numClickListener);
        num3.setOnClickListener(numClickListener);
        num4.setOnClickListener(numClickListener);
        num5.setOnClickListener(numClickListener);
        num6.setOnClickListener(numClickListener);
        num7.setOnClickListener(numClickListener);
        num8.setOnClickListener(numClickListener);
        num9.setOnClickListener(numClickListener);

        add.setOnClickListener(markClickListener);
        sub.setOnClickListener(markClickListener);
        mul.setOnClickListener(markClickListener);
        div.setOnClickListener(markClickListener);
        clear.setOnClickListener(markClickListener);
        bracket.setOnClickListener(markClickListener);
        percent.setOnClickListener(markClickListener);
        back.setOnClickListener(markClickListener);
        dot.setOnClickListener(markClickListener);

        equal.setOnClickListener(markClickListener);
    }

    private void setTextView(){
        textView = findViewById(R.id.first_textView);
        textView2 = findViewById(R.id.second_textView);
    }

    Button.OnClickListener numClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.num0)textView.append("0");
            else if (v.getId() == R.id.num1)textView.append("1");
            else if (v.getId() == R.id.num2)textView.append("2");
            else if (v.getId() == R.id.num3)textView.append("3");
            else if (v.getId() == R.id.num4)textView.append("4");
            else if (v.getId() == R.id.num5)textView.append("5");
            else if (v.getId() == R.id.num6)textView.append("6");
            else if (v.getId() == R.id.num7)textView.append("7");
            else if (v.getId() == R.id.num8)textView.append("8");
            else if (v.getId() == R.id.num9)textView.append("9");
            num_and_mark_check.add(1);
            preview();
        }
    };

    Button.OnClickListener markClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.add) {
                textView.append(" + ");
                isPreview = true;
                num_and_mark_check.add(3);
            } else if (v.getId() == R.id.sub) {
                textView.append(" - ");
                isPreview = true;
                num_and_mark_check.add(3);
            } else if (v.getId() == R.id.mul) {
                textView.append(" X ");
                isPreview = true;
                num_and_mark_check.add(3);
            } else if (v.getId() == R.id.div) {
                textView.append(" / ");
                isPreview = true;
                num_and_mark_check.add(3);
            } else if (v.getId() == R.id.percent) {
                textView.append(" % ");
                isPreview = true;
                num_and_mark_check.add(3);
                if (textView.getText().length() != 3) preview();
            } else if (v.getId() == R.id.clear){
                textView.setText("");
                textView2.setText("");

                calculateHelper = new CalculateHelper();

                num_and_mark_check = new ArrayList();
                isBracket = false;
                isPreview = false;
            }
            else if (v.getId() == R.id.bracket) {
                if (!isBracket) {
                    textView.append("( ");
                    isBracket = true;
                } else {
                    textView.append(" )");
                    isBracket = false;
                }
                num_and_mark_check.add(2);
                isPreview = true;

            }
            else if (v.getId() == R.id.back) {
                size = textView.getText().length();

                if (size >= 1) {
                    ch = (int)num_and_mark_check.get(num_and_mark_check.size() - 1);
                    if(textView.getText().toString().substring(size - ch, size).equals("( ")) isBracket = false;
                    else if(textView.getText().toString().substring(size - ch, size).equals(" )")) isBracket = true;
                    textView.setText(textView.getText().toString().substring(0, size - ch));
                    num_and_mark_check.remove(num_and_mark_check.size() - 1);

                    if (size > 1 && size != ch){
                        if (calculateHelper.checkNumber(textView.getText().toString().substring(size - ch - 1)))
                            preview();
                        else {
                            isPreview = false;
                            textView2.setText("");
                        }
                    }
                }

            }
            else if (v.getId() == R.id.dot) {
                textView.append(".");
                num_and_mark_check.add(1);
                //isDot = true;
            }
            else if (v.getId() == R.id.equal) {
                try{
                    result = textView.getText().toString();
                    double r = calculateHelper.process(result);
                    num_and_mark_check = new ArrayList();
                    if(r == (int)r){
                        textView.setText(String.format("%d", (int)r));
                    }else {
                        textView.setText(String.format("%.5f", r));
                    }
                    for(int i = 0; i < textView.getText().toString().length(); i++) num_and_mark_check.add(1);

                    textView2.setText("");
                    isBracket = false;
                    isPreview = false;
                }catch (Exception e){
                    e.printStackTrace();
                    textView2.setText("에러");
                }
            }
        }
    };

    private void preview(){
        if(isPreview){
            result = textView.getText().toString();
            double r = calculateHelper.process(result);
            if(r == (int)r){
                textView2.setText(String.format("%d", (int)r));
            }else {
                textView2.setText(String.format("%.5f", r));
            }
        }
    }
}