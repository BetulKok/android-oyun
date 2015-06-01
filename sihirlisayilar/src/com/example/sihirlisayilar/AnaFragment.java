package com.example.sihirlisayilar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

//Ana ekran�n fragmenti.
/*
 * Fragmentlerin her biri birer s�n�ft�r.
 * Her birinin kendi i�lerinde birer ya�am d�ng�s� vard�r.
 * Activity'lerin ya�am d�ng�lerine benzerler fakat baz� farklar vard�r.
 * Mesela onCreateView metodu gibi.
 * Bu metot fragmentin sahneye g�r�nt� y�kledi�i aland�r.
 * Activitylerde onCreate metodunda sahneye g�r�nt� y�klenirken
 * Fragmentlerde bu metotta y�klenir ve kontroller bu noktada �ekillenir.
 * 
 * Birde burda baz� aray�zleri implements ettik.
 * bu aray�zde tan�ml� metotlar sahnedeki nesneler ile kullan�c� aras�nda ki etkile�imi takip ederler.
 */
public class AnaFragment extends Fragment implements OnTouchListener,OnClickListener {

	ImageButton btnBasla; //Sahnede bulunan play tu�u.
	TextView link; //http://duzce.edu.tr adresine link.
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//onCreateView geriye bir g�r�nt�(view) nesnesi d�nd�r�r.
		//bu nesne sahneye y�klenecek olan nesnedir.
		View v = inflater.inflate(R.layout.ana_fragment, container, false);
		//v nesnesine ana_fragment.xml deki kontrolleri y�kl�yoruz.
		//Kontrollerimizi tan�ml�yoruz.
		//sahnede bulunan her�ey bir viewdir ve kontrollere d�n���mleri yap�labilir.(cast i�lemi);
		btnBasla = (ImageButton)v.findViewById(R.id.btnBaslat);
		link = (TextView)v.findViewById(R.id.lblAnaEkran2);
		//Kontrollerimize birer takip mekan�zmas� kuruyoruz.
		//onTouchListener nesne �zerine dokundu�u anda tetiklenir.
		//onClickListener ise nesneye bas �ek yapt���n�zda tetiklenir.
		btnBasla.setOnTouchListener(this);
		btnBasla.setOnClickListener(this);
		link.setOnClickListener(this);
		//T�m tan�mlamalar� yapt�ktan sonra v nesnesini d�nd�r�yoruz.
		return v;
	}

	//nesneye t�kland��� anda ya�palacak i�lemler
	@Override
	public void onClick(View v) {
		if(v.getId() == link.getId()) { //e�er linke t�klanm��sa
			//yeni bir web browser intenti olu�turuyoruz.
			//Intentler activityleri ba�latan yap�lard�r.
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.duzce_link_url).toString()));
			//ve olu�turdu�umuz browser intentini ba�lat�yoruz.
			//Sistem otomatik oalrak belirtilen url yi varsay�lan taray�c�da a�acakt�r.
			startActivity(browserIntent);
		} else if(v.getId() == btnBasla.getId()) { //e�er paly butonuna t�klarsak
			AnaActivity.afYuklumu = false; //Ana ekrandan ��kld���n� belirtip
			//Oyun fragmentimizi sahneye y�kleriz.
			SS.SahneYukle(getActivity(), R.id.tasiyici, new OyunFragment());
		}
	}

	//nesneye dokunuldu�u anda �al���r
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//switch ile olay takip edilir.
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //e�er parmak nesne �zerinde ise
			//Buton resmini play_down ile de�i�tir.
			btnBasla.setImageResource(R.drawable.play_down);
			break;

		case MotionEvent.ACTION_UP: // e�er parmak nesne �zerinden kalkm�� ise
			//buton resmini play_up ile de�i�tir.
			btnBasla.setImageResource(R.drawable.play_up);
			break;
		}
		return false;
	}

}
