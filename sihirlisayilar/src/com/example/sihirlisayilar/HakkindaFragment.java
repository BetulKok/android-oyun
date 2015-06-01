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
	
	TextView link; //hakk�nda k�sm�nda buluna link
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.hakkinda_fragment, container, false);
		link = (TextView)v.findViewById(R.id.linkDuzce);
		link.setOnClickListener(this);
		link.setOnTouchListener(this);
		return v;
	}

	//G�r�nt� elemanlar� sahneden temizlenince �al��t�r�l�r.
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//activity �zerinde �al��an ta��y�c� �zerinde ki nesneler temizlenir.
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
			//linke dokunuldu�u anda yaz� rengi color_ana_ekran_1 parametresinde ki renk kodu ile de�i�tirilecek.
			link.setTextColor(Color.parseColor(getText(R.color.color_ana_ekran_1).toString()));
			break;
		case MotionEvent.ACTION_UP:
			//linkten parmak kalkt��� anda yaz� rengi color_ana_ekran_2 parametresinde ki renk kodu ile de�i�tirilecek.
			link.setTextColor(Color.parseColor(getText(R.color.color_ana_ekran_2).toString()));
			break;
		}
		return false;
	}
}
