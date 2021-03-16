package saleTest2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GoodsController {
	public void menu() throws IOException {
		boolean run = true;

		while (run) {
			System.out.println("\n◈◈◈◈◈  상품 등록 하위메뉴 ◈◈◈◈◈");
			System.out.println("1. 등록");
			System.out.println("2. 목록");
			System.out.println("3. 종료");
			System.out.print("메뉴 번호를 선택 하세요 : ");

			int sNum = Common.input.nextInt(); // 메뉴 선택값 저장.

			fileExistCheck(); // 상품 목록 저장용 파일이 없다면, 생성.
			switch (sNum) {
			case 1:
				preListCheck(); // 상품 목록에 이전 저장 내용 확인 및 문자열(data)로 저장.
				regitstProduct(); // 상품 등록 처리
				break;
			case 2:
				preListCheck(); // 상품 목록 출력
				break;
			case 3:
				run = false; // 종료, 메인 메뉴로 돌아감.
				System.out.println();
				break;
			}
		}
	}

	public void regitstProduct() throws IOException {
		System.out.println("상품을 등록 하세요.");
		StringBuilder sb = new StringBuilder(Common.data);
		// 문자열 data에 저장된 값을 이용하여 sb 생성.

		Common.fos = new FileOutputStream(Common.dir_regist);
		Common.toDo = new OutputStreamWriter(Common.fos);
		Common.input.nextLine(); // 엔터 제거.
		String product = Common.input.nextLine(); // 상품정보 가져오기
		// 상품코드:상품명:가격:재고수량

		sb.append(product); // 기존 내용에 추가.
//		Common.toDo.write("");
		Common.toDo.write(sb + "\r\n"); // 줄바꿈 추가.

		Common.toDo.flush();
		Common.toDo.close();
		Common.fos.close();
		System.out.println(); // 콘솔창 보기용 줄바꿈.
	}

	// 이전 작성 파일 확인.
	public void preListCheck() throws IOException {
		System.out.println("\n===== 상품 목록 =====");
		System.out.println("구분) 상품코드:상품명:가격:입고수량");
		Common.fis = new FileInputStream(Common.dir_regist);
//		Common.reader1 = new InputStreamReader(Common.fis, "utf-8");
		Common.reader1 = new InputStreamReader(Common.fis);

		int readCharNo;
		char[] cbuf = new char[1000];
		if ((readCharNo = Common.reader1.read(cbuf)) != -1) {
			// 1회에 모든 내용을 읽어 오므로 while 이 아니라 if 로 처리해도 결과 동일.
			Common.data = new String(cbuf, 0, readCharNo);
			System.out.println(Common.data);
			System.out.println(readCharNo);
		}
//		if (cnt == 0) {
//			System.out.println("등록된 상품이 없습니다.");
//		}
		Common.reader1.close();
		Common.fis.close();
	}

	// 파일이 없다면 생성.
	public void fileExistCheck() throws IOException {
		Common.file1 = new File(Common.dir_regist);
		if (!Common.file1.isFile()) {
			Common.file1.createNewFile();
		}
	}
}
