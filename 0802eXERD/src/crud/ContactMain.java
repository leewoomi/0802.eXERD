package crud;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class ContactMain {

	public static void main(String[] args) {
		//�������̽��� Ŭ������ ����� ��쿡��
		//���� �������̽��� Ŭ���� �̸����� ������ �����
		//���� Ŭ������ �ν��Ͻ��� �����ؼ� �����մϴ�.
		ContactDao dao = new ContactDaoImpl();
		//Contact contact = new Contact();
//		contact.setNum(1);
//		contact.setName("�ں���");
//		contact.setPhone("01027839942");
//		contact.setEmail("packbo@naver.com");
//		//���� �ð��� ������ Ķ���� ����
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 1992);
//		cal.set(Calendar.MONTH, 3);
//		cal.set(Calendar.DAY_OF_MONTH,23);
//		Date birthDay = new Date(cal.getTimeInMillis());
//		contact.setBirthday(birthDay);
		
		//��� �����͸� ��ȸ�ϴ� �޼ҵ� ȣ��
//		List<Contact> list =dao.allConact();
//		//System.out.println(list);
//		for(Contact contact : list) {
//			System.out.println(contact);
//		}
		
		//�̸��� ������ ��ȸ�ϴ� �޼ҵ� ȣ��
		List<Contact> list =dao.nameConact("��");
		//System.out.println(list);
		for(Contact contact : list) {
			System.out.println(contact);
		}
		//�����͸� �����ϴ� �޼ҵ� ȣ��
//		boolean result = dao.insertContact(contact);
//		if(result ==true) {
//			System.out.println("���� ����");
//		}else {
//			System.out.println("���� ����");
//		}
		
		//�����͸� �����ϴ� �޼ҵ� ȣ��
//		boolean r = dao.updateContact(contact);
//		if(r ==true) {
//			System.out.println("���� ����");
//		}else {
//			System.out.println("���� ����");
//		}

		
		
		//�����͸� �����ϴ� �޼ҵ� ȣ��
//		boolean r = dao.deleteContact(2);
//		if(r ==true) {
//			System.out.println("���� ����");
//		}else {
//			System.out.println("���� ����");
//		}
	}

}
