package com.example.drugiekran_04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etEntry;
    private TextView tvList;
    private ArrayList<String> entries = new ArrayList<>();

    private ActivityResultLauncher<Intent> randomLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEntry = findViewById(R.id.etEntry);
        tvList = findViewById(R.id.tvList);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRandom = findViewById(R.id.btnRandom);

        randomLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String selected = result.getData().getStringExtra(RandomActivity.EXTRA_SELECTED);

                        Snackbar.make(findViewById(R.id.mainLayout),
                                "Wybrano: " + selected,
                                Snackbar.LENGTH_LONG).show();
                    }
                }
        );

        btnAdd.setOnClickListener(v -> {
            String entry = etEntry.getText().toString().trim();

            if (entry.isEmpty()) {
                etEntry.setError("Wpisz coś");
                return;
            }

            entries.add(entry);
            etEntry.setText("");
            updateList();
        });

        btnRandom.setOnClickListener(v -> {
            if (entries.isEmpty()) {
                Snackbar.make(findViewById(R.id.mainLayout),
                        "Najpierw dodaj przynajmniej jeden wpis",
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, RandomActivity.class);
            intent.putExtra(RandomActivity.EXTRA_ENTRIES, entries.toArray(new String[0]));

            randomLauncher.launch(intent);
        });
    }

    private void updateList() {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < entries.size(); i++) {
            text.append(i + 1).append(". ").append(entries.get(i)).append("\n");
        }

        tvList.setText(text.toString());
    }
}
