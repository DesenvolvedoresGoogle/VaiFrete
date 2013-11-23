package br.com.vaifrete;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class UtilManager 
{
	
	
	 public static Drawable getRoundedShape(Bitmap scaleBitmapImage) 
	 {
		  Drawable verticalImage = new BitmapDrawable();
		  verticalImage = new BitmapDrawable(getCroppedBitmap(scaleBitmapImage, 400));
		  
		  return verticalImage;
		 }
	 
	 public static Drawable drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException 
	 {
		if (url == null) {
			Bitmap x;
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.setRequestProperty("User-agent", "Mozilla/4.0");
			connection.connect();
			InputStream input = connection.getInputStream();
			x = BitmapFactory.decodeStream(input);
			return new BitmapDrawable(x);
		}
		return null;
	}
	 
	
	 public static Bitmap fixOrientation(Bitmap mBitmap,String photoPath)  {
		 
		 
		 //usado para consertar o posicionamento da Foto com EXIF
		 
		 ExifInterface ei =null;
		
		 try {
			ei = new ExifInterface(photoPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Log.d("LOG VaiFRete","Rotacionandol imagem");
		 
		 int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		
		 System.out.println(orientation);
		 Matrix matrix = new Matrix();
		 // 1  - 8 - 6
		 switch(orientation) {
		 
		     case ExifInterface.ORIENTATION_NORMAL:
		    	 	
			        mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			        System.out.println("Rotacionando nada");
		         break;
		         
		     case ExifInterface.ORIENTATION_ROTATE_270:
			        matrix.postRotate(-90);
			        mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			        System.out.println("Rotacionando -90");
		         break;
		         
		     case ExifInterface.ORIENTATION_ROTATE_90:
			       
		    	 	matrix.postRotate(90);
			        mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			        System.out.println("Rotacionando 90");
		         break;     
		     // etc.
		 }
		    return mBitmap;
		}
	 
	 public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		    
		 Bitmap sbmp;
		    if(bmp.getWidth() != radius || bmp.getHeight() != radius)
		        sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		    else
		        sbmp = bmp;
		    Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
		            sbmp.getHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(output);

		    final int color = 0xffa19774;
		    final Paint paint = new Paint();
		    final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		    paint.setAntiAlias(true);
		    paint.setFilterBitmap(true);
		    paint.setDither(true);
		    
		    canvas.drawARGB(0, 0, 0, 0);
		    paint.setColor(Color.parseColor("#BAB399"));
		    canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
		            sbmp.getWidth() / 2+0.1f, paint);
		    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		    canvas.drawBitmap(sbmp, rect, rect, paint);

//		    return fixOrientation(output);
            return output;
		}

}
