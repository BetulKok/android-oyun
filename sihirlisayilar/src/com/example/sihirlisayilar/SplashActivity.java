package com.example.sihirlisayilar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

//Bu activity ekran ilk açýlýrken gösterilen activitydir.
public class SplashActivity extends FragmentActivity {

	//3 saniye ekranda durduktan sonra ana activity yüklenir.
	private final int SECOND = 3; 
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.splash_activity);
		SS ss = new SS();
		ss.Beklet(this, SECOND);
	}
}
