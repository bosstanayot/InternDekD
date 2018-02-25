package dekd.intern.bosstanayot.interndekd;

import android.content.Context;
import android.widget.Toast;



public class MessageAlert {
    private Toast toast;

    public MessageAlert(Context context){
        toast = new Toast(context);
    }

    public void setMessageAlert(Context context, String message){
        toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
