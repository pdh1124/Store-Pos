package pos;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
	static Scanner sca = new Scanner(System.in);

	public static void main(String[] args) {
		ProductManager manager = ProductManager.creatManagerInst();
		manager.readFromFile();

		while (true) {
			Menu.showProduct();
			System.out.print("선택 : ");
			int cho_2 = sca.nextInt();
			
			if (cho_2 == 1) {
				manager.ProductManagement();
			}
			else if (cho_2 == 2) {
				manager.ProductBuying();
			}
			else if (cho_2 == 3) {

			}
			else if (cho_2 == 4) {

			}
			else if (cho_2 == 5) { //프로그램 종료
				sca.close();
				manager.storeToFile();
				System.exit(0);
			} else { //다른 번호는 없다는 문구
				try {
					throw new Excep(cho_2);
				} catch (Excep e) {
					System.out.println(e.getMessage());
				}
			}

		}
	}

}
