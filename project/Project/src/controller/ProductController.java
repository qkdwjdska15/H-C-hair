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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CustomerVO;
import model.ProductVO;

public class ProductController implements Initializable {
	@FXML
	private ComboBox<String> cbx_pkind;// 분류 콤보박스(컷트,펌....)
	@FXML
	private TextField txtPCode;// 상품 코드 텍스트필드
	@FXML
	private TextField txtPName;// 상품 이름 텍스트필드
	@FXML
	private TextField txtPPrice;// 상품 가격 텍스트필드

	@FXML
	private Button btnProductJoin;// 상품 등록 버튼
	@FXML
	private Button btnProductInit;// 상품 초기화 버튼
	@FXML
	private Button btnProductUpdate;// 상품 수정 버튼
	@FXML
	private Button btnProductDelete;// 상품 삭제 버튼

	@FXML
	private Button btnPALL;// 테이블 뷰 전체 상품 목록 버튼
	@FXML
	private Button btnPCut;// 테이블 뷰 컷트 상품 목록 버튼
	@FXML
	private Button btnPFirm;// 테이블 뷰 펌 상품 목록 버튼
	@FXML
	private Button btnPDigitalSetting;// 테이블 뷰 디지털&셋팅 목록 버튼
	@FXML
	private Button btnPVolumeMagic;// 테이블 뷰 볼륨매직 목록 버튼
	@FXML
	private Button btnPDyeing;// 테이블 뷰 염색 목록 버튼
	@FXML
	private Button btnPClinic;// 테이블 뷰 크리닉 목록 버튼

	@FXML
	private TableView<ProductVO> productTotalView = new TableView<>();// 저장되어있는 상품을 보여주는 테이블 뷰

	public static ObservableList<ProductVO> productDataList = FXCollections.observableArrayList();
	ObservableList<ProductVO> selectProduct = null;// 테이블에서 선택한 정보 저장
	int selectedIndex; // 테이블에서 선택한 상품 정보 인덱스 저장.

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			// 상품 등록 초기화
			btnProductUpdate.setDisable(true);// 수정 버튼 비활성화
			btnProductDelete.setDisable(true);// 삭제 버튼 비활성화
			productTotalView.setEditable(false);// 테이블뷰 수정 금지
			txtPCode.setEditable(false);// 수정금지
			btnProductJoin.setDisable(true);// 등록 버튼 비활성화
			txtPName.setEditable(false);// 수정 금지
			txtPPrice.setEditable(false);// 수정금지

			// 상품 테이블뷰 컬럼 이름 설정
			TableColumn colP_no = new TableColumn("NO");
			colP_no.setPrefWidth(200);// 컬럼 넓이 설정
			colP_no.setStyle("-fx-alignment:CENTER");
			colP_no.setCellValueFactory(new PropertyValueFactory<>("p_no"));

			TableColumn colP_code = new TableColumn("상품 코드");
			colP_code.setPrefWidth(250);// 컬럼 넓이 설정
			colP_code.setStyle("-fx-alignment:CENTER");
			colP_code.setCellValueFactory(new PropertyValueFactory<>("p_code"));

			TableColumn colP_name = new TableColumn("상품명");
			colP_name.setPrefWidth(400);// 컬럼 넓이 설정
			colP_name.setStyle("-fx-alignment:CENTER");
			colP_name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

			TableColumn colP_price = new TableColumn("상품 가격");
			colP_price.setPrefWidth(250);// 컬럼 넓이 설정
			colP_price.setStyle("-fx-alignment:CENTER");
			colP_price.setCellValueFactory(new PropertyValueFactory<>("p_price"));

			productTotalView.setItems(productDataList);
			productTotalView.getColumns().addAll(colP_no, colP_code, colP_name, colP_price);

			productTotalList();
			// 상품분류 콤보박스 값 설정

			cbx_pkind.setItems(FXCollections.observableArrayList("컷트", "펌", "디지털,셋팅", "볼륨매직", "염색", "크리닉"));
			// 상품 분류 콤보박스 이벤트 핸들러
			cbx_pkind.setOnAction(event -> handlerCbx_pKindAction(event));

			// 상품 등록 버튼 이벤트 핸들러
			btnProductJoin.setOnAction(event -> handlerBtnProductJoin(event));
			// 상품 초기화 버튼 이벤트 핸들러
			btnProductInit.setOnAction(event -> handlerBtnProductInit(event));
			// 상품 수정 버튼 이벤트 핸들러
			btnProductUpdate.setOnAction(event -> handlerBtnProductUpdate(event));
			// 상품 삭제 버튼 이벤트 핸들러
			btnProductDelete.setOnAction(event -> handlerBtnProductDelete(event));

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
			//뷰 염색 목록 버튼 이벤트 핸들러
			btnPDyeing.setOnAction(event -> handlerBtnPDyeing(event));
			//뷰 크리닉 목록 버튼 이벤트 핸들러
			btnPClinic.setOnAction(event -> handlerBtnPClinic(event));

			// 테이블뷰 마우스 클릭 이벤트 핸들러
			productTotalView.setOnMouseClicked(event -> handlerProductTotalViewClickAction(event));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//뷰 크리닉 목록 버튼
	public void handlerBtnPClinic(ActionEvent event) {
		try {
			//ProductVO 인스턴스객체 생성
			ProductVO pvo = new ProductVO();
			//ProductDAO 인스턴스 객체 생성
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

	//뷰 염색 목록 버튼
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
			handlerBtnProductInit(event);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 삭제 버튼 이벤트
	public void handlerBtnProductDelete(ActionEvent event) {
		try {
			boolean sucess;

			ProductDAO pdao = new ProductDAO();
			sucess = pdao.getProductDelete(selectedIndex);

			if (sucess) {
				productDataList.removeAll(productDataList);
				productTotalList();

				handlerBtnProductInit(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 수정 버튼 이벤트
	public void handlerBtnProductUpdate(ActionEvent event) {
		try {
			boolean sucess;

			ProductDAO pdao = new ProductDAO();
			sucess = pdao.getProductUpdate(selectedIndex, txtPCode.getText().trim(), txtPName.getText().trim(),
					txtPPrice.getText().trim());

			if (sucess) {
				productDataList.removeAll(productDataList);
				productTotalList();

				handlerBtnProductInit(event);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 상품 테이블뷰 클릭 이벤트
	public void handlerProductTotalViewClickAction(MouseEvent event) {
		if (event.getClickCount() == 2) {// 테이블 원하는 행 더블 클릭시

			try {
				selectProduct = productTotalView.getSelectionModel().getSelectedItems();
				selectedIndex = selectProduct.get(0).getP_no();
				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				String selectedP_price = selectProduct.get(0).getP_price();

				if (selectedP_code != null) {

					// 앞글자가 A일때
					if (selectedP_code.substring(0, 1).equals("A")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("컷트");
					}
					// 앞글자가 B일때
					if (selectedP_code.substring(0, 1).equals("B")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("펌");
					}
					// 앞글자가 C일때
					if (selectedP_code.substring(0, 1).equals("C")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("디지털,셋팅");
					}
					// 앞글자가 D일때
					if (selectedP_code.substring(0, 1).equals("D")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("볼륨매직");
					}
					// 앞글자가 E일때
					if (selectedP_code.substring(0, 1).equals("E")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("염색");
					}
					// 앞글자가 F일때
					if (selectedP_code.substring(0, 1).equals("F")) {
						// 콤보박스 값 변경
						cbx_pkind.setValue("크리닉");
					}
				}
				txtPCode.setText(selectedP_code);
				txtPName.setText(selectedP_name);
				txtPPrice.setText(selectedP_price);

				// 버튼 설정
				btnProductJoin.setDisable(true);// 등록 버튼 비활성화
				btnProductUpdate.setDisable(false);// 수정 버튼 활성화
				btnProductDelete.setDisable(false);// 삭제 버튼 활성화
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 상품 초기화 버튼 이벤트
	public void handlerBtnProductInit(ActionEvent event) {
		// 칸 비우기
		cbx_pkind.getSelectionModel().clearSelection();
		txtPCode.clear();
		txtPName.clear();
		txtPPrice.clear();

		// 버튼 설정
		btnProductJoin.setDisable(true);// 비활성화
		btnProductUpdate.setDisable(true);// 비활성화
		btnProductDelete.setDisable(true);// 비활성화

		// 텍스트 필드 설정
		txtPName.setEditable(false);// 수정금지
		txtPCode.setEditable(false);// 수정금지
		txtPPrice.setEditable(false);// 수정금지

	}

	// 상품 등록 버튼 이벤트
	public void handlerBtnProductJoin(ActionEvent event) {
		try {
			btnProductJoin.setDisable(true);// 등록버튼 끄기
			productDataList.removeAll(productDataList);
			ProductVO pvo = null;
			ProductDAO pdao = null;

			pvo = new ProductVO(txtPCode.getText().trim(), txtPName.getText().trim(), txtPPrice.getText().trim());

			pdao = new ProductDAO();
			pdao.getProductRegiste(pvo);
			if (pdao != null) {
				productTotalList();
				handlerBtnProductInit(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("상품 등록");
			alert.setHeaderText("상품 등록 실패");
			alert.setContentText("상품 등록 실패.");
			alert.showAndWait();
		}
	}

	// 상품 분류 콤보박스 이벤트 핸들러
	public void handlerCbx_pKindAction(ActionEvent event) {

		try {
			btnProductJoin.setDisable(false);// 등록 버튼 활성화
			txtPName.setEditable(true);// 상품 이름 텍스트필드 활성화
			txtPPrice.setEditable(true);// 상품 가격 텍스트 필드 활성화
			String search = "";
			search = cbx_pkind.getSelectionModel().getSelectedItem();
			ProductDAO pDao = new ProductDAO();
			if (search != null) {

				if (cbx_pkind.getSelectionModel().getSelectedItem().equals("컷트")) {
					txtPCode.setText("A" + pDao.getProductCodeSet());
					System.out.println(pDao.getProductCodeSet());
				}
				if (search.equals("펌")) {
					txtPCode.setText("B" + pDao.getProductCodeSet());
				}
				if (search.equals("디지털,셋팅")) {
					txtPCode.setText("C" + pDao.getProductCodeSet());
				}
				if (search.equals("볼륨매직")) {
					txtPCode.setText("D" + pDao.getProductCodeSet());
				}
				if (search.equals("염색")) {
					txtPCode.setText("E" + pDao.getProductCodeSet());
				}
				if (search.equals("크리닉")) {
					txtPCode.setText("F" + pDao.getProductCodeSet());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 상품 전체 리스트
	public void productTotalList() throws Exception {
		productDataList.removeAll(productDataList);

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

	}

}
