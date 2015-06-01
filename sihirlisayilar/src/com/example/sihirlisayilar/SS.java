package com.example.sihirlisayilar;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/*
 * Bu s�n�f bizim s�k�a kulland���m�z static metotlar� bar�nd�ran s�n�ft�r.
 * Thread s�n�f�ndan kal�t�m almaktad�r. Bu sayede splash ekran� gibi 
 * baz� zamana dayal� uygulamalar� ger�ekle�tirebilmekteyiz.
 */
public class SS extends Thread {
	
	private int Second = 0; //Bekleme yap�lacak olan s�re
	private FragmentActivity splash; //splash ekran�nda y�nlendirme yap�lacak olan ekran
	
	//Sahne y�kleme i�lemi.
	/*
	 * Parametre oalrak 3 de�er al�r.
	 * 1. parametre fragmentin �al��t�r�laca�� activitydir.
	 * 2. parametre fragmentin y�klenece�i ana activity �zerinde bulunan bir layout'un ID sidir.
	 * 3. parametre y�klenecek olan fragmentin s�n�f�nda �rneklenmi� bir nesne.
	 */
	public static void SahneYukle(FragmentActivity activity, int layoutID, Fragment fragment) {
		//Fragmentler fragmentmanager yard�m� ile y�klenirler.
		FragmentManager fragmentManager =  activity.getSupportFragmentManager();
		//G�rselleri y�kleyecek olan s�n�f fragmenttransaction d�r.
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//bu a�amada g�rseller, ta��y�c� layout �zerine y�klenir.
		fragmentTransaction.replace(layoutID, fragment);
		//ve y�kleme i�leminin tamamaland��� sisteme bildirilir.
		fragmentTransaction.commit();
	}
	
	//sahneye y�klenen fragmentleri temizler.
	public static void SahneTemizle(FragmentActivity activity, Fragment fragment) {
		FragmentManager fragmentManager =  activity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}
	
	//Tablo olu�turma i�lemi yap�l�r.
	public static List<String> SayiGetir(int tablo) {
		tablo--; // tabloyu s�f�rdan ba�latmak i�in kullan�yoruz.
		//Say�lar� ta��yacak olan liste.
		List<String> liste = new ArrayList<String>();
		
		int i = 0;//d�ng�n�n s�n�r�n� takip eden de�i�ken.
		while (i < 16) { //e�er i 16 dan k���kse d�ng� devam eder.
			//0 ila 1000 aras�nda rasgeele bir say� �retilir ve bunun mod 31 de�eri al�n�r. ve 1 eklenir.
			//Bu k�saca 1 ile 31 aras�nda bir say� demektir.
			//Bu i�lem ile rasgele say� aral��� b�y�t�lerek ayn� say�n�n tekrar� ihtimali azalt�lmaktad�r.
			int r = 1 + ((int) (Math.random() * 1000) % 31); 

			if(liste.indexOf(String.valueOf(r)) < 0) { // e�er �retilen say� daha �nce �retilmemi�se
				//Binary(r) onluk tabandan 2lik tabana d�n���m yapar ve 
				//de�eri 5 de�erli olacak �ekilde string olarak d�nd�r�r.
				//D�nene say�n�n de�eri parametre olarak al�nan tablo say�s�na g�re par�alan�r.
				//�rne�im 1. tablo ise say�n�n ilk de�eri substring ile elde edilir.
				//5. tablo ise son de�eri substring ile elde edilir.
				//ve do�ru bir kar��la�t�rma yapabilmek ad�na say�ya �evrilir.
				int s = Integer.parseInt(Binary(r).substring(tablo, tablo + 1));
				if(s == 1) { // e�er �retilen say�n�n binary sistemde gerekli basama�� 1 ise
					//say� listeye eklenir.
					liste.add(String.valueOf(r));
					//kontrol de�i�keni 1 art�r�l�r.
					i++;
				}
			}
		}
		//i�lem bittikten sonra geriye liste d�nd�r�l�r.
		return liste;
	}
	
	//Onluk say� sisteminde ki bir say�y� 2lik say� sistemine �evirir.
	//Decimal(10'lik say� sistemi) to Binary(2'lik say� sistemi)
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
	
	// BU k�smda bulunan metotlar Thread i�in ge�erli metotlard�r.
	
	//Beklet metodu ald��� int t�r�nden de�eri saniye olarak kabul eder ve
	//bu kadar saniye uygulaman�n �al��mas�n� bekletir.
	public void Beklet(FragmentActivity splash, int sn) {
		this.Second = sn;
		this.splash = splash;
		this.start();
	}
	
	//Bu metot Thread'i kal�tsal alaran metotlar�n tan�mlamas� gereken zorunlu bir metotdur.
	@Override
	public void run() {
		if(Second != 0) {
			try {
				//Uygulama belli bir microsaniye uyutulur.
				Thread.sleep(Second*1000);
				Second = 0;
			} catch (Exception e) {}
			//ve sonras�nda i�lemler yap�l�r.
			//Yani g�sterilecek ekran y�klenir.
			Intent intent = new Intent(splash, AnaActivity.class);
			splash.startActivity(intent);
			splash.finish();
		}
	}
}
