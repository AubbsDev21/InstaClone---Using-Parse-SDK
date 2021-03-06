package com.example.instaclone;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;

//Will probably be the base activity for the Action Bar

public class Welcome extends Activity {

	Button clickButton;
	Button logoutButton;
	EditText titleText;
	ImageView imageV;
	Pic pic;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		ParseUser currentUser = ParseUser.getCurrentUser();
		

		clickButton = (Button) findViewById(R.id.click);
		logoutButton = (Button) findViewById(R.id.logout_button);
		titleText = (EditText) findViewById(R.id.title_text);
		imageV = (ImageView) findViewById(R.id.image_view);


		clickButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			    }
			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				Intent intent = new Intent(Welcome.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        imageV.setImageBitmap(imageBitmap);
	        
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray(); // got the image file
            
            ParseFile file = new ParseFile("androidbegin.png", image);
            pic = new Pic();
    		pic.setTitle(titleText.getText().toString());
    		pic.setAuthor(ParseUser.getCurrentUser());
            pic.setPhotoFile(file);
            pic.saveInBackground();
	        
	    }
	}
	
	
	
}

