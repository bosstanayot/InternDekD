package dekd.intern.bosstanayot.interndekd;

import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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


/**
 * Created by barjord on 2/17/2018 AD.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder>  {
    private final ListFragment fragment;
    private Context context;
    AlertDialog.Builder builder;
    ImageView img;
    String imgUrl, title, message;
    JSONArray jsonlist;
    ProgressBar progressBar;

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
            try {
                final int list_po = (getItemCount()-1)-position;
                final JSONObject jsonObject = jsonlist.getJSONObject((list_po));
                progressBar = holder.progressBar;
                img = holder.img;
                imgUrl = jsonObject.getString("imgUrl");
                title = jsonObject.getString("title");
                message = jsonObject.getString("message");
                //ImageView image = holder.img;
                holder.titleText.setText(title);
                holder.msgText.setText(message);
                holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        createDialog(list_po);
                        return false;
                    }
                });
                setImgUrl();
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeToShowFrag();
                    }
                });
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

    public ListAdapter(JSONArray jsonlist, Context context, ListFragment fragment){
        this.jsonlist = jsonlist;
        this.context = context;
        this.fragment = fragment;
    }

    public void createDialog(final int list_po){
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to DELETE?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jsonlist.remove(list_po);
                notifyDataSetChanged();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void sentToShow(ShowListFragment showListFragment){
        Bundle bundle = new Bundle();
        bundle.putString("imgUrl", imgUrl);
        bundle.putString("title", title);
        bundle.putString("message", message);
        showListFragment.setArguments(bundle);
    }

    public void setImgUrl(){
        Glide.with(context)
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
                .into(img);
    }

    public void changeToShowFrag(){
        ShowListFragment showListFragment = new ShowListFragment();
        FragmentManager manager = fragment.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_contianer, showListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        sentToShow(showListFragment);
    }
}
