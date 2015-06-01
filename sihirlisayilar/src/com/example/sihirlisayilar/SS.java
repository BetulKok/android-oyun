package com.example.sihirlisayilar;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/*
 * Bu sýnýf bizim sýkça kullandýðýmýz static metotlarý barýndýran sýnýftýr.
 * Thread sýnýfýndan kalýtým almaktadýr. Bu sayede splash ekraný gibi 
 * bazý zamana dayalý uygulamalarý gerçekleþtirebilmekteyiz.
 */
public class SS extends Thread {
	
	private int Second = 0; //Bekleme yapýlacak olan süre
	private FragmentActivity splash; //splash ekranýnda yönlendirme yapýlacak olan ekran
	
	//Sahne yükleme iþlemi.
	/*
	 * Parametre oalrak 3 deðer alýr.
	 * 1. parametre fragmentin çalýþtýrýlacaðý activitydir.
	 * 2. parametre fragmentin yükleneceði ana activity üzerinde bulunan bir layout'un ID sidir.
	 * 3. parametre yüklenecek olan fragmentin sýnýfýnda örneklenmiþ bir nesne.
	 */
	public static void SahneYukle(FragmentActivity activity, int layoutID, Fragment fragment) {
		//Fragmentler fragmentmanager yardýmý ile yüklenirler.
		FragmentManager fragmentManager =  activity.getSupportFragmentManager();
		//Görselleri yükleyecek olan sýnýf fragmenttransaction dýr.
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//bu aþamada görseller, taþýyýcý layout üzerine yüklenir.
		fragmentTransaction.replace(layoutID, fragment);
		//ve yükleme iþleminin tamamalandýðý sisteme bildirilir.
		fragmentTransaction.commit();
	}
	
	//sahneye yüklenen fragmentleri temizler.
	public static void SahneTemizle(FragmentActivity activity, Fragment fragment) {
		FragmentManager fragmentManager =  activity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}
	
	//Tablo oluþturma iþlemi yapýlýr.
	public static List<String> SayiGetir(int tablo) {
		tablo--; // tabloyu sýfýrdan baþlatmak için kullanýyoruz.
		//Sayýlarý taþýyacak olan liste.
		List<String> liste = new ArrayList<String>();
		
		int i = 0;//döngünün sýnýrýný takip eden deðiþken.
		while (i < 16) { //eðer i 16 dan küçükse döngü devam eder.
			//0 ila 1000 arasýnda rasgeele bir sayý üretilir ve bunun mod 31 deðeri alýnýr. ve 1 eklenir.
			//Bu kýsaca 1 ile 31 arasýnda bir sayý demektir.
			//Bu iþlem ile rasgele sayý aralýðý büyütülerek ayný sayýnýn tekrarý ihtimali azaltýlmaktadýr.
			int r = 1 + ((int) (Math.random() * 1000) % 31); 

			if(liste.indexOf(String.valueOf(r)) < 0) { // eðer üretilen sayý daha önce üretilmemiþse
				//Binary(r) onluk tabandan 2lik tabana dönüþüm yapar ve 
				//deðeri 5 deðerli olacak þekilde string olarak döndürür.
				//Dönene sayýnýn deðeri parametre olarak alýnan tablo sayýsýna göre parçalanýr.
				//Örneðim 1. tablo ise sayýnýn ilk deðeri substring ile elde edilir.
				//5. tablo ise son deðeri substring ile elde edilir.
				//ve doðru bir karþýlaþtýrma yapabilmek adýna sayýya çevrilir.
				int s = Integer.parseInt(Binary(r).substring(tablo, tablo + 1));
				if(s == 1) { // eðer üretilen sayýnýn binary sistemde gerekli basamaðý 1 ise
					//sayý listeye eklenir.
					liste.add(String.valueOf(r));
					//kontrol deðiþkeni 1 artýrýlýr.
					i++;
				}
			}
		}
		//iþlem bittikten sonra geriye liste döndürülür.
		return liste;
	}
	
	//Onluk sayý sisteminde ki bir sayýyý 2lik sayý sistemine çevirir.
	//Decimal(10'lik sayý sistemi) to Binary(2'lik sayý sistemi)
	private static String Binary(int sayi) {
		String s = "";
		while(true) {
			if(sayi == 0) 
				break;
			int i = sayi % 2;
			sayi = (sayi - i) / 2;
			s = String.valueOf(i) + s; 
		}
		for(int j = s.length(); j < 5; j++) {
			s = "0" + s;
		}
		return s;
	}
	
	//Binary to decimal
	public static String Decimal(String binary) {
		int r = 0;
		int l = binary.length();
		for (int i = 0; i < l; i++) {
			r += Math.pow(2, i) * Integer.parseInt(binary.substring(l - i - 1, l - i));
		}
		return String.valueOf(r);
	}
	
	// BU kýsmda bulunan metotlar Thread için geçerli metotlardýr.
	
	//Beklet metodu aldýðý int türünden deðeri saniye olarak kabul eder ve
	//bu kadar saniye uygulamanýn çalýþmasýný bekletir.
	public void Beklet(FragmentActivity splash, int sn) {
		this.Second = sn;
		this.splash = splash;
		this.start();
	}
	
	//Bu metot Thread'i kalýtsal alaran metotlarýn tanýmlamasý gereken zorunlu bir metotdur.
	@Override
	public void run() {
		if(Second != 0) {
			try {
				//Uygulama belli bir microsaniye uyutulur.
				Thread.sleep(Second*1000);
				Second = 0;
			} catch (Exception e) {}
			//ve sonrasýnda iþlemler yapýlýr.
			//Yani gösterilecek ekran yüklenir.
			Intent intent = new Intent(splash, AnaActivity.class);
			splash.startActivity(intent);
			splash.finish();
		}
	}
}
