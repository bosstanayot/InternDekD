package dekd.intern.bosstanayot.interndekd.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import dekd.intern.bosstanayot.interndekd.R;

public class ShowListFragment extends Fragment {
    String imgUrl, title, message;
    ImageView imgInList;
    ProgressBar progressBar;
    TextView titleInList, messageInList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_list, container, false);

        bindView(v);
        getBundle();
        setView();

        return v;

    }

    public void bindView(View v){
        imgInList = v.findViewById(R.id.imgInList);
        progressBar = v.findViewById(R.id.progressInlist);
        titleInList = v.findViewById(R.id.titleInList);
        messageInList = v.findViewById(R.id.messageInList);
    }

    public void setView(){
        setImgFromUrl();
        titleInList.setText(title);
        messageInList.setText(message);
    }

    public void getBundle(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            imgUrl = bundle.getString("imgUrl");
            title = bundle.getString("title");
            message = bundle.getString("message");
        }
    }

    public void setImgFromUrl(){
        Glide.with(this)
                .load(imgUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.mipmap.ic_launcher)
                .into(imgInList);
    }

}
