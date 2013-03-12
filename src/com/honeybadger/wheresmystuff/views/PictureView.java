package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureView extends Activity {
	
	private static int LoadImage = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view);
		
		//intent for opening gallery
	    Intent intent = new Intent(Intent.ACTION_PICK,
	    		android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	     
	   startActivityForResult(intent, LoadImage);
	   
	   //sharing image from gallery
	   ImageView iv = (ImageView) findViewById(R.id.imageView1);
	   iv.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
	}
	

}
