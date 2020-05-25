package com.example.dogbreed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchDogBreed extends AppCompatActivity {

    TextView searchBreedText;
    int count = 1;
    boolean slideShow = true;
    ImageView image;
    public ArrayList<String> breedArray = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog_breed);
        getTypesOfBreeds(); //get the array list
        searchBreedText = findViewById(R.id.search_breed); //get search breed text from layout.xml
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

    //https://stackoverflow.com/questions/41479017/how-to-get-id-of-images-in-drawable
    protected final static int getBreedImageId(final String imageName, final String imageType, final Context context) {
        final int imageResource = context.getResources().getIdentifier(imageName, imageType, context.getApplicationInfo().packageName);
        if (imageResource == 0) {
            throw new IllegalArgumentException("Image not found with " + imageName);

        } else {
            return imageResource;
        }
    }


        // java delay method from stackoverflow (https://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android)
        public void delay ( int seconds, final ImageView image, final String breedType){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String imageName = breedType + "_" + count;
                    if (slideShow==true) {
                        //Get the images from the drawable folder
                        image.setImageDrawable(getResources().getDrawable(getBreedImageId(imageName, "drawable", getApplicationContext())));
                        image.animate(); //apply animate to the image
                        if (count > 30) {
                            count = 1; // if count is greater than 30 then set count to 1 again
                        } else {
                            count++;
                        }
                        delay(5, image, breedType);
                    }

                }
            }, seconds * 1000); // afterDelay execute after 5 seconds
        }





        public void onClickSubmitBtn(View view) {

            String selectedBreed = searchBreedText.getText().toString();
            //if text field is empty, make a toast
            if (selectedBreed.isEmpty()) {
                Toast.makeText(getBaseContext(), "Enter a Breed", Toast.LENGTH_SHORT).show();
                return;
            }
            // if invalid breed entered, make a toast
            else if(!breedArray.contains(selectedBreed)){
                Toast.makeText(getBaseContext(), "Invalid Breed", Toast.LENGTH_SHORT).show();
                return;
            } else {

                ImageView image = findViewById(R.id.slideshow);
                slideShow = true;

                String imageName = selectedBreed + "_" + count;
                image.setImageDrawable(getResources().getDrawable(getBreedImageId(imageName, "drawable", getApplicationContext())));
                count++;
                delay(5, image, selectedBreed);
            }
        }

    public void onClickStopBtn (View view){
        slideShow = false;
        searchBreedText = findViewById(R.id.search_breed);
        searchBreedText.setText(""); //set text blank

        image = findViewById(R.id.slideshow);
        image.setImageResource(android.R.color.transparent); //set image transparent

    }

}
