package com.example.sihirlisayilar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

//Uygulamam�n�z ana activity'sidir.
public class AnaActivity extends FragmentActivity {

	/*
	 * Android'de 3 s�r�m�nden beri var olan fragmentler,
	 * yeni android sdk s�nda varsay�lan olarak gelmektedir.
	 * Eski mant�kta activityler ile sayfa ge�i�leri yap�l�rken art�k
	 * Tek bir activity �zerinde fragment ge�i�leri yap�lmaktad�r.
	 * Faydalar�:
	 * Bir activity de�i�ti�inde t�m ekran� de�i�tirmektedir buda cihaz i�in y�kt�r.
	 * fragment ise sadece g�r�nt� elemanlar�n� de�i�tirir.
	 * Buda bize esnek bir sayfa tasarlama imkan� sunar.
	 * bir aktivity �zerindeki farkl� layoutlara farkl� fragmentler y�klenerek g��rselli�i art�rabiliriz.
	 * ve fragment ge�i�leri activity ge�i�lerine g�re daha h�zl�d�r.
	 * Fakat kullan�rken dikkat edilmesi gereken bir konu vard�r.
	 * Geri tu�una bas�l�nca �nce ki fragment geri gelmez.
	 * Sistem geri tu�unda ge�mi� haf�zas� oalrak sadece ectivity leri saklar.
	 * Bu nedenle uygulama i�erisinde gezinme ortam� olu�turabilmek i�in 
	 * mecburen geri tu�unu takip eden bir kontrol olu�turmak gerekmektedir.
	 */
	
	public static boolean afYuklumu; //Bu de�i�ken ana ekran�n y�kl� olup olmad���n� kontrol eder.
	
	/*
	 * Her uygulama bir Activity'den ba�lamak zorundad�r.
	 * Bu aktivitelerin bir ya�am d�ng�s� vard�r.
	 * http://www.fpganedir.com/android/aktivite_yonetim/index.php
	 * linkte ki ya�am d�ng�s� gere�i her uygulama onCreate metodu ile ba�lar.
	 * onCreate metodu uygulama ilk a��ld�ktan sonra uygulamaya 
	 * g�rsel aray�z�n eklemesinin yap�laca�� yerdir.
	 * Ve xml dosyalar� i�erisinde ki kontrollere eri�mek i�in 
	 * bu metotta tan�mlaam yapmak gerekir.
	 * 
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		//super Kal�t�m�n al�nd��� s�n�f� temsil eder.
		// Bizim uygulamam�zda Kal�t�m�n al�nd��� s�n�f FragmentActivity'dir.
		super.onCreate(arg0); // Javada Override edilen metot kal�t�m� ald��� metota super parametresi ile de�er g�nderir.
		setContentView(R.layout.ana_activity); //Bu metot olu�turdu�umuz aray�zleri ekrana y�kler.
		afYuklumu = true; //Ana ekran�n y�klendi�ini belirtiyoruz.
		//SS s�n�f� bizim olu�turdu�umuz bir s�n�ft�r ve 
		//i�erisinde �ok kulland���m�z baz� static metotlar vard�r.
		//Metot a��klamalar� SS.java dosyas� i�erisindedir.
		//Bu noktada sahneye ana ekran fragmentini y�kl�yoruz.
		SS.SahneYukle(this, R.id.tasiyici, new AnaFragment());
	}

	//onKeyDown metodu cihaz �zerinde ki fiziksel tu�lar� kontrol eder.
	//Geri tu�u, men� tu�u, anasayfa tu�u, ses a�-kapat vb. tu�lar.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*
		 * Yukarda bahsetti�imiz gibi uygulama i�erisinde bir ak�� sa�lamak i�in 
		 * geri tu�unu takip etmemiz gerekmektedir.
		 * Bu metotda geriye d�nd�r�len de�er false ise butona bas�ld� kabul edilir.
		 * e�er true d�nerse o tu�un basma i�lemi iptal edilir.
		 */
		//E�er cihaz �zerinden geri tu�una bas�lm�� ve ana ekran y�kl� de�ilse
		if(keyCode == KeyEvent.KEYCODE_BACK && !afYuklumu) {
			//ana ekran�n y�klendi�ini belirt.
			afYuklumu = true;
			//Ana ekran fragmentini y�kle.
			SS.SahneYukle(this, R.id.tasiyici, new AnaFragment());
			//geriye true d�nerek geri tu�unun sistem �zerinde i�lem yapmas�n� engeleldik.
			return true;
		} else if(keyCode == KeyEvent.KEYCODE_BACK && afYuklumu) {
			//e�er geri tu�una bas�ld��� s�rada uygulama ekran�nda 
			//ana ekran fragmenti varsa uygulamay� sonland�r�yoruz.
			//Normalde geri tu�u sonland�r�r fakat bazen s�k�nt� ��kartabiliyor.
			//Biz i�imizi sa�lama ald�k burada :)
			this.finish();
		}
		return false;
	}

	//Option menu cihaz �zerinde men� tu�una bas�nca a��lan listedir.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Bu noktada xml olarak haz�rlad���m�z men�y� activity e y�kl�yoruz.
		getMenuInflater().inflate(R.menu.ana_menu, menu);
		return true;
	}
	
	//Option men�den herhangi bir tu� se�ilirse yap�lacak i�lemler.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId(); // id de�i�kenine listeden se�ilen elaman�n id'sini at�yoruz.
		if (id == R.id.action_hakkinda) { //e�er se�ilen eleman hakk�nda ise
			afYuklumu = false; //ana ekrandan ��kld���n� belirtiyoruz.
			//ve ekrana hakk�nda fragmentini y�kl�yoruz.
			SS.SahneYukle(this, R.id.tasiyici, new HakkindaFragment());
		}
		return super.onOptionsItemSelected(item);
	}
	
}
