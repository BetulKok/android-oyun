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

//Ana ekranýn fragmenti.
/*
 * Fragmentlerin her biri birer sýnýftýr.
 * Her birinin kendi içlerinde birer yaþam döngüsü vardýr.
 * Activity'lerin yaþam döngülerine benzerler fakat bazý farklar vardýr.
 * Mesela onCreateView metodu gibi.
 * Bu metot fragmentin sahneye görüntü yüklediði alandýr.
 * Activitylerde onCreate metodunda sahneye görðntð yðklenirken
 * Fragmentlerde bu metotta yüklenir ve kontroller bu noktada þekillenir.
 * 
 * Birde burda bazý arayüzleri implements ettik.
 * bu arayüzde tanýmlý metotlar sahnedeki nesneler ile kullanýcý arasýnda ki etkileþimi takip ederler.
 */
public class AnaFragment extends Fragment implements OnTouchListener,OnClickListener {

	ImageButton btnBasla; //Sahnede bulunan play tuþu.
	TextView link; //http://duzce.edu.tr adresine link.
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//onCreateView geriye bir görüntü(view) nesnesi döndürür.
		//bu nesne sahneye yüklenecek olan nesnedir.
		View v = inflater.inflate(R.layout.ana_fragment, container, false);
		//v nesnesine ana_fragment.xml deki kontrolleri yüklüyoruz.
		//Kontrollerimizi tanýmlýyoruz.
		//sahnede bulunan herþey bir viewdir ve kontrollere dönüþümleri yapýlabilir.(cast iþlemi);
		btnBasla = (ImageButton)v.findViewById(R.id.btnBaslat);
		link = (TextView)v.findViewById(R.id.lblAnaEkran2);
		//Kontrollerimize birer takip mekanýzmasý kuruyoruz.
		//onTouchListener nesne üzerine dokunduðu anda tetiklenir.
		//onClickListener ise nesneye bas çek yaptýðýnýzda tetiklenir.
		btnBasla.setOnTouchListener(this);
		btnBasla.setOnClickListener(this);
		link.setOnClickListener(this);
		//Tüm tanýmlamalarý yaptýktan sonra v nesnesini döndürüyoruz.
		return v;
	}

	//nesneye týklandýðý anda yaýpalacak iþlemler
	@Override
	public void onClick(View v) {
		if(v.getId() == link.getId()) { //eðer linke týklanmýþsa
			//yeni bir web browser intenti oluþturuyoruz.
			//Intentler activityleri baþlatan yapýlardýr.
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.duzce_link_url).toString()));
			//ve oluþturduðumuz browser intentini baþlatýyoruz.
			//Sistem otomatik oalrak belirtilen url yi varsayýlan tarayýcýda açacaktýr.
			startActivity(browserIntent);
		} else if(v.getId() == btnBasla.getId()) { //eðer paly butonuna týklarsak
			AnaActivity.afYuklumu = false; //Ana ekrandan çýkldýðýný belirtip
			//Oyun fragmentimizi sahneye yükleriz.
			SS.SahneYukle(getActivity(), R.id.tasiyici, new OyunFragment());
		}
	}

	//nesneye dokunulduðu anda çalýþýr
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//switch ile olay takip edilir.
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: //eðer parmak nesne üzerinde ise
			//Buton resmini play_down ile deðiþtir.
			btnBasla.setImageResource(R.drawable.play_down);
			break;

		case MotionEvent.ACTION_UP: // eðer parmak nesne üzerinden kalkmýþ ise
			//buton resmini play_up ile deðiþtir.
			btnBasla.setImageResource(R.drawable.play_up);
			break;
		}
		return false;
	}

}
