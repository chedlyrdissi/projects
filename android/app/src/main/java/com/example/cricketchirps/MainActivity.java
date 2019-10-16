package com.example.cricketchirps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView description;
    EditText inputchirps;
    TextView temptext;
    Button submitchirpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description=findViewById(R.id.description);
        temptext=findViewById(R.id.temptext);
        submitchirpbutton=findViewById(R.id.submitchirpbutton);
        inputchirps = findViewById(R.id.inputchirps);


        description.setText("Did you know that you can tell the temperature by counting \n " +
                "the chirps of a cricket? It’s true!");

        temptext.setVisibility(View.GONE);

        submitchirpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!String.valueOf(inputchirps.getText()).isEmpty()){
                    double nb=Double.parseDouble(String.valueOf(inputchirps.getText()))+40;

                    temptext.setText("The number you get will be an approximation of the outside temperature " +
                            nb+" F");

                    temptext.setVisibility(View.VISIBLE);
                }

            }
        });

    }
}
