package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 데이터베이스 연결을 수행하는 메소드
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

	// 가장 큰 num을 찾아오는 메소드
	// sql : select max(num) from contact;
	private int getMaxNum() {
		int result = 0;
		connect();
		try {

			// 가장 큰 글번호를 찾아오는 sql을 실행하는 객체 생성
			pstmt = conn.prepareStatement("select max(num) from contact");

			// sql 실행
			rs = pstmt.executeQuery();

			// 결과 읽기
			while (rs.next()) {
				// select 절의 첫번째 컬럼의 값을 읽어서 result에 저장
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
	// 데이터를 삽입하는 메소드
	public boolean insertContact(Contact contact) {
		boolean result = false;

		try {

			// 가장 큰 번호 찾아오는 SQL을 실행
			int maxNum = getMaxNum();

			connect();

			// SQL 실행 객체 만들기
			pstmt = conn
					.prepareStatement("insert into contact(num, name, phone, email,birthday) " + "values(?,?,?,?,?)");

			// 물음표에 값을 바인딩
			// 가장 큰 번호 +1로 설정
			pstmt.setInt(1, maxNum + 1);
			pstmt.setString(2, contact.getName());
			pstmt.setString(3, contact.getPhone());
			pstmt.setString(4, contact.getEmail());
			pstmt.setDate(5, contact.getBirthday());

			// select를 제외한 구문은 executeUpdate로 실행
			// 실행 결과는 영향받은 행의 개수를 정수로 리턴
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

			// SQL 생성
			pstmt = conn.prepareStatement(
					"update contact " + "set  name =?, phone = ?, email = ? ,birthday=? " + "where num =?");

			// 물음표의 값을 바인딩
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getEmail());
			pstmt.setDate(4, contact.getBirthday());
			pstmt.setInt(5, contact.getNum());

			// SQL 실행
			int r = pstmt.executeUpdate();
			// 결과 사용
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

			// SQL 생성
			pstmt = conn.prepareStatement("delete from contact where num=?");

			// 물음표의 값을 바인딩

			pstmt.setInt(1, num);

			// SQL 실행
			int r = pstmt.executeUpdate();
			// 결과 사용
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
		// 읽어온 데이터를 저장하기 위한 리스트 생성
		List<Contact> list = new ArrayList<>();
		// 데이터베이스 연결을 수행하는 메소드 호출
		connect();

		try {
			// contact 테이블에 있는 전체 데이터를 가져오는 SQL 실행 객체를 생성합니다.
			// select * from contact
			// select num, name, phone, email, birthday from contact
			// 둘 다 가능함.
			pstmt = conn.prepareStatement("select num, name, phone, email, birthday from contact");
			// select 구문 실행
			rs = pstmt.executeQuery();

			// 반복문을 이용해서 데이터를 읽어서 리스트에 저장
			// 저장은 반복문 안에
			while (rs.next()) {
				// 하나의 행을 읽어서 DTO에 저장
				// DTO dto = new DTO();
				// dto.set컬럼명(rs.get변수타입("컬럼명"));
				Contact contact = new Contact();
				contact.setNum(rs.getInt("num"));
				contact.setName(rs.getString("name"));
				contact.setPhone(rs.getString("phone"));
				contact.setEmail(rs.getString("email"));
				contact.setBirthday(rs.getDate("birthday"));

				// 읽은 데이터를 리스트에 저장
				list.add(contact);
			}
		} catch (Exception e) {
			// 예외 처리 문장
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// 연결 끊기
			close();
		}
		return list;
	}

	@Override
	public List<Contact> nameConact(String name) {
		List<Contact> list = new ArrayList<>();
		connect();
		try {
			// contact 테이블의 name 컬럼에 name의 값이 포함된
			// 데이터를 조회하는 SQL 만들기
			pstmt = conn.prepareStatement("select * from contact where lower(name) like ?");
			// 물음표에 데이터 바인딩
			pstmt.setString(1, "%" + name + "%");
			//SQL 실행
			rs = pstmt.executeQuery();
			//데이터를 읽어서 list에 저장하기
			while (rs.next()) {
				// 하나의 행을 읽어서 DTO에 저장
				// DTO dto = new DTO();
				// dto.set컬럼명(rs.get변수타입("컬럼명"));
				Contact contact = new Contact();
				contact.setNum(rs.getInt("num"));
				contact.setName(rs.getString("name"));
				contact.setPhone(rs.getString("phone"));
				contact.setEmail(rs.getString("email"));
				contact.setBirthday(rs.getDate("birthday"));

				// 읽은 데이터를 리스트에 저장
				list.add(contact);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return list;
	}

}
