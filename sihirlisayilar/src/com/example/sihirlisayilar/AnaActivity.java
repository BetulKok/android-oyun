package com.example.sihirlisayilar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

//Uygulamamýnýz ana activity'sidir.
public class AnaActivity extends FragmentActivity {

	/*
	 * Android'de 3 sürümünden beri var olan fragmentler,
	 * yeni android sdk sýnda varsayýlan olarak gelmektedir.
	 * Eski mantýkta activityler ile sayfa geçiþleri yapýlýrken artýk
	 * Tek bir activity üzerinde fragment geçiþleri yapýlmaktadýr.
	 * Faydalarý:
	 * Bir activity deðiþtiðinde tüm ekraný deðiþtirmektedir buda cihaz için yüktür.
	 * fragment ise sadece görüntü elemanlarýný deðiþtirir.
	 * Buda bize esnek bir sayfa tasarlama imkaný sunar.
	 * bir aktivity üzerindeki farklý layoutlara farklý fragmentler yüklenerek göörselliði artýrabiliriz.
	 * ve fragment geçiþleri activity geçiþlerine göre daha hýzlýdýr.
	 * Fakat kullanýrken dikkat edilmesi gereken bir konu vardýr.
	 * Geri tuþuna basýlýnca önce ki fragment geri gelmez.
	 * Sistem geri tuþunda geçmiþ hafýzasý oalrak sadece ectivity leri saklar.
	 * Bu nedenle uygulama içerisinde gezinme ortamý oluþturabilmek için 
	 * mecburen geri tuþunu takip eden bir kontrol oluþturmak gerekmektedir.
	 */
	
	public static boolean afYuklumu; //Bu deðiþken ana ekranýn yüklü olup olmadýðýný kontrol eder.
	
	/*
	 * Her uygulama bir Activity'den baþlamak zorundadýr.
	 * Bu aktivitelerin bir yaþam döngüsü vardýr.
	 * http://www.fpganedir.com/android/aktivite_yonetim/index.php
	 * linkte ki yaþam döngüsü gereði her uygulama onCreate metodu ile baþlar.
	 * onCreate metodu uygulama ilk açýldýktan sonra uygulamaya 
	 * görsel arayüzün eklemesinin yapýlacaðý yerdir.
	 * Ve xml dosyalarý içerisinde ki kontrollere eriþmek için 
	 * bu metotta tanýmlaam yapmak gerekir.
	 * 
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		//super Kalýtýmýn alýndýðý sýnýfý temsil eder.
		// Bizim uygulamamýzda Kalýtýmýn alýndýðý sýnýf FragmentActivity'dir.
		super.onCreate(arg0); // Javada Override edilen metot kalýtýmý aldýðý metota super parametresi ile deðer gönderir.
		setContentView(R.layout.ana_activity); //Bu metot oluþturduðumuz arayüzleri ekrana yükler.
		afYuklumu = true; //Ana ekranýn yüklendiðini belirtiyoruz.
		//SS sýnýfý bizim oluþturduðumuz bir sýnýftýr ve 
		//içerisinde çok kullandýðýmýz bazý static metotlar vardýr.
		//Metot açýklamalarý SS.java dosyasý içerisindedir.
		//Bu noktada sahneye ana ekran fragmentini yüklüyoruz.
		SS.SahneYukle(this, R.id.tasiyici, new AnaFragment());
	}

	//onKeyDown metodu cihaz üzerinde ki fiziksel tuþlarý kontrol eder.
	//Geri tuþu, menü tuþu, anasayfa tuþu, ses aç-kapat vb. tuþlar.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*
		 * Yukarda bahsettiðimiz gibi uygulama içerisinde bir akýþ saðlamak için 
		 * geri tuþunu takip etmemiz gerekmektedir.
		 * Bu metotda geriye döndürülen deðer false ise butona basýldý kabul edilir.
		 * eðer true dönerse o tuþun basma iþlemi iptal edilir.
		 */
		//Eðer cihaz üzerinden geri tuþuna basýlmýþ ve ana ekran yüklü deðilse
		if(keyCode == KeyEvent.KEYCODE_BACK && !afYuklumu) {
			//ana ekranýn yüklendiðini belirt.
			afYuklumu = true;
			//Ana ekran fragmentini yükle.
			SS.SahneYukle(this, R.id.tasiyici, new AnaFragment());
			//geriye true dönerek geri tuþunun sistem üzerinde iþlem yapmasýný engeleldik.
			return true;
		} else if(keyCode == KeyEvent.KEYCODE_BACK && afYuklumu) {
			//eðer geri tuþuna basýldýðý sýrada uygulama ekranýnda 
			//ana ekran fragmenti varsa uygulamayý sonlandýrýyoruz.
			//Normalde geri tuþu sonlandýrýr fakat bazen sýkýntý çýkartabiliyor.
			//Biz iþimizi saðlama aldýk burada :)
			this.finish();
		}
		return false;
	}

	//Option menu cihaz üzerinde menü tuþuna basýnca açýlan listedir.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Bu noktada xml olarak hazýrladýðýmýz menüyü activity e yüklüyoruz.
		getMenuInflater().inflate(R.menu.ana_menu, menu);
		return true;
	}
	
	//Option menüden herhangi bir tuþ seçilirse yapýlacak iþlemler.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId(); // id deðiþkenine listeden seçilen elamanýn id'sini atýyoruz.
		if (id == R.id.action_hakkinda) { //eðer seçilen eleman hakkýnda ise
			afYuklumu = false; //ana ekrandan çýkldýðýný belirtiyoruz.
			//ve ekrana hakkýnda fragmentini yüklüyoruz.
			SS.SahneYukle(this, R.id.tasiyici, new HakkindaFragment());
		}
		return super.onOptionsItemSelected(item);
	}
	
}
