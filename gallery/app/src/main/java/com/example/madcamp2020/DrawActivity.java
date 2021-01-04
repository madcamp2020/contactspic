package com.example.madcamp2020;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    // 그림을 그릴 CustomView
    private DrawingView drawingView;

    // 색상 선택 버튼
    private ImageButton[] colorImageButtons;

    // 초기화 버튼, 저장 버튼
    private Button resetButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        tedPermission();

        // 변수 초기화
        drawingView = (DrawingView) findViewById(R.id.drawingView);
/*
        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        Drawable d = new BitmapDrawable(getResources(), image);

        drawingView.setBackground(d);
*/
        Intent intent = getIntent();
        byte[] b = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        Drawable d = new BitmapDrawable(getResources(), bitmap);

        drawingView.setBackground(d);

        colorImageButtons = new ImageButton[3];
        colorImageButtons[0] = (ImageButton) findViewById(R.id.blackColorBtn);
        colorImageButtons[1] = (ImageButton) findViewById(R.id.redColorBtn);
        colorImageButtons[2] = (ImageButton) findViewById(R.id.blueColorBtn);
        for (ImageButton colorImageButton : colorImageButtons) {
            colorImageButton.setOnClickListener(this);
        }

        resetButton = (Button) findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(this);
    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한 요청 성공 : 여기 코드 작성
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blackColorBtn:
                drawingView.setColor(Color.BLACK);
                break;
            case R.id.redColorBtn:
                drawingView.setColor(Color.RED);
                break;
            case R.id.blueColorBtn:
                drawingView.setColor(Color.BLUE);
                break;
            case R.id.resetBtn:
                drawingView.reset();
                break;
            case R.id.saveBtn:
                Bitmap draw = drawingView.save(DrawActivity.this);
                Log.d("fragment4", draw.toString());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                draw.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent();
                intent.putExtra("draw", bytes);
                setResult(RESULT_OK, intent);

                finish();
                break;
        }
    }
}
