package com.example.dogbreed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.widget.Spinner;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class IdentifyTheBreed extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static final long countdown_millis = 10000;
    private CountDownTimer countDownTimer;
    private long timeLeft;

    private static final String key_spinner= "keySpinner";
    private static final String key_breedSubmit= "keyBreedSubmit";
    private static final String key_image= "keyImage";
    private static final String key_correctAnswer= "keyCorrectAnswer";
    private static final String key_answerStatus= "keyAnswerStatus";
    private static final String key_countdown= "keyCountdown";

    TextView countdownText;
    Button breedBtn;
    String spinner;
    String image;
    ImageView imageView;
    TextView correct_answer;
    TextView status;
    private String dogName;
    boolean statusCountdown;

    public ArrayList<String> breedArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_breed);
        getTypesOfBreeds(); //Get the arrayList
        run(); //execute the run method
    }

    //add breeds to the array
    public void getTypesOfBreeds() {
        this.breedArray.add("papillon");
        this.breedArray.add("appenzeller");
        this.breedArray.add("basenji");
        this.breedArray.add("boxer");
        this.breedArray.add("briard");
        this.breedArray.add("chow");
        this.breedArray.add("clumber");
        this.breedArray.add("japanese_spaniel");
        this.breedArray.add("kuvasz");
        this.breedArray.add("labrador");
        this.breedArray.add("lhasa");
        this.breedArray.add("chihuahua");
        this.breedArray.add("japanese_spaniel");
        this.breedArray.add("pekinese");
        this.breedArray.add("beagle");
        this.breedArray.add("pomeranian");
        this.breedArray.add("rottweiler");
    }

//    public void selectSubmit(View view) {
//        String submit = "Submit";
//        if (submit.equals(breedBtn.getText())) {
//            getSpinnerValue(imageView);
//            breedBtn.setText("Next");
//        } else {
//            breedBtn.setText("Submit");
//        }
//    }

    // https://stackoverflow.com/questions/41479017/how-to-get-id-of-images-in-drawable
    //check weather the getting imageId is exist
    protected final static int getBreedImageId(final String imageName, final String imageType, final Context context) {
        final int imageResource = context.getResources().getIdentifier(imageName, imageType, context.getApplicationInfo().packageName);
        if (imageResource == 0) {
            throw new IllegalArgumentException("Image not found with" + imageName);

        } else {
            return imageResource;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner = parent.getItemAtPosition(position).toString(); // invoked when an item in this view has been selected

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void run() {
        Bundle bundle = getIntent().getExtras();
         statusCountdown = bundle.getBoolean("countDownStatus"); //Get status value from the Main Activity
        System.out.println(statusCountdown);
        if (statusCountdown) {
            timeLeft = countdown_millis;
            startCountdown();
            start();
        } else {
            start();
        }
    }

    public void start() {
        final ImageView breedImage = findViewById(R.id.breed_imageView); //find image view from the layout and inflated it
        dogName = getRandomBreed();

        int numberOfDogImagesPerBreed = 30;  //Assign number of images  per breed
        image = dogName + "_" + getRandomNumber(1, numberOfDogImagesPerBreed); //assign a variable according to the number of the dogs name and number between 1 and 30

        //get the images from the drawable directory and set to breedImage
        breedImage.setImageDrawable(getResources().getDrawable(getBreedImageId(image, "drawable", getApplicationContext())));//get the images from the drawable directory
        Spinner spinner1 = findViewById(R.id.selectDogBreed); //find spinner from the layout and inflated it

        if (spinner1 != null) {
            //if select an item from the spinner then run the method
            spinner1.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.breed, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        if (spinner1 != null) {
            spinner1.setAdapter(adapter);
        }

        //Set the button label to Submit
        imageView = findViewById(R.id.imageView); //find image view from the layout and assign it
        breedBtn = findViewById(R.id.breedSubmit);//find image view from the layout and assign it

        breedBtn.setText("Submit"); //Set the button label to Submit

    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min; //get the values between 0 and this bound
    }

    private String getRandomBreed() {
        return breedArray.get(getRandomNumber(0, (breedArray.size() - 1))); //get the integer between 0 and size of the breed array-1
    }

//    https://www.youtube.com/watch?v=bLUXfWkZMD8
    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateCountdownText();
                onClickBreedButton(breedBtn); //when time left is zero call the onclick breed button method
            }
        }.start();
    }

//    https://www.youtube.com/watch?v=bLUXfWkZMD8
    private void updateCountdownText() {
        int minutes = (int) (timeLeft / 1000) / 60; //get the minutes
        int seconds = (int) (timeLeft / 1000) % 60; //get the seconds

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownText = findViewById(R.id.breed_countdown); //get the breed countdown from the layout.xml and assign it to variable
        countdownText.setTextColor(Color.RED); // set colour to red
        countdownText.setText(timeFormatted); // set the timeformatted value to countdown text


    }

    public void onClickBreedButton(View view) {
        if(statusCountdown){
            countDownTimer.cancel(); //cancel the countdown timer is status countdown is true
        }

        if (breedBtn.getText().equals("Submit")) {
            breedBtn.setText("Next");
            if (spinner.equals(dogName)) {
                String result = " CORRECT! ";
                status = findViewById(R.id.answer_status_breed); //Get the text view answer status breed from the layout.xml and assign it to a variable
                status.setTextColor(Color.GREEN); //set green in color
                status.setText(result); //set status text to result


            } else {
                String result = " WRONG!!! ";
                status = findViewById(R.id.answer_status_breed);//Get the text view answer status breed from the layout.xml and assign it to a variable
                status.setTextColor(Color.RED);//set red in color
                status.setText(result); //set status text to result

                //display the correct answer in text view
                correct_answer = findViewById(R.id.correct_answer_breed);
                correct_answer.setTextColor(Color.BLUE);//set blue in color
                correct_answer.setText(dogName.toUpperCase()); //set to uppercase
            }
        } else {
            run();
            status = findViewById(R.id.answer_status_breed);
            status.setText(""); //display blank in status text view

            correct_answer = findViewById(R.id.correct_answer_breed);
            correct_answer.setText(""); //display blank in correct answer text view

        }
    }

    //final call from the activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); //cancel the countdown if countdown timer is not null
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putInt(key_answerStatus,status);
//        outState.putInt(key_answerStatus,key_answerStatus);
//    }
}
