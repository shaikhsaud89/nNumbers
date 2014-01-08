package com.saud.nnumbers;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class Divisors extends Activity {
	
	String number;
	long N;
	TextView divisorsForN, NIsPrime;
	ListView listView;
    ArrayList<Item> listArray;
    CustomAdapter customListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_divisors);
		
		divisorsForN = (TextView) findViewById(R.id.list_of_divisors);
		NIsPrime = (TextView) findViewById(R.id.n_is_prime);
        listView = (ListView) findViewById(R.id.divisors_listview);
        
        number = getIntent().getExtras().getString("number");
        
        divisorsForN.setText("Divisors for " + number);
        
        N = Long.parseLong(number);
        
		listArray = new ArrayList<Item>();
		
		new ComputeDivisors(N).execute();
		
	}
	
	@Override
	public void onBackPressed() {    
		finish();
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);    
	}
	
	private class ComputeDivisors extends AsyncTask<Void, String, Void> {
		
		ProgressDialog progressBar;
		long Number, count = 1;
		
		public ComputeDivisors(long Number) {
			this.Number = N;
		}
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(Divisors.this);
			progressBar.setCancelable(false);
    		progressBar.setMessage("Finding Divisors ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
			progressBar.setMax(100);
            progressBar.setProgressDrawable(Divisors.this.getResources().getDrawable(R.drawable.custom_progressbar_background));
			progressBar.show();
        }

		@Override
		protected Void doInBackground(Void... params) {
			
			for(long i = 1; i <= Number; i++) {	    
	            if ((Number % i) == 0) {
	            	listArray.add(new Item(Long.toString(i), "#" + count));
	            	count++;
	            }
            	publishProgress(""+ (int)((i*100)/Number));
	        }
			
			return null;
		}

        protected void onProgressUpdate(String... progress) {
               progressBar.setProgress(Integer.parseInt(progress[0]));
        }
		
		@Override
		protected void onPostExecute(Void result) {
			if (listArray.size() == 2) {
				NIsPrime.setText(Long.toString(Number) + " is Prime");
				NIsPrime.setTextColor(Color.parseColor("#008000"));
			} else {
				NIsPrime.setText(Long.toString(Number) + " is not Prime");
				NIsPrime.setTextColor(Color.parseColor("#FF0000"));
			}
			customListAdapter = new CustomAdapter(Divisors.this, R.layout.listview_element, listArray);
	        listView.setAdapter(customListAdapter);
	        customListAdapter.notifyDataSetChanged();
	        if(progressBar != null) {
				progressBar.dismiss();
			} 
		}

	}

}