package rdproject.calculator;

import android.view.View;

/**
 * Created by Ro on 15.08.2017.
 */

public class ButtonListener implements View.OnClickListener, View.OnLongClickListener {
    private ButtonListener() {
    }
    private final static ButtonListener BUTTON_LISTENER = new ButtonListener();
    private static MainActivity.ModelViev modelViev = new MainActivity.ModelViev();

    public static MainActivity.ModelViev getModelViev() {
        return modelViev;
    }

    protected static ButtonListener getInstance() {
        return BUTTON_LISTENER;
    }
    public static Memory m;

    @Override
    public void onClick(View v) {
        modelViev.setAlignmentEnd();
        m = Memory.getInstance();
        switch (v.getId()) {
            case (R.id.c):
                modelViev.clearView();
                break;
            case (R.id.m_plus):
                m.plus();
                break;
            case (R.id.m_minus):
                m.minus();
                break;
            case (R.id.m_r):
                m.recall();
                break;
            case (R.id.stepback):
                modelViev.stepBack();
                break;
            case (R.id.button1):
                modelViev.reNewView("1");
                break;
            case (R.id.button2):
                modelViev.reNewView("2");
                break;
            case (R.id.button3):
                modelViev.reNewView("3");
                break;
            case (R.id.button4):
                modelViev.reNewView("4");
                break;
            case (R.id.button5):
                modelViev.reNewView("5");
                break;
            case (R.id.button6):
                modelViev.reNewView("6");
                break;
            case (R.id.button7):
                modelViev.reNewView("7");
                break;
            case (R.id.button8):
                modelViev.reNewView("8");
                break;
            case (R.id.button9):
                modelViev.reNewView("9");
                break;
            case (R.id.button0):
                modelViev.reNewView("0");
                break;
            case (R.id.button_point):
                modelViev.reNewView(".");
                break;
            case (R.id.button_percent):
                modelViev.percent();
                break;
            case (R.id.devide):
                modelViev.initOperation("/");
                break;
            case (R.id.produce):
                modelViev.initOperation("x");
                break;
            case (R.id.minus):
                modelViev.initOperation("-");
                break;
            case (R.id.plus):
                modelViev.initOperation("+");
                break;
            case (R.id.plus_minus):
                modelViev.plusMinus();
                break;
            case (R.id.x2):
                modelViev.simple("x2");
                break;
            case (R.id.sqrt):
                modelViev.simple("sqrt");
                break;
            case (R.id.calculate):
                modelViev.produceResult();

        }

    }

    @Override
    public boolean onLongClick(View v) {
        m = Memory.getInstance();
        m.memoryClear();
        return true;
    }
}
