package com.example.thbuoi8bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnStop;
    MediaRecorder recorder;
    static  final String TAG = "MediaRecording";
    File audioFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

    }

    public void startRecording(View view) throws IOException {
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        File dir = getExternalFilesDir(null);
        try {
            audioFile = File.createTempFile("sound", ".3gp", dir);
        }catch (Exception e) {
            Log.e(TAG, "external storage access error!!!");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioFile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    public void stopRecording(View view) {
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        recorder.stop();
        recorder.release();
        addRecordedToMediaLib();
    }

    protected void addRecordedToMediaLib() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio_"+audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current/1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

        ContentResolver resolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = resolver.insert(base, values);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(this, "Added File [" + newUri + "] successfully", Toast.LENGTH_LONG).show();
    }
}