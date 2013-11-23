package br.com.vaifrete;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ListFreteActivity extends Activity {
	
	List<Frete> listd= new ArrayList<Frete>();
	FreteAdapter adapter;
	 @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_default_estab);

		    final ListView listview = (ListView) findViewById(R.id.list);
		    
		    
		    new MakeRequestGetFrete().execute("");
		    
		    
		    //assinc http client
		    AsyncHttpClient client = new AsyncHttpClient();
		    RequestParams params = new RequestParams();
//		    new LoadFrete().execute("");
		    client.addHeader("Content-Type", "application/json");
//		    client.post(Constants.urlservice+"/DeliveryService.get_my_offers", new AsyncHttpResponseHandler(){
//
//				@Override
//				public void onSuccess(String arg0) {
//					// TODO Auto-generated method stub
//					
//					 Gson gson = new Gson();
//					 Type token = new TypeToken<List<Frete>>(){}.getType();
//					 arg0 = arg0.replace("{\"deliveries\":\"", "" );
//					 arg0 = arg0.trim().substring(0, arg0.length()-1);
//					 String g = arg0.replace("{\"deliveries\":", "");
//					 List<Frete> list = gson.fromJson(g.trim(), token);
//					 System.out.println(list.size());
//					 adapter = new FreteAdapter(getApplicationContext(),false,list);
//					 listview.setAdapter(adapter);
//
//					super.onSuccess(arg0);
//				}
//		    	
//		    });
//	
		    getActionBar().setTitle("VaiFrete!");
		    getActionBar().setIcon(R.drawable.botao);
		    
	}

	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
//			getMenuInflater().inflate(R.menu.list_frete, menu);
			return true;
		}
	 
	  private class MakeRequestGetFrete extends AsyncTask<Object, String, String>
	    {
		  
		  
		  
	        @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
	        	System.out.println("REsult json");
	        	System.out.println(result);
	        	if(result.length()>10){
				Gson gson = new Gson();
				Type token = new TypeToken<List<Frete>>(){}.getType();
				 result = result.replace("{\"deliveries\":\"", "" );
				 result = result.trim().substring(0, result.length()-1);
				 String g = result.replace("{\"deliveries\":", "");
				 List<Frete> list = gson.fromJson(g.trim(), token);
               
				
				
				for (Frete frete : list)
				{
					System.out.println(list.size());
				}
	        	}
			super.onPostExecute(result);
		}

		//instantiates httpclient to make request
	       @Override
	    protected String doInBackground(Object... params) {
	    	// TODO Auto-generated method stub
	    	   
	    	   DefaultHttpClient httpclient = new DefaultHttpClient();

	           //url with the post data
	           HttpPost httpost = new HttpPost(Constants.urlservice+"/DeliveryService.get_my_offers");

	           //convert parameters into JSON object
//	           11-23 13:06:07.264: I/System.out(4406): 5154321532452864

	           String u = "{\"email\": \"felipe@devbus.com\"}";

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
//	           		"   http://vaifreteapp.appspot.com/DeliveryService.update_position
	           //Handles what is returned from the page 
	           ResponseHandler responseHandler = new BasicResponseHandler();
	           try {
	        	   HttpResponse response = httpclient.execute(httpost);
//				httpclient.execute(httpost, responseHandler);
				
				String responseBody = EntityUtils.toString(response.getEntity());
				return responseBody;
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
	
	 
	
	 }
