package com.example.sihirlisayilar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HakkindaFragment extends Fragment implements OnClickListener, OnTouchListener {
	
	TextView link; //hakkýnda kýsmýnda buluna link
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hakkinda_fragment, container, false);
		link = (TextView)v.findViewById(R.id.linkDuzce);
		link.setOnClickListener(this);
		link.setOnTouchListener(this);
		return v;
	}

	//Görüntü elemanlarý sahneden temizlenince çalýþtýrýlýr.
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//activity üzerinde çalýþan taþýyýcý üzerinde ki nesneler temizlenir.
		SS.SahneTemizle(getActivity(), this);
	}

	@Override
	public void onClick(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.duzce_link_url).toString()));
		startActivity(browserIntent);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//linke dokunulduðu anda yazý rengi color_ana_ekran_1 parametresinde ki renk kodu ile deðiþtirilecek.
			link.setTextColor(Color.parseColor(getText(R.color.color_ana_ekran_1).toString()));
			break;
		case MotionEvent.ACTION_UP:
			//linkten parmak kalktýðý anda yazý rengi color_ana_ekran_2 parametresinde ki renk kodu ile deðiþtirilecek.
			link.setTextColor(Color.parseColor(getText(R.color.color_ana_ekran_2).toString()));
			break;
		}
		return false;
	}
}
