package br.com.vaifrete;

import java.util.List;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecomenderAdapter extends BaseAdapter {
    
	ImageView thumb;
	ProgressBar pb;
    private Activity activity;
    private static LayoutInflater inflater=null;
    private Boolean isNear;
    List<String> fts = null;
    
    public RecomenderAdapter(Context context,Boolean isNearList,List<String> list) 
    {
       fts = list;
        inflater = LayoutInflater.from(context);
        isNear = isNearList;
    }

    public int getCount() {
        return fts.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if(convertView==null)
            view = inflater.inflate(br.com.vaifrete.R.layout.list_default_estab_row, null);
//
        System.out.println("NO ADAPTER");
        System.out.println(fts.size());
        TextView title = (TextView)view.findViewById(br.com.vaifrete.R.id.title);
        TextView subtitle = (TextView)view.findViewById(br.com.vaifrete.R.id.subtitle);
       
        title.setText(fts.get(position).toString().split(":")[0]);
        subtitle.setText(fts.get(position).toString().split(":")[1]);
        
        return view;
    }
//    A8:51:80:7F:7C:1E:EC:E4:AC:9D:E4:5A:28:64:E4:7C:61:7B:C8:ED
}