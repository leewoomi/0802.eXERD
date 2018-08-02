package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

public class ContactDaoImpl implements ContactDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// �����ͺ��̽� ������ �����ϴ� �޼ҵ�
	private void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ���� ū num�� ã�ƿ��� �޼ҵ�
	// sql : select max(num) from contact;
	private int getMaxNum() {
		int result = 0;
		connect();
		try {

			// ���� ū �۹�ȣ�� ã�ƿ��� sql�� �����ϴ� ��ü ����
			pstmt = conn.prepareStatement("select max(num) from contact");

			// sql ����
			rs = pstmt.executeQuery();

			// ��� �б�
			while (rs.next()) {
				// select ���� ù��° �÷��� ���� �о result�� ����
				result = rs.getInt(1);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	@Override
	// �����͸� �����ϴ� �޼ҵ�
	public boolean insertContact(Contact contact) {
		boolean result = false;

		try {

			// ���� ū ��ȣ ã�ƿ��� SQL�� ����
			int maxNum = getMaxNum();

			connect();

			// SQL ���� ��ü �����
			pstmt = conn
					.prepareStatement("insert into contact(num, name, phone, email,birthday) " + "values(?,?,?,?,?)");

			// ����ǥ�� ���� ���ε�
			// ���� ū ��ȣ +1�� ����
			pstmt.setInt(1, maxNum + 1);
			pstmt.setString(2, contact.getName());
			pstmt.setString(3, contact.getPhone());
			pstmt.setString(4, contact.getEmail());
			pstmt.setDate(5, contact.getBirthday());

			// select�� ������ ������ executeUpdate�� ����
			// ���� ����� ������� ���� ������ ������ ����
			int r = pstmt.executeUpdate();

			if (r == 1) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	@Override
	public boolean updateContact(Contact contact) {
		boolean result = false;
		connect();
		try {

			// SQL ����
			pstmt = conn.prepareStatement(
					"update contact " + "set  name =?, phone = ?, email = ? ,birthday=? " + "where num =?");

			// ����ǥ�� ���� ���ε�
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getEmail());
			pstmt.setDate(4, contact.getBirthday());
			pstmt.setInt(5, contact.getNum());

			// SQL ����
			int r = pstmt.executeUpdate();
			// ��� ���
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	@Override
	public boolean deleteContact(int num) {
		boolean result = false;
		connect();
		try {

			// SQL ����
			pstmt = conn.prepareStatement("delete from contact where num=?");

			// ����ǥ�� ���� ���ε�
		
			pstmt.setInt(1, num);

			// SQL ����
			int r = pstmt.executeUpdate();
			// ��� ���
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	@Override
	public List<Contact> allConact() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> nameConact(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}