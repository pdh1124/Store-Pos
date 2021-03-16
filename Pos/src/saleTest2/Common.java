package saleTest2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class Common {
	// 상품정보는 1건당 최대 1000byte 씩 저장 가능.
	// 상품정보는 최대 100건 저장 가능.
	static Scanner input = new Scanner(System.in); // 콘솔 입력 준비
	static File file1, file2; // file1(상품목록), file2(판매내역)
	static FileInputStream fis;
	static Reader reader1;
	static InputStreamReader is;
	static FileOutputStream fos;
	static Writer toDo;
	static OutputStreamWriter osw;
	static String data = "", data2 = ""; // data(상품목록 임시 저장), data2(판매내역 임시 저장)
	static GoodsMethod[] ptArr = new GoodsMethod[100]; // 상품 정보 비교를 위한 객체 배열 공간.
	static int gCnt = 0; // 객체 배열의 갯수.
	static String dir_base = "c:/temp2/";
	static String dir_regist = dir_base + "temp.txt";
	static String dir_sale = dir_base + "temp_sale.txt";

	public static void main(String[] args) throws IOException {
		// 목적 폴더가 없다면 생성.
		File dir = new File(dir_base);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		Common c = new Common();
		c.menu(); // 메인 메뉴 시작.
	}

	public void menu() throws IOException {
		boolean run = true;

		while (run) {
			System.out.println("▒▒▒▒▒ 메인 메뉴 ▒▒▒▒▒");
			System.out.println("1. 상품 등록 하위 메뉴");
			System.out.println("2. 판매 등록 하위 메뉴");
			System.out.println("3. 종료");

			System.out.print("메뉴 번호를 선택 하세요 : ");
			int sNum = input.nextInt();

			switch (sNum) {
			case 1:
				GoodsController gc = new GoodsController();
				gc.menu(); // 상품 등록 메뉴
				break;
			case 2:
				SalesController sc = new SalesController();
				sc.menu(); // 판매 내역 메뉴
				break;
			case 3:
				System.out.println("프로그램을 종료 합니다.");
				run = false;
			default:
				System.out.println();
			}
		}

		input.close(); // 스캐너 사용이 완료되면 운영체제로 자원 반납.
	}
}
