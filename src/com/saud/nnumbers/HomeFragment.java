package com.saud.nnumbers;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class HomeFragment extends Fragment {
	
	ActionBar actionBar;
    Handler mHandler = new Handler();

	public HomeFragment() {
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		actionBar = getActivity().getActionBar();
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);
		RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.root_home);
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				actionBar.show();
				mHandler.postDelayed(new Runnable() {
		            public void run() {
		                actionBar.hide();
		            }
		        }, 1000);
				return false;
			}
		});
		return rootView;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		Button about = (Button) getActivity().findViewById(R.id.about);
		about.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), About.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
			}
		});
	}
	
}
