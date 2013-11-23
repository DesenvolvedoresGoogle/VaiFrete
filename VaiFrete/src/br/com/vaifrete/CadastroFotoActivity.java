package br.com.vaifrete;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CadastroFotoActivity extends Activity {
	
	ImageView im;
    Uri imageUri;

	
	 public void takePhoto() 
	    {
	        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	        File photo = new File(Environment.getExternalStorageDirectory(),  "/VaiFrete/profile.jpg");
	        intent.putExtra(MediaStore.EXTRA_OUTPUT,
	                Uri.fromFile(photo));
	        File folder = new File(Environment.getExternalStorageDirectory() + "/VaiFrete");

	        if(!folder.exists())
	         {
	             if(folder.mkdir()) 
	               {
	                 System.out.println("diretorio criado com sucesso");
	               
	               }else{
	            	   System.out.println("prblemas ao criar diretorio criado com sucesso");
	               }

	         }
	        imageUri = Uri.fromFile(photo);
	        System.out.println(photo.getAbsolutePath());
	        System.out.println(photo.getPath());
	        startActivityForResult(intent, 100);
	    }
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
	        System.out.println("Foto recebida com sucesso!");
	    	System.out.println(resultCode);
	    	if(data!=null)System.out.println("data eh diferente de nulo");
	    	if(data!=null)System.out.println( data.getData());
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	    	options.inPurgeable=true;
	    	options.inSampleSize =  3;
	    	Bitmap largeIcon = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/VaiFrete/profile.jpg",options);
	    	largeIcon =	UtilManager.fixOrientation(largeIcon, Environment.getExternalStorageDirectory()+"/VaiFrete/profile.jpg");
		   	Drawable photo =  UtilManager.getRoundedShape(largeIcon);	
		   	im.setBackgroundDrawable(photo);
	    }
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_step2);
		
		
	    im = (ImageView) findViewById(R.id.navmenuprofile_photo);
	   
	   Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.person2);
	   Drawable photo =  UtilManager.getRoundedShape(largeIcon);
	   im.setBackgroundDrawable(photo);	
	   getActionBar().setTitle("VaiFrete!");
	   getActionBar().setIcon(R.drawable.botao);
	   
	   
	   im.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			takePhoto();
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	

}
