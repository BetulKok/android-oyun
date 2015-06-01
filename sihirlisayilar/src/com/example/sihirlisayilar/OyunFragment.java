package com.example.sihirlisayilar;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class OyunFragment extends Fragment implements OnClickListener,OnTouchListener {
	
	/*
	 * Bu oyunun algoritmasý http://www.seckin.com.tr/9789750217166 bu adreste bahsedilen kitaptan alýnmýþtýr.
	 * Oyunun mantýðý þudur.
	 * kiþi 0 ila 31 arasýnda rasgele bi sayý tutar.
	 * neden 31 çünkü ikilik sistemde 5basamklý en büyük sayý 31 dir.
	 * kýsacasý biz arkaplanda kullanýcýdan 
	 * ikilik sistemde 00000 ila 11111 arasýnda bir sayý tutmasýný istiyoruz.
	 * ve kiþiye 5 farklý tablo gösteriyoruz.
	 * Bu tablolar oluþturulurken dikkat edilmesi gerekn bir husus var.
	 * ilk tabloda 1. basamkta 1 olan sayýlar vardýr.
	 * 10000(16) - 11111(31) arasýnda ki sayýalrdýr.
	 * ikinci tabloda 2.basamaðýnda 1 olan sayýalr vardýr.
	 * 01000, 01001, 01011, ..... 11000, 11001, ... 11111 gibi. her defasýnda 16 sayý çýkar.
	 * üçüncü tabloda ise 3. basamaðýnda 1 olan sayýlar ele alýnýr.
	 * buda 00100, 00101, ... 01100, ... 11100, ... 11111 gibi;
	 * dördüncü tabloda ise 4. basamaðýnda 1 olan sayýlar alýnýr.
	 * en son tabloda ise son basamaðý 1 olan sayýalr alýnýr.
	 * Bu tablolarýn hepsinde 31 vardýr, 1 ise sadece son tabloda vardýr.
	 * Tabloda ki sayýlarýn yerlerinin deðiþmesi sadece bi aldatmacadýr.
	 * aslýnda program her çalýþtýðýnda tablolarda hep ayný sayýlar vardýr.
	 * Neyse kullanýcý tuttuðu sayýyý tabloda göremezse hayýra, görürse evete basar.
	 * hayýra basarsa sonuca 0 eklenir.
	 * evete basarsa 1 eklenir ve 5 tablonun ardýndan 5 basamaklý bir sayý elde edilir.
	 * Tabi baþta sýfýr olunca beþ basamaklý demek doðru olmaz ama 5deðerli denilebilir.
	 * mesele,
	 * ilk tabloda sayý var: Sonuc = "1";
	 * ikinci tabloda sayý yok: Sonuc = "10";
	 * üçüncü tabloda sayý var: Sonuc = "101";
	 * dördüncü tabloda sayý yok: Sonuc = "1010";
	 * son tabloda sayý yok: Sonuc = "10100";
	 * bu sayýyýda onluk sisteme çevirdiðimizde tutlan sayý 20 bulunur.
	 * Ýspatýda istatistiki bir durumdur.
	 * Eleme iþlemi yapýlmakta. 5 deðerli bir sayý aramaktasýnýz.
	 * bu sayýnýn ilk deðeri 1 dediðimiz anda ilk deðeri 0 olan sayýlar silinir.
	 * sonra bu sayýnýn ikinci deðeri 0 dersek kalan sayýlardan 2. deðeri 1 olanlar silinir.
	 * bbu iþlem tüm basamak deðerleri için yapýlýnca geriye sadece 1 deðer kalýr.
	 * böylece geriye sadece bir sayý kalýr.
	 */
	
	Button btnDevam; //Devam et butonu
	public static String Sonuc; //Deðeri sakladýðýmýz deðiþken
	TextView lblAciklama; // Açýklama butona týklandýkça deðiþeceði için tanýmaldýk.
	List<TextView> txtListe; //Bu sahnede sayýlarýn gösterileceði yazý alanlarýný tutan liste.
	int oyunBasladimi; //Ayný fragment içinde görüntüler deðiþtiði için 
	//Oyunun hangi aþamasýnda olduðumuzu kontrol eden deðiþkendir.
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Ýlk deðer atamalarýný yapýyoruz.
		View v = inflater.inflate(R.layout.oyun_fragment, container, false);
		txtListe = new ArrayList<TextView>();
		Sonuc = "";
		btnDevam = (Button)v.findViewById(R.id.btnDevam);
		btnDevam.setOnClickListener(this);
		btnDevam.setOnTouchListener(this);
		lblAciklama = (TextView) v.findViewById(R.id.lblOyunAciklama);
		txtListe.add((TextView) v.findViewById(R.id.txtSayi1_1));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi1_2));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi1_3));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi1_4));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi2_1));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi2_2));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi2_3));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi2_4));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi3_1));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi3_2));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi3_3));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi3_4));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi4_1));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi4_2));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi4_3));
		txtListe.add((TextView) v.findViewById(R.id.txtSayi4_4));
		oyunBasladimi = 0;
		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		SS.SahneTemizle(getActivity(), this);
	}

	@Override
	public void onClick(View v) {
		if(oyunBasladimi > 0) { // eðer devam et butonuna en az bir kez basýlmýþsa
			//Bir diyalog ekraný yapýlandýrýcýsý hazýrlanýr
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			//diyalog ekranýnda gösterilecek olan baþlýðýn yanýnda ki þekil.
			builder.setIcon(R.drawable.question);
			//baþlýk
			builder.setTitle(R.string.app_name);
			//gösterilecek olan metin
			builder.setMessage(R.string.lbl_oyun_akis);
			//Olumcu cevap butonu
			builder.setPositiveButton(R.string.btn_var, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				   	//cevap olumluysa Sonuc deðiþkeninin deðerinin sonuna 1 deðeri eklenir.
					Sonuc += "1";
				   	if(oyunBasladimi > 5) { //eðer devam et butonuna en az 6 kez baýlmýþsa
				   		//oyun bitmiþ demektir. Sonuc fragemntini yükle.
			   			SS.SahneYukle(getActivity(), R.id.tasiyici, new SonucFragment());
				   	}
			   	}
			});
			//olumsuz cevap butonu
			builder.setNegativeButton(R.string.btn_yok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//cevap olumsuzsa Sonuc deðiþkeninin deðerinin sonuna 0 deðeri eklenir.
					Sonuc += "0";
				   	if(oyunBasladimi > 5) {
			   			SS.SahneYukle(getActivity(), R.id.tasiyici, new SonucFragment());
				   	}
				}
			});
			//diyalog ekraný oluþturulur ve
			AlertDialog dialog = builder.create();
			//ekranda gösterilir.
			dialog.show();
		} else {
			//eðer daha önce devam et butonuna hiç basýlmamýþsa üstte ki yazýyý deðiþtir.
			lblAciklama.setText(getText(R.string.lbl_oyun_akis));
		}
		oyunBasladimi++; //devam et butonuna basýlmýþsa deðiþkenin deðerini 1 artýr.
		if(oyunBasladimi > 5) { //eðer devam et butonuna en az 5 kere basýlmýþsa 
			return; //metotu bitir.
		}
		//SayiGetir metodu parametre olarak aldýðý tablo sayýsýna göre tabloda ki sayýlarý rasgele oluþturur.
		List<String> liste = SS.SayiGetir(oyunBasladimi);
		//oluþturduðumuz deðerleri bir döngü vasýtasý ile 
		for (int i = 0; i < liste.size(); i++) {
			txtListe.get(i).setText(liste.get(i)); //ekranda bulunan textview lere deeðr olarak atýyoruz.
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//Butona basýlýnca arka plan rengini deðiþtirdik.
			v.setBackgroundResource(R.color.color_ana_ekran_1);
			break;

		case MotionEvent.ACTION_UP:
			v.setBackgroundResource(R.color.color_ana_ekran_2);
			break;
		}
		return false;
	}
}
