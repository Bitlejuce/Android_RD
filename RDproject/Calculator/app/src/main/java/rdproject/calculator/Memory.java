package rdproject.calculator;

import android.util.Log;
import android.widget.TextView;

public class Memory {
    private Memory() {
    }
    private static Memory memory = new Memory();
    public static Memory getInstance() {
        return memory;
    }

    public String getMem() {
        return mem;
    }

    private String mem = null;
    private TextView mainWindow = MainActivity.getMainWindow();
    private MainActivity.ModelViev modelViev = ButtonListener.getModelViev();
    private TextView memoryViev = MainActivity.getMemoryViev();
    private static NumberFormatter formatter = NumberFormatter.getFormatter();


    public void plus() {
        mem = mem == null ? "0" : formatter.normalizeNumber(mem);
        Log.d("Debugging", "M = " + mem + "   GettingLastStringOfView = " + GettingLastStringOfView());
            CalcHelper calcHelper = new CalcHelper();
            mem = calcHelper.calculate(mem, GettingLastStringOfView(), "+");
        Log.d("Debugging", "Calculated M = " + mem );
        memoryViev.setText(formatter.formatNumber(mem));
    }

    public void minus() {
        mem = mem == null ? "0" : formatter.normalizeNumber(mem);
        CalcHelper calcHelper = new CalcHelper();
        mem = calcHelper.calculate(mem, GettingLastStringOfView(), "-");
        Log.d("Debugging", "M = " + mem );
        memoryViev.setText(formatter.formatNumber(mem));
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
        } catch (Exception e) {
        }
    }
    public void memoryClear() {
        memoryViev.setText("");
        mem = null;
    }

    public String GettingLastStringOfView() {
        String text = "";
        try {
            text = mainWindow.getText().toString();

        } catch (Exception e) {
        }
        if (text.equals("") || text.endsWith("\n") || modelViev.isOperationActive()) {
            return "0";
        }
        if (text.contains("\n")) {
            text = text.substring(text.lastIndexOf("\n") + 1); //getting last string of view
        }
        Log.d("Debugging", "Captured text = " + text );
        //text = formatter.formatNumber(text);
        return text;
    }
}
