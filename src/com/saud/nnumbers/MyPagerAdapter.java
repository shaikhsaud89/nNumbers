package com.saud.nnumbers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if (arg0 == 0) {
			fragment = new HomeFragment();
		}
		if (arg0 == 1) {
			fragment = new NPrimesFragment();
		}
		if (arg0 == 2) {
			fragment = new NDivisorsFragment();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

}