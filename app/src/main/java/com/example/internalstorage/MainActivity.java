package com.example.internalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";
    EditText mEditText;
    EditText fileName;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text);
        fileName=findViewById(R.id.fileName);
        text=findViewById(R.id.text);

        isExternalStorageWritable();
    }
    public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean isExternalStorageWritable(){
if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
    Log.i("State", "yes,it is writable");
    return true;
}else {
    return false;
}

    }
  public void writeFile(View v){
 if (isExternalStorageWritable()&&checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
     File textfile = new File(Environment.getExternalStorageDirectory(), fileName.getText().toString());
     try {
         FileOutputStream fos = new FileOutputStream(textfile);
         fos.write(text.getText().toString().getBytes());
         fos.close();

         Toast.makeText(this, "file saved", Toast.LENGTH_SHORT).show();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }else {
     Toast.makeText(this, "can't erite to externalStorage", Toast.LENGTH_SHORT).show();

 }

  }
public boolean checkPermission(String permission){
      int check= ContextCompat.checkSelfPermission(this,permission);
      return (check== PackageManager.PERMISSION_GRANTED);

}

}
