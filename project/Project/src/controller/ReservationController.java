package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CustomerVO;
import model.DesignerVO;
import model.ProductVO;
import model.ReservationVO;

public class ReservationController implements Initializable {
//1번.
	@FXML
	private ComboBox<String> cbx_searchCustomerList;// 회원정보 콤보박스
	@FXML
	private TextField txtCSearch;// 회원정보 검색필드
	@FXML
	private Button btnCSearch;// 회원정보 검색버튼
	@FXML
	private Button btnCALL;// 회원 정보 전체버튼
	@FXML
	private TableView<CustomerVO> customerTotalView;// 회원정보 테이블 뷰
	CustomerVO customer = new CustomerVO();// model.customer 인스턴스 화 --> VO
	ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스 생성
	ObservableList<CustomerVO> selectCustomer;// 테이블에 선택한 정보 저장
	int selectedCustomerIndex;// 상품 테이블에서 선택한 상품 정보 인덱스 저장
//2.
	@FXML
	private Button btnPALL;// 전체 상품 버튼
	@FXML
	private Button btnPCut;// 컷트 상품 버튼
	@FXML
	private Button btnPFirm;// 펌 상품 버튼
	@FXML
	private Button btnPDigitalSetting;// 디지털,셋팅 상품 버튼
	@FXML
	private Button btnPVolumeMagic;// 볼륨매직 상품 버튼
	@FXML
	private Button btnPDyeing;// 염색 상품 버튼
	@FXML
	private Button btnPClinic;// 크리닉 상품 버튼
	@FXML
	private TableView<ProductVO> productTotalView;// 상품정보 테이블 뷰
	ProductVO product = new ProductVO();
	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList();
	ObservableList<ProductVO> selectProduct;// 테이블에서 선택한 정보 저장
	int selectedProductIndex;// 상품 테이블에서 선택한 상품 정보 인덱스 저장
	@FXML
	private Button btnPChoice;// 상품 선택 버튼
	@FXML
	private TableView<ReservationVO> productAllTotalView;// 예약 상품정보 테이블 뷰
	ObservableList<ReservationVO> productAllDataList = FXCollections.observableArrayList();
	ObservableList<ReservationVO> selectAllProduct; // 예약 상품 테이블에서 선택한 정보 저장
	int selectedProductAllIndex; // 예약 상품 테이블에서 선택한 상품 정보 인덱스 저장
//3.
	@FXML
	private DatePicker dpRDate;// 예약일
	@FXML
	private TextField txtRTime;// 예약시간
	@FXML
	private ComboBox<DesignerVO> cbx_designerName;// 담당자(디자이너 성함)콤보박스
	ObservableList<DesignerVO> designerDataList = FXCollections.observableArrayList();

	ObservableList<DesignerVO> selectDesigner;
	int selectedDesignerIndex; // 예약 상품 테이블에서 선택한 상품 정보 인덱스 저장
	@FXML
	private Button btnRJoin;// 등록 버튼
	@FXML
	private Button btnRInit;// 초기화 버튼
	@FXML
	private Button btnRDelete;// 삭제 버튼
//final
	@FXML
	private ComboBox<String> cbx_reservationSearchList;// 예약정보 검색을위한 콤보박스
	@FXML
	private TextField txtRsearch;// 예약정보 텍스트필드
	@FXML
	private Button btnRsearch;// 예약정보 찾기 버튼
	@FXML
	private Button btnRsearchALL;// 예약정보 전체 버튼
	@FXML
	private TableView<ReservationVO> reservationView;// 예약정보 테이블 뷰

	ReservationVO reservation = new ReservationVO();// model.reservation 인스턴스 화 --> VO
	ObservableList<ReservationVO> reservationDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스 생성
	ObservableList<ReservationVO> selectreservation;// 테이블에 선택한 정보 저장
	int selectedRIndex;// 테이블에서 선택한 예약 정보 인덱스 저장.

	int r_code;// 삭제시 테이블에서 선택한 예약의 코드 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {

			// 회원 전체 버튼 비활성화
			btnCALL.setDisable(true);
			// 상품 선택 버튼 비활성화
			btnPChoice.setDisable(true);
			// 예약 정보 전체버튼 비활성화
			btnRsearchALL.setDisable(true);

			btnRInit.setDisable(true);
			btnRDelete.setDisable(true);
			try {

				// 예약 정보 테이블 뷰 컬럼 이름 설정
				TableColumn colR_code = new TableColumn("NO");
				colR_code.setPrefWidth(60);// 넓이 설정
				colR_code.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_code.setCellValueFactory(new PropertyValueFactory<>("r_code"));

				TableColumn colRC_name = new TableColumn("성 명");
				colRC_name.setPrefWidth(60);// 넓이 설정
				colRC_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRC_name.setCellValueFactory(new PropertyValueFactory<>("c_name"));

				TableColumn colRC_gender = new TableColumn("성 별");
				colRC_gender.setPrefWidth(60);// 넓이 설정
				colRC_gender.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRC_gender.setCellValueFactory(new PropertyValueFactory<>("c_gender"));

				TableColumn colRC_phone = new TableColumn("전화 번호");
				colRC_phone.setPrefWidth(100);// 넓이 설정
				colRC_phone.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRC_phone.setCellValueFactory(new PropertyValueFactory<>("c_phone"));

				TableColumn colRC_Rating = new TableColumn("정액권 등급");
				colRC_Rating.setPrefWidth(120);// 넓이 설정
				colRC_Rating.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRC_Rating.setCellValueFactory(new PropertyValueFactory<>("c_tiket_rating"));

				TableColumn colRC_sales = new TableColumn("할인율");
				colRC_sales.setPrefWidth(60);// 넓이 설정
				colRC_sales.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRC_sales.setCellValueFactory(new PropertyValueFactory<>("c_tiket_sales"));

				TableColumn colRD_name = new TableColumn("담당디자이너");
				colRD_name.setPrefWidth(100);// 넓이 설정
				colRD_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colRD_name.setCellValueFactory(new PropertyValueFactory<>("d_name"));

				TableColumn colR_product_name = new TableColumn("예약 상품명");
				colR_product_name.setPrefWidth(360);// 넓이 설정
				colR_product_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_product_name.setCellValueFactory(new PropertyValueFactory<>("r_product_name"));

				TableColumn colR_add_product = new TableColumn("추가된 상품명");
				colR_add_product.setPrefWidth(100);// 넓이 설정
				colR_add_product.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_add_product.setCellValueFactory(new PropertyValueFactory<>("r_add_product"));

				TableColumn colR_total_price = new TableColumn("예약상품총액");
				colR_total_price.setPrefWidth(120);// 넓이 설정
				colR_total_price.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_total_price.setCellValueFactory(new PropertyValueFactory<>("r_total_price"));

				TableColumn colR_date = new TableColumn("예약일");
				colR_date.setPrefWidth(200);// 넓이 설정
				colR_date.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_date.setCellValueFactory(new PropertyValueFactory<>("r_date"));

				TableColumn colR_visit_time = new TableColumn("예약 시간");
				colR_visit_time.setPrefWidth(60);// 넓이 설정
				colR_visit_time.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_visit_time.setCellValueFactory(new PropertyValueFactory<>("r_visit_time"));

				TableColumn colR_condetion = new TableColumn("예약 상태");
				colR_condetion.setPrefWidth(100);// 넓이 설정
				colR_condetion.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colR_condetion.setCellValueFactory(new PropertyValueFactory<>("r_condetion"));

				reservationView.setItems(reservationDataList);
				reservationView.getColumns().addAll(colR_code, colRC_name, colRC_gender, colRC_phone, colRC_Rating,
						colRC_sales, colRD_name, colR_product_name, colR_add_product, colR_total_price, colR_date,
						colR_visit_time, colR_condetion);
				// 예약 정보 전체 보기
				reservationTotalList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				// 회원 정보 테이블 뷰 컬럼 이름 설정
				TableColumn colC_name = new TableColumn("성 명");
				colC_name.setPrefWidth(100);// 넓이 설정
				colC_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colC_name.setCellValueFactory(new PropertyValueFactory<>("c_name"));

				TableColumn colC_gender = new TableColumn("성 별");
				colC_gender.setPrefWidth(100);// 넓이 설정
				colC_gender.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colC_gender.setCellValueFactory(new PropertyValueFactory<>("c_gender"));

				TableColumn colC_phone = new TableColumn("전화 번호");
				colC_phone.setPrefWidth(150);// 넓이 설정
				colC_phone.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colC_phone.setCellValueFactory(new PropertyValueFactory<>("c_phone"));

				TableColumn colC_Rating = new TableColumn("정액권 등급");
				colC_Rating.setPrefWidth(100);// 넓이 설정
				colC_Rating.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colC_Rating.setCellValueFactory(new PropertyValueFactory<>("c_tiket_rating"));

				TableColumn colC_sales = new TableColumn("할인율");
				colC_sales.setPrefWidth(100);// 넓이 설정
				colC_sales.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
				colC_sales.setCellValueFactory(new PropertyValueFactory<>("c_tiket_sales"));

				// 테이블에 데이터 추가
				customerTotalView.setItems(customerDataList);
				customerTotalView.getColumns().addAll(colC_name, colC_gender, colC_phone, colC_Rating, colC_sales);
				// 회원 전체 정보
				customerTotalList();

				// 상품 테이블뷰 컬럼 이름 설정
				TableColumn colP_name = new TableColumn("상품명");
				colP_name.setPrefWidth(200);// 컬럼 넓이 설정
				colP_name.setStyle("-fx-alignment:CENTER");
				colP_name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

				TableColumn colP_price = new TableColumn("상품 가격");
				colP_price.setPrefWidth(200);// 컬럼 넓이 설정
				colP_price.setStyle("-fx-alignment:CENTER");
				colP_price.setCellValueFactory(new PropertyValueFactory<>("p_price"));

				productTotalView.setItems(productDataList);
				productTotalView.getColumns().addAll(colP_name, colP_price);

				// 선택상품 전체 정보
				productTotalList();

			} catch (Exception e) {
			}

			try {

				// 예약 상품 테이블뷰 컬럼 이름 설정
				TableColumn colP_rname = new TableColumn("상품명");
				colP_rname.setPrefWidth(200);// 컬럼 넓이 설정
				colP_rname.setStyle("-fx-alignment:CENTER");
				colP_rname.setCellValueFactory(new PropertyValueFactory<>("p_name"));

				TableColumn colP_rprice = new TableColumn("상품 가격");
				colP_rprice.setPrefWidth(200);// 컬럼 넓이 설정
				colP_rprice.setStyle("-fx-alignment:CENTER");
				colP_rprice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

				productAllTotalView.setItems(productAllDataList);
				productAllTotalView.getColumns().addAll(colP_rname, colP_rprice);
			} catch (Exception e) {
				// TODO: handle exception
			}

			// 회원 정보를 찾기위한 콤보박스 내용 설정
			cbx_searchCustomerList.setItems(FXCollections.observableArrayList("이름", "전화번호"));
			// 회원 검색 버튼 이벤트 핸들러
			btnCSearch.setOnAction(event -> handlerBtnCSearchAtion(event));
			// 회원 검색후 전체목록을 보기위한 전체 버튼
			btnCALL.setOnAction(event -> handlerBtnCALLAtion(event));
			// 회원 정보 의 인덱스 번호 끌고오기위한 클릭 이벤트
			customerTotalView.setOnMouseClicked(event -> handlerCustomerTotalViewClikcAction(event));

			// 디자이너 콤보박스 선택한 이름을 가지고 디자이너 코드 뽑아내기위한 이벤트 핸들러
			cbx_designerName.setOnAction(event -> handlerCbx_DesignerNameAction(event));

			// 상품 뷰 전체 목록 버튼 이벤트 핸들러
			btnPALL.setOnAction(event -> handlerBtnPALL(event));
			// 상품 뷰 컷트 목록 버튼 이벤트 핸들러
			btnPCut.setOnAction(event -> handlerBtnPCut(event));
			// 상품 뷰 펌 목록 버튼 이벤트 핸들러
			btnPFirm.setOnAction(event -> handlerBtnPFirm(event));
			// 상품 뷰 디지털,셋팅 목록 버튼 이벤트 핸들러
			btnPDigitalSetting.setOnAction(event -> handlerBtnPDigitalSetting(event));
			// 상품 뷰 볼륨매직 목록 버튼 이벤트 핸들러
			btnPVolumeMagic.setOnAction(event -> handlerBtnPVolumeMagic(event));
			// 상품 뷰 염색 목록 버튼 이벤트 핸들러
			btnPDyeing.setOnAction(event -> handlerBtnPDyeing(event));
			// 상품 뷰 크리닉 목록 버튼 이벤트 핸들러
			btnPClinic.setOnAction(event -> handlerBtnPClinic(event));

			// 상품 목록 더블 클릭 이벤트 핸들러
			productTotalView.setOnMouseClicked(event -> handlerProductTotalViewClickAction(event));
			// 상품 목록 선택후 선택버튼 이벤트 핸들러
			btnPChoice.setOnAction(event -> handlerBtnPChoiceAction(event));

			// 예약 목록 등록 버튼 이벤트 핸들러
			btnRJoin.setOnAction(event -> handlerBtnRJoinAction(event));
			// 예약 정보 검색 버튼 이벤트 핸들러
			btnRsearch.setOnAction(event -> handlerBtnRsearchAction(event));
			// 예약 정보 검색 버튼 후 전체 정보를 보기위한 예약정보 전체버튼 이벤트 핸들러
			btnRsearchALL.setOnAction(event -> handlerBtnRsearchALLAction(event));
			// 예약 정보 테이블 마우스 클릭 이벤트 핸들러
			reservationView.setOnMouseClicked(event -> handlerReservationViewClickAction(event));
			// 예약 초기화 버튼 이벤트 핸들러
			btnRInit.setOnAction(event -> handlerBtnRInitAction(event));
			// 예약 정보 삭제 버튼 이벤트 핸들러
			btnRDelete.setOnAction(event -> handlerBtnRDeleteAction(event));

			// 디자이너 이름 콤보박스내용물 설정
			DesignerDAO ddao = new DesignerDAO();
			designerDataList.addAll(ddao.getDesignerName());
			System.out.println(designerDataList);
			cbx_designerName.setItems(designerDataList);

			// 예약 검색 분류 선택하는 콤보박스 내용 설정
			cbx_reservationSearchList.setItems(FXCollections.observableArrayList("이름", "전화번호", "담당디자이너"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약 정보 삭제 버튼 이벤트
	public void handlerBtnRDeleteAction(ActionEvent event) {

		ReservationDAO rDao = null;
		rDao = new ReservationDAO();

		try {
			rDao.getReservationDelete(selectedRIndex);
			System.out.println(selectedRIndex + "테이블에서 선택한 인덱스 확인 바람!");
			reservationDataList.removeAll(reservationDataList);// 데이터 삭제
			reservationTotalList();
			handlerBtnRInitAction(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약 초기화 버튼 이벤트
	public void handlerBtnRInitAction(ActionEvent event) {
		try {
			reservationDataList.removeAll(reservationDataList);
			reservationTotalList();
			customerDataList.removeAll(customerDataList);
			customerTotalList();
			productDataList.removeAll(productDataList);
			productTotalList();
			productAllDataList.removeAll(productAllDataList);

			txtCSearch.clear();
			txtRsearch.clear();
			txtRTime.clear();
			dpRDate.setValue(null);

			cbx_designerName.getSelectionModel().clearSelection();
			cbx_reservationSearchList.getSelectionModel().clearSelection();
			cbx_searchCustomerList.getSelectionModel().clearSelection();

			btnRInit.setDisable(true);
			btnRDelete.setDisable(true);
			btnCALL.setDisable(true);
			btnRJoin.setDisable(false);
			btnPChoice.setDisable(true);
			btnRsearchALL.setDisable(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 예약정보 테이블뷰 마우스 클릭 이벤트
	public void handlerReservationViewClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {// 더블 클릭
			btnRDelete.setDisable(false);
			selectreservation = reservationView.getSelectionModel().getSelectedItems();
			selectedRIndex = selectreservation.get(0).getR_code();
			btnRJoin.setDisable(true);
		}
	}

	// 예약정보 검색 버튼 사용후 전체 정보를 보기위한 전체 버튼
	public void handlerBtnRsearchALLAction(ActionEvent event) {
		try {
			// 예약 전체 리스트 불러옴
			reservationTotalList();
			btnRsearchALL.setDisable(true);// 전체버튼 비활성화
			// 예약정보 검색 텍스트필드 초기화
			txtRsearch.clear();
			// 예약정보 검색 분류 콤보박스 초기화
			cbx_reservationSearchList.getSelectionModel().clearSelection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 예약정보 검색 버튼 이벤트
	public void handlerBtnRsearchAction(ActionEvent event) {
		btnRsearchALL.setDisable(false);// 전체버튼 활성화
		String search = "";
		search = cbx_reservationSearchList.getSelectionModel().getSelectedItem();

		System.out.println(search + "검색 제대로 들어왓는지 확인해라");
		ReservationVO rVo = new ReservationVO();
		ReservationDAO rDao = new ReservationDAO();

		String searchName = "";
		boolean searchResult = false;

		searchName = txtRsearch.getText().trim();
		try {
			if (searchName.equals("") || search.equals("")) {
				try {
					searchResult = true;

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("예약 정보 검색");
					alert.setHeaderText("예약 정보를 입력하세요");
					alert.setContentText("다음에는 주의하세요");
					alert.showAndWait();

					customerTotalList(); // 회원 전체 목록 메소드 호출
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ArrayList<String> title;
				ArrayList<ReservationVO> list = null;

				title = rDao.getReservationColumnName();

				int columnCount = title.size();

				if (search.equals("이름")) {// 콤보박스 이름 선택시
					list = rDao.getReservationSearchC_name(searchName);
					if (list.size() == 0) {// 값이 없을때
						txtRsearch.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("예약 정보(회원이름) 검색");
						alert.setHeaderText(searchName + " 회원의 예약 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();
						list = rDao.getReservationTotal();
					}
				}
				if (search.equals("전화번호")) {// 콤보박스에서 전화번호 선택시
					list = rDao.getReservationSearchC_phone(searchName);
					if (list.size() == 0) {// 값이 없을때
						txtRsearch.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("예약 전화번호 정보 검색");
						alert.setHeaderText("전화번호가 " + searchName + " 인 예약의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = rDao.getReservationTotal();
					}
				}
				if (search.equals("담당디자이너")) {
					list = rDao.getReservationSearchD_name(searchName);
					if (list.size() == 0) {// 값이 없을때
						txtRsearch.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("예약 담당디자이너 정보 검색");
						alert.setHeaderText("담당디자이너가 " + searchName + " 인 예약의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = rDao.getReservationTotal();
					}
				}

				reservationDataList.removeAll(reservationDataList);
				int rowCount = list.size();
				for (int index = 0; index < rowCount; index++) {
					rVo = list.get(index);
					reservationDataList.add(rVo);
				}
				searchResult = true;
			}

			if (!searchResult) {
				txtCSearch.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약 정보 검색");
				alert.setHeaderText(searchName + " 회원의 예약 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
			}
		} catch (Exception e) {

		}
	}

	// 디자이너 이름으로 디자이너 코드 뽑아내는 콤보박스 이벤트
	public void handlerCbx_DesignerNameAction(ActionEvent event) {

	}

	// 회원 정보 의 인덱스 번호 끌고오기위한 클릭 이벤트
	public void handlerCustomerTotalViewClikcAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			btnRInit.setDisable(false);
			// 테이블에서 선택한 정보를 저장
			selectCustomer = customerTotalView.getSelectionModel().getSelectedItems();
			selectedCustomerIndex = selectCustomer.get(0).getC_code();

		}

	}

	// 예약 정보 등록 버튼 이벤트
	public void handlerBtnRJoinAction(ActionEvent event) {
		ReservationVO rvo = null;
		ReservationDAO rdao = null;
		try {

			reservationDataList.removeAll(reservationDataList);
			// 내가선택한 날짜 문자형으로 끌고온다.
			selectProduct = productTotalView.getSelectionModel().getSelectedItems();
			selectedCustomerIndex = selectCustomer.get(0).getC_code();

			System.out.println(dpRDate.getValue().toString());
			System.out.println(txtRTime.getText().trim() + "시");
			System.out.println(selectProduct.get(0).getP_name());
			System.out.println(selectProduct.get(0).getP_price());
			System.out.println(selectCustomer.get(0).getC_code());
			System.out.println(selectProduct.get(0).getP_no());
			System.out.println(productAllDataList.get(0).getP_name());

			// 테이블 데이터리스트에 저장되어있는 이름 뽑아내기
			String producttotalname = "";
			int size = productAllDataList.size();
			for (int i = 0; i < size; i++) {
				producttotalname = producttotalname + productAllDataList.get(i).getP_name() + "  ";
			}
			// 테이블 데이터리스트에 저장되어있는 상품금액 뽑아내기
			int producttotalprice = 0;
			for (int i = 0; i < size; i++) {
				producttotalprice += Integer.parseInt(productAllDataList.get(i).getP_price());

			}
			// 등록문
			rvo = new ReservationVO(producttotalname, producttotalprice + "", dpRDate.getValue().toString(),
					txtRTime.getText().trim() + "시", selectProduct.get(0).getP_no(), selectCustomer.get(0).getC_code(),
					cbx_designerName.getSelectionModel().getSelectedItem().getD_code());
			rdao = new ReservationDAO();
			rdao.getReservationJoin(rvo);
			if (rdao != null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약 정보 등록");
				alert.setHeaderText("예약 정보 등록 완료");
				alert.setContentText("예약 정보 등록 성공");
				alert.showAndWait();
				reservationTotalList();
				handlerBtnRInitAction(event);
			}
		} catch (Exception e) {
			try {
				reservationTotalList();
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("예약 정보 등록");
				alert.setHeaderText("예약 정보를 입력하세요");
				alert.setContentText("다음에는 주의하세요");
				alert.showAndWait();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	// 예약 정보 전체 목록
	public void reservationTotalList() throws Exception {
		ReservationDAO rDao = new ReservationDAO();
		ReservationVO rVo = new ReservationVO();
		ArrayList<ReservationVO> list;

		list = rDao.getReservationTotal();
		int rowCount = list.size();
		for (int index = 0; index < rowCount; index++) {
			rVo = list.get(index);
			reservationDataList.add(rVo);
		}
	}

	// 디자이너 이름 가져와서 콤보박스에 넣어주기//보류
	/*
	 * public void DesignerName() { DesignerDAO dDao = new DesignerDAO(); ArrayList
	 * designerName = new ArrayList<>();
	 * 
	 * try { designerName = dDao.getDesignerName();
	 * cbx_designerName.setItems(FXCollections.observableArrayList(designerName));
	 * System.out.println(cbx_designerName.getSelectionModel().getSelectedItem()+
	 * "여기입니다"); } catch (Exception e) { e.printStackTrace(); } }
	 */

	// 상품 목록 마우스 클릭후 선택버튼 이벤트
	public void handlerBtnPChoiceAction(ActionEvent event) {
		try {
			// 테이블에서 선택한 정보를 저장

			selectProduct = productTotalView.getSelectionModel().getSelectedItems();
			selectedProductIndex = selectProduct.get(0).getP_no();

			String selectedP_name = selectProduct.get(0).getP_name();
			String selectedP_price = selectProduct.get(0).getP_price();

			ReservationVO rvo = new ReservationVO();
			rvo = new ReservationVO(selectedP_name, selectedP_price);

			productAllDataList.add(rvo);

			System.out.println(productDataList.toString() + "확인후 삭제 22");

			System.out.println(productAllDataList.toString() + "확인후 삭제");

			// 나중에 삭제
			System.out.println(productDataList.size());
			for (int i = 0; i < productAllDataList.size(); i++) {

				System.out.println(productAllTotalView.getItems().get(i));
			}

			// 선택버튼 비활성화
			btnPChoice.setDisable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 목록 더블 클릭 이벤트
	public void handlerProductTotalViewClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {
			btnPChoice.setDisable(false);
		}
	}

	// 상품 뷰 크리닉 목록 버튼
	public void handlerBtnPClinic(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductClinicTotal();

			if (pdao.getProductClinicTotal() != null) {
				int rowCount = pdao.getProductClinicTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductClinicTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 염색 목록 버튼
	public void handlerBtnPDyeing(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductDyeingTotal();

			if (pdao.getProductDyeingTotal() != null) {
				int rowCount = pdao.getProductDyeingTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductDyeingTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 볼륨매직 목록 버튼
	public void handlerBtnPVolumeMagic(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductVolumeMagicTotal();

			if (pdao.getProductVolumeMagicTotal() != null) {
				int rowCount = pdao.getProductVolumeMagicTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductVolumeMagicTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 디지털,셋팅 목록 버튼
	public void handlerBtnPDigitalSetting(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductDigitalSettingTotal();

			if (pdao.getProductDigitalSettingTotal() != null) {
				int rowCount = pdao.getProductDigitalSettingTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductDigitalSettingTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 펌 목록 버튼
	public void handlerBtnPFirm(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductFirmTotal();

			if (pdao.getProductFirmTotal() != null) {
				int rowCount = pdao.getProductFirmTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductFirmTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 컷트 목록 버튼
	public void handlerBtnPCut(ActionEvent event) {
		try {
			ProductVO pvo = new ProductVO();
			ProductDAO pdao = new ProductDAO();
			pdao.getProductCutTotal();

			if (pdao.getProductCutTotal() != null) {
				int rowCount = pdao.getProductCutTotal().size();
				productDataList.removeAll(productDataList);

				for (int index = 0; index < rowCount; index++) {
					pvo = pdao.getProductCutTotal().get(index);
					productDataList.add(pvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 뷰 전체 목록 버튼
	public void handlerBtnPALL(ActionEvent event) {

		try {
			productDataList.removeAll(productDataList);
			productTotalList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원 검색후 전체목록을 보기위한 전체 버튼
	public void handlerBtnCALLAtion(ActionEvent event) {
		customerDataList.removeAll(customerDataList);
		customerTotalList();
		// 콤보박스 초기화
		cbx_searchCustomerList.getSelectionModel().clearSelection();
		// 전체버튼 비활성화
		btnCALL.setDisable(true);

	}

	// 회원 검색 버튼 이벤트
	public void handlerBtnCSearchAtion(ActionEvent event) {
		String search = "";
		search = cbx_searchCustomerList.getSelectionModel().getSelectedItem();

		System.out.println(search);
		CustomerVO cVo = new CustomerVO();
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

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("회원 전화번호 정보 검색");
						alert.setHeaderText("전화번호가 " + serchName + " 인 회원의 정보가 없습니다");
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

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 검색");
				alert.setHeaderText(serchName + " 회원이 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
			}
			// 전체 버튼 활성화
			btnCALL.setDisable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 테이블 전체 정보 메소드
	public void productTotalList() {
		try {
			ProductDAO pDao = new ProductDAO();
			ProductVO pVo = null;
			ArrayList<String> title;
			ArrayList<ProductVO> list;

			title = pDao.getProductColumnName();
			int columnCount = title.size();

			list = pDao.getProductTotal();
			int rowCount = list.size();
			for (int index = 0; index < rowCount; index++) {
				pVo = list.get(index);
				productDataList.add(pVo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원 테이블 전체 정보 메소드
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
		System.out.println(cVo + "회원테이블 CVO값");

	}

}
