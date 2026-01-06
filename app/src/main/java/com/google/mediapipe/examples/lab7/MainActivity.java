package com.google.mediapipe.examples.lab7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;

    private ActivityResultLauncher<ScanOptions> barLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button_scan);

        barLauncher = registerForActivityResult(new com.journeyapps.barcodescanner.ScanContract(), result -> {
            if (result.getContents() != null) {
                handleScanResult(result.getContents());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                } else {
                    startScan();
                }
            }
        });
    }

    private void startScan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Скан кода");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Требуется разрешение")
                        .setMessage("Разрешение камеры необходимо для сканирования")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }
    private void handleScanResult(String contents) {
        Log.d("ScanResult", "Contents: " + contents);
        Toast.makeText(this, "Считано: " + contents, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ResultActivity.class);
        switch (contents.toLowerCase()) {
            case "house":
                intent.putExtra("title", "house");
                intent.putExtra("imageRes", R.drawable.house);
                intent.putExtra("description", "Дом");
                break;
            case "car":
                intent.putExtra("title", "car");
                intent.putExtra("imageRes", R.drawable.car);
                intent.putExtra("description", "Машина");
                break;
            case "tree":
                intent.putExtra("title", "tree");
                intent.putExtra("imageRes", R.drawable.tree);
                intent.putExtra("description", "Дерево");
                break;
            default:
                intent.putExtra("title", "unknown");
                intent.putExtra("imageRes", R.drawable.unknown);
                intent.putExtra("description", "Неизвестно");
                break;
        }
        startActivity(intent);
    }
}