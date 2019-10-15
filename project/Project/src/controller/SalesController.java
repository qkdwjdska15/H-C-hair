package controller;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.SalesVO;
import model.CustomerVO;
import model.ProductVO;
import model.ReservationVO;

public class SalesController implements Initializable {
//예약
	@FXML
	ComboBox<String> cbx_reservationSearchList;// 이름,전화번호,담당디자이너 성함을 이용한 예약 정보 찾기위해 콤보박스
	@FXML
	private TextField txtRSerach;// 선택한 목록에 맞게 검색내용 입력칸
	@FXML
	private Button btnRSerach;// 선택 버튼
	@FXML
	private TableView<ReservationVO> reservationView;// 예약정보 테이블 뷰
	ObservableList<ReservationVO> reservationDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스 생성
	ObservableList<ReservationVO> selectreservation;// 테이블에 선택한 정보 저장
	int selectedRIndex;// 테이블에서 선택한 예약 정보 인덱스 저장.
	@FXML
	private Button btnRChoice;// 예약정보테이블에서 더블클릭후 선택버튼 (선택한 값 저장)

//상품
	@FXML
	private Button btnPALL;// 전체 상품 버튼
	@FXML
	private Button btnPCut;// 컷트 상품 버튼
	@FXML
	private Button btnPFirm;// 펌 상품 버튼
	@FXML
	private Button btnPDigitalSetting;// 디지털,셋팅 상품 버튼
	@FXML
	private Button btnPVolumeMagic;// 볼륨 매직 상품 버튼
	@FXML
	private Button btnPDyeing;// 염색 상품 버튼
	@FXML
	private Button btnPClinic;// 크리닉 상품 버튼
	@FXML
	private TableView<ProductVO> productView;// 상품 테이블 뷰
	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList();
	ObservableList<ProductVO> selectProduct;// 테이블에서 선택한 정보 저장
	int selectedProductIndex;// 상품 테이블에서 선택한 상품 정보 인덱스 저장
	@FXML
	private Button btnProductAdd;// 상품 추가 버튼(기존상품 명에 추가된다.)
	@FXML
	private Button btnProductNotAdd;// 상품 추가 안함 버튼

//예약한 회원의 정보 출력
	@FXML
	private TextField txtReservationName;// 예약정보 뷰 더브클릭시 이름을 자동으로 끌고오는 예약자 이름
	@FXML
	private TableView<SalesVO> reservationProductPriceView;// 받은 시술명과 총액을 보여주는 테이블뷰
	ObservableList<SalesVO> reservationDataListConfimation = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스
	int selectedIndexProductPrice;
	// 생성
	ObservableList<SalesVO> selectreservationConfimation;// 테이블에 선택한 정보 저장
	int selectedRIndexConfimation;// 테이블에서 선택한 예약 정보 인덱스 저장.

//결과 값 표출
	@FXML
	private TextField txtMtotalprice;// 상품총액
	@FXML
	private TextField txtMtiketrating;// 고객 정액제 등금
	@FXML
	private TextField txtMtiketsales;// 등급에 따른 할인율
	@FXML
	private TextField txtMconfirmationprice;// 결제 금액
	@FXML
	private Button btnMdecision;// 결제 버튼
	@FXML
	private Button btnMInit;// 초기화버튼

	@FXML
	private TextField txtMTodayVisit;// 방문자수
	@FXML
	private TextField txtMTotalInCome;// 총 수입

	@FXML
	private TableView<SalesVO> SalesView;// 매장 현황 뷰 (db테이블 도 다시확인)
	ObservableList<SalesVO> SalesDataList = FXCollections.observableArrayList();// 컬렉션 클래스의 인스턴스
	// 생성
	ObservableList<SalesVO> selectSales;// 테이블에 선택한 정보 저장
	int selectedSalesIndex;// 테이블에서 선택한 예약 정보 인덱스 저장.

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// 버튼 초기값 설정
			btnRChoice.setDisable(true);
			btnProductAdd.setDisable(true);
			btnProductNotAdd.setDisable(true);
			btnMdecision.setDisable(true);
			btnMInit.setDisable(true);
			// 테이블 선택 설정
			productView.setDisable(true);
			reservationProductPriceView.setDisable(true);
			// 텍스트필드 설정
			txtReservationName.setDisable(true);
			txtMconfirmationprice.setDisable(true);
			txtMtiketrating.setDisable(true);
			txtMtiketsales.setDisable(true);
			txtMtotalprice.setDisable(true);

			// 예약 정보 테이블 뷰 컬럼 이름 설정
			TableColumn colR_code = new TableColumn("예약번호");
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
			colR_product_name.setPrefWidth(260);// 넓이 설정
			colR_product_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colR_product_name.setCellValueFactory(new PropertyValueFactory<>("r_product_name"));

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

			reservationView.setItems(reservationDataList);
			reservationView.getColumns().addAll(colR_code, colRC_name, colRC_gender, colRC_phone, colRC_Rating,
					colRC_sales, colRD_name, colR_product_name, colR_total_price, colR_date, colR_visit_time);
			// 예약 정보 전체 보기
			reservationTotalList();

			// 상품 추가할수있도록 상품테이블뷰 생성
			TableColumn colP_name = new TableColumn("상품명");
			colP_name.setPrefWidth(200);// 컬럼 넓이 설정
			colP_name.setStyle("-fx-alignment:CENTER");
			colP_name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

			TableColumn colP_price = new TableColumn("상품 가격");
			colP_price.setPrefWidth(200);// 컬럼 넓이 설정
			colP_price.setStyle("-fx-alignment:CENTER");
			colP_price.setCellValueFactory(new PropertyValueFactory<>("p_price"));
			productView.setItems(productDataList);
			productView.getColumns().addAll(colP_name, colP_price);

			// 상품 정보 전체 보기
			productTotalList();

			// 회원의 서비스정보만 뽑아낸 테이블뷰
			TableColumn colP_rname = new TableColumn("시술받으신 상품명");
			colP_rname.setPrefWidth(450);// 컬럼 넓이 설정
			colP_rname.setStyle("-fx-alignment:CENTER");
			colP_rname.setCellValueFactory(new PropertyValueFactory<>("r_product_name"));

			TableColumn colP_rTotalprice = new TableColumn("시술 총액");
			colP_rTotalprice.setPrefWidth(150);// 컬럼 넓이 설정
			colP_rTotalprice.setStyle("-fx-alignment:CENTER");
			colP_rTotalprice.setCellValueFactory(new PropertyValueFactory<>("r_total_price"));
			reservationProductPriceView.setItems(reservationDataListConfimation);
			reservationProductPriceView.getColumns().addAll(colP_rname, colP_rTotalprice);

			// 매장현황 테이블 뷰
			TableColumn colS_code = new TableColumn("결제 번호");
			colS_code.setPrefWidth(60);// 넓이 설정
			colS_code.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colS_code.setCellValueFactory(new PropertyValueFactory<>("s_no"));

			TableColumn colSC_name = new TableColumn("회원 성명");
			colSC_name.setPrefWidth(60);// 넓이 설정
			colSC_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSC_name.setCellValueFactory(new PropertyValueFactory<>("s_customer_name"));

			TableColumn colSC_gender = new TableColumn("성 별");
			colSC_gender.setPrefWidth(40);// 넓이 설정
			colSC_gender.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSC_gender.setCellValueFactory(new PropertyValueFactory<>("s_customer_gender"));

			TableColumn colSC_phone = new TableColumn("전화 번호");
			colSC_phone.setPrefWidth(150);// 넓이 설정
			colSC_phone.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSC_phone.setCellValueFactory(new PropertyValueFactory<>("s_customer_phone"));

			TableColumn colSD_name = new TableColumn("담당디자이너");
			colSD_name.setPrefWidth(90);// 넓이 설정
			colSD_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSD_name.setCellValueFactory(new PropertyValueFactory<>("s_designer_name"));

			TableColumn colSR_product_name = new TableColumn("시술받은 상품명");
			colSR_product_name.setPrefWidth(420);// 넓이 설정
			colSR_product_name.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSR_product_name.setCellValueFactory(new PropertyValueFactory<>("s_reservation_product_name"));

			TableColumn colSR_total_price = new TableColumn("받은 시술가격 총액");
			colSR_total_price.setPrefWidth(120);// 넓이 설정
			colSR_total_price.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSR_total_price.setCellValueFactory(new PropertyValueFactory<>("s_reservation_total_price"));

			TableColumn colSC_Rating = new TableColumn("회원 정액권 등급");
			colSC_Rating.setPrefWidth(100);// 넓이 설정
			colSC_Rating.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSC_Rating.setCellValueFactory(new PropertyValueFactory<>("s_customer_tiket_rating"));

			TableColumn colSC_sales = new TableColumn("할인율");
			colSC_sales.setPrefWidth(60);// 넓이 설정
			colSC_sales.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colSC_sales.setCellValueFactory(new PropertyValueFactory<>("s_customer_tiket_sales"));

			TableColumn colS_sales_total_price = new TableColumn("결제금액");
			colS_sales_total_price.setPrefWidth(100);// 넓이 설정
			colS_sales_total_price.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colS_sales_total_price.setCellValueFactory(new PropertyValueFactory<>("s_total_price"));

			TableColumn colS_date = new TableColumn("결제일");
			colS_date.setPrefWidth(195);// 넓이 설정
			colS_date.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
			colS_date.setCellValueFactory(new PropertyValueFactory<>("s_date"));

			SalesView.setItems(SalesDataList);
			SalesView.getColumns().addAll(colS_code, colSC_name, colSC_gender, colSC_phone, colSD_name,
					colSR_product_name, colSR_total_price, colSC_Rating, colSC_sales, colS_sales_total_price,
					colS_date);
			// 매장현황 정보 전체 리스트
			salesTotalList();

			// 회원 정보를 찾기위한 콤보박스 내용 설정
			cbx_reservationSearchList.setItems(FXCollections.observableArrayList("이름", "전화번호"));
			// 회원 검색 버튼 이벤트 핸들러
			btnRSerach.setOnAction(event -> handlerBtnRSerachAction(event));

			// 예약정보 클릭 이벤트 핸들러
			reservationView.setOnMouseClicked(event -> handlerReservationViewClickAction(event));
			// 예약정보 클릭후 선태 이벤트 핸들러
			btnRChoice.setOnAction(event -> handlerBtnRChoiceAction(event));
			// 추가선택 버튼 이벤트 핸들러
			btnProductAdd.setOnAction(event -> handlerBtnProductAddAction(event));
			// 추가선택x 버튼 이벤트 핸들러
			btnProductNotAdd.setOnAction(event -> handlerBtnProductNotAddAction(event));

			// 뷰 전체 목록 버튼 이벤트 핸들러
			btnPALL.setOnAction(event -> handlerBtnPALL(event));
			// 뷰 컷트 목록 버튼 이벤트 핸들러
			btnPCut.setOnAction(event -> handlerBtnPCut(event));
			// 뷰 펌 목록 버튼 이벤트 핸들러
			btnPFirm.setOnAction(event -> handlerBtnPFirm(event));
			// 뷰 디지털,셋팅 목록 버튼 이벤트 핸들러
			btnPDigitalSetting.setOnAction(event -> handlerBtnPDigitalSetting(event));
			// 뷰 볼륨매직 목록 버튼 이벤트 핸들러
			btnPVolumeMagic.setOnAction(event -> handlerBtnPVolumeMagic(event));
			// 뷰 염색 목록 버튼 이벤트 핸들러
			btnPDyeing.setOnAction(event -> handlerBtnPDyeing(event));
			// 뷰 크리닉 목록 버튼 이벤트 핸들러
			btnPClinic.setOnAction(event -> handlerBtnPClinic(event));

			// 결제 버튼 이벤트 핸들러
			btnMdecision.setOnAction(event -> handlerBtnMDecisionAction(event));
			// 초기화 버튼 이벤트 핸들러
			btnMInit.setOnAction(event -> handlerBtnMInitAction(event));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// ReservationController rco = new ReservationController();여기다여기

	// 초기화 버튼 이벤트 핸들러
	public void handlerBtnMInitAction(ActionEvent event) {
		try {
			reservationDataList.removeAll(reservationDataList);
			reservationTotalList();
			reservationDataListConfimation.removeAll(reservationDataListConfimation);
			productDataList.removeAll(productDataList);
			productTotalList();
			SalesDataList.removeAll(SalesDataList);
			salesTotalList();
			// 버튼 초기값 설정
			btnRChoice.setDisable(true);
			btnProductAdd.setDisable(true);
			btnProductNotAdd.setDisable(true);
			btnMdecision.setDisable(true);
			btnMInit.setDisable(true);
			// 테이블 선택 설정
			productView.setDisable(true);
			reservationProductPriceView.setDisable(true);
			// 텍스트필드 설정
			txtReservationName.clear();
			txtMconfirmationprice.clear();
			txtMtiketrating.clear();
			txtMtiketsales.clear();
			txtMtotalprice.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 뷰 크리닉 목록 버튼
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

	// 뷰 염색 목록 버튼
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

	// 뷰 볼륨매직 목록 버튼
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

	// 뷰 디지털,셋팅 목록 버튼
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

	// 뷰 펌 목록 버튼
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

	// 뷰 컷트 목록 버튼
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

	// 뷰 전체 목록 버튼
	public void handlerBtnPALL(ActionEvent event) {

		try {
			productDataList.removeAll(productDataList);
			productTotalList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약 회원 검색 버튼 이벤트
	public void handlerBtnRSerachAction(ActionEvent event) {
		String search = "";
		search = cbx_reservationSearchList.getSelectionModel().getSelectedItem();

		System.out.println(search);
		ReservationVO rVo = new ReservationVO();
		ReservationDAO rDao = new ReservationDAO();

		String serchName = "";
		boolean searchResult = false;

		serchName = txtRSerach.getText().trim();
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

					reservationTotalList();
					; // 회원 전체 목록 메소드 호출
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ArrayList<String> title;
				ArrayList<ReservationVO> list = null;

				title = rDao.getReservationColumnName();

				int columnCount = title.size();

				if (search.equals("이름")) {// 콤보박스에서 이름 선택시
					list = rDao.getReservationCNameSearchList(serchName);
					if (list.size() == 0) { // 값이 없을 때
						txtRSerach.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("회원 이름 정보 검색");
						alert.setHeaderText(serchName + " 회원의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = rDao.getReservationTotal();
					}
				}
				if (search.equals("전화번호")) {// 콤보박스에서 전화번호 선택시
					list = rDao.getReservationCPhoneSearchList(serchName);
					if (list.size() == 0) {// 값이 없을때
						txtRSerach.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("회원 전화번호 정보 검색");
						alert.setHeaderText("전화번호가 " + serchName + " 인 회원의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = rDao.getReservationTotal();
					}
				}
				txtRSerach.clear();
				reservationDataList.removeAll(reservationDataList);

				int rowCount = list.size();
				for (int index = 0; index < rowCount; index++) {
					rVo = list.get(index);
					reservationDataList.add(rVo);
				}
				searchResult = true;
			}

			if (!searchResult) {
				txtRSerach.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 검색");
				alert.setHeaderText(serchName + " 회원이 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 매장현황 전체 리스트 메소드
	public void salesTotalList() throws Exception {
		SalesDAO mDao = new SalesDAO();
		SalesVO mVo = new SalesVO();
		ArrayList<SalesVO> list;
		list = mDao.getSalesTotal();
		int rowCount = list.size();
		for (int index = 0; index < rowCount; index++) {
			mVo = list.get(index);
			SalesDataList.add(mVo);
		}

	}

	// 결제 버튼 이벤트
	public void handlerBtnMDecisionAction(ActionEvent event) {
		SalesVO mvo = null;
		SalesDAO mdao = null;
		try {
			// 기존 데이터 삭제후 다시 등록시킨 데이터를 넣어줌
			SalesDataList.removeAll(SalesDataList);

			selectreservation = reservationView.getSelectionModel().getSelectedItems();
			selectreservationConfimation = reservationProductPriceView.getSelectionModel().getSelectedItems();
			selectedSalesIndex = selectreservation.get(0).getR_code();
			String a = selectreservation.get(0).getC_name();
			String b = selectreservation.get(0).getC_gender();
			String c = selectreservation.get(0).getC_phone();
			String d = selectreservation.get(0).getD_name();
			String g = selectreservation.get(0).getC_tiket_rating();
			String h = selectreservation.get(0).getC_tiket_sales();
			String z = reservationProductPriceView.getItems().get(0).getR_product_name();
			System.out.println(z + "7");
			// 여기부터 수정합시다.
			mvo = new SalesVO(a, b, c, d, z, txtMtotalprice.getText(), g, h, txtMconfirmationprice.getText());
			mdao = new SalesDAO();
			mdao.getSalesJoin(mvo);

			// 여기서부터는 예약정보 예약컨디션에 확정으로 수정 하는 명령문
			boolean sucess;
			ReservationDAO rdao = new ReservationDAO();
			sucess = rdao.getReservationConditionUpdate(selectedSalesIndex);

			// 테이블에서 선택초기화
			reservationView.getSelectionModel().clearSelection();
			if (mdao != null) {
				System.out.println("일단 값 들어갔다");
				salesTotalList();
				reservationDataList.removeAll(reservationDataList);
				reservationTotalList();
			}
			handlerBtnMInitAction(event);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 추가선택x 버튼 이벤트
	public void handlerBtnProductNotAddAction(ActionEvent event) {
		//결제버튼 활성화
		btnMdecision.setDisable(false);
		//추가선택 버튼 비활성화
		btnProductAdd.setDisable(true);
		//추가선택x 버튼 비활성화
		btnProductNotAdd.setDisable(true);
		
		// 예약한 상품명
		String selectedR_product_name = selectreservation.get(0).getR_product_name();
		// 예약한 상품 총액
		String selectedR_total_price = selectreservation.get(0).getR_total_price();
		// 예약한 회원의 정액권 등급
		String rTiketRating = selectreservation.get(0).getC_tiket_rating();
		// 예약한 회원의 정액권 등급의 따른 할인율
		String rTiketSales = selectreservation.get(0).getC_tiket_sales();
		SalesVO rvo = new SalesVO();

		rvo = new SalesVO(selectedR_product_name, selectedR_total_price);
		reservationDataListConfimation.add(rvo);

		// 할인 가격을 뺀 확정난 결제금액.
		int confirmationprice;
		// 예약한 회원의 정액권 등급이 Gold일때
		if (rTiketRating.equals("Gold")) {
			// 결제 금액 = 총상품 금액-(총상품금액*0.3(30%))
			// 문자형을 숫자형으로 변환후 계산
			confirmationprice = (int) (Integer.parseInt(selectedR_total_price)
					- Integer.parseInt(selectedR_total_price) * 0.3);

			// 예약한 회원의 정액권 등급이 Silver일때
		} else if (rTiketRating.equals("Silver")) {
			// 결제 금액 = 총상품 금액-(총상품금액*0.2(20%))
			// 문자형을 숫자형으로 변환후 계산
			confirmationprice = (int) (Integer.parseInt(selectedR_total_price)
					- Integer.parseInt(selectedR_total_price) * 0.2);

			// 예약한 회원의 정액권 등급이 Gold,Silver 가 아닌 나머지 일때(Bronze)
		} else {
			// 결제 금액 = 총상품 금액-(총상품금액*0.15(15%))
			// 문자형을 숫자형으로 변환후 계산
			confirmationprice = (int) (Integer.parseInt(selectedR_total_price)
					- Integer.parseInt(selectedR_total_price) * 0.15);
		}
		// 상품 총액 TextField 에 값을 설정.
		txtMtotalprice.setText(selectedR_total_price);
		// 정액권 등급 TextField 에 값을 설정.
		txtMtiketrating.setText(rTiketRating);
		// 할인율 TextField 에 값을 설정.
		txtMtiketsales.setText(rTiketSales);

		// 결제금액(정액권등급에 따른 할인율 가격을 뺀 총액) Textfield에 값을 설정
		// 문자형으로 변환하여 설정해야하기때문에 +""을 사용 하여 정수형을 문자형으로 변환.
		txtMconfirmationprice.setText(confirmationprice + "");

	}

	// 추가선택 버튼 이벤트
	public void handlerBtnProductAddAction(ActionEvent event) {
		try {
			//결제버튼 활성화
			btnMdecision.setDisable(false);
			//추가선택 버튼 비활성화
			btnProductAdd.setDisable(true);
			//추가선택x 버튼 비활성화
			btnProductNotAdd.setDisable(true);
			selectProduct = productView.getSelectionModel().getSelectedItems();
			selectedProductIndex = selectProduct.get(0).getP_no();
			//추가할 상품 이름
			String selectedP_name = selectProduct.get(0).getP_name();
			//추가할 상품 가격
			String selectedP_price = selectProduct.get(0).getP_price();
			// 예약한 상품명
			String selectedR_product_name = selectreservation.get(0).getR_product_name();
			// 예약한 상품 총액
			String selectedR_total_price = selectreservation.get(0).getR_total_price();
			//예약 회원 정액권 등급
			String rTiketRating = selectreservation.get(0).getC_tiket_rating();
			//예약 회원 정액권 등급에 따른 할인율
			String rTiketSales = selectreservation.get(0).getC_tiket_sales();
			
			//상품추가시 상품총액값 새로운 변수로 다시 설정
			int updateR_total_price = Integer.parseInt(selectedR_total_price) + Integer.parseInt(selectedP_price);

			// 상품추가하면 수정되게.
			boolean sucess;// 제대로 수정됬나 알아보기위한 sucess 변수선언
			ReservationDAO rdao = new ReservationDAO();
			//상품추가시 예약 정보 총상품명에 + 추가된 상품명 , 예약정보 DB 추가된상품 컬럼에 추가된 상품 이름 UPDATE
			sucess = rdao.getReservationTotalPnamePpriceUpdate(selectedR_product_name + " " + selectedP_name,
					updateR_total_price + "", selectedP_name, selectedRIndex);
			SalesVO rvo = new SalesVO();
			//reservationDataListConfimation 설정 기존 예약한 상품+추가선택 상품 , 상품추가하여 UPDATE 된 총액 (기존총액+추가된상품가격)
			rvo = new SalesVO(selectedR_product_name + " " + selectedP_name, updateR_total_price + "");

			reservationDataListConfimation.add(rvo);

			//결제금액(updateR_total_price 에 회원 정액권 등급에 따른 할인가격 뺀 총결제금액)
			int rConfirmationprice;
			
			//회원의 정액권 등급이 Gold일때
			if (rTiketRating.equals("Gold")) {
				//결제금액 = 총액(기존총액+추가된상품금액) - (총액(기존총액+추가된상품금액)*0.3)
				rConfirmationprice = (int) (updateR_total_price - (updateR_total_price * 0.3));

				//회원의 정액권 등급이 Silver일때
			} else if (rTiketRating.equals("Silver")) {
				//결제금액 = 총액(기존총액+추가된상품금액) - (총액(기존총액+추가된상품금액)*0.2)
				rConfirmationprice = (int) (updateR_total_price - (updateR_total_price * 0.2));
				
				// 예약한 회원의 정액권 등급이 Gold,Silver 가 아닌 나머지 일때(Bronze)
			} else {
				//결제금액 = 총액(기존총액+추가된상품금액) - (총액(기존총액+추가된상품금액)*0.15)
				rConfirmationprice = (int) (updateR_total_price - (updateR_total_price * 0.15));
			}

			//상품 총액 TextField 총액(기존총액+추가된상품금액) 값 설정
			txtMtotalprice.setText(updateR_total_price + "");
			//회원 정액권 등급 TextField 회원 정액권 등급 값 설정 
			txtMtiketrating.setText(rTiketRating);
			//할인율 TextField 회원 정액권에 따른 할인율 설정
			txtMtiketsales.setText(rTiketSales);
			//결제금액 TextField 결제금액 = 총액(기존총액+추가된상품금액) - (총액(기존총액+추가된상품금액*할인율) 설정
			txtMconfirmationprice.setText(rConfirmationprice + "");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 테이블 전체 정보 메소드
	private void productTotalList() {
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

	// 예약 정보 클릭후 선택 이벤트 핸들러
	public void handlerBtnRChoiceAction(ActionEvent event) {
		String rCName = selectreservation.get(0).getC_name();
		txtReservationName.setText(rCName);
		btnProductAdd.setDisable(false);
		btnProductNotAdd.setDisable(false);
		productView.setDisable(false);

	}

	// 예약 정보 클릭 이벤트
	public void handlerReservationViewClickAction(MouseEvent event) {
		if (event.getClickCount() == 1) {
			selectreservation = reservationView.getSelectionModel().getSelectedItems();
			selectedRIndex = selectreservation.get(0).getR_code();
			btnRChoice.setDisable(false);
			btnMInit.setDisable(false);
		}
	}

	// 예약 정보 전체 목록
	public void reservationTotalList() throws Exception {
		ReservationDAO rDao = new ReservationDAO();
		ReservationVO rVo = new ReservationVO();
		ArrayList<ReservationVO> list;

		list = rDao.getReservationConditionNullTotal();
		int rowCount = list.size();
		for (int index = 0; index < rowCount; index++) {
			rVo = list.get(index);
			reservationDataList.add(rVo);
		}
	}

}
