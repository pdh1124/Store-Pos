package pos;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 직렬화를 위해서는 Serializable 인터페이스를 구현하라.
	// 직렬화 : 객체를 바이너리 정보로 전환하여 원격지로 보내는 기술.
	
	
	private int productCode; //상품코드
	private String productName; //상품명
	private int productPrice; //상품가격
	private int stock; //재고량
	
	//기본 생성자
	public ProductInfo() {
		
	}
	
	//변수를 담은 생성자
	public ProductInfo(int productCode ,String productName, int productPrice, int stock) {
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = productPrice;
		this.stock = stock;
	}
	
	//상품 출력
	void showProductInfo() {
		System.out.println("상품코드 :" + productCode);
		System.out.println("상품명 :" + productName);
		System.out.println("상품가격 :" + productPrice);
		System.out.println("재고량 :" + stock);
	}
	
	
	
	
}
