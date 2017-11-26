package rdproject.calculator;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ro on 14.09.2017.
 */

public class Memory {
    private Memory() {
    }
    private static Memory memory = new Memory();
    public static Memory getInstance() {
        return memory;
    }

    private String mem = null;
    private TextView mainWindow = MainActivity.getMainWindow();
    private MainActivity.ModelViev modelViev = ButtonListener.getModelViev();
    private TextView memoryViev = MainActivity.getMemoryViev();


    public void plus() {
        mem = mem == null ? "0" : mem;
        Log.d("Debugging", "M = " + mem + "   GettingLastStringOfView = " + GettingLastStringOfView());
            CalcHelper calcHelper = new CalcHelper();
            mem = calcHelper.calculate(mem, GettingLastStringOfView(), "+");
        Log.d("Debugging", "Calculated M = " + mem );
        memoryViev.setText(mem);
    }

    public void minus() {
        mem = mem == null ? "0" : mem;
        CalcHelper calcHelper = new CalcHelper();
        mem = calcHelper.calculate(mem, GettingLastStringOfView(), "-");
        Log.d("Debugging", "M = " + mem );
        memoryViev.setText(mem);
    }

    public void recall() {
        if (mem == null) return;
        Log.d("Debugging", "Recalling: M = " + mem );


        try {
            if ( !modelViev.isReadyToNum2 && !modelViev.justResulted) {
                modelViev.reNewView( "\n" + mem );
                return;
            }
            modelViev.reNewView(mem);
            // mem = null;
        } catch (Exception e) {
        }
    }
    public void memoryClear() {
        memoryViev.setText("");
        mem = null;
    }

    public void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public String GettingLastStringOfView() {
        String text = "";
        try {
            text = mainWindow.getText().toString();

        } catch (Exception e) {
        }
        if (text.equals("") || text.endsWith("\n") || modelViev.isOperationActive()) {
            // new Memory().showToast(MainActivity.this);
            return "0";
        }
        if (text.contains("\n")) {
            text = text.substring(text.lastIndexOf("\n") + 1); //getting last string of view
        }
        Log.d("Debugging", "Captured text = " + text );
        return text;
    }
}
