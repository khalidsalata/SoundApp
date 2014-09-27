package com.khalid.sup;

import java.io.File;
import java.io.IOException;

//import com.khalid.soundsyo.createGetContentIntent;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

   private MediaRecorder myAudioRecorder;
   private String outputFile = null;
   private Button start,stop,play;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      start = (Button)findViewById(R.id.button1);
      stop = (Button)findViewById(R.id.button2);
      play = (Button)findViewById(R.id.button3);

      stop.setEnabled(false);
      play.setEnabled(false);
      outputFile = Environment.getExternalStorageDirectory().
      getAbsolutePath() + "/myrecording.3gp";;

      myAudioRecorder = new MediaRecorder();
      myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
      myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
      myAudioRecorder.setOutputFile(outputFile);

   }

   public void start(View view){
      try {
         myAudioRecorder.prepare();
         myAudioRecorder.start();
      } catch (IllegalStateException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      start.setEnabled(false);
      stop.setEnabled(true);
      Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

   }

   public void stop(View view){
      myAudioRecorder.stop();
      myAudioRecorder.release();
      myAudioRecorder  = null;
      stop.setEnabled(false);
      play.setEnabled(true);
      Toast.makeText(getApplicationContext(), "Audio recorded successfully",
      Toast.LENGTH_LONG).show();
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
   public void play(View view) throws IllegalArgumentException,   
   SecurityException, IllegalStateException, IOException{
   
   MediaPlayer m = new MediaPlayer();
   m.setDataSource(outputFile);
   m.prepare();
   m.start();
   Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

   }

   public static Intent createGetContentIntent() {
       // Implicitly allow the user to select a particular kind of data
       final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
       // The MIME data type filter
       intent.setType("*/*");
       // Only return URIs that can be opened with ContentResolver
       intent.addCategory(Intent.CATEGORY_OPENABLE);
       return intent;
   }
}