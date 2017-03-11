package com.samkeet.revamp17.coordinator;

/**
 * Created by Frost on 10-03-2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samkeet.revamp17.App;

/**
 * Created by Dmytro Denysenko on 5/6/15.
 */
public class CoCanaroTextView extends TextView {
    public CoCanaroTextView(Context context) {
        this(context, null);
    }

    public CoCanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoCanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.canaroExtraBold);
    }

}