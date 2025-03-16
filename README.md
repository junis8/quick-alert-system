#Deprem Tespit Sistemi
Bu proje, deprem verilerini işlemek ve büyük depremleri tespit etmek amacıyla geliştirilmiş bir yazılımdır. Sistem, KD-Tree veri yapısını kullanarak 2D noktalar üzerinden hızlı aramalar yapar. Ayrıca Priority Queue veri yapısı ile en büyük depremler sıralanarak en güçlü depremin tespiti sağlanır.

#Özellikler
KD-Tree Veri Yapısı: 2D noktalar üzerinde hızlı arama ve ekleme işlemleri.
Priority Queue: En büyük depremleri sıralamak için kullanılır.
Deprem Verisi İşleme: Deprem bilgilerini içeren veriler işlenir (ID, zaman, yer, koordinatlar, büyüklük).
Komut Tabanlı İşlem: Sistemde add, delete, query-largest gibi işlemler yapılabilir.
XML Veri Yükleme: Deprem verileri XML formatında yüklenebilir.

#Teknolojiler ve Araçlar
Java 8+
KD-Tree Veri Yapısı (2D nokta verilerini hızlıca işlemek için)
HeapPriorityQueue (En büyük depremleri sıralamak için)
XML İşleme (Deprem verilerinin yüklenmesi için)

#Kurulum ve Çalıştırma
1. Adım: Depoyu Klonlama
Projeyi bilgisayarınıza klonlamak için terminalde aşağıdaki komutu çalıştırabilirsiniz:
git clone https://github.com/kullaniciadi/deprem-tespit-sistemi.git

2. Adım: Bağımlılıkları Yükleyin
Projede ek bir bağımlılık yönetimi (Maven, Gradle vb.) kullanılmamaktadır, ancak Java'yı yüklemeniz gerekecektir. Projeyi çalıştırmak için JDK'nın yüklü olduğundan emin olun.

3. Adım: Uygulamayı Çalıştırma
Projeyi çalıştırmak için Java IDE'nizde projeyi açın veya terminalde şu komutu çalıştırın:

java Main
Bu komut, Main sınıfındaki programı çalıştırarak deprem tespit sistemini başlatacaktır.

#Komutlar
Proje belirli komutlarla çalışır. İşte bazı komutlar:

add: Yeni bir deprem ekler.
Örnek: "0 add -105.7 -24.3 Tom"
Bu komut, "Tom" isimli deprem gözlemcisini (-105.7, -24.3) koordinatlarına ekler.

delete: Bir deprem gözlemcisini siler.
Örnek: "8 delete Taylor"
Bu komut, "Taylor" isimli gözlemciyi siler.

query-largest: En büyük büyüklüğe sahip depremi sorgular.
Örnek: "10 query-largest"
Bu komut, mevcut en büyük depremi ekrana yazdırır.

Deprem verilerini yüklemek: XML dosyasından deprem verilerini yükler.

#Deprem Verisi Formatı
Deprem verileri XML formatında aşağıdaki şekilde temsil edilir:
<earthquakes>
  <earthquake>
    <id>1</id>
    <time>2023-10-01T10:00:00Z</time>
    <place>San Francisco</place>
    <coordinates>-122.4194,37.7749</coordinates>
    <magnitude>6.8</magnitude>
  </earthquake>
  <!-- Diğer depremler -->
</earthquakes>
