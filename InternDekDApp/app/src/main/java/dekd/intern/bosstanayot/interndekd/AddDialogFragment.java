package dekd.intern.bosstanayot.interndekd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

public class AddDialogFragment extends DialogFragment {

    private static final String KEY_IMG = "key_img";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_POSITIVE = "key_positive";
    private static final String KEY_NEGATIVE = "key_negative";

    EditText imageUrl, titleInp, messageInp;
    Button positiveBtn, negativeBtn;
    String image, title, message;
    AlertDialog.Builder builder;

    private String imgText;
    private String titleText;
    private String messageText;
    private String positiveText;
    private String negativeText;

    public static AddDialogFragment newInstance(String imgText, String titleText,
                                                String messageText, String positiveText,
                                                String negativeText) {
        AddDialogFragment fragment = new AddDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMG, imgText);
        bundle.putString(KEY_TITLE, titleText);
        bundle.putString(KEY_MESSAGE, messageText);
        bundle.putString(KEY_POSITIVE, positiveText);
        bundle.putString(KEY_NEGATIVE, negativeText);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    public void bindView(View v){
        imageUrl = v.findViewById(R.id.imageUrl);
        titleInp = v.findViewById(R.id.titleInp);
        messageInp = v.findViewById(R.id.messageInp);
        positiveBtn = v.findViewById(R.id.addBtn);
        negativeBtn = v.findViewById(R.id.cancelBtn);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
        /**addBtn.setOnClickListener(new View.OnClickListener() {
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
        });**/

    }
    public interface OnDialogListener {
        void onPositiveButtonClick(String imgUrl, String title, String message);

        void onNegativeButtonClick();
    }


    private void setupView() {
        imageUrl.setHint(imgText);
        titleInp.setHint(titleText);
        messageInp.setHint(messageText);
        positiveBtn.setText(positiveText);
        negativeBtn.setText(negativeText);

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onPositiveButtonClick(imageUrl.getText().toString(),
                    titleInp.getText().toString(), messageInp.getText().toString());
                }
                dismiss();
            }
        });

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onNegativeButtonClick();
                }
                dismiss();
            }
        });
    }

    private OnDialogListener getOnDialogListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (OnDialogListener) fragment;
            } else {
                return (OnDialogListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMG, imgText);
        outState.putString(KEY_TITLE, titleText);
        outState.putString(KEY_MESSAGE, messageText);
        outState.putString(KEY_POSITIVE, positiveText);
        outState.putString(KEY_NEGATIVE, negativeText);
    }

    private void restoreInstanceState(Bundle bundle) {
        imgText = bundle.getString(KEY_IMG);
        titleText = bundle.getString(KEY_TITLE);
        messageText = bundle.getString(KEY_MESSAGE);
        positiveText = bundle.getString(KEY_POSITIVE);
        negativeText = bundle.getString(KEY_NEGATIVE);
    }

    private void restoreArguments(Bundle bundle) {
        imgText = bundle.getString(KEY_IMG);
        titleText = bundle.getString(KEY_TITLE);
        messageText = bundle.getString(KEY_MESSAGE);
        positiveText = bundle.getString(KEY_POSITIVE);
        negativeText = bundle.getString(KEY_NEGATIVE);
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

    public static class Builder {
        private String imgUrl;
        private String title;
        private String message;
        private String positive;
        private String  negative;

        public Builder() {
        }

        public Builder setImgHint(String imgHint) {
            this.imgUrl = imgHint;
            return this;
        }

        public Builder setTitleHint(String titleHint) {
            this.title = titleHint;
            return this;
        }

        public Builder setMessageHint(String messageHint) {
            this.message = messageHint;
            return this;
        }

        public Builder setPositiveText(String positiveText) {
            this.positive = positiveText;
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            this.negative = negativeText;
            return this;
        }
        public AddDialogFragment build() {
            AddDialogFragment fragment = AddDialogFragment.newInstance(imgUrl, title, message, positive, negative);
            return fragment;
        }
    }
}
