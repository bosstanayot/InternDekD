package dekd.intern.bosstanayot.interndekd.model;

import android.content.Context;
import android.widget.Toast;



public class MessageAlert {
    private Toast toast;

    public MessageAlert(Context context){
        toast = new Toast(context);
    }

    public void setMessageAlert(Context context, int message){
        toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
