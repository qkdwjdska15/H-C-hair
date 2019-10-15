package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {
@FXML
private TabPane mainPane;
@FXML
private Tab customer;
@FXML
private Tab designer;
@FXML
private Tab product;
@FXML
private Tab reservation;
@FXML
private Tab sales;
@FXML
private Tab totalsales;

@FXML
private CustomerController customerTabController;
@FXML
private DesignerController designerTabController;
@FXML
private ProductController productTabController;
@FXML
private ReservationController reservationTabController;
@FXML
private SalesController salesTabController;
@FXML
private TotalSalesController totalSalesController;

//페이지 이동시 새로운 값 적용시키기위한 새로고침

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
		mainPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				
				if(newValue==designer) {
					try {
						designerTabController.designerDataList.removeAll(designerTabController.designerDataList);
						designerTabController.designerTotalList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(newValue==reservation) {
					try {
						reservationTabController.reservationDataList.removeAll(reservationTabController.reservationDataList);
						reservationTabController.customerDataList.removeAll(reservationTabController.customerDataList);
						reservationTabController.reservationTotalList();
						reservationTabController.customerTotalList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(newValue==sales) {
					try {
						salesTabController.SalesDataList.removeAll(salesTabController.SalesDataList);
						salesTabController.reservationDataList.removeAll(salesTabController.reservationDataList);
						salesTabController.reservationTotalList();
						salesTabController.salesTotalList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(newValue ==totalsales) {
					try {
						totalSalesController.totalSalesDataList.removeAll(totalSalesController.totalSalesDataList);
						totalSalesController.totalSalesList();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
