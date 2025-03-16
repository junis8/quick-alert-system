import java.util.ArrayList;
import java.util.List;

public class KDTree2D {
	private Node root; // agacin kök dugumu.
	private String name;
	private int time;

	private static class Node {// node classi
		Point2D point; // icindeki veri yapisi
		Node left, right;

		Node(Point2D point) {
			this.point = point;
			this.left = null;
			this.right = null;
		}
	}

	public KDTree2D() {// constructer metot
		root = null;
	}

	public Node getRoot() {// rootu dondurur
		return root;
	}
	
	public String getName() {// rootu dondurur
		return name;
	}
	public void setName(String name) {// rootu dondurur
		this.name=name;
	}
	public void setTime(int time) {// rootu dondurur
		this.time=time;
	}
	public int getTime() {// rootu dondurur
		return time;
	}

	public Point2D insert(Point2D point) {// verilen point verisini eger agacta yoksa ekler ve pointi doner varsa null
											// doner ve eklemez
		if (point == null) {
			return null; // point null ise eklemez ve null doner
		}

		boolean[] eklendiMi = new boolean[1]; // eklenip eklenmedigini takip etmek icin olusturdugum dizi yardımcı
												// metoda veriyorum eger eklenirse true yapıyorum eklenmezse false
												// yapıyorum
		root = insertAux(root, point, 0, eklendiMi); // yardımcı metoda root dugumu verip ekleme islemini yaptırıyorum
														// donen node da guncellenmis rootumuz oluyor
		if (eklendiMi[0]) { // eger eklenme islemi basarılıysa yani dizideki veri true ise pointi donuyor
			return point;
		} else {
			return null;// eger eklenmemisse null donuyor
		}
	}

	private Node insertAux(Node node, Point2D point, int depth, boolean[] eklendiMi) {// insert islemi yardimci metot
		if (node == null) { // leaf dugume ulasildi mi kontorl ediyor
			eklendiMi[0] = true; // ekleme islemi basarili oldugu icin boolean degeri true yapıyor
			Node temp = new Node(point);
			return temp; // yeni dugumu olusturup donduruyor
		}

		// eklenmek istenen pointin daha onceden var olup olmadigini kontrol ediyor
		// varsa ekleme islemi yapılmayıp suanki node u direkt donuyor
		if (point.getX() == node.point.getX() && point.getY() == node.point.getY()) {
			eklendiMi[0] = false; // eklenmedigi icin false olarak ayarliyor
			return node; // suanki node u donduruyor
		}

		// derinligin durumuna gore x ve y degerlerini karsilastirir ve ona gore
		// recursive olarak kendini cagirir
		if (depth % 2 == 0 && point.getX() <= node.point.getX()) {// eger deinlik cift sayi ise ve verinin x degeri
																	// suanki nodeun x degerinden kucuk esit ise
			node.left = insertAux(node.left, point, depth + 1, eklendiMi);// nodeun sol alt agacina eklemek icin
																			// recursive olarak cagirirliir derinlik 1
																			// arttirilir
		} else if (depth % 2 == 1 && point.getY() <= node.point.getY()) { // eger deinlik tek sayi ise ve verinin y
																			// degeri suanki nodeun y degerinden kucuk
																			// esit ise
			node.left = insertAux(node.left, point, depth + 1, eklendiMi);// nodeun sol alt agacina eklemek icin
																			// recursive olarak cagirirliir derinlik 1
																			// arttirilir
		} else {// burda ise verinin x veya y degeri nodeun degelreinden buyuk olacagi icin sag
				// alt agaca eklenmek uzere recursive cagiri yapilir
			node.right = insertAux(node.right, point, depth + 1, eklendiMi);// nodeun sag alt agaci recursive olarak
																			// cagirilan metoda esitlenir donen node
																			// agacin sag alt agaci olarak atanicak
		}

		return node; // suanki node degerini dondurerek atama islemlerinin yapilmasini saglar
	}

	public Point2D search(Point2D point) {// serach metodu
		return searchAux(root, point, 0);
	}

	private Point2D searchAux(Node node, Point2D point, int depth) { // arama islemi icin kullanilan yardimci metot

		if (node == null) {
			return null; // eger point bulunamazsa null doner.
		}
		if (node.point.equals(point)) {
			return node.point; // eger point bulunursa pointi doner
		}

		if (depth % 2 == 0 && point.getX() <= node.point.getX()) {
			// derinlik çift ise ve pointin x degeri suanki nodeun x degerinden kucuk esit
			// mi kontrol eder
			return searchAux(node.left, point, depth + 1); // kucuk oldugu icin sol alt agac recursive olarak cagirilir
		} else if (depth % 2 == 1 && point.getY() <= node.point.getY()) {
			// derinlik tek ise ve pointin y degeri suanki nodeun y degerinden kucuk esit mi
			// kontrol eder
			return searchAux(node.left, point, depth + 1); // kucuk oldugu icin sol alt agac recursive olarak cagirilir
		} else {
			return searchAux(node.right, point, depth + 1); // geri kalan durmularda yani pointin degerlerinin buyuk
															// oldugu durumlarda sag alt agaci recursive olarak cagirir
		}
	}

	public void displayTree() {// agaci goruntulemek icin metot
		displayTreeAux(root, 0);
	}

	private void displayTreeAux(Node node, int depth) { // agaci yazdirmak için yardimci metot
		if (node == null)
			return; // node null ise durur

		for (int i = 0; i < depth; i++) {
			// mevcut derinlige gore nokta ekler
			System.out.print(". ");
		}

		System.out.println("(" + node.point.getX() + ", " + node.point.getY() + ")");// output metni
		displayTreeAux(node.left, depth + 1); // sol alt agaci yazdirir
		displayTreeAux(node.right, depth + 1); // sag alt agaci yazdirir
	}

	public void remove(Point2D point) {// verilen point degerini agactan siler
		if (point == null) { // point null ise hata verir
			throw new IllegalArgumentException("Point cannot be null");
		}
		root = removeAux(root, point, 0);// yardimci metoda root vererek cagirir
	}

	private Node removeAux(Node node, Point2D point, int depth) {// silmek icin yardimci metot
		if (node == null) { // eger node null ise null return eder
			return null;
		}

		if (node.point.equals(point)) {// point degeri bulnudysa bu ife girer
			// eger nodeun cocugu yoksa direkt siler
			if (node.left == null && node.right == null) {
				return null; // null dondurup nodeun kalkmasi saglanir
			}

			// eger nodeun sadece 1 cocugu varsa cocuk nodeun yerine gecer
			if (node.right == null) {
				return node.left; // sol cocuk avrsa yeni node sol cocuk olur
			}
			if (node.left == null) {
				return node.right; // sag cocuk avrsa yeni node sag cocuk olur
			}

			// eger nodeun iki cocugu da varsa sag alt ağaçtaki maksimum pointi bulur bunu
			// agacin yapisini bozmamak icin yapar
			Node minNode = findMinRec(node.right, depth % 2, depth + 1);
			node.point = minNode.point; // nodeun pointini bu minimum pointle degistir
			node.right = removeAux(node.right, minNode.point, depth + 1); // daha sonra minimum nodeu kaldirir
			return node;
		}

		// point suanki dugumde degilse ilerler
		if ((depth % 2 == 0 && point.getX() <= node.point.getX()
				|| (depth % 2 == 1 && point.getY() <= node.point.getY()))) { // derinlige gore x degerlerini kontorl
																				// eder kucukse sol alt agaca bakar
			node.left = removeAux(node.left, point, depth + 1); // sol alt agaca ilerler ve return edilen alt agaci sol
																// alt agac olarak duzenler
		} else {// kalan tum durmular icin sag alt agaca ilerler
			node.right = removeAux(node.right, point, depth + 1); // sag alt agaca ilerler ve return edilen alt agaci
																	// sag alt agac olarak duzenler
		}

		return node; // nodeu geri dondurur

	}

	public Point2D findMin(int d) {
		if (d < 0 || d > 1) { // dimension degeri gecersizse exception atar
			throw new IllegalArgumentException("Dimension must be 0 (x) or 1 (y).");
		}

		if (root == null) {
			return null; // agac bossa null doner
		}
		Node sonuc = findMinRec(root, d, 0);//yardimci metodu cagirir ve roottan baslayarak min point degrini bulur
		return sonuc.point; 
	}

	private Node findMinRec(Node node, int dimension, int depth) {
		if (node == null) {
			return null; // alt agac bossa null dondurur
		}

		// recursive olarak sol ve sag alt agaclari kontrol eder ve en kucuk point degerlerini bulur kaydeder
		Node solMin = findMinRec(node.left, dimension, depth + 1);
		Node sagMin = findMinRec(node.right, dimension, depth + 1);

		// suanki nodeu ,sag alt agactan ve sol alt agactan gelen min degerleri karsiastirilir en kucuk olan minNode variableina atanir
		
		//  suanki nodeu baslangic olarak min ayarlar
		Node minNode = node;

		// sol alt agactan bulunan min degeir null degilse bu suanki minNode degeriyle karslastirir kucuk olani alir
		if (solMin != null) {
			if (dimension == 0) { // dimensiona gore x veya y dgerini karsılastirir kucuk olanı minNode degerine atar
				if (solMin.point.getX() < minNode.point.getX()) {
					minNode = solMin;
				}
			} else { // Y boyutunu kontrol eder
				if (solMin.point.getY() < minNode.point.getY()) {
					minNode = solMin;
				}
			}
		}

		// sag alt agactan bulunan min degeir null degilse bu degeri suanki minNode degeriyle karslastirir kucuk olani alir
		if (sagMin != null) {
			if (dimension == 0) {// dimensiona gore x veya y dgerini karsılastirir kucuk olanı minNode degerine atar
				if (sagMin.point.getX() < minNode.point.getX()) {
					minNode = sagMin;
				}
			} else { // Y boyutunu kontrol eder
				if (sagMin.point.getY() < minNode.point.getY()) {
					minNode = sagMin;
				}
			}
		}

		return minNode; // minimum nodeu dondurur
	}

	public Point2D findMax(int d) {
		if (d < 0 || d > 1) { // dimension degeri gecersizse exception atar
			throw new IllegalArgumentException("Dimension must be 0 (x) or 1 (y).");
		}

		if (root == null) {
			return null; // agac bossa null doner
		}
		Node sonuc = findMaxRec(root, d, 0); //yardimci metodu cagirir ve roottan baslayarak max point degrini bulur
		return sonuc.point; // 
	}

	private Node findMaxRec(Node node, int dimension, int depth) {
		if (node == null) {
			return null; // alt agac bossa null dondurur
		}

		// recursive olarak sol ve sag alt agaclari kontrol eder ve en buyuk point degerlerini bulur kaydeder
		Node solMax = findMaxRec(node.left, dimension, depth + 1);
		Node sagMax = findMaxRec(node.right, dimension, depth + 1);

		// suanki nodeu ,sag alt agactan ve sol alt agactan gelen max degerleri karsiastirilir en buyuk olan maxNode variableina atanir
		
		//  suanki nodeu baslangic olarak max ayarlar
		Node maxNode = node;

		// sol alt agactan bulunan max degeir null degilse bu suanki maxNode degeriyle karslastirir buyuk olani alir
		if (solMax != null) {
			if (dimension == 0) { // dimensiona gore x veya y dgerini karsılastirir buyuk olanı maxNode degerine atar
				if (solMax.point.getX() > maxNode.point.getX()) {
					maxNode = solMax;
				}
			} else { // Y boyutunu kontrol eder
				if (solMax.point.getY() > maxNode.point.getY()) {
					maxNode = solMax;
				}
			}
		}

		// sag alt agactan bulunan max degeir null degilse bu degeri suanki maxNode degeriyle karslastirir buyuk olani alir
		if (sagMax != null) {
			if (dimension == 0) { // dimensiona gore x veya y dgerini karsılastirir buyuk olanı maxNode degerine atar
				if (sagMax.point.getX() > maxNode.point.getX()) {
					maxNode = sagMax;
				}
			} else { // Y boyutunu kontrol eder
				if (sagMax.point.getY() > maxNode.point.getY()) {
					maxNode = sagMax;
				}
			}
		}
		
		

		return maxNode; // maksimum nodeu doner
	}

	public Iterable<Point2D> printRectangularRange(Point2D ll, Point2D ur) { // verilen dikdortgen degerin icindeki
																				// pointleri liste olarak dondurur
		ArrayList<Point2D> sonuc = new ArrayList<>(); // dondurulecek olan liste
		printRectangularRangeAux(root, ll, ur, 0, sonuc); // yardımcı metot
		return sonuc;
	}

	private void printRectangularRangeAux(Node node, Point2D ll, Point2D ur, int depth, List<Point2D> sonuc) {
		if (node == null) {
			return; // eger dugum null ise return edip cikar
		}

		double x = node.point.getX(); // pointin x ve y degerleri
		double y = node.point.getY();

		// pointin dikdortgen icinde olup olmadigini kontrol eder
		if (x >= ll.getX() && x <= ur.getX() && y >= ll.getY() && y <= ur.getY()) {
			sonuc.add(node.point); // point aralıkta listeye ekler
		}

		// Sol alt agaci gerekliyse kontrol eder
		if (depth % 2 == 0 && ll.getX() <= x) {
			printRectangularRangeAux(node.left, ll, ur, depth + 1, sonuc);
		}
		if (depth % 2 == 1 && ll.getY() <= y) {
			printRectangularRangeAux(node.left, ll, ur, depth + 1, sonuc);
		}

		// sag alt agaci gerekliyse kontrol eder
		if (depth % 2 == 0 && ur.getX() > x) {
			printRectangularRangeAux(node.right, ll, ur, depth + 1, sonuc);
		}
		if (depth % 2 == 1 && ur.getY() > y) {
			printRectangularRangeAux(node.right, ll, ur, depth + 1, sonuc);
		}
	}

	public Iterable<Point2D> printCircularRange(Point2D center, double radius) {// verilen cemberin icindeki pointleri
																				// liste olarak dondurur
		if (radius < 0) { // yaricap negatifse hata firlatir
			throw new IllegalArgumentException("Radius cannot be negative.");
		}
		ArrayList<Point2D> sonuc = new ArrayList<>(); // sonuc listesi
		printCircularRangeRec(root, center, radius, 0, sonuc);// yardimci metot
		return sonuc;
	}

	private void printCircularRangeRec(Node node, Point2D center, double radius, int depth, List<Point2D> sonuc) {
		if (node == null) {
			return; // eger dugum null ise return edip cikar
		}

		double x = node.point.getX();// pointin x ve y degerleri
		double y = node.point.getY();

		// cemberin icindeki noktalari kontrol eder
		if (daireIcindeMi(center, node.point, radius)) { // yardimci metot verilen point cemberin icindeyse true
															// doner
			sonuc.add(node.point); // point dairenin icinde oldugu icin listeye ekler
		}

		// Sol alt agaci gerekliyse kontrol eder
		if (depth % 2 == 0 && center.getX() - radius <= x) {
			printCircularRangeRec(node.left, center, radius, depth + 1, sonuc);
		}
		if (depth % 2 == 1 && center.getY() - radius <= y) {
			printCircularRangeRec(node.left, center, radius, depth + 1, sonuc);
		}

		// sag alt agaci gerekliyse kontrol eder
		if (depth % 2 == 0 && center.getX() + radius > x) {
			printCircularRangeRec(node.right, center, radius, depth + 1, sonuc);
		}
		if (depth % 2 == 1 && center.getY() + radius > y) {
			printCircularRangeRec(node.right, center, radius, depth + 1, sonuc);
		}
	}

	private boolean daireIcindeMi(Point2D center, Point2D point, double yarıcap) {
		double xFark = point.getX() - center.getX(); // X eksenindeki fark
		double yFark = point.getY() - center.getY(); // Y eksenindeki fark
		double mesafeKare = xFark * xFark + yFark * yFark; // Mesafe karesi
		double yarıcapKare = yarıcap * yarıcap; // Yarıçapın karesi
		//iki nokta arasindaki uzaklik formulunu uygular 
		//d = √((x2 - x1)^2 + (y2 - y1)^2)
		//  yarıcap ile veirlen poitnin uzakligini bulur bu yarıcaptan kucuk mu diye bakar

		boolean icindeMi = mesafeKare <= yarıcapKare;
		return icindeMi; // point cemberin icindeyse true doner
	}
	
	 // getAllPoints metodunu ekleyelim.
    public List<Point2D> getAllPoints() {
        List<Point2D> points = new ArrayList<>();
        getAllPointsAux(root, points);
        return points;
    }

    // getAllPointsAux yardımıyla tüm noktaları toplayacağız
    private void getAllPointsAux(Node node, List<Point2D> points) {
        if (node == null) {
            return;
        }
        points.add(node.point); // mevcut noktayı ekle
        getAllPointsAux(node.left, points); // Sol alt ağacı kontrol et
        getAllPointsAux(node.right, points); // Sağ alt ağacı kontrol et
    }

}