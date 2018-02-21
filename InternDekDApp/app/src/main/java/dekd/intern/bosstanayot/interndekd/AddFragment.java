package dekd.intern.bosstanayot.interndekd;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

public class AddFragment extends Fragment  {
    EditText imageUrl, titleInp, messageInp;
    String image, title, message;
    AlertDialog.Builder builder;
    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        imageUrl = v.findViewById(R.id.imageUrl);
        titleInp = v.findViewById(R.id.titleInp);
        messageInp = v.findViewById(R.id.messageInp);
        Button addBtn = v.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonViewModel jsonViewModel = ViewModelProviders.of(getActivity()).get(JsonViewModel.class);

                image = imageUrl.getText().toString();
                title = titleInp.getText().toString();
                message = messageInp.getText().toString();
                if(image.equals("") || title.equals("") || message.equals("")){
                    createDialog();
                }else {
                    JSONObject jsonList = writeJson(image, title, message);
                    jsonViewModel.setJsonArray(jsonViewModel.getJsonArray().put(jsonList));
                    getFragmentManager().popBackStack();
                }

            }
        });

        return v;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

    public void createDialog(){
        builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please complete the form.");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
