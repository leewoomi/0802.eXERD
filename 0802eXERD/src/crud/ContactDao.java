package crud;

import java.util.List;

public interface ContactDao {

	// �����͸� �����ϴ� �޼ҵ�

	public boolean insertContact(Contact contact);

	// �����͸� �����ϴ� �޼ҵ�
	public boolean updateContact(Contact contact);

	// �����͸� �����ϴ� �޼ҵ�
	public boolean deleteContact(int num);

	// ������ ��ü�� �о���� �޼ҵ�
	//�����Ͱ� 0�� �̻��̹Ƿ� List�� �����ϰ� �о�� �÷����� ������ 
	//DTO Ŭ������ Map���� ���ʸ��� �����ϸ� ��.
	public List<Contact> allConact();
	
	//�̸��� ������ ��ȸ�ϴ� �޼ҵ�
	//����Ʈ�� ������ ���� ��
	public List<Contact> nameConact(String name);
	

}
