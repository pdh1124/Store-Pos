package saleTest2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesMethod {
// 계좌를 관리하는 뱅크 처럼.

	public void regitstSale() throws IOException {
		System.out.println("판매 내역을 등록 하세요.");
		int tmpI = -1;
		String pCode, pName, pValue, pStock;
		int saleStock;

		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddhhmmss");
		String oCode = dayTime.format(new Date(time));
		System.out.print("주문번호 : " + oCode + "\n");

		// 상품코드 확인하여
		// 객체 배열에서 검색후
		// 해당 상품 정보 표시
		while (true) {
			System.out.print("상품코드 : ");
			pCode = Common.input.next();
			tmpI = searchProduct(pCode); // 객체 배열에서 일치하는 코드 검색.

			if (tmpI != -1) { // 일치하는 값이 있다면,
				System.out.println(Common.ptArr[tmpI].getpCode() + ":" + Common.ptArr[tmpI].getpName() + ":"
						+ Common.ptArr[tmpI].getpValue() + ":" + Common.ptArr[tmpI].getpStock());
				break;
			} else {
				System.out.println("입력하신 상품코드로 등록된 내역이 없습니다.");
			}
		}

		// 정확한 상품명을 입력할때 까지 반복 입력
		while (true) {
			System.out.print("상품명 : ");
			pName = Common.input.next();

			if (Common.ptArr[tmpI].getpName().equals(pName)) {
				break;
			} else {
				System.out.println("상품명이 일치하지 않습니다.");
			}
		}

		// 정확한 상품가격을 입력할때 까지 반복 입력
		while (true) {
			System.out.print("상품가격 : ");
			pValue = Common.input.next();

			if (Common.ptArr[tmpI].getpValue().equals(pValue)) {
				break;
			} else {
				System.out.println("상품가격이 일치하지 않습니다.");
			}
		}

		// 정확한 구매수량을 입력할때 까지 반복 입력
		while (true) {
			System.out.print("판매수량 : ");
			pStock = Common.input.next();

			saleStock = Integer.parseInt(pStock);
			int stock = Integer.parseInt(Common.ptArr[tmpI].getpStock());

			if (saleStock <= stock) { // 판매수량이 재고수량 이상 이라면,
				stock -= saleStock;
				Common.ptArr[tmpI].setpStock(stock + "");
				break;
			} else {
				System.out.println("재고가 부족합니다.");
			}
		}

		int total = saleStock * Integer.parseInt(pValue);

		System.out.println("판매 내역이 등록 되었습니다.");
		System.out.print("확인) ");
		System.out.println(oCode + ":" + pCode + ":" + pName + ":" + pValue + ":" + pStock + ":" + total);
		String result = oCode + ":" + pCode + ":" + pName + ":" + pValue + ":" + pStock + ":" + total;

		updateProduct(); // save temp, 상품 목록 저장
		saveSales(result); // save temp_sale, 판매 내역 저장

	}

	public int searchProduct(String pCode) {
		int findIndex = -1;
//		System.out.println("searchProduct 의 총레코드수"+Common.gCnt);
		for (int i = 0; i < Common.gCnt; i++) {
			if (Common.ptArr[i].getpCode().equals(pCode)) {
				findIndex = i;
				System.out.println("\n해당 코드 상품을 찾았습니다.");
				System.out.println("구분) 상품코드:상품명:가격:입고수량");
			}

//			System.out.println(Common.ptArr[i].getpCode() + ":" + Common.ptArr[i].getpName() + ":" 
//			+ Common.ptArr[i].getpValue() + ":"
//					+ Common.ptArr[i].getpStock());
		}

		return findIndex;
	}

	public void updateProduct() throws IOException {
		Common.fos = new FileOutputStream(Common.dir_regist);
		Common.toDo = new OutputStreamWriter(Common.fos);

		for (int i = 0; i < Common.gCnt; i++) {
			Common.toDo.write(Common.ptArr[i].getpCode() + ":" + Common.ptArr[i].getpName() + ":"
					+ Common.ptArr[i].getpValue() + ":" + Common.ptArr[i].getpStock());
			Common.toDo.write("\r\n");

//			System.out.print("update_goods = "+Common.ptArr[i].getpCode() + ":" 
//					+ Common.ptArr[i].getpName() + ":" 
//					+ Common.ptArr[i].getpValue() + ":"
//					+ Common.ptArr[i].getpStock() + "\r\n");
		}

		Common.toDo.flush();
		Common.toDo.close();
		Common.fos.close();
	}

	// 판매내역 저장 메소드
	public void saveSales(String result) throws IOException {
		preListCheck(0);

		Common.fos = new FileOutputStream(Common.dir_sale);
		Common.toDo = new OutputStreamWriter(Common.fos);
		Common.toDo.write(Common.data2);
		Common.toDo.write(result);
		Common.toDo.write("\r\n");

		Common.toDo.flush();
		Common.toDo.close();
		Common.fos.close();
	}

	public void preListCheck(int mode) throws IOException {
		if (mode == 1) {
			System.out.println("\n▒▒▒▒▒ 판매 내역 ▒▒▒▒▒");
			System.out.println("주문코드:상품코드:상품명:단가:판매수량:매출");
		}

		Common.fis = new FileInputStream(Common.dir_sale);
		Common.reader1 = new InputStreamReader(Common.fis, "utf-8");

		int readCharNo, cnt = 0;
		char[] cbuf = new char[1000];
		while ((readCharNo = Common.reader1.read(cbuf)) != -1) {
			Common.data2 = new String(cbuf, 0, readCharNo);
			if (mode == 1) {
				System.out.println(Common.data2);
			}
			cnt++;
		}
		if (cnt == 0) {
			if (mode == 1) {
				System.out.println("판매된 상품이 없습니다.");
			}
			Common.data2 = "";
		}
		Common.reader1.close();
		Common.fis.close();
	}

}
