package br.com.vaifrete;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ComoEstouActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_como_estou);
		 getActionBar().setTitle("VaiFrete!");
		 getActionBar().setIcon(R.drawable.botao);
		
		 //moch pegar do appengine quando a cota liberar
		 List<String> list = new ArrayList<String>();
		 list.add("Muito Bom : luizfelipetx@gmail.com");
		 list.add("Muito Bom : juca@gmail.com");
		 list.add("Muito Bom : felpz@gmail.com");
		 list.add("Muito Bom : diego@gmail.com");
		 list.add("Muito Bom : julio@gmail.com");
		 list.add("Muito Bom : rafael@gmail.com");
		 
	    final ListView listview = (ListView) findViewById(R.id.listView1);

	    
	    RecomenderAdapter adapter = new RecomenderAdapter(getApplicationContext(),false,list);
		listview.setAdapter(adapter);
	    
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

}
