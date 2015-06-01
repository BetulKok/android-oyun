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
	 * Bu oyunun algoritmas� http://www.seckin.com.tr/9789750217166 bu adreste bahsedilen kitaptan al�nm��t�r.
	 * Oyunun mant��� �udur.
	 * ki�i 0 ila 31 aras�nda rasgele bi say� tutar.
	 * neden 31 ��nk� ikilik sistemde 5basamkl� en b�y�k say� 31 dir.
	 * k�sacas� biz arkaplanda kullan�c�dan 
	 * ikilik sistemde 00000 ila 11111 aras�nda bir say� tutmas�n� istiyoruz.
	 * ve ki�iye 5 farkl� tablo g�steriyoruz.
	 * Bu tablolar olu�turulurken dikkat edilmesi gerekn bir husus var.
	 * ilk tabloda 1. basamkta 1 olan say�lar vard�r.
	 * 10000(16) - 11111(31) aras�nda ki say�alrd�r.
	 * ikinci tabloda 2.basama��nda 1 olan say�alr vard�r.
	 * 01000, 01001, 01011, ..... 11000, 11001, ... 11111 gibi. her defas�nda 16 say� ��kar.
	 * ���nc� tabloda ise 3. basama��nda 1 olan say�lar ele al�n�r.
	 * buda 00100, 00101, ... 01100, ... 11100, ... 11111 gibi;
	 * d�rd�nc� tabloda ise 4. basama��nda 1 olan say�lar al�n�r.
	 * en son tabloda ise son basama�� 1 olan say�alr al�n�r.
	 * Bu tablolar�n hepsinde 31 vard�r, 1 ise sadece son tabloda vard�r.
	 * Tabloda ki say�lar�n yerlerinin de�i�mesi sadece bi aldatmacad�r.
	 * asl�nda program her �al��t���nda tablolarda hep ayn� say�lar vard�r.
	 * Neyse kullan�c� tuttu�u say�y� tabloda g�remezse hay�ra, g�r�rse evete basar.
	 * hay�ra basarsa sonuca 0 eklenir.
	 * evete basarsa 1 eklenir ve 5 tablonun ard�ndan 5 basamakl� bir say� elde edilir.
	 * Tabi ba�ta s�f�r olunca be� basamakl� demek do�ru olmaz ama 5de�erli denilebilir.
	 * mesele,
	 * ilk tabloda say� var: Sonuc = "1";
	 * ikinci tabloda say� yok: Sonuc = "10";
	 * ���nc� tabloda say� var: Sonuc = "101";
	 * d�rd�nc� tabloda say� yok: Sonuc = "1010";
	 * son tabloda say� yok: Sonuc = "10100";
	 * bu say�y�da onluk sisteme �evirdi�imizde tutlan say� 20 bulunur.
	 * �spat�da istatistiki bir durumdur.
	 * Eleme i�lemi yap�lmakta. 5 de�erli bir say� aramaktas�n�z.
	 * bu say�n�n ilk de�eri 1 dedi�imiz anda ilk de�eri 0 olan say�lar silinir.
	 * sonra bu say�n�n ikinci de�eri 0 dersek kalan say�lardan 2. de�eri 1 olanlar silinir.
	 * bbu i�lem t�m basamak de�erleri i�in yap�l�nca geriye sadece 1 de�er kal�r.
	 * b�ylece geriye sadece bir say� kal�r.
	 */
	
	Button btnDevam; //Devam et butonu
	public static String Sonuc; //De�eri saklad���m�z de�i�ken
	TextView lblAciklama; // A��klama butona t�kland�k�a de�i�ece�i i�in tan�mald�k.
	List<TextView> txtListe; //Bu sahnede say�lar�n g�sterilece�i yaz� alanlar�n� tutan liste.
	int oyunBasladimi; //Ayn� fragment i�inde g�r�nt�ler de�i�ti�i i�in 
	//Oyunun hangi a�amas�nda oldu�umuzu kontrol eden de�i�kendir.
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//�lk de�er atamalar�n� yap�yoruz.
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
		if(oyunBasladimi > 0) { // e�er devam et butonuna en az bir kez bas�lm��sa
			//Bir diyalog ekran� yap�land�r�c�s� haz�rlan�r
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			//diyalog ekran�nda g�sterilecek olan ba�l���n yan�nda ki �ekil.
			builder.setIcon(R.drawable.question);
			//ba�l�k
			builder.setTitle(R.string.app_name);
			//g�sterilecek olan metin
			builder.setMessage(R.string.lbl_oyun_akis);
			//Olumcu cevap butonu
			builder.setPositiveButton(R.string.btn_var, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				   	//cevap olumluysa Sonuc de�i�keninin de�erinin sonuna 1 de�eri eklenir.
					Sonuc += "1";
				   	if(oyunBasladimi > 5) { //e�er devam et butonuna en az 6 kez ba�lm��sa
				   		//oyun bitmi� demektir. Sonuc fragemntini y�kle.
			   			SS.SahneYukle(getActivity(), R.id.tasiyici, new SonucFragment());
				   	}
			   	}
			});
			//olumsuz cevap butonu
			builder.setNegativeButton(R.string.btn_yok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//cevap olumsuzsa Sonuc de�i�keninin de�erinin sonuna 0 de�eri eklenir.
					Sonuc += "0";
				   	if(oyunBasladimi > 5) {
			   			SS.SahneYukle(getActivity(), R.id.tasiyici, new SonucFragment());
				   	}
				}
			});
			//diyalog ekran� olu�turulur ve
			AlertDialog dialog = builder.create();
			//ekranda g�sterilir.
			dialog.show();
		} else {
			//e�er daha �nce devam et butonuna hi� bas�lmam��sa �stte ki yaz�y� de�i�tir.
			lblAciklama.setText(getText(R.string.lbl_oyun_akis));
		}
		oyunBasladimi++; //devam et butonuna bas�lm��sa de�i�kenin de�erini 1 art�r.
		if(oyunBasladimi > 5) { //e�er devam et butonuna en az 5 kere bas�lm��sa 
			return; //metotu bitir.
		}
		//SayiGetir metodu parametre olarak ald��� tablo say�s�na g�re tabloda ki say�lar� rasgele olu�turur.
		List<String> liste = SS.SayiGetir(oyunBasladimi);
		//olu�turdu�umuz de�erleri bir d�ng� vas�tas� ile 
		for (int i = 0; i < liste.size(); i++) {
			txtListe.get(i).setText(liste.get(i)); //ekranda bulunan textview lere dee�r olarak at�yoruz.
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//Butona bas�l�nca arka plan rengini de�i�tirdik.
			v.setBackgroundResource(R.color.color_ana_ekran_1);
			break;

		case MotionEvent.ACTION_UP:
			v.setBackgroundResource(R.color.color_ana_ekran_2);
			break;
		}
		return false;
	}
}
