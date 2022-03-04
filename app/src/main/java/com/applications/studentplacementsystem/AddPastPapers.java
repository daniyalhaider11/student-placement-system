package com.applications.studentplacementsystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class AddPastPapers extends AppCompatActivity {

    Button SelectButton, UploadButton;

    EditText PdfNameEditText,PdfNameYearText ;

    Uri uri;

    public static final String PDF_UPLOAD_HTTP_URL = "#";

    public int PDF_REQ_CODE = 1;

    String PdfNameHolder, PdfPathHolder, PdfID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_past_papers);

        AllowRunTimePermission();

        SelectButton = (Button) findViewById(R.id.btnselectfile);
        UploadButton = (Button) findViewById(R.id.btnuploadfile);
        PdfNameEditText = (EditText) findViewById(R.id.pastpapername);
        PdfNameYearText = (EditText) findViewById(R.id.pastpaperyear);

        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // PDF selection code start from here .

                Intent intent = new Intent();

                intent.setType("application/pdf");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                PdfUploadFunction();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            SelectButton.setText("PDF is Selected");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void PdfUploadFunction() {

        PdfNameHolder = PdfNameEditText.getText().toString().trim();

        PdfPathHolder = FilePath.getPath(this, uri);

        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        } else {

            try {

                PdfID = UUID.randomUUID().toString();

                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", PdfNameHolder)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void AllowRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(AddPastPapers.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(AddPastPapers.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(AddPastPapers.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AddPastPapers.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(AddPastPapers.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}