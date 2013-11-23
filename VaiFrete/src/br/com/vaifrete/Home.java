package br.com.vaifrete;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Home extends Activity {

	private static int size = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		getActionBar().setTitle("VaiFrete!");
	    getActionBar().setIcon(R.drawable.botao);
	    
		TextView vi = (TextView) findViewById(R.id.ofertasfrete);
		vi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i  = new Intent(getApplicationContext(), HelpFragment.class); startActivity(i);
			}
		});
		
		//Servico para retornar novos fretes de 5 em 5 minutos..
//		final Handler handler = new Handler(); 
//        Runnable runable = new Runnable() { 
//
//            @Override 
//            public void run() { 
//                try{
//                    Toast.makeText(getApplicationContext(), "call new fretes", Toast.LENGTH_LONG).show();
//                    
//                    AsyncHttpClient client = new AsyncHttpClient();
//        		    RequestParams params = new RequestParams();
//        		    client.addHeader("Content-Type", "application/json");
//        		    client.post("http://vaifreteapp.appspot.com/DeliveryService.get_deliveries", new AsyncHttpResponseHandler(){
//
//        				@Override
//        				public void onSuccess(String arg0) {
//        					// TODO Auto-generated method stub
//        					
//        					 Gson gson = new Gson();
//        					 Type token = new TypeToken<List<Frete>>(){}.getType();
//        					 arg0 = arg0.replace("{\"deliveries\":\"", "" );
//        					 arg0 = arg0.trim().substring(0, arg0.length()-1);
//        					 String g = arg0.replace("{\"deliveries\":", "");
//        					 System.out.println("TRATADO...");
//        					 System.out.println(g);
//        					 List<Frete> list = gson.fromJson(g.trim(), token);
//        					 
//        					 //verificand novas notificacoes
//        					 if(size!=list.size())
//        						 triggerNotification(null,0);        					
//        					
//        					size = list.size();
//        					super.onSuccess(arg0);
//        				}
//        		    });
//                    
//                    handler.postDelayed(this, 300000);
//                }
//                catch (Exception e) {
//                    // TODO: handle exception
//                }
//                finally{
//                    //also call the same runnable 
//                    handler.postDelayed(this, 300000); 
//                }
//            } 
//        }; 
//        handler.postDelayed(runable, 300000); 
	}
	private void triggerNotification(String string, int i)
	{
	    CharSequence title = "Novo Frete Cadastrado!";
	    CharSequence message ="Verifique no mapa os novos fretes";

	    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    Notification notification = new Notification(R.drawable.ic_launcher, "Novo Frete!", System.currentTimeMillis());

	    Intent notificationIntent = new Intent(this, MapaActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

	    notification.setLatestEventInfo(Home.this, title, message, pendingIntent);
	    notificationManager.notify(1010, notification);
	}
	
	private void triggerNotification2(String string, int i)
	{
		
		//Som da notificacao
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	    Intent notificationIntent = new Intent(this, Home.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

	    NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(this)
	            .setContentIntent(pendingIntent)
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setContentTitle("Existem novos Fretes Cadastrados")
                .setSound(soundUri)
	            .setContentText("Entre no mapa e veja novos fretes cadastrados!");
	    NotificationManager mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    mgr.notify(999, mBuilder.build());
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
    return true;
	    
	}
}
