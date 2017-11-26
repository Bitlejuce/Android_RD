package rdproject.calculator;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static TextView mainWindow;
    private static TextView memory;
    private Button clear, mPlus, mMinus, mRecall, backSpace, one, two, three, four, five, six, seven, eight,
            nine, zero, point, percent, divide, multiply, minus, plus, plusMinus, x2, sqrt, ravno;

    private List<Button> buttonList;
    ButtonListener listener = ButtonListener.getInstance();
    public static TextView getMainWindow() {
        return mainWindow;
    }
    public static TextView getMemoryViev() {
        return memory;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mainWindow = (TextView) findViewById(R.id.textView);
        mainWindow.setMovementMethod(new ScrollingMovementMethod());
        mainWindow.setText("");
        memory = (TextView) findViewById(R.id.memoryView);
        createAllButtons();
        //clear.setText(Html.fromHtml(clearBtn));
        listener = ButtonListener.getInstance();
        buttonList = initButtonList();
        for (Button button : buttonList) {
            button.setOnClickListener(listener);
        }
        clear.setOnLongClickListener(listener);
    }

    private void createAllButtons() {
        clear = (Button) findViewById(R.id.c);
        mPlus = (Button) findViewById(R.id.m_plus);
        mMinus = (Button) findViewById(R.id.m_minus);
        mRecall = (Button) findViewById(R.id.m_r);
        backSpace = (Button) findViewById(R.id.stepback);
        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        five = (Button) findViewById(R.id.button5);
        six = (Button) findViewById(R.id.button6);
        seven = (Button) findViewById(R.id.button7);
        eight = (Button) findViewById(R.id.button8);
        nine = (Button) findViewById(R.id.button9);
        zero = (Button) findViewById(R.id.button0);
        point = (Button) findViewById(R.id.button_point);
        percent = (Button) findViewById(R.id.button_percent);
        divide = (Button) findViewById(R.id.devide);
        multiply = (Button) findViewById(R.id.produce);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        plusMinus = (Button) findViewById(R.id.plus_minus);
        x2 = (Button) findViewById(R.id.x2);
        sqrt = (Button) findViewById(R.id.sqrt);
        ravno = (Button) findViewById(R.id.calculate);
    }

    private List<Button> initButtonList() {
        Button[] buttons = new Button[]{clear, mPlus, mMinus, mRecall, backSpace,
                one, two, three, four, five, six, seven, eight,
                nine, zero, point, percent, divide, multiply, minus, plus, plusMinus, x2, sqrt, ravno};
        return Arrays.asList(buttons);
    }

    public void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static class ModelViev{
        private CalcHelper calcHelper = new CalcHelper();
        private static String currentOperation = null;
        private static String lastOperation = null;
        private static String currentNumber1 = null;
        private static String currentNumber2 = null;
        protected boolean isReadyToNum2 = false;
        protected boolean justResulted = false;

        public void setAlignmentEnd() {
            mainWindow.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            mainWindow.setPadding(5, 5, 50, 5);
        }

        public void clearView() {
            mainWindow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mainWindow.setText("");
            isReadyToNum2 = false;
            currentNumber1 = null;
            currentNumber2 = null;
            lastOperation = null;
            currentOperation = null;
            justResulted = false;
            Log.d("Debugging", "justResulted = " + justResulted);
        }

        public void stepBack() {
            if (isOperationActive()) return;
            try {
                String text = mainWindow.getText().toString();
                if (!text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                mainWindow.setText(text);
            } catch (Exception e) {
            }
        }

        public String clearLastNum() {
            String text = "";
            String lastNum = "";
            text = mainWindow.getText().toString();
            lastNum = text.substring(text.lastIndexOf("\n") + 1);
            try {
                text = text.substring(0, text.lastIndexOf("\n"));
            } catch (Exception e) {
                text = "";
            }
            mainWindow.setText(text);
            justResulted = true;
            return lastNum;

        }

        public void reNewView(String s) {
            String text = mainWindow.getText().toString();
            if (text.substring(text.lastIndexOf("\n") + 1).contains(".") && s.equals(".")) return;
            if (justResulted && !isReadyToNum2) {
                text += "\n";
                justResulted = false;
            }
            if (isReadyToNum2) {
                text += "\n";
                isReadyToNum2 = false;
            }
            text += s;
            mainWindow.setText(text);
        }

        public boolean isOperationActive() {
            boolean b = false;
            try {
                b = mainWindow.getText() != null && (mainWindow.getText().toString().endsWith("/") ||
                        mainWindow.getText().toString().endsWith("x") ||
                        mainWindow.getText().toString().endsWith("+") ||
                        mainWindow.getText().toString().endsWith("-"));
            } catch (Exception e) {
            }
            return b;
        }

        public void initOperation(String s) {
            String text = "";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception e) {
            }

            if (text.equals("")) return;
            if (text.endsWith("\n")) return;

            if (isOperationActive()) {
                currentOperation = s;
                isReadyToNum2 = false;

                if (!text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                mainWindow.setText(text);

                reNewView(s);
                isReadyToNum2 = true;
                return;
            }
            if (currentOperation != null) {
                produceResult();
                currentOperation = s;
                reNewView(s);
                isReadyToNum2 = true;
                return;
            }
            if (!isReadyToNum2) {
                currentOperation = s;
                currentNumber1 = mainWindow.getText().toString();
                currentNumber1 = currentNumber1.substring(currentNumber1.lastIndexOf("\n") + 1); //getting last string of view
                if (justResulted) {
                    reNewView(s);
                } else {
                    reNewView("\n" + s);
                }
                isReadyToNum2 = true;
            }
        }

        public void produceResult() {
            if (isOperationActive()) return;
            if (mainWindow.getText().toString().endsWith("\n")) return;
            if (justResulted) {
                justResulted = false;
                currentOperation = lastOperation;
                String result = "";
                try {
                    result = calcHelper.calculate(currentNumber1, currentNumber2, currentOperation);
                } catch (Exception e) { }
                currentOperation = null;
                currentNumber1 = result;
                reNewView("\n" + "=" + "\n" + result);
                isReadyToNum2 = false;
                justResulted = true;
                return;
            }
            if (mainWindow.getText() == null || currentNumber1 == null || currentOperation == null)
                return;

            currentNumber2 = mainWindow.getText().toString();
            currentNumber2 = currentNumber2.substring(currentNumber2.lastIndexOf("\n") + 1); //getting last string of view
            String result = "";
            try {
                result = calcHelper.calculate(currentNumber1, currentNumber2, currentOperation);
            } catch (Exception e) {}
            lastOperation = currentOperation;
            currentOperation = null;
            currentNumber1 = result;
            reNewView("\n" + "=" + "\n" + result);
            isReadyToNum2 = false;
            justResulted = true;
        }

        public void simple(String operation) {
            String text = "";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception e) {
            }

            if (text.equals("")) return;
            if (text.endsWith("\n")) return;
            if (isOperationActive()) return;

            text = clearLastNum();
            switch (operation) {
                case "x2":
                    text = calcHelper.simpleOperation(text, "x2");
                    break;
                case "sqrt":
                    try {
                        text = calcHelper.simpleOperation(text, "sqrt");
                    } catch (Exception e) {
                        //MainActivity.showToast(this, "Opa-4ike");
                    }
                    break;

            }
            reNewView(text);
            // isReadyToNum2 = false;
            // justResulted = true;
        }
        public void percent() {
            String text = "";
            String startText ="";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception e) {
            }

            if (text.equals("")) return;
            if (text.endsWith("\n")) return;
            if (isOperationActive()) return;
            if (justResulted) return;

            Log.d("Debugging", "text = " + text + ", currentNumber1 = " + currentNumber1);
            Log.d("Debugging", "justResulted = " + justResulted);

//working code for /100
            if (text.contains("\n")) {
                startText = text.substring(0, text.lastIndexOf("\n") + 1);
                text = text.substring(text.lastIndexOf("\n") + 1); //getting last string of view
            }
            Log.d("Debugging", "text = " + text);
            if (currentNumber1 != null) {
                text = calcHelper.percOperation(text, currentNumber1);
            }  else {
                text = calcHelper.percOperation(text);
            }
            text = startText + text;
            mainWindow.setText(text);
        }
// it does work

        public void plusMinus() {
            if (isOperationActive() || mainWindow.getText().toString().equals("")) return;
            try {
                String text = mainWindow.getText().toString();
                if (text.endsWith("\n"))  return;
                if (!text.contains("\n")) {
                    text = text.startsWith("-") ? text.substring(1): "-" + text;
                }
                else {
                    String currentNumber = text.substring(text.lastIndexOf("\n") + 1);
                    currentNumber = currentNumber.startsWith("-") ? currentNumber.substring(1): "-" + currentNumber;
                    text = text.substring(0, text.lastIndexOf("\n")+1);
                    text = text + currentNumber;
                }
                mainWindow.setText(text);

            } catch (Exception e) {
            }
        }
    }
}
