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
	

	String path = "C:/Users/pdw32/Documents/storePos/ProductList.dat";
	
	// 파일을 읽어오는 메소드
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
	
	
	//파일을 생성하고 읽어오는 메소드
	void readFromFile() {
		ObjectInputStream objInputStream = null;
		FileInputStream inputStream = null;
		
		File dir = new File("C:/Users/pdw32/Documents/storePos"); //파일 경로
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
			inputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			if (inputStream.available() != -1) {
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
				System.out.println("�� ���Ϸ� ���� ������ �����ϴ�.");
			}
		} catch (EOFException e) {
			System.out.println("�� ���Ϸ� ���� ������ �����ϴ�.");
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
}
