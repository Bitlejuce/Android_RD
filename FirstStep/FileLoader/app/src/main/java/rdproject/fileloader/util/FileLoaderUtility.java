package rdproject.fileloader.util;

import android.content.Context;

public class FileLoaderUtility implements OnSuccessListener{
    private OnSuccessListener onSuccessListener;

    public FileLoaderUtility(Context context) {

    }


    @Override
    public String onSuccess() {
        return null;
    }

    @Override
    public String onError() {
        return null;
    }

    public void addOnEventListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}
