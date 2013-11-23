package br.com.vaifrete;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class MapaActivity extends Activity {
	private LocationManager locationManager ;
	private MyLocationListener lListener;
	static final LatLng POINT = new LatLng(0, 0);
	static final LatLng ME = new LatLng(0, 0);
    // Google Map
    private GoogleMap googleMap;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
 
        getActionBar().setTitle("VaiFrete!");
	    getActionBar().setIcon(R.drawable.botao);
       initilizeMap();
       
   	 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
   	lListener = new MyLocationListener();
   	Location loc = null;
	String provider = "1";
	String addressUser ="Bairro";
			
	  //Try to get it from the GPS_PROVIDER
	  if (loc == null)
	  {
	    loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
	  }
	
	  if(loc == null){
			loc =   locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
	  }
	
       
       
       new MakeRequest().execute("");
       
    }
    
    private class MakeRequest extends AsyncTask<Object, String, String>
    {
        //instantiates httpclient to make request
       @Override
    protected String doInBackground(Object... params) {
    	// TODO Auto-generated method stub
    	   
    	   DefaultHttpClient httpclient = new DefaultHttpClient();

           //url with the post data
           HttpPost httpost = new HttpPost(Constants.urlservice+"/DeliveryService.update_position");

           //convert parameters into JSON object
           								//add native email android	//posicao				//posicao2
           String u = "{\"user_email\": \"juliow2@gmail.com\", \"last_lat\":\"40.9999999\", \"last_lng\":\"-73.999999\" }";

           //passes the results to a string builder/entity
           StringEntity se = null;
			try {
			se = new StringEntity(u);
			} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

           //sets the post request as the resulting string
           httpost.setEntity(se);
           //sets a request header so the page receving the request
           //will know what to do with it
           httpost.setHeader("Accept", "application/json");
           httpost.setHeader("Content-type", "application/json");
//           		"   http://vaifreteapp.appspot.com/DeliveryService.update_position
           //Handles what is returned from the page 
           ResponseHandler responseHandler = new BasicResponseHandler();
           try {
			httpclient.execute(httpost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
    	   
    }
    }
    
    
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
    	
    	 if (googleMap == null) {
             googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                     br.com.vaifrete.R.id.map)).getMap();
  
             // check if map is created successfully or not
             if (googleMap == null) {
                 Toast.makeText(getApplicationContext(),
                         "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                         .show();
             }
         }
    	
    	 
    	 AsyncHttpClient client = new AsyncHttpClient();
		    RequestParams params = new RequestParams();
		    params.put("limit", "10");
		    
		    Marker eu = googleMap.addMarker(new MarkerOptions().position(new LatLng(-23.600959,-46.681179))
	     	        .title("SUA POSIÇÃO"));
	     	   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.600959,-46.681179), 15));

		    client.addHeader("Content-Type", "application/json");
		    client.post(Constants.urlservice+"/DeliveryService.get_deliveries", new AsyncHttpResponseHandler(){

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					
					System.out.println("get call http");
					System.out.println(arg0);
					Gson gson = new Gson();
					Type token = new TypeToken<List<Frete>>(){}.getType();
					 arg0 = arg0.replace("{\"deliveries\":\"", "" );
					 arg0 = arg0.trim().substring(0, arg0.length()-1);
					 String g = arg0.replace("{\"deliveries\":", "");
					 System.out.println("TRATADO...");
					 System.out.println(g);
					 List<Frete> list = gson.fromJson(g.trim(), token);
					 System.out.println(list.size());
					System.out.println("das sad dasa da");
	               
					
					
					for (Frete frete : list) {
						
						
					     LatLng o = new LatLng(Double.valueOf(frete.dest_lat), Double.valueOf(frete.dest_lng));
						 Marker k = googleMap.addMarker(new MarkerOptions()
					     	        .position(o)
					     	        .title(""+frete.deliver_id)
					     	        .snippet(frete.destination_adress).icon(BitmapDescriptorFactory
						     	            .fromResource(R.drawable.caminhaogif)));
					     	       
					     	    // Move the camera instantly to hamburg with a zoom of 15.

					     	    // Zoom in, animating the camera.
					     	  googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
						
					     	  
					}
					

					super.onSuccess(arg0);
				}
		    	
		    	
		    });
		    
    	 
    	 
		  googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getApplicationContext(), "TESTE", Toast.LENGTH_LONG).show();
				
				System.out.println( marker.getTitle());
				Intent i = new Intent(getApplicationContext(),DetailFreteActivity.class);
				i.putExtra("id", marker.getTitle());
				i.putExtra("info", marker.getSnippet());
				startActivity(i);
				return false;
			}
		});
       
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    if(item.getItemId() == R.id.action_settings){
	    	Intent i = new Intent(getApplicationContext(), CadastroFotoActivity.class);
	        startActivity(i);
	    }
	    if(item.getItemId() == R.id.action_rating){
	    	Intent i = new Intent(getApplicationContext(), ComoEstouActivity.class);
	        startActivity(i);
	    }
	    
    return true;
	}
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    private class MyLocationListener implements LocationListener 
   	{
   	    @Override
   	    public void onLocationChanged(Location location) 
   	    {
   	        if (location != null) 
   	        {
   	        	
   	        	Log.d("LOCATION CHANGED", location.getLatitude() + "");
   	        	Log.d("LOCATION CHANGED", location.getLongitude() + "");
   	            Toast.makeText(getApplicationContext(),"euu:\nLatitude:"+location.getLatitude() + "\nLogitude:" + location.getLongitude(),Toast.LENGTH_LONG).show();
   	        	System.out.println(location.getLatitude());
   	        	
   	        }
   	    }

   	    @Override
   	    public void onProviderDisabled(String provider) {
   	        // TODO Auto-generated method stub

   	    }

   	    @Override
   	    public void onProviderEnabled(String provider) {
   	        // TODO Auto-generated method stub

   	    }

   	    @Override
   	    public void onStatusChanged(String provider, int status, Bundle extras) {
   	        // TODO Auto-generated method stub

   	    }
   	}
 
}
