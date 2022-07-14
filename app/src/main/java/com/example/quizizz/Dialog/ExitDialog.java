package com.example.quizizz.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quizizz.R;

public class ExitDialog extends Dialog {

    public TextView title, body;
    public Button exit, cancel;

    public ExitDialog(@NonNull Context context) {
        super(context);

        setContentView(R.layout.exit_dialog);
        setCancelable(false);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        exit = findViewById(R.id.btnExit);
        cancel = findViewById(R.id.btnCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
