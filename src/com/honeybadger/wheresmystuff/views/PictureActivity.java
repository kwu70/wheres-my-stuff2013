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

public class PictureActivity extends Activity {
	
	//for selecting intent
	private static int load_image = 1;

	private ImageView iv;
	private static Bitmap Image = null;
	private static Bitmap rotateImage = null;
	private static final int CAMERA_REQUEST = 100;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_view);
        
        findViewById(R.id.loadPicBtn1).setOnClickListener(new GalleryClickListener());
        findViewById(R.id.loadPicBtn2).setOnClickListener(new CameraClickListener());
        
        iv = (ImageView) findViewById(R.id.imageView1);
	}
	
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
        	
            Intent i = new Intent();
			i.setType("image/*");
			i.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(i, "Select Picture"), load_image);
			finish();
		}
	}
	
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
        	
        	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        	startActivityForResult(cameraIntent, CAMERA_REQUEST);
        	finish();
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap pic = (Bitmap) data.getExtras().get("data"); 
            iv.setImageBitmap(pic);
        }
		
		if (requestCode == load_image && resultCode != 0) {
			Uri imageUri = data.getData();        
		    try 
		    {
		    	Image = Media.getBitmap(this.getContentResolver(), imageUri);
		    	
		    	if (getOrientation(getApplicationContext(), imageUri) != 0) {
		    		Matrix m = new Matrix();
		    		m.postRotate(getOrientation(getApplicationContext(), imageUri));
		    		
		    		if (rotateImage != null) {
		    			rotateImage.recycle();
		    		}
		    		
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
	
	public static int getOrientation(Context context, Uri imageUri) {
		
		String[] filePath = new String[] { MediaStore.Images.ImageColumns.ORIENTATION };
	
	    Cursor cursor = context.getContentResolver().
	    	query(imageUri, filePath,null, null, null);
	 
	    if (cursor.getCount() != 1) {
	      return -1;
	    }
	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}
}