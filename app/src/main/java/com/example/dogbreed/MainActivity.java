package com.example.dogbreed;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    Switch counter;
    boolean status=false; //set countdown off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the background music
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.setLooping(false); //disable looping
        mediaPlayer.setVolume(70, 70); //set the default volume
        mediaPlayer.start();

        counter=(Switch)findViewById(R.id.countdown_switch); //Get the countdown switch value as switch type
        counter.setChecked(status);
    }


    public void OpenIdentifyTheBreed(View view) {
        Intent intent = new Intent(this,IdentifyTheBreed.class);
        intent.putExtra("countDownStatus", status); //pass status boolean value to IdentifyTheBreed class
        startActivity(intent);
    }

    public void OpenIdentifyTheDog(View view) {
        Intent intent = new Intent(this,IdentifyTheDog.class);
        intent.putExtra("countDownStatus", status);  //pass status boolean value to IdentifyTheDog class
        startActivity(intent);
    }

    public void OpenSearchDogBreed(View view) {
        Intent intent = new Intent(this,SearchDogBreed.class);
        startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.release(); //stop the background music

    }

    public void checkCounter(View view) {
        status=!status; //convert status value to opposite
        counter.setChecked(status);
        System.out.println(status);

    }

}
