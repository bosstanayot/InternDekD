package dekd.intern.bosstanayot.interndekd;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class ListFragment extends DialogFragment implements AddDialogFragment.OnDialogListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

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
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.jsonlist);
        callLists();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
    }

    public void showDialogAdd(){
        /**AddDialogFragment addFragment = new AddDialogFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_contianer, addFragment);
        transaction.addToBackStack(null);
        transaction.commit();**/

        AddDialogFragment fragment = new AddDialogFragment.Builder()
                .setImgHint("Image URL")
                .setTitleHint("Title (30 Character)")
                .setMessageHint("Message (600 Character)")
                .setPositiveText("ADD")
                .setNegativeText("CANCEL")
                .build();
        fragment.show(getChildFragmentManager(), null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPositiveButtonClick(String imgUrl, String title, String message) {
        JsonViewModel jsonViewModel = ViewModelProviders.of(getActivity()).get(JsonViewModel.class);
        JSONObject jsonList = writeJson(imgUrl, title, message);
        jsonViewModel.setJsonArray(jsonViewModel.getJsonArray().put(jsonList));
        //getFragmentManager().popBackStack();
        callLists();
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

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(getContext(),"test", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}