package com.example.dogbreed;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class IdentifyTheDog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public ArrayList<String> breedArray = new ArrayList<>();
    TextView status;
    TextView result;
    Button dogButton;
    String image;
    String img1;
    String img2;
    String img3;
    String selectImage;

    ImageView imageName;
    String selectedBreedType;
    String mOrderMessage;
    private static final long countdown_millis = 10000;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    TextView countdownText;
    boolean statusCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_dog);
        getTypesOfBreeds(); // Get the arraylist
        run();
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

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        //get the values between 0 and this bound
        return random.nextInt((max - min) + 1) + min;
    }

    public String getRandomBreed() {
        //get the integer between 0 and size of the breed array-1
        return breedArray.get(getRandomNumber(0, (breedArray.size() - 1)));
    }

    //https://stackoverflow.com/questions/41479017/how-to-get-id-of-images-in-drawable
    //check weather the getting imageId is exist
    protected final static int getBreedImageId(final String imageName, final String imageType, final Context context) {
        final int imageResource = context.getResources().getIdentifier(imageName, imageType, context.getApplicationInfo().packageName);
        if (imageResource == 0) {
            throw new IllegalArgumentException("Image not found with " + imageName);

        } else {
            return imageResource;
        }
    }


    public ArrayList<String> getRandomPickerArray() {
        ArrayList<String> arrayList = new ArrayList<>();
        // get three breeds from the breed array and add them to the arrayList if that breed not exist in the arraylist
        do {
            String oneTimeDogBreed = getRandomBreed(); // get the breed
            if (arrayList.indexOf(oneTimeDogBreed) == -1) {
                arrayList.add(oneTimeDogBreed); //if the breed not exist, add it to the array
            }
        } while (arrayList.size() != 3); //only take three breeds

        return arrayList;
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
        ImageView image1 = findViewById(R.id.imageView1_dog); //Get the image view1 from the layout.xml and assign it to the variable
        ImageView image2 = findViewById(R.id.imageView2_dog); //Get the image view2 from the layout.xml and assign it to the variable
        ImageView image3 = findViewById(R.id.imageView3_dog); //Get the image view3 from the layout.xml and assign it to the variable

        ArrayList<String> dogRandomPicker;
        dogRandomPicker = getRandomPickerArray(); //assign the three different breeds to the dogRandomPicker array list

        int numberOfDogImages = 30;

        selectedBreedType = dogRandomPicker.get(getRandomNumber(0, 2)); //get one of that three breeds


        //get one image per one breed
        image = dogRandomPicker.get(0) + "_" + getRandomNumber(1, numberOfDogImages);
        //get the images from the drawable folder
        image1.setImageDrawable(getResources().getDrawable(getBreedImageId(image, "drawable", getApplicationContext())));
        img1 = image;

        //get one image per one breed
        image = dogRandomPicker.get(1) + "_" + getRandomNumber(1, numberOfDogImages);
        //get the images from the drawable folder
        image2.setImageDrawable(getResources().getDrawable(getBreedImageId(image, "drawable", getApplicationContext())));
        img2 = image;

        //get one image per one breed
        image = dogRandomPicker.get(2) + "_" + getRandomNumber(1, numberOfDogImages);
        //get the images from the drawable folder
        image3.setImageDrawable(getResources().getDrawable(getBreedImageId(image, "drawable", getApplicationContext())));
        img3 = image;

        result = findViewById(R.id.breed_name_dog); //get the text view from the layout.xml and assign it to result variable
        result.setText(selectedBreedType); //set the selected breed to the breed name dog text view

        dogButton = findViewById(R.id.dog_button);
        dogButton.setText("Submit");
    }

    //display message to image view 1
    public void showImage1(View view) {
        imageName = findViewById(R.id.imageView1_dog);
        mOrderMessage = "Image Selected";
        displayToast(mOrderMessage);
        image = img1; //set image to img1
    }

    //display message to image view 2
    public void showImage2(View view) {
        imageName = findViewById(R.id.imageView2_dog);
        mOrderMessage = "Image Selected";
        displayToast(mOrderMessage);
        image = img2; //set image to img2
    }

    //display message to image view 3
    public void showImage3(View view) {
        imageName = findViewById(R.id.imageView3_dog);
        mOrderMessage = "Image Selected";
        displayToast(mOrderMessage);
        image = img3; //set image to img3
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
                onClickDogButton(dogButton); //when time left is zero call the onclick breed button method
            }
        }.start();
    }

    //    https://www.youtube.com/watch?v=bLUXfWkZMD8
    private void updateCountdownText() {
        int minutes = (int) (timeLeft / 1000) / 60; //get the minutes
        int seconds = (int) (timeLeft / 1000) % 60; //get the seconds

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownText = findViewById(R.id.dog_countdown); //get the breed countdown from the layout.xml and assign it to variable
        countdownText.setTextColor(Color.RED); // set colour to red
        countdownText.setText(timeFormatted); // set the timeformatted value to countdown text


    }


    public void onClickDogButton(View view) {
        // System.out.println(image+"________-----____"+result.getText().toString());
        if (statusCountdown) {
            countDownTimer.cancel(); //cancel the countdown timer is status countdown is true
        }

        if (dogButton.getText().equals("Submit")) {
            dogButton.setText("Next");
            if (image.contains(result.getText().toString())) {
                String result = " CORRECT! ";
                status = findViewById(R.id.dog_status);//Get the text view answer status dog from the layout.xml and assign it to a variable
                status.setTextColor(Color.GREEN); //set green in color
                status.setText(result); //set status text to result


            } else {
                String result = " WRONG!!! ";
                status = findViewById(R.id.dog_status); //Get the text view answer status dog from the layout.xml and assign it to a variable
                status.setTextColor(Color.RED);//set red in color
                status.setText(result);//set status text to result

            }
        } else {
            run();
            status = findViewById(R.id.dog_status); //Get the text view answer status dog from the layout.xml and assign it to a variable
            status.setText("");//display blank in status text view

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // invoked when an item in this view has been selected
        selectImage = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Display toast message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    //final call from the activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) { //cancel the countdown if countdown timer is not null
            countDownTimer.cancel();
        }
    }

}
