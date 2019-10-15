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
import model.SalesVO;

public class TotalSalesController implements Initializable {
	@FXML
	ComboBox<String> cbx_totalSalesChoice;// 연간 월간 일간 콤보박스
	@FXML
	private TextField txtTotalSalesSearch;// 연,월,일 입력하는 텍스트필드
	@FXML
	private Button btnTotalSalesSearch;// 찾기 버튼
	@FXML
	private Button btnTotalSales;// 전체버튼
	@FXML
	TableView<SalesVO> totalSalesView;// 모든 매출 현황 보여주는 매출현황테이블뷰
	ObservableList<SalesVO> totalSalesDataList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//버튼 초기화
		btnTotalSales.setDisable(true);
		
		// 연간,월간,일일 매출 정보를 찾기위한 콤보박스 내용 설정
		cbx_totalSalesChoice.setItems(FXCollections.observableArrayList("일일매출", "월간매출", "연간매출"));

		// 검색 버튼 이벤트 핸들러
		btnTotalSalesSearch.setOnAction(event -> handlerBtnTotalSalesSearchAction(event));
		//전체 버튼 이벤트 핸들러
		btnTotalSales.setOnAction(event -> handlerBtnTotalSalesAction(event));

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
		colS_date.setPrefWidth(290);// 넓이 설정
		colS_date.setStyle("-fx-alignment:CENTER");// 텍스트 가운데 정렬
		colS_date.setCellValueFactory(new PropertyValueFactory<>("s_date"));

		totalSalesView.setItems(totalSalesDataList);
		totalSalesView.getColumns().addAll(colS_code, colSC_name, colSC_gender, colSC_phone, colSD_name,
				colSR_product_name, colSR_total_price, colSC_Rating, colSC_sales, colS_sales_total_price, colS_date);
		// 매장현황 정보 전체 리스트
		totalSalesList();
	}

	//전체 버튼 이벤트
	public void handlerBtnTotalSalesAction(ActionEvent event) {
		cbx_totalSalesChoice.getSelectionModel().clearSelection();
		txtTotalSalesSearch.clear();
		btnTotalSales.setDisable(true);
		totalSalesDataList.removeAll(totalSalesDataList);
		totalSalesList();
	}

	// 매장현황 검색 버튼 이벤트
	public void handlerBtnTotalSalesSearchAction(ActionEvent event) {
		String search = "";
		search = cbx_totalSalesChoice.getSelectionModel().getSelectedItem();
		System.out.println(search + "확인한번 해보실?");
		SalesVO sVo = new SalesVO();
		SalesDAO sDao = new SalesDAO();

		String searchName = "";
		boolean searchResult = false;

		searchName = txtTotalSalesSearch.getText().trim();
		System.out.println(searchName + "잘가져오나 확인해보실?");
		try {
			if (searchName.equals("") || search.equals("")) {
				try {
					searchResult = true;

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("매장 현황 검색");
					alert.setHeaderText("매장 현황 정보를 입력하세요");
					alert.setContentText("매장 현황 검색실패");
					alert.showAndWait();

					// 매장 전체 목록 메소드호출
					totalSalesList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ArrayList<String> title;
				ArrayList<SalesVO> list = null;

				title = sDao.getSalesColumnName();

				int columnCount = title.size();

				if (search.equals("일일매출")) {
					list = sDao.getTotalSalesDaySearch(searchName);
					if (list.size() == 0) {// 값이 없으면
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 일일매출 정보 검색");
						alert.setHeaderText(searchName + " 날짜의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = sDao.getTotalSales();
						totalSalesList();
					}
				}
				if (search.equals("월간매출")) {
					list = sDao.getTotalSalesMonthSearch(searchName);
					if (list.size() == 0) {// 값이 없으면
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 일일매출 정보 검색");
						alert.setHeaderText(searchName + " 날짜의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = sDao.getTotalSales();
						totalSalesList();
					}
				}
				if (search.equals("연간매출")) {
					list = sDao.getTotalSalesYearSearch(searchName);
					if (list.size() == 0) {// 값이 없으면
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 일일매출 정보 검색");
						alert.setHeaderText(searchName + " 날짜의 정보가 없습니다");
						alert.setContentText("다시 검색하세요");
						alert.showAndWait();

						list = sDao.getTotalSales();
						totalSalesList();
					}
				}

				totalSalesDataList.removeAll(totalSalesDataList);

				int rowCount = list.size();
				for (int index = 0; index < rowCount; index++) {
					sVo = list.get(index);
					totalSalesDataList.add(sVo);
				}
				searchResult = true;
			}

			if (!searchResult) {
				txtTotalSalesSearch.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장현황 정보 검색");
				alert.setHeaderText(searchName + " 날짜가 리스트에 없습니다");
				alert.setContentText("다시 검색하세요");
				alert.showAndWait();
				totalSalesList();

			}
			
			btnTotalSales.setDisable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 매장현황 전체 정보 리스트
	public void totalSalesList() {
		try {
			SalesDAO mDao = new SalesDAO();
			SalesVO mVo = new SalesVO();
			ArrayList<SalesVO> list;
			list = mDao.getTotalSales();
			int rowCount = list.size();
			for (int index = 0; index < rowCount; index++) {
				mVo = list.get(index);
				totalSalesDataList.add(mVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
