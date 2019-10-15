package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DesignerVO;

public class DesignerController implements Initializable {
	@FXML
	private TextField txtDName;// 디자이너 이름 텍스트 필드
	@FXML
	private ToggleGroup designergenderGroup;// 디자이너 성별 라디오버튼 그룹
	@FXML
	private RadioButton rbDMale;// 남성 라디오 버튼
	@FXML
	private RadioButton rbDFemale;// 여성 라디오 버튼
	@FXML
	private TextField txtDPhone;// 디자이너 전화번호 텍스트필드
	@FXML
	private TextField txtDAge;// 디자이너 생년월일 텍스트 필드
	@FXML
	private TextField txtDAddress;// 디자이너 주소 텍스트 필드
	@FXML
	private TextField txtDId;// 디자이너 아이디 텍스트 필드
	@FXML
	private Button btnDIdOverlap;// 디자이너 아이디 중복체크 버튼
	@FXML
	private TextField txtDPw;// 디자이너 비밀번호 텍스트 필드
	@FXML
	private Button btnDesignerJoin;// 디자이너 등록 버튼
	@FXML
	private Button btnDesignerInit;// 디자이너 초기화 버튼
	@FXML
	private Button btnDesignerUpdate;// 디자이너 수정 버튼
	@FXML
	private Button btnDesignerDelete;// 디자이너 삭제 버튼
	@FXML
	private ComboBox<String> cbx_searchList; // 검색 분류 콤보박스
	@FXML
	private TextField txtDSearch;// 라디오 버튼 형식에맞는 검색 필드창
	@FXML
	private Button btnDSearch;// 검색 버튼
	@FXML
	private Button btnDALL;// 검색후 전체뷰를 보기위한 전체 버튼
	@FXML
	private TableView<DesignerVO> designerTotalView = new TableView<>();// 저장되어있는 디자이너목록을 보여주는 테이블뷰

	DesignerVO designer = new DesignerVO();// model.designer 인스턴스 화 --> VO
	ObservableList<DesignerVO> designerDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스 생성
	ObservableList<DesignerVO> selectdesigner;// 테이블에 선택한 정보 저장
	boolean editDelete = false;// 수정할 때 확인 버튼 상태 설정
	int selectedIndex;// 테이블에서 선택한 회원 정보 인덱스 저장.

	int d_code;// 삭제시 테이블에서 선택한 학생의 이름 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 텍스트 필드 초기화
		txtDPw.setEditable(false);// 비밀번호 입력 텍스트필드 수정불가
		// 버튼 초기화
		btnDesignerJoin.setDisable(true);// 등록 버튼 비활성화
		btnDesignerUpdate.setDisable(true);// 수정 버튼 비활성화
		btnDesignerDelete.setDisable(true);// 삭제 버튼 비활성화
		btnDALL.setDisable(true);//전체 버튼 비활성화

		// 콤보박스 내용 설정
		cbx_searchList.setItems(FXCollections.observableArrayList("이름", "전화번호"));

		// 전화번호 필드에 숫자만 입력할 수 있도록 설정.
		DecimalFormat phoneformat = new DecimalFormat("###########");// 번호를 11자리까지만 입력할 수 있게 설정
		txtDPhone.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) { // 이벤트 발생시 값이 비어있으면 이벤트로 반환
				return event;
			}

			ParsePosition parsePosition = new ParsePosition(0); // 0으로 초기화
			// 입력한 값을 11자리로 설정해 객체로 설정
			Object object = phoneformat.parse(event.getControlNewText(), parsePosition);

			// 값이 없거나 자릿수가 인덱스의 값이 입력한 값의 길이보다 작거나 입력값이 12이면 null
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 12) {
				return null;
			} else { // 그렇지 않으면 이벤트로 반환
				return event;
			}
		}));

		// 생년월일 필드에 숫자만 입력할 수 있도록 설정
		DecimalFormat ageformat = new DecimalFormat("######");// 생년월일을 6자리까지만 입력할 수 있게 설정
		txtDAge.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) { // 이벤트 발생시 값이 비어있으면 이벤트로 반환
				return event;
			}

			ParsePosition parsePosition = new ParsePosition(0); // 0으로 초기화
			// 입력한 값을 11자리로 설정해 객체로 설정
			Object object = ageformat.parse(event.getControlNewText(), parsePosition);

			// 값이 없거나 자릿수가 인덱스의 값이 입력한 값의 길이보다 작거나 입력값이 7이면 null
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else { // 그렇지 않으면 이벤트로 반환
				return event;
			}
		}));

		// 테이블 뷰 컬럼 이름 설정
		TableColumn colCode = new TableColumn("NO");
		colCode.setPrefWidth(100);// 넓이 설정
		colCode.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colCode.setCellValueFactory(new PropertyValueFactory<>("d_code"));

		TableColumn colName = new TableColumn("성 함");
		colName.setPrefWidth(142);// 넓이 설정
		colName.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colName.setCellValueFactory(new PropertyValueFactory<>("d_name"));

		TableColumn colGender = new TableColumn("성 별");
		colGender.setPrefWidth(144);// 넓이 설정
		colGender.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colGender.setCellValueFactory(new PropertyValueFactory<>("d_gender"));

		TableColumn colPhone = new TableColumn("전화 번호");
		colPhone.setPrefWidth(152);// 넓이 설정
		colPhone.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colPhone.setCellValueFactory(new PropertyValueFactory<>("d_phone"));

		TableColumn colAge = new TableColumn("생년 월일");
		colAge.setPrefWidth(132);// 넓이 설정
		colAge.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colAge.setCellValueFactory(new PropertyValueFactory<>("d_age"));

		TableColumn colAddress = new TableColumn("주 소");
		colAddress.setPrefWidth(140);// 넓이 설정
		colAddress.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colAddress.setCellValueFactory(new PropertyValueFactory<>("d_address"));

		TableColumn colId = new TableColumn("ID");
		colId.setPrefWidth(130);// 넓이 설정
		colId.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colId.setCellValueFactory(new PropertyValueFactory<>("d_id"));

		TableColumn colPw = new TableColumn("PW");
		colPw.setPrefWidth(130);// 넓이 설정
		colPw.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colPw.setCellValueFactory(new PropertyValueFactory<>("d_pw"));

		// 테이블에 데이터 추가
		designerTotalView.setItems(designerDataList);
		designerTotalView.getColumns().addAll(colCode, colName, colGender, colPhone, colAge, colAddress, colId, colPw);

		// 디자이너 등록 버튼 이벤트 핸들러
		btnDesignerJoin.setOnAction(event -> handlerBtnDesignerJoinAction(event));
		// 디자이너 초기화 버튼 이벤트 핸들러
		btnDesignerInit.setOnAction(event -> handlerBtnDesignerInitAction(event));
		// 디자이너 수정 버튼 이벤트 핸들러
		btnDesignerUpdate.setOnAction(event -> handlerBtnDesignerUpdateAction(event));
		// 디자이너 삭제 버튼 이벤트 핸들러
		btnDesignerDelete.setOnAction(event -> handlerBtnDesignerDeleteAction(event));

		// 이름,전화번호를 이용한 검색버튼 이벤트 핸들러
		btnDSearch.setOnAction(event -> handlerBtnDSearchAction(event));
		//검색후 전체 버튼
		btnDALL.setOnAction(event -> handlerBtnDALLAction(event));

		// 아이디 중복 버튼 이벤트 핸들러
		btnDIdOverlap.setOnAction(event -> handlerBtnDIdOverlapAction(event));

		// 디자이너 정보 테이블뷰 마우스 클릭 이벤트 핸들러
		designerTotalView.setOnMouseClicked(event -> handlerDesignerTotalViewClickAction(event));

		// 디자이너 전체 정보
		designerTotalList();
	}

	
	//검색 후 전체 버튼
	public void handlerBtnDALLAction(ActionEvent event) {
		//전체버튼 비활성화
		btnDALL.setDisable(true);
		txtDSearch.clear();
		cbx_searchList.getSelectionModel().clearSelection();
		//초기화 이벤트 호출
		handlerBtnDesignerInitAction(event);
	}

	// 이름,전화번호를 이용한 검색버튼 이벤트
	public void handlerBtnDSearchAction(ActionEvent event) {
		String search = "";
		search = cbx_searchList.getSelectionModel().getSelectedItem();
		System.out.println(search);

		DesignerVO dVo = new DesignerVO();
		DesignerDAO dDao = new DesignerDAO();

		String searchName = "";
		boolean searchResult = false;

		searchName = txtDSearch.getText().trim();
		System.out.println(searchName);

		try {
			if (searchName.equals("") || search.equals("")) {
				try {
					searchResult = true;

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("디자이너 정보 검색");
					alert.setHeaderText("디자이너 검색 정보를 입력하세요");
					alert.setContentText("다음에는 주의하세요");
					alert.showAndWait();
					designerDataList.removeAll(designerDataList);
					btnDALL.setDisable(true);

					designerTotalList();// 디자이너 전체 목록 메소드 호출
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ArrayList<String> title;
				ArrayList<DesignerVO> list = null;

				title = dDao.getDColumnName();

				int columnCount = title.size();

				if (search.equals("이름")) {// 라디오 버튼에서 이름 선택시
					list = dDao.getDesignerDNameSearchList(searchName);
					if (list.size() == 0) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("디자이너 이름 정보 검색");
						alert.setHeaderText(searchName + " 디자이너 의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = dDao.getDesignerTotal();
					}
				}
				if (search.equals("전화번호")) {// 콤보박스에서 전화번호 선택시
					list = dDao.getDesignerDPhoneSearchList(searchName);
					if (list.size() == 0) {// 값이 없을때

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("디자이너 전화번호 정보 검색");
						alert.setHeaderText("전화번호가 "+searchName + " 인 디자이너의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = dDao.getDesignerTotal();
					}
				}
				designerDataList.removeAll(designerDataList);

				int rowCount = list.size();
				for (int index = 0; index < rowCount; index++) {
					dVo = list.get(index);
					designerDataList.add(dVo);
				}
				searchResult = true;
			}
			if (!searchResult) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("디자이너 정보 검색");
				alert.setHeaderText(searchName + " 디자이너가 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
				
			}
			//전체 버튼 활성화
			btnDALL.setDisable(false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 디자이너 삭제 이벤트
	public void handlerBtnDesignerDeleteAction(ActionEvent event) {

		DesignerDAO dDao = null;
		dDao = new DesignerDAO();

		try {
			// 테이블에서 선택한 디자이너의 디자이너코드를 DesignerDAO에서 불러온다
			dDao.getDesignerDelete(selectedIndex);
			designerDataList.removeAll(designerDataList);// 데이터를 지운다.
			designerTotalList();
			handlerBtnDesignerInitAction(event);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 디자이너 수정 이벤트
	public void handlerBtnDesignerUpdateAction(ActionEvent event) {
		try {

			boolean sucess;
			DesignerDAO ddao = new DesignerDAO();
			sucess = ddao.getDesignerUpdate(txtDName.getText().trim(), txtDPhone.getText().trim(),
					txtDAddress.getText().trim(), txtDId.getText().trim(), txtDPw.getText().trim());

			if (sucess) {
				designerDataList.removeAll(designerDataList);
				designerTotalList();
				handlerBtnDesignerInitAction(event);
			}else {
				designerDataList.removeAll(designerDataList);
				handlerBtnDesignerInitAction(event);
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("디자이너 정보 수정");
				alert.setHeaderText("디자이너 정보 수정 실패");
				alert.setContentText("디자이너 정보 수정 실패.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 디자이너 정보 테이블 뷰 클릭 이벤트
	public void handlerDesignerTotalViewClickAction(MouseEvent event) {

		if (event.getClickCount() == 2) {// 테이블 뷰 디자이너 정보 더블클릭시
			try {
				btnDesignerJoin.setDisable(true);
				// 선택한 행의 디자이너 정보를 새로운 객체에 저장.
				selectdesigner = designerTotalView.getSelectionModel().getSelectedItems();
				selectedIndex = selectdesigner.get(0).getD_code();
				System.out.println(selectedIndex);
				String selectedD_name = selectdesigner.get(0).getD_name();
				String selectedD_phone = selectdesigner.get(0).getD_phone();
				String selectedD_age = selectdesigner.get(0).getD_age();
				String selectedD_address = selectdesigner.get(0).getD_address();
				String selectedD_id = selectdesigner.get(0).getD_id();
				String selectedD_pw = selectdesigner.get(0).getD_pw();

				// 라디오 버튼 정보를 불러오기위한 if문
				// 선택한 디자이너 정보에서 선택한 디자이너의 성별의값이 남성일때
				if (selectdesigner.get(0).getD_gender().equals("남성")) {
					rbDMale.setSelected(true);// 남성선택

					// 선택한 정보의 성별값이 남성이아닐경우
				} else {
					// 여성선택
					rbDFemale.setSelected(true);
				}

				// 선택한 행의 디자이너 정보를 읽어온다.
				txtDName.setText(selectedD_name);
				txtDPhone.setText(selectedD_phone);
				txtDAge.setText(selectedD_age);
				txtDAddress.setText(selectedD_address);
				txtDId.setText(selectedD_id);
				txtDPw.setText(selectedD_pw);

				// 뷰 더블클릭시 비활성화 목록
				btnDesignerJoin.setDisable(true);
				rbDMale.setDisable(true);
				rbDFemale.setDisable(true);
				// 뷰더블클릭시 텍스트 필드 수정불가 목록
				txtDName.setDisable(true);
				txtDAge.setDisable(true);

				// 활성화 목록
				btnDesignerInit.setDisable(false);
				btnDesignerUpdate.setDisable(false);
				btnDesignerDelete.setDisable(false);

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	// 디자이너 초기화 버튼 이벤트
	public void handlerBtnDesignerInitAction(ActionEvent event) {
		try {
			designerDataList.removeAll(designerDataList);
			designerTotalList();// 디자이너 전체 메소드 호출

			// 텍스트 필드값 모두 초기화
			txtDName.clear();// 이름 텍스트필드 초기화
			designergenderGroup.selectToggle(null);// 선택 초기화
			txtDPhone.clear();// 전화번호 텍스트필드 초기화
			txtDAge.clear();// 생년월일 텍스트필드 초기화
			txtDAddress.clear();// 주소 텍스트필드 초기화
			txtDId.clear();// 아이디 텍스트필드 초기화
			txtDPw.clear();// 비밀번호 텍스트필드 초기화
			txtDId.setDisable(false);

			// 수정가능한 필드값 설정
			txtDPhone.setEditable(true);// 전화번호 텍스트필드 수정가능
			txtDAddress.setEditable(true);// 주소 텍스트필드 수정가능
			txtDId.setEditable(true);// 아이디 텍스트필드 수정가능

			// 버튼 설정
			btnDIdOverlap.setDisable(false);// 아이디 중복체크 버튼 활성화
			btnDesignerUpdate.setDisable(true);// 수정 버튼 비활성화
			btnDesignerJoin.setDisable(true);// 등록 버튼 비활성화
			btnDesignerDelete.setDisable(true);// 삭제 버튼 비활성화
			txtDPw.setEditable(false);

			// 추가적인 초기화
			rbDMale.setDisable(false);
			rbDFemale.setDisable(false);
			// 뷰더블클릭시 텍스트 필드 수정불가 목록 다시 수정가능
			txtDName.setDisable(false);
			txtDAge.setDisable(false);
			
			//콤보박스 초기화
			cbx_searchList.getSelectionModel().clearSelection();
			
			//테이블뷰에서 선택한거 초기화
			designerTotalView.getSelectionModel().clearSelection();
		} catch (Exception e) {
			e.printStackTrace();// 오류 내용 출력
		}
	}

	// 아이디 중복 버튼 이벤트
	public void handlerBtnDIdOverlapAction(ActionEvent event) {

		DesignerDAO dDao = null;

		String searchId = "";// 검색할 아이디
		boolean searchResult = true;// 검색 결과 중복된 값이 없음

		try {
			searchId = txtDId.getText().trim();
			dDao = new DesignerDAO();
			searchResult = (boolean) dDao.getStudentIdOverlap(searchId);// 중복된 아이디가 없을 경우false

			// 검색 결과 true , 검색할 아이디가 있으면
			if (!searchResult && !searchId.equals("")) {
				txtDId.setDisable(true);// 아이디 텍스트 필드 비활성화
				txtDPw.setEditable(true);// 비밀번호 텍스트 필드 활성화

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchId + "를 사용할 수 있습니다");
				alert.setContentText("패스워드를 입력하세요");
				alert.showAndWait();

				btnDesignerJoin.setDisable(false); // 등록 버튼 활성화
				btnDIdOverlap.setDisable(true); // 중복체크 버튼 비활성화

				// 검색할 아이디가 공백이면
			} else if (searchId.equals("")) {
				btnDesignerJoin.setDisable(true);// 등록버튼 비활성화
				btnDIdOverlap.setDisable(false);// 중복체크 버튼 활성화

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText("아이디를 입력하세요");
				alert.setContentText("등록할 아이디를 입력하세요");
				alert.showAndWait();

				// 중복된 값이 있을경우
			} else {
				btnDesignerJoin.setDisable(true);// 등록 버튼 비활성화
				btnDIdOverlap.setDisable(false);// 중복체크 버튼 활성화
				txtDId.clear();// 아이디 텍스트필드 클리어

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchId + "를 사용할 수 없습니다");
				alert.setContentText("다른 아이디를 사용하세요");
				alert.showAndWait();

				txtDId.requestFocus();// 아이디 텍스트 필드 포커스(이동)
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("아이디 중복 검사 오류");
			alert.setHeaderText("아이디 중복 검사에 오류가 발생했습니다");
			alert.setContentText("다시 하세요");
			alert.showAndWait();
		}
	}

	// 디자이너 등록 버튼 이벤트
	public void handlerBtnDesignerJoinAction(ActionEvent event) {
		try {
			designerDataList.removeAll(designerDataList);

			DesignerVO dvo = null;
			DesignerDAO ddao = null;

			dvo = new DesignerVO(txtDName.getText().trim(),
					designergenderGroup.getSelectedToggle().getUserData().toString(), txtDPhone.getText().trim(),
					txtDAge.getText().trim(), txtDAddress.getText().trim(), txtDId.getText().trim(),
					txtDPw.getText().trim());
			ddao = new DesignerDAO();
			ddao.getDesignerRegiste(dvo);
			// 초기화버튼 가져와야함.

			if (ddao != null) {
				designerTotalList();// 디자이너 전체 리스트 호출

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("디자이너 등록");
				alert.setHeaderText(txtDName.getText() + "디자이너의 정보가 성공적으로 추가되었습니다");
				alert.setContentText("디자이너 등록완료");
				alert.showAndWait(); // 확인 창 누르기 전까지 대기

				handlerBtnDesignerInitAction(event);
				txtDId.setDisable(false);

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("디자이너 정보 입력");
			alert.setHeaderText("디자이너 정보를 정확히 입력하세요");
			alert.setContentText("디자이너 정보 누락");
			alert.showAndWait(); // 확인 창 누르기 전까지 대기
			handlerBtnDesignerInitAction(event);
		}
	}

	// 디자이너 전체 정보
	public void designerTotalList() {
		Object[][] designerTotalData;

		DesignerDAO dDao = new DesignerDAO();// DesignerDAO객체 생성
		DesignerVO dVo = null;
		ArrayList<String> designerTitle; // 디자이너 컬럼의 갯수를 알아낸다.
		ArrayList<DesignerVO> designerList;// 디자이너 데이터,row를 알아낸다.

		designerTitle = dDao.getDColumnName(); // 컬럼네임을 읽어와 designerTitle에 저장
		int columnCount = designerTitle.size();// 타이틀의 사이즈를 designerCount에 저장

		designerList = dDao.getDesignerTotal();// 디자이너 전체 리스트를 읽어와 list에 저장
		int rowCount = designerList.size();// 리스트의 사이즈를 rowCount에 저장

		designerTotalData = new Object[rowCount][columnCount];// customerTotalData 배열

		for (int index = 0; index < rowCount; index++) {
			dVo = designerList.get(index);
			designerDataList.add(dVo); // 디자이너 의 데이터를 추가한다
		}

	}

}
