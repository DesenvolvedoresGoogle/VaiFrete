package br.com.vaifrete;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;

public class HelpFragment extends SherlockFragmentActivity {

        private ViewPagerAdapter mAdapter;
        private ViewPager mViewPager;
        private CirclePageIndicator mIndicator;
        private ImageView closeButton;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) 
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.help_main);
                this.getSupportActionBar().hide();
                
                mAdapter = new ViewPagerAdapter();
                mViewPager = (ViewPager) findViewById(R.id.pager);
                mViewPager.setAdapter(mAdapter);
                mViewPager.setCurrentItem(0);
                
                mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
                mIndicator.setViewPager(mViewPager);
                
                
//                closeButton.setOnClickListener(new OnClickListener() {
//            	@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//            		 Intent i = new Intent(getBaseContext(),MainActivity2.class);
//                	 startActivity(i);
//                	 finish();
//					}
//				});
        }
        
        public void openHome()
        {
        	
        }
        
        public class ViewPagerAdapter extends PagerAdapter {

                public Object instantiateItem(View collection, int position) {
                        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        int resId = 0;
                        switch (position) {
                        case 0:
                                resId = R.layout.help_1;
                                break;
                        case 1:
                                resId = R.layout.help_2;
                                break;
		                case 2:
			                    resId = R.layout.help_3;
			                    break;
		                case 3:
			                    resId = R.layout.help_4;
			                    break;
                        }
                        
                        View view = inflater.inflate(resId, null);
                        
                        if(position==2){
                        	ImageView mStart = (ImageView) view.findViewById(R.id.imageView1);
                            mStart.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
                                    Intent i = new Intent(getApplicationContext(),MapaActivity.class); startActivity(i); 

								}
                            	
                            });
                        	
                        	
                        }
                       
                        
                        if(position==3){
                        
                           TextView mStart = (TextView) view.findViewById(R.id.title_help_step4_top);
                            mStart.setOnClickListener(new View.OnClickListener() {
                                    
                                     @Override
                                    public void onClick(View v) {
                                           Intent i = new Intent(getApplicationContext(),Home.class); startActivity(i); 
                                    }
                            });
 
                        }
                        ((ViewPager) collection).addView(view, 0);

                       
						return view;
                }
                
                
                @Override
                public void destroyItem(View arg0, int arg1, Object arg2) {
                        ((ViewPager) arg0).removeView((View) arg2);
                }
                
                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {
                        return arg0 == ((View) arg1);
                }
                
                @Override
                public Parcelable saveState() {
                        return null;
                }
                
                @Override
                public int getCount() {
                        return 3;
                }
        }
}