package pos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;

public class ProductManager {
	
	//HashSet생성 (상품리스트)
	HashSet<ProductInfo> ProductList = new HashSet<>();
	

	String path = "C:/Users/pdw32/Documents/storePos/data/goods.txt";
	
	// 파일을 출력하는 메소드(프로그램을 종료할 때 실행)
	void storeToFile() {
		if (ProductList != null) { // ProductList가 있다면
			Iterator<ProductInfo> itr = ProductList.iterator(); //ProductList에 있는 것을 차례대로 가져온다.
			// hashSet 객체에서 요소에 접근하기 위한 iterator(반복자) 변수를 초기화.

			ObjectOutputStream objOut = null; // 출력 처리를 위한 변수지정.
			FileOutputStream fileOut = null; // 출력 처리를 위한 변수지정.

			try {
				fileOut = new FileOutputStream(path); // path파일에
				objOut = new ObjectOutputStream(fileOut); // fileOut내용을 넣는다.

				while (itr.hasNext()) { //hasNext() : Iterator으로 다음 원소가 남아있는지 여부를 알아냅니다. 다음 원소가 남았다면 true를 반환
					ProductInfo p = itr.next(); //next() : 다음 원소를 가져옴.
					objOut.writeObject(p); //직렬화 : 객체를 바이너리 정보로 전환하여 원격지로 보내는 기술.
					//ProductInfo.java에 implements Serializable가 필요
				}
			} catch (IOException e) { //오류 출력
				e.printStackTrace();
			} finally { //끝나면 무조건 실행하는 역할
				try {
					objOut.close(); //기본적으로 닫는 메소드
					fileOut.close(); //기본적으로 닫는 메소드
				} catch (IOException e) { //오류 출력
					e.printStackTrace();
				}
			}
		}
	}
	
	
	static ProductManager inst = null;

	public static ProductManager creatManagerInst() {
		if (inst == null)
			inst = new ProductManager();
		return inst;
	}
	
	
	//읽어오고 없으면 생성하는 메소드 (프로그램 실행시 실행)
	void readFromFile() {
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;
		
		File dir = new File("C:/Users/pdw32/Documents/storePos/data"); //파일 경로
		//파일 경로에 폴더가 없다면 폴더 생성
		if (!dir.isDirectory()) { //isDirectory() : 경로에 있는 객체가 Folder이면 true 리턴 / 아니면 false 리턴
			dir.mkdir(); //mkdir() : 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 생성 불가
		}
		
		
		File file1 = new File(path);
		//폴더 경로 안에 파일이 없다면 파일생성
		if (!file1.isFile()) { //isFile() : 경로가 file인지 확인한다.
			try {
				file1.createNewFile(); //createNewFile() : 파일생성
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		try {
			inputStream = new FileInputStream(path); //역질렬화
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			if (inputStream.available() != -1) { //available() : 현재 읽을수 있는 바이트수를 반환한다.
				// inputStream.available() : inputStream 에서 읽어올 내용이 있는가?
				// 없다면 -1 리턴.
				objInputStream = new ObjectInputStream(inputStream);
				// 역직렬화 준비.
				// 바이너리 코드를 자바 객체로 전환.

				while (inputStream.available() > 0) {
					// eof : end of file
					try {
						ProductList.add((ProductInfo) objInputStream.readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				objInputStream.close();

			} else {
				System.out.println("저장된 파일이 존재하지 않습니다.");
			}
		} catch (EOFException e) {
			System.out.println("저장된 파일이 존재하지 않습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//상품 관리
	void ProductManagement() {
		System.out.println("----------------");
		System.out.println("1. 상품 등록");
		System.out.println("2. 상품 삭제");
		System.out.println("3. 상품 수정");
		System.out.println("4. 상품 재고량");
		System.out.println("5. 뒤로 가기");
		System.out.println("----------------");	
		System.out.print("번호를 선택해주세요 : ");
		int cho_1 = Main.sca.nextInt();
		
		if (cho_1 == 1) {
			ProductEnrollment();
			ProductManagement();
		} else if(cho_1 == 2) {
			ProductDelete();
			ProductManagement();
		} else if(cho_1 == 3) {
			ProductChange();
			ProductManagement();
		} else if(cho_1 == 4) {
			ProductQuantity();
			ProductManagement();
		} else if(cho_1 == 5) {
			Menu.showProduct();
		}
		
		
	}
	
	//1-1.상품 등록
	void ProductEnrollment() {
		String pCode, pName;
		int pPrice, pStock;
				
		System.out.println("상품을 등록을 시작합니다.");
		System.out.print("상품코드:");
		pCode = Main.sca.next();
		System.out.print("상품명:");
		pName = Main.sca.next();
		System.out.print("상품가격:");
		pPrice = Main.sca.nextInt();
		System.out.print("재고량:");
		pStock = Main.sca.nextInt();
		
		ProductInfo pi = new ProductInfo(pCode, pName, pPrice, pStock);
		ProductList.add(pi);
		
		System.out.println();
	}
	
	
	//삭제,수정,재고량 사용
	boolean searchIndex(String name, String state) {
		Iterator<ProductInfo> itr = ProductList.iterator();
		boolean has = false;

		while (itr.hasNext()) {
			ProductInfo p = itr.next();

			if (name.equals(p.getProductName())) {
				if (state.equals("del")) { //삭제할 때
					itr.remove();
					System.out.println("해당 제품을 삭제하였습니다.");
					has = true;
				} 
				else if (state.equals("Change")) { //변경할 때
					System.out.println("상품정보를 수정합니다.");
					System.out.print("상품가격:");
					int pPrice = Main.sca.nextInt();
					System.out.print("재고량:");
					int pStock = Main.sca.nextInt();
					
					p.setProductPrice(pPrice);
					p.setStock(pStock);

					has = true;
				} 
				break;
			}
		}

		return has;

	}
	
	//1-2.상품 삭제
	void ProductDelete() {
		System.out.println("삭제하고 싶은 상품명을 검색하세요.");
		System.out.print("상품명 : ");
		String pName = Main.sca.next();
		boolean has = searchIndex(pName, "del");
		
		if (!has) {
			System.out.println("찾으시는 이름이 없습니다.");
		}

	}
	
	//1-3.상품 수정
	void ProductChange() {
		System.out.println("수정하고 싶은 상품명을 검색하세요.");
		System.out.print("상품명 : ");
		String pName = Main.sca.next();
		boolean has = searchIndex(pName, "Change");
		
		if (!has) {
			System.out.println("찾으시는 이름이 없습니다.");
		}

	}
	
	
	//1-4.재고량
	void ProductQuantity() {
		Iterator<ProductInfo> itr = ProductList.iterator();
		
		while(itr.hasNext()) {
			ProductInfo p = itr.next();
			p.showProductQuantity();
		}
	}
	
		
	//1-5.뒤로가기
	void moveBack() {
		Menu.showProduct();
		System.out.print("선택 : ");
		int choice = Main.sca.nextInt();
	}
	
	
	//2. 상품 주문
	void ProductBuying() {
		Iterator<ProductInfo> itr = ProductList.iterator();
		
		//출력
		int amount = 1;
		while(itr.hasNext()) {
			ProductInfo p = itr.next();
			p.showCart(amount);
			amount++;
		}
		System.out.println("0. 최종주문 \t 1000.뒤로가기");
		
		System.out.print("선택: ");
		
		while(true) {
			int i = Main.sca.nextInt();
			int count = 0;
			if(i == 0) {
				System.out.println("");
				
			} else if(i > 0 || i < 1000) {
				
				
				count += count;
				
			} else if(i == 1000) {
				Menu.showProduct();
			}
		}
		
	}
}
