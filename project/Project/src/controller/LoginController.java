package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private TextField txtId;// 아이디 입력 텍스트필드
	@FXML
	private TextField txtPw;// 비밀번호 입력 텍스트 필드
	@FXML
	private Button btnAdmin;// 관리자 로그인 버트
	@FXML
	private Button btnDesigner;// 디자이너 로그인 버튼(아직 이용안할거임.)
	@FXML
	private Button btnCancel;// 닫기 버튼

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 아이디 입력에서 엔터키 이벤트 적용
		txtId.setOnKeyPressed(event -> handerTxtIdKeyPressed(event));
		// 패스워드 입력에서 엔터키 이벤트 적용
		txtPw.setOnKeyPressed(event -> handerTxtPwKeyPressed(event));
		// 관리자 로그인 버튼
		btnAdmin.setOnAction(event -> handlerBtnAdminAction(event));
		// 로그인창 닫기 이벤트
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
	}

	// 관리자 로그인 버튼 이벤트
	public void handlerBtnAdminAction(ActionEvent event) {
		login();// 로그인 메소드 호출
	}

	// 로그인 메소드
	public void login() {
		// LoginDAO 인스턴스화
		LoginDAO login = new LoginDAO();
		// 로그인 성공여부 변수
		boolean sucess = false;
		try {
			// LoginDAO에서 getLogin메소드에 아이디와 비밀번호를 공백제거후 넣어주고 성공여부를 반환받는다
			sucess = login.getLogin(txtId.getText().trim(), txtPw.getText().trim());

			if (sucess) {
				// 로그인 성공여부가 true일 경우
				try {
					// 메인뷰를 불러온다
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
					// 부모창을 login.fxml로 로드
					Parent mView = (Parent) loader.load();
					// Scene 객체 생성
					Scene scane = new Scene(mView);
					// Stage 객체 생성
					Stage mainMtage = new Stage();
					// 타이틀 설정
					mainMtage.setTitle("H&C");
					// 사이즈 재설정 불가
					mainMtage.setResizable(false);
					// 씬설정
					mainMtage.setScene(scane);
					// 그전에 있던창 oldStage로 저장
					Stage oldStage = (Stage) btnAdmin.getScene().getWindow();
					// 그전에 있던 창을 닫음
					oldStage.close();
					// 등록창 열기
					mainMtage.show();
				} catch (IOException e) {
					System.out.println("오류 : " + e);
				}
			} else {
				// 로그인 성공여부가 false일 경우
				// 경고창을 보여준다
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("아이디와 비밀번호 불일치");
				alert.setContentText("다음에는 주의하세요.");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
				// 입력한 아이디와 비밀번호를 지워준다
				txtId.clear();
				txtPw.clear();
				txtId.focusedProperty();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		

	}

	// 아이디 입력에서 엔터키 이벤트 적용
	public void handerTxtIdKeyPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 비밀번호창으로 포커스를 준다.
			txtPw.requestFocus();
		}
	}

	// 패스워드 입력에서 엔터키 이벤트 적용
	public void handerTxtPwKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			// 로그인 메소드 호출
			login();
		}

	}

	// 로그인창 닫기 이벤트
	public void handlerBtnCancelAction(ActionEvent event) {
		Platform.exit();

	}

}
