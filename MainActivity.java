package com.quit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);

        startButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Perform some action when the start button is clicked
				}
			});

        stopButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Perform some action when the stop button is clicked
				}
			});
    }
}

