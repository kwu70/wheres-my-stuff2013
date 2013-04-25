package com.honeybadger.wheresmystuff.views;

import java.io.FileNotFoundException;

import java.io.IOException;

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 
 * This class contains the intent for 
 * adding a picture from gallery or camera to image view
 *  
 * @author Honey Badger
 * @version 1.0
 */
public class PictureActivity extends Activity {
	
	//for selecting intent
	private static int load_image = 1;

	private ImageView iv;
	private static Bitmap Image = null;
	private static Bitmap rotateImage = null;
	private static final int CAMERA_REQUEST = 100;

	/**
	 * Called when the activity is first created.
	 * Creates a click listener for each button in this activity
	 * Set the ImageView to where the picture will be displayed
	 * 
	 * @param savedInstanceState state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_view);
        
        findViewById(R.id.loadPicBtn1).setOnClickListener(new GalleryClickListener());
        findViewById(R.id.loadPicBtn2).setOnClickListener(new CameraClickListener());
        
        iv = (ImageView) findViewById(R.id.imageView1);
	}
	
	/**
	 * This class is a listener for the gallery button. When it is clicked
	 * it takes the user to the gallery
	 * 
	 * @author Honey Badger
	 */
	private class GalleryClickListener implements OnClickListener{

		/**
		 * Activity for detecting if gallery button is pressed or not
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			iv.setImageBitmap(null);
        	//if there is a picture, recycle picture so it don't cause out of memory error
        	if (Image != null)
        	{
        	    Image.recycle();
        	}
        	//intent for accessing the gallery
            Intent i = new Intent();
			i.setType("image/*");
			i.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(i, "Select Picture"), load_image);
		}
	}
	
	/**
	 * This class is a listener for the camera button. When it is clicked
	 * it takes the user to the camera
	 * 
	 * @author Honey Badger
	 */
	private class CameraClickListener implements OnClickListener{

		/**
		 * Activity for detecting if camera button is pressed or not
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			iv.setImageBitmap(null);
        	//if there is a picture, recycle picture so it don't cause out of memory error
        	if (Image != null)
        	{
        	    Image.recycle();
        	}
        	//intent for accessing the camera
        	Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
        	startActivityForResult(cameraIntent, CAMERA_REQUEST);
	    }
	}
	
	/**
	 * Activity for receiving data from the click listener
	 * 
	 * @param requestCode request the status code
	 * @param resultCode status of the intent
	 * @param data the data the is taken from the listener
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap pic = (Bitmap) data.getExtras().get("data"); 
            iv.setImageBitmap(pic);
        }
		
		else if (requestCode == load_image && resultCode == RESULT_OK) {
			Uri imageUri = data.getData();        
		    try 
		    {
		    	Image = Media.getBitmap(this.getContentResolver(), imageUri);
		    	//get the original orientation the photo was taken
		    	if (getOrientation(getApplicationContext(), imageUri) != 0) {
		    		//for setting the rotation of the image
		    		Matrix m = new Matrix();
		    		m.postRotate(getOrientation(getApplicationContext(), imageUri));
		    		
		    		if (rotateImage != null) {
		    			rotateImage.recycle();
		    		}
		    		//set the image to be viewed in the orientation the picture was taken
		    		rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), m,true);
		    		iv.setImageBitmap(rotateImage);
		        }
		        else {
		        	iv.setImageBitmap(Image);
		        }        
			} 
			catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	/**
	 * Activity for getting the original orientation of the picture
	 * 
	 * @param context context for the image
	 * @param imageUri uri of the image
	 */
	public static int getOrientation(Context context, Uri imageUri) {
	
	    Cursor cursor = context.getContentResolver().
	    	query(imageUri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);
	 
	    if (cursor.getCount() != 1) {
	      return -1;
	    }
	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}
}