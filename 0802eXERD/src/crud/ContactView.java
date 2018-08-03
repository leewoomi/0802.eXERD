package crud;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ContactView extends JFrame {

	// 필요한 UI 변수 선언
	// 레이블 선언
	JLabel lblName, lblPhone, lblEmail, lblBirthday;
	// 입력받을 텍스트 필드 선언
	JTextField tfName, tfPhone, tfEmail, tfBirthday;
	// 버튼 변수
	JButton btnFirst, btnLast, btnPrev, btnNext;
	JButton btnInsert, btnUpdate, btnDelete, btnSelect, btnClear;

	// 현재 출력 중인 데이터의 인덱스를 표시할 레이블
	JLabel lblIndex;

	// 데이터베이스 작업을 위한 Dao 변수
	ContactDao dao = new ContactDaoImpl();

	// 데이터베이스에서 가져온 결과를 저장하기 위한 list
	List<Contact> list;
	// 현재 출력 중인 데이터의 인덱스를 저장할 변수
	int idx;

	// idx번째 데이터를 화면에 출력해주는 메소드
	// 맨 처음 한번 호출하고 데이터에 작업이 발생하면 호출하는 메소드
	private void printData() {
		// 읽은 데이터가 없다면 메시지 박스를 출력하고 return
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(null, "읽을 데이터가 없습니다.", "데이터 없음", JOptionPane.ERROR_MESSAGE);

			return;
		}
		// List에서 idx 번째 데이터를 가져옴
		Contact contact = list.get(idx);
		tfName.setText(contact.getName());
		tfPhone.setText(contact.getPhone());
		tfEmail.setText(contact.getEmail());
		// 생일은 Date타입이므로 toString을 호출해서 String으로 변환해서 출력함.
		tfBirthday.setText(contact.getBirthday().toString());
		// 레이블에 현재 인덱스를 출력
		// lblIndex.setText(String.format("%d", idx+1));
		lblIndex.setText((idx + 1) + "");
	}

	// 생성자
	// 객체를 만들 때 초기화를 위해서 생성
	public ContactView() {
		// 중앙에 배치할 패널 생성
		JPanel centerPanel = new JPanel();

		// 레이블을 생성
		lblName = new JLabel("이름", JLabel.RIGHT);
		lblPhone = new JLabel("전화번호", JLabel.RIGHT);
		lblEmail = new JLabel("이메일", JLabel.RIGHT);
		lblBirthday = new JLabel("생년월일", JLabel.RIGHT);

		// 텍스트 필드 생성
		tfName = new JTextField();
		tfPhone = new JTextField();
		tfEmail = new JTextField();
		tfBirthday = new JTextField();

		// 4행 2열 배치될 수 있는 레이아웃으로 변경
		centerPanel.setLayout(new GridLayout(4, 2, 3, 3));
		centerPanel.add(lblName);
		centerPanel.add(tfName);

		centerPanel.add(lblPhone);
		centerPanel.add(tfPhone);

		centerPanel.add(lblEmail);
		centerPanel.add(tfEmail);

		centerPanel.add(lblBirthday);
		centerPanel.add(tfBirthday);

		add("Center", centerPanel);

		display();

		// 데이터베이스에서 데이터를 가져옴
		list = dao.allConact();
		printData();

		setBounds(500, 300, 800, 500);
		setTitle("연락처");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		setVisible(true);

	}

	private void display() {
		// 버튼 생성
		btnFirst = new JButton("<<");
		btnFirst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idx == 0) {
					JOptionPane.showMessageDialog(null, "첫 번째 데이터입니다.", "조회에러", JOptionPane.INFORMATION_MESSAGE);
				}
				idx = 0;
				printData();

			}
		});
		btnPrev = new JButton("<");
		btnPrev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idx == 0) {
					idx = list.size();
				}
				idx = idx - 1;
				printData();

			}

		});
		btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// if(idx >= list.size() -1) {
				// JOptionPane.showMessageDialog(null, "마지막 데이터입니다.",
				// "조회에러",JOptionPane.ERROR_MESSAGE);
				// }
				idx = idx + 1;
				if (idx == list.size()) {
					idx = 0;
				}
				printData();
			}
		});
		btnLast = new JButton(">>");
		btnLast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idx >= list.size() - 1) {
					JOptionPane.showMessageDialog(null, "마지막 데이터입니다.", "조회에러", JOptionPane.INFORMATION_MESSAGE);
				}

				idx = list.size() - 1;
				printData();

			}
		});

		btnInsert = new JButton("삽입");
		btnInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// lblIndex의 텍스가 삽입이 아니면 메시지 박스를 출력하고 함수의 수행을 종료
				// 문자열의 동일성 여부는 ==가 아니고 equals로 해야 함.
				if (lblIndex.getText().equals("삽입") == false) {
					JOptionPane.showMessageDialog(null, "지우고 쓰세요", "삽입에러", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 입력한 데이터를 가져오기
				String name = tfName.getText();
				String phone = tfPhone.getText();
				String email = tfEmail.getText();
				String birthday = tfBirthday.getText();

				// System.out.println("name : "+ tfName.getText());

				// name은 필수 입력이고 3자 이상 입력했는지 검사
				if (name.trim().length() < 3) {
					JOptionPane.showMessageDialog(null, "이름은 세글자 이상 입력 필수입니다.", "이름 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// phone은 전부 숫자이고 11자리인지 검사
				if (phone.trim().length() != 11) {
					JOptionPane.showMessageDialog(null, "전화번호는 11자리 숫자만 입력해주세요.", "전화번호 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int i = 0;
				while (i < phone.trim().length()) {
					char ch = phone.trim().charAt(i);
					if (ch < '0' || ch > '9') {
						JOptionPane.showMessageDialog(null, "전화번호는 11자리 숫자만 입력해주세요.", "전화번호 오류",
								JOptionPane.ERROR_MESSAGE);
						return;

					}
					i = i + 1;
				}
				
				

				// Dao의 메소드에 맞게 데이터를 생성

				Contact contact = new Contact();
				contact.setName(name);
				contact.setPhone(phone);
				contact.setEmail(email);
				// birthday가 yyyymmdd 형식으로 입력되었다고 가정하고
				// Date 타입으로 변경하기
				int year = Integer.parseInt(birthday.substring(0, 4));
				int month = Integer.parseInt(birthday.substring(4, 6));
				int day = Integer.parseInt(birthday.substring(6));
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month - 1);
				cal.set(Calendar.DAY_OF_MONTH, day);
				Date date = new Date(cal.getTimeInMillis());
				contact.setBirthday(date);
				//데이터 베이스 작업 수행
				dao.insertContact(contact);
				//작업 수행 후 재출력을 위해서 데이터를 다시 가져오고 인덱스 조정
				list = dao.allConact();
				idx = list.size() -1;
				printData();
				//대화상자 출력하기
				JOptionPane.showMessageDialog(null, "삽입 성공" , "삽입",JOptionPane.INFORMATION_MESSAGE);
			}

		});

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 입력한 데이터 전부 가져오기
				String name = tfName.getText();
				String phone = tfPhone.getText();
				String email = tfEmail.getText();
				String birthday = tfBirthday.getText();

				String message = "";
				// name의 데이터가 비어있으면 메시지 박스를 출력하고 리턴하기
				if (name.trim().length() < 1) {
					message = "이름은 필수 입력입니다.\n이름을 입력하십시오.";

				}

				// phone의 데이터가 비어있으면 메시지 박스를 출력하고 리턴하기
				else if (phone.trim().length() < 11) {
					message = "전화번호는 필수 입력입니다.\n전화번호를 입력하십시오.";

				}
				if (message.length() >= 1) {
					JOptionPane.showMessageDialog(null, message, "입력 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 날짜 데이터가 년도 4자리 - 월2자리-일2자리 형식인지 조사
				int i = 0;
				if (birthday.length() != 10) {
					JOptionPane.showMessageDialog(null, "생일은 10자리로 입력해주세요. \n예)1980-01-01", "입력 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				while (i < birthday.length()) {
					char ch = birthday.charAt(i);
					// 첫번째부터 네번째까지 숫자가 아니면 반복문 종료
					if (i >= 0 && i <= 3) {
						if (ch < '0' || ch > '9') {
							break;
						}
					}

					if(i==4 ||i==7) {
						if(ch != '-') {
							break;
						}
					}
					if(i==5) {
						if (ch < '0' || ch > '9') {
							break;
						}
					}
					if(i==6) {
						if (ch < '0' || ch > '9') {
							break;
						}
					}
					if(i==8) {
						if (ch < '0' || ch > '9') {
							break;
						}
					}
					if(i==9) {
						if (ch < '0' || ch > '9') {
							break;
						}
					}
					
					i += 1;
				}
				// 유효성 검사에 실패하면 메시지 박스를 출력하고 리턴
				if (i != birthday.length()) {
					JOptionPane.showMessageDialog(null, "생일은 YYYY-MM-DD 형식", "입력 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Contact contact = new Contact();
				contact.setNum(list.get(idx).getNum());
				contact.setName(name);
				contact.setEmail(email);
				contact.setPhone(phone);
				// yyyy-mm-dd 형식의 문자열을 가지고 Date 만들기
				// 0번째 부터 4번째 앞까지
				int year = Integer.parseInt(birthday.substring(0, 4));
				int month = Integer.parseInt(birthday.substring(5, 7));
				int day = Integer.parseInt(birthday.substring(8));
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month - 1);
				cal.set(Calendar.DAY_OF_MONTH, day);
				// Calendar 객체를 이용해서 Date 객체 만들기
				Date date = new Date(cal.getTimeInMillis());
				contact.setBirthday(date);
				dao.updateContact(contact);
				list = dao.allConact();
				printData();
				JOptionPane.showMessageDialog(null, "수정 성공", "데이터 수정", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				System.out.println(r);
				if (r == 0) {
					JOptionPane.showMessageDialog(null, "삭제 성공", "삭제", JOptionPane.INFORMATION_MESSAGE);
					dao.deleteContact(list.get(idx).getNum());
					idx = 0;
					list = dao.allConact();
					printData();
				}
			}
		});

		btnSelect = new JButton("조회");
		btnSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "조회할 이름의 일부분을 입력하세요", "이름으로 조회",
						JOptionPane.QUESTION_MESSAGE);

				tfName.setText("");
				tfPhone.setText("");
				tfEmail.setText("");
				tfBirthday.setText("");
				if (name != null) {
					list = dao.nameConact(name.trim().toLowerCase());
					idx = 0;
					printData();
				}
			}
		});

		btnClear = new JButton("지움");
		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 텍스트 필드들의 텍스트를 전부 삭제
				tfName.setText("");
				tfPhone.setText("");
				tfEmail.setText("");
				tfBirthday.setText("");

				// lblIndex의 타이틀을 삽입으로 변경
				lblIndex.setText("삽입");

			}

		});

		lblIndex = new JLabel("", JLabel.CENTER);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 5, 7, 7));
		panel1.setBorder(new TitledBorder("이동"));
		panel1.add(btnFirst);
		panel1.add(btnPrev);
		panel1.add(lblIndex);
		panel1.add(btnNext);
		panel1.add(btnLast);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 5, 7, 7));
		panel2.setBorder(new TitledBorder("작업"));
		panel2.add(btnInsert);
		panel2.add(btnUpdate);
		panel2.add(btnDelete);
		panel2.add(btnSelect);
		panel2.add(btnClear);

		JPanel p = new JPanel(new BorderLayout());

		p.add("Center", panel1);
		p.add("South", panel2);

		add("South", p);

	}
}
