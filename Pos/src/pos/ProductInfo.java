package pos;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 직렬화를 위해서는 Serializable 인터페이스를 구현하라.
	// 직렬화 : 객체를 바이너리 정보로 전환하여 원격지로 보내는 기술.

	private String productCode; // 상품코드
	private String productName; // 상품명
	private int productPrice; // 상품가격
	private int stock; // 재고량

	// 기본 생성자
	public ProductInfo() {

	}

	// 변수를 담은 생성자
	public ProductInfo(String productCode, String productName, int productPrice, int stock) {
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = productPrice;
		this.stock = stock;
	}
	
	
	
	// 상품 출력
	void showProductInfo() {
		System.out.println("상품코드: " + productCode);
		System.out.println("상품명: " + productName);
		System.out.println("상품가격: " + productPrice);
		System.out.println("재고량: " + stock);
		System.out.println("");
	}

	// 재고량 출력
	public void showProductQuantity() {
		System.out.print("상품명: " + productName + " \t");
		System.out.println("재고량: " + stock);
	}

	// 장바구니 출력
	public void showCart(int amount) {
		System.out.print(amount + ". ");
		System.out.print("상품명: " + productName +"\t");
		System.out.println("재고량: " + stock);
	}
	
	// 수정을 위한 
	public ProductInfo(int productPrice, int stock) {
		this.productPrice = productPrice;
		this.stock = stock;
	}

	// 상품코드와 상품명만 중복 안되게 설정
	@Override
	public int hashCode() {
		return (productCode + productName).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProductInfo) {
			ProductInfo temp = (ProductInfo) obj;
			return productCode.equals(temp.productCode) && productName.equals(temp.productName);
		} else {
			return false;
		}
	}
	
	
	//getter, setter
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
