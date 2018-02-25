package dekd.intern.bosstanayot.interndekd;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListHolder extends RecyclerView.ViewHolder{
    ImageView img;
    TextView titleText, msgText;
    ProgressBar progressBar;
    CardView cardView;

    public ListHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.imgItem);
        titleText = itemView.findViewById(R.id.titleText);
        msgText = itemView.findViewById(R.id.msgText);
        progressBar = itemView.findViewById(R.id.progressbar);
        cardView = itemView.findViewById(R.id.cardView);
    }
}