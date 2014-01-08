package com.saud.nnumbers;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class NDivisorsFragment extends Fragment {
	
    CustomDialogClass custom_dialog;
    ActionBar actionBar;
    Handler mHandler = new Handler();

	public NDivisorsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		actionBar = getActivity().getActionBar();
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ndivisors_fragment, container, false);
		RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.root_ndivisors);
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
		final EditText enter_n_for_divisors = (EditText) getActivity().findViewById(R.id.enter_n_for_divisors);
		Button show_divisors = (Button) getActivity().findViewById(R.id.compute_divisors);
		show_divisors.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((enter_n_for_divisors.getText().toString().equals(""))) {
					custom_dialog = new CustomDialogClass(getActivity());
					custom_dialog.setVariables("Error", "No value has been entered. Please enter a value.");
					custom_dialog.show();
				} else if ((enter_n_for_divisors.getText().toString().matches("[0]+"))) {
					custom_dialog = new CustomDialogClass(getActivity());
					custom_dialog.setVariables("Error", "Please check the value you have entered.");
					custom_dialog.show();
				} else {
					Intent intent = new Intent(getActivity(), Divisors.class);
					intent.putExtra("number", enter_n_for_divisors.getText().toString());
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
				}
			}
		});
	}

}