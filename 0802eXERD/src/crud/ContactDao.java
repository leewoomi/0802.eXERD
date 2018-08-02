package crud;

import java.util.List;

public interface ContactDao {

	// 데이터를 삽입하는 메소드

	public boolean insertContact(Contact contact);

	// 데이터를 수정하는 메소드
	public boolean updateContact(Contact contact);

	// 데이터를 삭제하는 메소드
	public boolean deleteContact(int num);

	// 데이터 전체를 읽어오는 메소드
	//데이터가 0개 이상이므로 List로 리턴하고 읽어올 컬럼들을 저장할 
	//DTO 클래스나 Map으로 제너릭을 적용하면 됨.
	public List<Contact> allConact();
	
	//이름을 가지고 조회하는 메소드
	//리스트가 있으면 여러 개
	public List<Contact> nameConact(String name);
	

}
