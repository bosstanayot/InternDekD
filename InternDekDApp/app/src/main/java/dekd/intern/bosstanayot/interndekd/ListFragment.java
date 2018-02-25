package dekd.intern.bosstanayot.interndekd;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;


public class ListFragment extends DialogFragment implements AddDialogFragment.OnDialogListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    FloatingActionButton fab;
    MessageAlert messageAlert;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public ListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        callLists();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
    }
    public void bindView(View v){
        fab = v.findViewById(R.id.fab);
        recyclerView = v.findViewById(R.id.jsonlist);
        messageAlert = new MessageAlert(getContext());
    }
    public void showDialogAdd(){
        AddDialogFragment addDialogFragment = new AddDialogFragment.Builder()
                .setImgHint(R.string.img_hint)
                .setTitleHint(R.string.title_hint)
                .setMessageHint(R.string.message_hint)
                .setPositiveText(R.string.add)
                .setNegativeText(R.string.cancel)
                .build();
        addDialogFragment.show(getChildFragmentManager(), null);
    }

   @Override
    public void onPositiveButtonClick(String imgUrl, String title, String message) {
        JsonViewModel jsonViewModel = ViewModelProviders.of(getActivity()).get(JsonViewModel.class);
        if(imgUrl.equals("") || title.equals("") || message.equals("")){
            messageAlert.setMessageAlert(getContext(), "Please complete the form.");
        }else {
            JSONObject jsonList = writeJson(imgUrl, title, message);
            jsonViewModel.setJsonArray(jsonViewModel.getJsonArray().put(jsonList));
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onNegativeButtonClick() {
    }

    public void callLists(){
        JsonViewModel jsonViewModel = ViewModelProviders.of(getActivity()).get(JsonViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ListAdapter(jsonViewModel.getJsonArray(),getContext(), ListFragment.this);
        recyclerView.setAdapter(adapter);
    }
    public JSONObject writeJson(String imgUrl, String title, String msg){
        JSONObject content = new JSONObject();
        try {
            content.put("imgUrl", imgUrl);
            content.put("title", title);
            content.put("message", msg);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return content;
    }

}