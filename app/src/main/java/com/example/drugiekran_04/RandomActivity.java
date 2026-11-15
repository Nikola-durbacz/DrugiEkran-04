package com.example.drugiekran_04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    public static final String EXTRA_ENTRIES = "ENTRIES";
    public static final String EXTRA_SELECTED = "SELECTED";

    private String[] entries;
    private String selected;

    private TextView tvResult;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        tvResult = findViewById(R.id.tvResult);
        tvCount = findViewById(R.id.tvCount);

        Button btnAgain = findViewById(R.id.btnAgain);
        Button btnChoose = findViewById(R.id.btnChoose);

        entries = getIntent().getStringArrayExtra(EXTRA_ENTRIES);

        if (entries == null || entries.length == 0) {
            tvResult.setText("Brak wpisów");
            tvCount.setText("Liczba wpisów: 0");
            btnAgain.setEnabled(false);
            btnChoose.setEnabled(false);
            return;
        }

        tvCount.setText("Liczba wpisów: " + entries.length);

        drawEntry();

        btnAgain.setOnClickListener(v -> drawEntry());

        btnChoose.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra(EXTRA_SELECTED, selected);
            setResult(RESULT_OK, result);
            finish();
        });
    }

    private void drawEntry() {
        Random random = new Random();
        int index = random.nextInt(entries.length);

        selected = entries[index];
        tvResult.setText(selected);
    }
}
