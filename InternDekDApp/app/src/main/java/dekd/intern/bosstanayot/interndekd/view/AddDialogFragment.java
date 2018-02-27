package dekd.intern.bosstanayot.interndekd.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dekd.intern.bosstanayot.interndekd.R;

public class AddDialogFragment extends DialogFragment {

    private static final String KEY_IMG = "key_img";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_POSITIVE = "key_positive";
    private static final String KEY_NEGATIVE = "key_negative";

    EditText imageUrl, titleInp, messageInp;
    Button positiveBtn, negativeBtn;

    private int imgText;
    private int titleText;
    private int messageText;
    private int positiveText;
    private int negativeText;

    public static AddDialogFragment newInstance(@StringRes int imgText, @StringRes int titleText,
                                                @StringRes int messageText, @StringRes int positiveText,
                                                @StringRes int negativeText) {
        AddDialogFragment addDialogFragment = new AddDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_IMG, imgText);
        bundle.putInt(KEY_TITLE, titleText);
        bundle.putInt(KEY_MESSAGE, messageText);
        bundle.putInt(KEY_POSITIVE, positiveText);
        bundle.putInt(KEY_NEGATIVE, negativeText);
        addDialogFragment.setArguments(bundle);
        return addDialogFragment;
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
                    listener.onPositiveButtonClick(imageUrl.getText().toString(), titleInp.getText().toString()
                            , messageInp.getText().toString());
                    if(!imageUrl.getText().toString().equals("") && !titleInp.getText().toString().equals("") &&
                            !messageInp.getText().toString().equals("")){
                        dismiss();
                    }
                }
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
        outState.putInt(KEY_IMG, imgText);
        outState.putInt(KEY_TITLE, titleText);
        outState.putInt(KEY_MESSAGE, messageText);
        outState.putInt(KEY_POSITIVE, positiveText);
        outState.putInt(KEY_NEGATIVE, negativeText);
    }

    private void restoreInstanceState(Bundle bundle) {
        imgText = bundle.getInt(KEY_IMG);
        titleText = bundle.getInt(KEY_TITLE);
        messageText = bundle.getInt(KEY_MESSAGE);
        positiveText = bundle.getInt(KEY_POSITIVE);
        negativeText = bundle.getInt(KEY_NEGATIVE);
    }

    private void restoreArguments(Bundle bundle) {
        imgText = bundle.getInt(KEY_IMG);
        titleText = bundle.getInt(KEY_TITLE);
        messageText = bundle.getInt(KEY_MESSAGE);
        positiveText = bundle.getInt(KEY_POSITIVE);
        negativeText = bundle.getInt(KEY_NEGATIVE);
    }

    public static class Builder {
        private int imgUrl;
        private int title;
        private int message;
        private int positive;
        private int  negative;

        public Builder() {
        }

        public Builder setImgHint(@StringRes int imgHint) {
            this.imgUrl = imgHint;
            return this;
        }

        public Builder setTitleHint(@StringRes int titleHint) {
            this.title = titleHint;
            return this;
        }

        public Builder setMessageHint(@StringRes int messageHint) {
            this.message = messageHint;
            return this;
        }

        public Builder setPositiveText(@StringRes int positiveText) {
            this.positive = positiveText;
            return this;
        }

        public Builder setNegativeText(@StringRes int negativeText) {
            this.negative = negativeText;
            return this;
        }
        public AddDialogFragment build() {
            AddDialogFragment fragment = AddDialogFragment.newInstance(imgUrl, title, message, positive, negative);
            return fragment;
        }
    }
}
