package br.com.vaifrete;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DetailFreteActivity extends Activity {

	TextView tv ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);
		
		final String id = (String) getIntent().getExtras().get("id");
	    Button b = (Button)findViewById(R.id.dialogButtonOK);
	    tv = (TextView) findViewById(R.id.textAguarde);
	    
	    
	    b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				 final ListView listview = (ListView) findViewById(R.id.list);
//				   
				    AsyncHttpClient client = new AsyncHttpClient();
				    RequestParams params = new RequestParams();
				    params.put("user_email", "f@teste.com");
				    System.out.println(id);
				    params.put("deliver_key",id);
				    params.put("offer_info", "10reias");
				    
				    new MakeRequestGetFrete().execute("");
//				    client.addHeader("Content-Type", "application/json");
//				    client.post(Constants.urlservice+"/DeliveryService.put_offer",params, new AsyncHttpResponseHandler(){
//				   
//			});

			}
	    });
	    
	    getActionBar().setTitle("VaiFrete!");
	    getActionBar().setIcon(R.drawable.botao);
	}
	  private class MakeRequestGetFrete extends AsyncTask<Object, String, String>
	    {
	        //instantiates httpclient to make request
	       @Override
	    protected String doInBackground(Object... params) {
	    	// TODO Auto-generated method stub
	    	   
	    	   DefaultHttpClient httpclient = new DefaultHttpClient();

	           //url with the post data
	           HttpPost httpost = new HttpPost(Constants.urlservice+"/DeliveryService.put_offer");

	           //convert parameters into JSON object
//	           11-23 13:06:07.264: I/System.out(4406): 5154321532452864

	           String u = "{\"user_email\": \"banana@gmail.com\", \"deliver_key\":\"5154321532452864\", \"offer_info\":\"73.999999\" }";

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
}
	    
