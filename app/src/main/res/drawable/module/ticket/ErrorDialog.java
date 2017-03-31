package com.weiruanit.lifepro.module.ticket;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.weiruanit.lifepro.R;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class ErrorDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.trainlist_err,  ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(450, 500);//这2行,和上面的一样,注意顺序就行;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getDialog().getWindow().setLayout(450, 500);
    }
}
