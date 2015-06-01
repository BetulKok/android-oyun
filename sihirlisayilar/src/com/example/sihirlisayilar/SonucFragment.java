package com.example.sihirlisayilar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

//Bu sahnede ekrana oyuncunun tuttuðu sayýyý basýyoruz.
public class SonucFragment extends Fragment implements OnTouchListener {

	TextView txtSonuc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sonuc_fragment, container, false);
		txtSonuc = (TextView)v.findViewById(R.id.txtSonuc);
		txtSonuc.setText(SS.Decimal(OyunFragment.Sonuc));
		v.setOnTouchListener(this);
		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		SS.SahneTemizle(getActivity(), this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//Ekranda hernahgi bir yere týklandýðýnda ana sahneye geri dönüyoruz.
		SS.SahneYukle(getActivity(), R.id.tasiyici, new AnaFragment());
		return false;
	}
}
