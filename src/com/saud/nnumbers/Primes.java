package com.saud.nnumbers;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Primes extends Activity {

	String n;
	int firstN, textLength;
	TextView firstnPrimes, emptyText;
	EditText searchNumber;
	ListView listView;
	ProgressBar progressCircle;
    ArrayList<Item> listArray;
    ArrayList<Item> searchResults;
    CustomAdapter customListAdapter;
    ComputePrimes computePrimes;
    CustomDialogClass custom_dialog;
    PrimesDatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_primes);
		
		firstnPrimes = (TextView) findViewById(R.id.list_of_primes);
		searchNumber = (EditText) findViewById(R.id.search_number);
        listView = (ListView) findViewById(R.id.listview);
        emptyText = (TextView) findViewById(android.R.id.empty);
        progressCircle = (ProgressBar) findViewById(R.id.progressBar); 
        
        emptyText.setVisibility(View.INVISIBLE);
        progressCircle.setVisibility(View.INVISIBLE);
        
		n = getIntent().getExtras().getString("n");
		
		if (n.equals("1")) {
			firstnPrimes.setText("First Prime");
		} else {
			firstnPrimes.setText("First " + n + " Primes");
		}
		
		firstN = Integer.parseInt(n);
		
		db = new PrimesDatabaseHandler(this);
		
		listArray = new ArrayList<Item>();
		searchResults = new ArrayList<Item>();

        computePrimes = (ComputePrimes) new ComputePrimes(firstN).execute();
        
		searchNumber.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				textLength = searchNumber.getText().length();
				
				searchResults.clear();

				for (int i = 0; i < listArray.size(); i++) {
					if (textLength <= listArray.get(i).getValue().length()) {
						if (searchNumber.getText().toString().equalsIgnoreCase((String) listArray.get(i).getValue().subSequence(0, textLength))) {
							searchResults.add(listArray.get(i));
						}
					}
				}

				if(searchResults.size() == 0) {
					emptyText.setVisibility(View.VISIBLE);
					listView.setVisibility(View.INVISIBLE);
				} else {
					emptyText.setVisibility(View.INVISIBLE);
					listView.setVisibility(View.VISIBLE);
				}
				
				listView.setAdapter(new CustomAdapter(Primes.this, R.layout.listview_element, searchResults));

			}
		});
                     
	}
	
	@Override
	public void onBackPressed() {    
		finish();
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);  
	}
	
	private class ComputePrimes extends AsyncTask<Void, String, Void> {
		
		ProgressDialog progressBar;
		int N, value = 2;
		boolean isPrime = true;
		
		public ComputePrimes(int N) {
			this.N = N;
		}
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!(db.getPrimesCount() >= N)) {
				progressBar = new ProgressDialog(Primes.this);
				progressBar.setCancelable(true);
				progressBar.setCanceledOnTouchOutside(false);
				progressBar.setMessage("Finding Primes ...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.setProgressDrawable(Primes.this.getResources().getDrawable(R.drawable.custom_progressbar_background));
				progressBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
		            @Override
		            public void onCancel(DialogInterface dialog) {
		                cancel(true);
		            	customListAdapter = new CustomAdapter(Primes.this, R.layout.listview_element, listArray);
		    	        listView.setAdapter(customListAdapter);
		    	        customListAdapter.notifyDataSetChanged();
		    	        firstnPrimes.setText("Aborted");
		            }
		        });
				progressBar.show();
			} else {
				progressCircle.setVisibility(View.VISIBLE);
			}
        }

		@Override
		protected Void doInBackground(Void... params) {
			
			if(db.getPrimesCount() == 0) {
				for (int count = 1; count <= N;) {
										
					for (int j = 2; j <= Math.sqrt(value); j++) {
						if (value % j == 0) {
							isPrime = false;
							break;
						}
					}

					if (isPrime) {
						db.addPrime(new Item(Integer.toString(value), "#" + count));
						listArray.add(new Item(Integer.toString(value), "#" + count));
						count++;
					}

					isPrime = true;
					value++;
					publishProgress(""+ (int)((count*100)/N));
					if(isCancelled()) break;
					
				}
								
			} else if (db.getPrimesCount() >= N) {
				listArray = db.getPrimesUpto(Integer.toString(N));
			} else if (db.getPrimesCount() < N) {
				listArray = db.getAllPrimes();
				int continuingNumber = db.getLastPrime() + 1;
				for (int continuingCount = (db.getPrimesCount() + 1); continuingCount <= N;) {
										
					for (int j = 2; j <= Math.sqrt(continuingNumber); j++) {
						if (continuingNumber % j == 0) {
							isPrime = false;
							break;
						}
					}
					
					if (isPrime) {
						db.addPrime(new Item(Integer.toString(continuingNumber), "#" + continuingCount));
						listArray.add(new Item(Integer.toString(continuingNumber), "#" + continuingCount));
						continuingCount++;
					}

					isPrime = true;
					continuingNumber++;
					publishProgress(""+ (int)((continuingCount*100)/N));
					if(isCancelled()) break;
					
				}
			}
						
			return null;
		}

        protected void onProgressUpdate(String... progress) {
               progressBar.setProgress(Integer.parseInt(progress[0]));
        }
		
		@Override
		protected void onPostExecute(Void result) {
			customListAdapter = new CustomAdapter(Primes.this, R.layout.listview_element, listArray);
	        listView.setAdapter(customListAdapter);
	        customListAdapter.notifyDataSetChanged();
	        
	        if(progressBar != null) {
				progressBar.dismiss();
			} else {
				progressCircle.setVisibility(View.INVISIBLE);
			}
	        
		}

	}

}