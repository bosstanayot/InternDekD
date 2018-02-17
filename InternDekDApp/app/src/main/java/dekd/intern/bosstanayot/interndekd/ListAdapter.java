package dekd.intern.bosstanayot.interndekd;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sql.DataSource;

/**
 * Created by barjord on 2/17/2018 AD.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private Context context;
    JSONArray jsonlist;
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
            try {
                JSONObject jsonObject = jsonlist.getJSONObject((getItemCount()-1)-position);
                final ProgressBar progressBar = holder.progressBar;
                //ImageView image = holder.img;
                Glide.with(context)
                        .load(jsonObject.getString("imgUrl"))
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
                        .into(holder.img);
                holder.titleText.setText(jsonObject.getString("title"));
                holder.msgText.setText(jsonObject.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public int getItemCount() {
        return jsonlist.length();
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView titleText, msgText;
        ProgressBar progressBar;
        public ListHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItem);
            titleText = itemView.findViewById(R.id.titleText);
            msgText = itemView.findViewById(R.id.msgText);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    public ListAdapter(JSONArray jsonlist, Context context){
        this.jsonlist = jsonlist;
        this.context = context;
    }
}
