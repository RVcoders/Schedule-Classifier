package com.rvcoder.scheduleclassifier;

import com.tokenautocomplete.TokenCompleteTextView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Sample token completion view for basic contact info
 *
 * Created on 9/12/13.
 * @author mgod
 */
public class TransactionView extends TokenCompleteTextView{

    public TransactionView(Context context) {
        super(context);
    }

    public TransactionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransactionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(Object object) {
        Transactiontext p = (Transactiontext)object;

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.contact_token, (ViewGroup)TransactionView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(p.getName());

        return view;
    }

    @Override
    protected Object defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Transactiontext(completionText);
        } else {
            return new Transactiontext(completionText);
        }
    }
}
