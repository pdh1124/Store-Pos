package saleTest2;

import java.io.File;
import java.io.IOException;

// 판매 내역 처리
public class SalesController {
	GoodsController gc = new GoodsController();
	// GoodsController 메소드에 접근하기 위해서 필드값으로 객체 생성.
	SalesMethod sm = new SalesMethod();
	// SalesMethod 메소드에 접근하기 위해서 필드값으로 객체 생성.

	public void menu() throws IOException {
		boolean run = true; // 종료 깃발
		gc.fileExistCheck(); // 상품 정보 저장용 파일 존재 확인.
		fileExistCheck(); // 판매 내역 저장용 파일 존재 확인
		loadProductObject(); // 상품 목록 객체 배열로 저장.

		while (run) {
			System.out.println("\n♠♠♠♠♠ 판매 등록 하위메뉴 ♠♠♠♠♠");
			System.out.println("1. 판매 등록");
			System.out.println("2. 판매 내역");
			System.out.println("3. 종료");
			System.out.print("메뉴 번호를 선택 하세요 : ");
			int sNum = Common.input.nextInt(); // 사용자 메뉴 선택 숫자값으로 저장.

			switch (sNum) {
			case 1:
				sm.regitstSale(); // 등록 시작
				break;
			case 2:
				sm.preListCheck(1); // 이전 판매 내역 조회 및 문자열에 저장.
				// 전달값 1은 내역 저장시 정보 출력.
				// 전달값이 0은 정보 출력 안함.
				break;
			case 3:
				run = false; // 종료
				Common.gCnt = 0; // 객체 배열에 종료후에 값이 저장되지 않도록 0으로 초기화.
				Common.data = ""; // 객체 배열에 이전 등록시 사용했던 정보가 저장되지 않도록 종료시 초기화.
				break;
			default:
				System.out.println();
			}
		}
	}

	// 파일 존재 여부 확인, 없다면 생성.
	public void fileExistCheck() throws IOException {
		Common.file2 = new File(Common.dir_sale);
		if (!Common.file2.isFile()) {
			Common.file2.createNewFile();
		}
	}

	// 상품목록 텍스트 파일을 객체 배열로 변환.
	public void loadProductObject() throws IOException {
		gc.preListCheck(); // 이전 상품 목록 읽어오기 및 데이타 문자열 변수에 저장.

		String[] row = Common.data.split("\r\n");
//		System.out.println("row.length = " + row.length);// 아무 내용이 없어도 1
		if (row.length > 0) {
			for (int i = 0; i < row.length; i++) {
				String[] col = row[i].split(":");
//				System.out.println("컬럼 갯수 : "+col.length);// 정상 4, 비정상 1
				if(col.length >=4) {
					GoodsMethod p = new GoodsMethod();
					for (int j = 0; j < col.length; j++) {
						p.setpCode(col[0]); // 상품코드
						p.setpName(col[1]); // 상품명
						p.setpValue(col[2]); // 상품가격
						p.setpStock(col[3]); // 상품수량
					}
					Common.ptArr[Common.gCnt++] = p; // 객체배열에 저장.					
				}else {
					System.out.println("등록된 상품이 없습니다.1");
				}
				
				
			}
		}
	}

}
