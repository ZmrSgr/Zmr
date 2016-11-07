package cn.sgr.zmr.com.sgr.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.test.suitebuilder.TestMethod;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.sgr.zmr.com.sgr.R;

/**
 * Created by Administrator on 2016/10/27.
 */

public class MsgDialog extends Dialog{
    public MsgDialog(Context context) {
        super(context);
    }

    public MsgDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{
        private Context context;
        private String title;
        private String message;
        private String positiveBtnText;
        private String negativeBtnText;
        private TextView tvPositive;
        private TextView tvNegative;
        private TextView tvMessage;
        private TextView tvTitle;
        private View contentView;
        private DialogInterface.OnClickListener positiveBtnClickListener;
        private DialogInterface.OnClickListener negativeBtnClickListener;
        private ContentViewIniter mContentViewIniter;

        public Builder(Context context) {
            this.context = context;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentView(View contentView, ContentViewIniter initer) {
            this.contentView = contentView;
            this.mContentViewIniter = initer;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener positiveBtnClickListener){
            this.positiveBtnText = (String) context.getText(positiveButtonText);
            this.positiveBtnClickListener = positiveBtnClickListener;
            return this;
        }

        public Builder setPositiveButton(String positiveBtnText, DialogInterface.OnClickListener positiveBtnClickListener){
            this.positiveBtnText = positiveBtnText;
            this.positiveBtnClickListener = positiveBtnClickListener;
            return this;
        }

        public Builder setNegativeButton(int negativeBtnText, DialogInterface.OnClickListener negativeBtnClickListener){
            this.negativeBtnText = (String) context.getText(negativeBtnText);
            this.negativeBtnClickListener = negativeBtnClickListener;
            return this;
        }

        public Builder setNegativeButton(String negativeBtnText, DialogInterface.OnClickListener negativeBtnClickListener){
            this.negativeBtnText = negativeBtnText;
            this.negativeBtnClickListener = negativeBtnClickListener;
            return this;
        }

        /**
         * 最后调用
         * @return
         */
        public MsgDialog create(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MsgDialog dialog = new MsgDialog(context, R.style.dialog);
//            View layout = inflater.inflate()
            View view = inflater.inflate(R.layout.dialog_layout_base, null);
            dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            tvTitle = (TextView) view.findViewById(R.id.dialog_tv_title);
            tvMessage = (TextView) view.findViewById(R.id.dialog_message);
            tvPositive = (TextView) view.findViewById(R.id.yes);
            tvNegative = (TextView) view.findViewById(R.id.no);

            if(!TextUtils.isEmpty(title)){
                tvTitle.setText(title);
            }

            if (!TextUtils.isEmpty(positiveBtnText)){
                tvPositive.setText(positiveBtnText);
                if(positiveBtnClickListener != null){
                    tvPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveBtnClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                tvPositive.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(negativeBtnText)){
                tvNegative.setText(negativeBtnText);
                if(negativeBtnClickListener != null){
                    tvNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeBtnClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                tvNegative.setVisibility(View.GONE);
            }

            // set the content message
            if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            } else if (contentView != null) {
                // if no message set
                mContentViewIniter.initContentView(contentView);
                // add the contentView to the dialog body
                ((LinearLayout) view.findViewById(R.id.dialog_ll_content))
                        .removeAllViews();
                ((LinearLayout) view.findViewById(R.id.dialog_ll_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(view);
            return dialog;
        }

        /**
         * 对话框contentView的初始化器
         */
        public interface ContentViewIniter {
            void initContentView(View contentView);
        }
    }
}
