package controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.Today;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CustomerVO;

public class CustomerController implements Initializable {
	@FXML
	private TextField txtCName;// 고객 성함
	@FXML
	private ToggleGroup customergenderGroup;// 성별 라디오 버튼 그룹
	@FXML
	private RadioButton rbCMale;// 남성 라디오 버튼
	@FXML
	private RadioButton rbCFemale;// 여성 라디오 버튼
	@FXML
	private TextField txtCPhone;// 고객 전화번호
	@FXML
	private TextField txtCAge;// 고객 생년월일
	@FXML
	private TextField txtCAddress;// 고객 주소
	@FXML
	private ToggleGroup tiketRatingGroup;// 정액권 등급 라디오 버튼 그룹
	@FXML
	private RadioButton rbCGold;// Gold등급 라디오 버튼
	@FXML
	private RadioButton rbCSilver;// Silver등급 라디오 버튼
	@FXML
	private RadioButton rbCBronze;// Bronze등급 라디오 버튼
	@FXML
	private TextField txtCExpiration;// 정액권 만료일자 (현재일 +365)
	@FXML
	private TextField txtCSale;// 정액권에 따른 할인율 (골드 30% 실버 20% 브론즈 15%)
	@FXML
	private Button btnCustomerJoin;// 회원 등록 버튼
	@FXML
	private Button btnCustomerInit;// 회원 등록 초기화버튼
	@FXML
	private Button btnCustomerUpdate;// 회원 수정 버튼
	@FXML
	private Button btnCustomerDelete;// 회원 삭제 버튼

	@FXML
	private ComboBox<String> cbx_searchList; // 검색 분류 콤보박스
	@FXML
	private TextField txtCSearch;// 콤보박스 에서 고른 분류의맞는 형식 입력하는 텍스트필드
	@FXML
	private Button btnCSearch;// 검색버튼 회원목록을 분류에 맞게 검색하기위해 있는 버튼
	@FXML
	private Button btnCAll;// 검색후 전체버튼으로 뷰 다시 다보기
	@FXML
	private TableView<CustomerVO> customerTotalView = new TableView<>();// 저장되어있는 회원목록을 보여주는 테이블뷰

	CustomerVO customer = new CustomerVO();// model.customer 인스턴스 화 --> VO
	ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스 생성
	ObservableList<CustomerVO> selectCustomer;// 테이블에 선택한 정보 저장
	boolean editDelete = false;// 수정할 때 확인 버튼 상태 설정
	int selectedIndex;// 테이블에서 선택한 회원 정보 인덱스 저장.

//primaryStage 스테이지 선언
	private Stage primaryStage;
	int c_code;// 삭제시 테이블에서 선택한 학생의 이름 저장
	File selectedFile = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 텍스트 필드 초기화
		txtCExpiration.setEditable(false);// 정액권 만료일자 텍스트필드를 수정불가능
		txtCSale.setEditable(false);// 정액권 등급에 따른 할인율 텍스트필드를 수정불가능

		// 버튼 초기화
		btnCustomerJoin.setDisable(true);// 회원 등록 버튼 비활성화
		btnCustomerInit.setDisable(false);// 회원 등록창 초기화 버튼 활성화
		btnCustomerUpdate.setDisable(true);// 회원 수정 버튼 비활성화
		btnCustomerDelete.setDisable(true);// 회원 삭제 버튼 비활성화
		btnCAll.setDisable(true);// 전체 버튼 비활성화

		// 전화번호 필드에 숫자만 입력할 수 있도록 설정.
		DecimalFormat phoneformat = new DecimalFormat("###########");// 번호를 11자리까지만 입력할 수 있게 설정
		txtCPhone.setTextFormatter(new TextFormatter<>(event -> {
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
		txtCAge.setTextFormatter(new TextFormatter<>(event -> {
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
		// 회원 등록 버튼 이벤트 핸들러
		btnCustomerJoin.setOnAction(event -> handlerBtnCustomerJoinAction(event));
		// 회원 초기화 버튼 이벤트 핸들러
		btnCustomerInit.setOnAction(event -> handlerBtnCustomerInitAction(event));
		// 회원 수정 버튼 이벤트 핸들러
		btnCustomerUpdate.setOnAction(event -> handlerBtnCustomerUpdateAction(event));
		// 회원 삭제 버튼 이벤트 핸들러
		btnCustomerDelete.setOnAction(event -> handlerBtnCustomerDeleteAction(event));
		// 회원 검색 버튼 이벤트 핸들러
		btnCSearch.setOnAction(event -> handlerBtnCSearchAction(event));
		// 회원 검색후 전체 버튼 이벤트 핸들러
		btnCAll.setOnAction(event -> handlerBtnCAllAction(event));

		// 회원 정액권 라디오 버튼 설정
		// 회원 정액권 골드 등급 라디오 버튼 이벤트 핸들러
		rbCGold.setOnAction(event -> handlerRbCGoldAction(event));
		// 회원 정액권 실버 등급 라디오 버튼 이벤트 핸들러
		rbCSilver.setOnAction(event -> handlerRbCSilverAction(event));
		// 회원 정액권 브론즈 등급 라디오 버튼 이벤트 핸들러
		rbCBronze.setOnAction(event -> handlerRbCBronzeAction(event));

		// 테이블 설정.
		customerTotalView.setEditable(false);// 테이블 수정 불가

		// 테이블뷰 마우스 클릭 이벤트 핸들러
		customerTotalView.setOnMouseClicked(event -> handlerCustomerTotalViewAction(event));
		
		
		//콤보박스 내용 설정
		cbx_searchList.setItems(FXCollections.observableArrayList("이름", "전화번호"));

		// 테이블 뷰 컬럼 이름 설정
		TableColumn colCode = new TableColumn("NO");
		colCode.setPrefWidth(40);// 넓이 설정
		colCode.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));

		TableColumn colName = new TableColumn("성 명");
		colName.setPrefWidth(132);// 넓이 설정
		colName.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colName.setCellValueFactory(new PropertyValueFactory<>("c_name"));

		TableColumn colGender = new TableColumn("성 별");
		colGender.setPrefWidth(132);// 넓이 설정
		colGender.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colGender.setCellValueFactory(new PropertyValueFactory<>("c_gender"));

		TableColumn colPhone = new TableColumn("전화 번호");
		colPhone.setPrefWidth(132);// 넓이 설정
		colPhone.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colPhone.setCellValueFactory(new PropertyValueFactory<>("c_phone"));

		TableColumn colAge = new TableColumn("생년 월일");
		colAge.setPrefWidth(132);// 넓이 설정
		colAge.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colAge.setCellValueFactory(new PropertyValueFactory<>("c_age"));

		TableColumn colAddress = new TableColumn("주 소");
		colAddress.setPrefWidth(140);// 넓이 설정
		colAddress.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colAddress.setCellValueFactory(new PropertyValueFactory<>("c_address"));

		TableColumn colRating = new TableColumn("정액권 등급");
		colRating.setPrefWidth(130);// 넓이 설정
		colRating.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colRating.setCellValueFactory(new PropertyValueFactory<>("c_tiket_rating"));

		TableColumn colExpirationdate = new TableColumn("정액권 만료일자");
		colExpirationdate.setPrefWidth(130);// 넓이 설정
		colExpirationdate.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colExpirationdate.setCellValueFactory(new PropertyValueFactory<>("c_expiration_date"));

		TableColumn colSales = new TableColumn("할인율");
		colSales.setPrefWidth(135);// 넓이 설정
		colSales.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colSales.setCellValueFactory(new PropertyValueFactory<>("c_tiket_sales"));

		// 테이블에 데이터 추가
		customerTotalView.setItems(customerDataList);
		customerTotalView.getColumns().addAll(colCode, colName, colGender, colPhone, colAge, colAddress, colRating,
				colExpirationdate, colSales);

		// 회원 전체 정보
		customerTotalList();

	}

	// 전체 버튼 이벤트
	public void handlerBtnCAllAction(ActionEvent event) {
		customerDataList.removeAll(customerDataList);
		customerTotalList();
		//콤보박스 초기화
		cbx_searchList.getSelectionModel().clearSelection();
		btnCAll.setDisable(true);
		
	}

	// 회원 검색 버튼 이벤트
	public void handlerBtnCSearchAction(ActionEvent event) {
		String search = "";
		//콤보박스에서 내가 선택한 아이템.
		search = cbx_searchList.getSelectionModel().getSelectedItem();
		//CustomerVO 인스턴스화
		CustomerVO cVo = new CustomerVO();
		//CustomerDAO 인스턴스화
		CustomerDAO cDao = new CustomerDAO();

		String serchName = "";
		boolean searchResult = false;

		serchName = txtCSearch.getText().trim();
		System.out.println(serchName);
		try {
			if (serchName.equals("") || search.equals("")) {
				try {
					searchResult = true;

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("회원 정보 검색");
					alert.setHeaderText("회원 검색 정보를 입력하세요");
					alert.setContentText("다음에는 주의하세요");
					alert.showAndWait();
					customerDataList.removeAll(customerDataList);
					customerTotalList(); // 회원 전체 목록 메소드 호출
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ArrayList<String> title;
				ArrayList<CustomerVO> list = null;

				title = cDao.getColumnName();

				int columnCount = title.size();

				if (search.equals("이름")) {// 콤보박스에서 이름 선택시
					list = cDao.getCustomerCNameSearchList(serchName);
					if (list.size() == 0) { // 값이 없을 때
						txtCSearch.clear();
						btnCAll.setDisable(false);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("회원 이름 정보 검색");
						alert.setHeaderText(serchName + " 회원의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = cDao.getCustomerTotal();
						
					}
				}
				if (search.equals("전화번호")) {// 콤보박스에서 전화번호 선택시
					list = cDao.getCustomerCPhoneSearchList(serchName);
					if (list.size() == 0) {// 값이 없을때
						txtCSearch.clear();
						btnCAll.setDisable(false);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("회원 전화번호 정보 검색");
						alert.setHeaderText("전화번호가 "+serchName + " 인 회원의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = cDao.getCustomerTotal();
					}
				}
				txtCSearch.clear();
				customerDataList.removeAll(customerDataList);

				int rowCount = list.size();
				for (int index = 0; index < rowCount; index++) {
					cVo = list.get(index);
					customerDataList.add(cVo);
				}
				searchResult = true;
			}

			if (!searchResult) {
				txtCSearch.clear();
				btnCAll.setDisable(false);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 검색");
				alert.setHeaderText(serchName + " 회원이 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		btnCAll.setDisable(false);
	}

	// 회원 삭제 버튼 이벤트
	public void handlerBtnCustomerDeleteAction(ActionEvent event) {

		// CustomerDAO 객체 생성
		CustomerDAO cDao = null;
		cDao = new CustomerDAO();

		try {
			cDao.getCustomerDelete(selectedIndex);// 테이블에서 선택한 회원의 이름을 CustomerDAO에서 불러온다
			System.out.println(selectedIndex);
			customerDataList.removeAll(customerDataList);// 데이터를 지운다
			// 회원 전체 정보
			customerTotalList();
			handlerBtnCustomerInitAction(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 테이블뷰 마우스 클릭 이벤트 핸들러
	public void handlerCustomerTotalViewAction(MouseEvent event) {
		if (event.getClickCount() == 2) {// 더블 클릭 하면

			try {
				selectCustomer = customerTotalView.getSelectionModel().getSelectedItems();
				selectedIndex = selectCustomer.get(0).getC_code();
				String selectedC_name = selectCustomer.get(0).getC_name();
				String selectedC_phone = selectCustomer.get(0).getC_phone();
				String selectedC_age = selectCustomer.get(0).getC_age();
				String selectedC_address = selectCustomer.get(0).getC_address();
				String selectedC_expiration_date = selectCustomer.get(0).getC_expiration_date();
				String selectedC_tiket_sales = selectCustomer.get(0).getC_tiket_sales();

				System.out.println(selectedC_name);
				// 선택한 행의 회원 정보를 읽어온다.
				System.out.println(selectCustomer.get(0).getC_gender().toString());
				System.out.println(rbCMale.toString());

				// 테이블뷰에서 선택한 정보 라디오 버튼의값이 남성일때.
				if (selectCustomer.get(0).getC_gender().equals("남성")) {
					rbCMale.setSelected(true);// 남성선택
				} else {// 아닐때
					rbCFemale.setSelected(true);// 여성선택
				}

				if (selectCustomer.get(0).getC_tiket_rating().equals("Gold")) {
					rbCGold.setSelected(true);
				} else if (selectCustomer.get(0).getC_tiket_rating().equals("Silver")) {
					rbCSilver.setSelected(true);
				} else {
					rbCBronze.setSelected(true);
				}

				txtCName.setText(selectedC_name);
				txtCPhone.setText(selectedC_phone);
				txtCAge.setText(selectedC_age);
				txtCAddress.setText(selectedC_address);
				txtCExpiration.setText(selectedC_expiration_date);
				txtCSale.setText(selectedC_tiket_sales);

				// 뷰 더블클릭시 비활성화 목록
				btnCustomerJoin.setDisable(true);
				rbCBronze.setDisable(true);
				rbCGold.setDisable(true);
				rbCSilver.setDisable(true);
				// 활성화 목록
				btnCustomerUpdate.setDisable(false);
				btnCustomerDelete.setDisable(false);

				// 텍스트필드 수정불가 목록
				txtCName.setDisable(true);
				rbCMale.setDisable(true);
				rbCFemale.setDisable(true);
				txtCAge.setDisable(true);
				txtCExpiration.setDisable(true);
				txtCSale.setDisable(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 회원 수정 버튼 이벤트 핸들러
	public void handlerBtnCustomerUpdateAction(ActionEvent event) {
		try {
			// customerVO customerEdit =
			// customerTotalView.getSelectionModel().getSelectedItem();// 선택한 객체를 저장
			// selectedIndex = customerTotalView.getSelectionModel().getSelectedIndex();//
			// 선택된 모델의 인덱스를 읽어온다.
			boolean sucess;
			CustomerDAO cdao = new CustomerDAO();
			sucess = cdao.getCustomerUpdate(txtCName.getText().trim(), txtCPhone.getText().trim(),
					txtCAddress.getText().trim());
			if (sucess) {
				customerDataList.removeAll(customerDataList);
				customerTotalList();
				handlerBtnCustomerInitAction(event);

			}else {
				handlerBtnCustomerInitAction(event);
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("회원 정보 수정");
				alert.setHeaderText("회원 정보 수정 실패");
				alert.setContentText("회원 정보 수정 실패!!!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원 초기화 버튼 이벤트 핸들러
	public void handlerBtnCustomerInitAction(ActionEvent event) {
		// 필드 선택 값 초기화
		txtCName.clear();
		customergenderGroup.selectToggle(null);// 선택 초기화
		txtCPhone.clear();
		txtCAge.clear();
		txtCAddress.clear();
		tiketRatingGroup.selectToggle(null);
		txtCExpiration.clear();
		txtCSale.clear();

		// 초기화시 사용가능하게 바꿈.
		txtCName.setDisable(false);
		rbCMale.setDisable(false);
		rbCFemale.setDisable(false);
		txtCAge.setDisable(false);
		rbCBronze.setDisable(false);
		rbCGold.setDisable(false);
		rbCSilver.setDisable(false);

		txtCExpiration.setDisable(false);
		txtCExpiration.setEditable(false);
		txtCSale.setDisable(false);
		txtCSale.setEditable(false);

		btnCustomerUpdate.setDisable(true);
		btnCustomerDelete.setDisable(true);
		btnCustomerJoin.setDisable(true);

		customerTotalView.getSelectionModel().clearSelection();
	}

	// 회원 정액권 등급 브론즈버튼 눌럿을때 이벤트
	public void handlerRbCBronzeAction(ActionEvent event) {
		txtCSale.setText("15%");
		CustomerDAO cdao = new CustomerDAO();
		try {
			txtCExpiration.setText(cdao.getSysdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtCSale.setEditable(false);
		txtCExpiration.setEditable(false);
		btnCustomerJoin.setDisable(false);// 회원 등록 버튼 활성화
	}

	// 회원 정액권 등급 실버버튼 눌렀을때 이벤트
	public void handlerRbCSilverAction(ActionEvent event) {
		txtCSale.setText("20%");
		CustomerDAO cdao = new CustomerDAO();
		try {
			txtCExpiration.setText(cdao.getSysdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtCSale.setEditable(false);
		txtCExpiration.setEditable(false);
		btnCustomerJoin.setDisable(false);// 회원 등록 버튼 활성화
	}

	// 회원 정액권 등급 골드버튼 눌렀을때 이벤트
	public void handlerRbCGoldAction(ActionEvent event) {
		txtCSale.setText("30%");
		CustomerDAO cdao = new CustomerDAO();
		try {
			//만료일자 텍스트필드에 오늘 날짜에 +365 일을 더한 쿼리문의 결과값을 넣어준다.
			txtCExpiration.setText(cdao.getSysdate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtCSale.setEditable(false);//회원 정액권의 따른 할인율 텍스트 필드 수정불가
		txtCExpiration.setEditable(false);//회원 정액권 만료일자 텍스트필드 수정불가
		btnCustomerJoin.setDisable(false);// 회원 등록 버튼 활성화
	}

	// 회원 등록 메소드
	public void handlerBtnCustomerJoinAction(ActionEvent event) {
		try {
			customerDataList.removeAll(customerDataList);

			CustomerVO cvo = null;
			CustomerDAO cdao = null;

			cvo = new CustomerVO(txtCName.getText().trim(),
					customergenderGroup.getSelectedToggle().getUserData().toString(), txtCPhone.getText().trim(),
					txtCAge.getText().trim(), txtCAddress.getText().trim(),
					tiketRatingGroup.getSelectedToggle().getUserData().toString(), txtCExpiration.getText().trim(),
					txtCSale.getText().trim());
			cdao = new CustomerDAO();
			cdao.getCustomerRegiste(cvo);
			handlerBtnCustomerInitAction(event);

			if (cvo != null) {
				customerDataList.removeAll(customerDataList);
				customerTotalList(); // 전체 리스트 호출
				handlerBtnCustomerInitAction(event);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("회원 정보 입력");
			alert.setHeaderText("회원 정보를 정확히 입력하세요");
			alert.setContentText("회원 정보 누락");
			alert.showAndWait(); // 확인 창 누르기 전까지 대기
			customerTotalList();
			handlerBtnCustomerInitAction(event);
		}
	}

	// 회원 전체 리스트 메소드
	public void customerTotalList() {

		Object[][] customerTotalData;

		CustomerDAO cDao = new CustomerDAO(); // CustomerDAO객체 생성
		CustomerVO cVo = null;
		ArrayList<String> customerTitle; // 회원 컬럼의 갯수를 알아낸다.
		ArrayList<CustomerVO> customerList;// 회원 데이터,row를 알아낸다.

		customerTitle = cDao.getColumnName(); // 컬럼네임을 읽어와 customerTitle에 저장
		int columnCount = customerTitle.size();// 타이틀의 사이즈를 columnCount에 저장

		customerList = cDao.getCustomerTotal();// 회원 전체 리스트를 읽어와 list에 저장
		int rowCount = customerList.size();// 리스트의 사이즈를 rowCount에 저장

		customerTotalData = new Object[rowCount][columnCount];// customerTotalData 배열

		for (int index = 0; index < rowCount; index++) {
			cVo = customerList.get(index);
			customerDataList.add(cVo); // 학생의 데이터를 추가한다
		}

	}

}
