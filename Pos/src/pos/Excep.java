package pos;

public class Excep extends Exception {
	private static final long serialVersionUID = 1L;
	
	int i;
	
	public Excep(int i) {
		this.i = i;
	}
	
	@Override
	public String getMessage() {
		return i + "번의 선택사항은 존재하지 않습니다.";
	}
	

}
