package com.example.demo7;

import domain.Match;
import domain.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import repo.MatchDBRepo;
import repo.MatchRepo;
import service.Service;


import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class UserController {

    @FXML
    private Button FilterBtn;

    @FXML
    private Button RefreshBtn;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button UndoBtn;

    @FXML
    private TableView<Match> MatchesTW;

    @FXML
    private TableColumn<Integer, Match> availableTicketsColumn;

    @FXML
    private TableColumn<Match, String> soldOutMessageColumn;


    @FXML
    private TableColumn<Integer, Match> idColumn;

    @FXML
    private TableColumn<String, Match> team1Column;

    @FXML
    private TableColumn<String, Match> team2Column;

    @FXML
    private TableColumn<Integer, Match> ticketPriceColumn;


    private final ObservableList<Match> matchModel = FXCollections.observableArrayList();


    public Properties readProps() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (
                IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        return props;
    }

    private final MatchDBRepo matchRepo = new MatchDBRepo(readProps());

    private Service service;
    private User loggedInUser;

    public UserController() throws IOException{
    }
    public void setService(Service service) throws IOException{
        this.service = service;
        initModel();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void initialize(){
        initModel();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        team1Column.setCellValueFactory(new PropertyValueFactory<>("teamA"));
        team2Column.setCellValueFactory(new PropertyValueFactory<>("teamB"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        availableTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("soldSeats"));
        soldOutMessageColumn.setCellFactory(column -> {
            return new TableCell<Match, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && getTableRow() != null && getTableRow().getItem() != null) {
                        Match match = getTableRow().getItem();
                        if (match.getSoldSeats() == 0) {
                            setText("SOLD OUT");
                            setTextFill(Color.RED);
                        } else {
                            setText("AVAILABLE");
                            setTextFill(Color.GREEN);
                        }
                    } else {
                        setText(null);
                    }
                }
            };
        });
        MatchesTW.setItems(matchModel);
        MatchesTW.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Get the selected item from the TableView
                Match selectedMatch = MatchesTW.getSelectionModel().getSelectedItem();
                if (selectedMatch != null) {
                    try {
                        // Load the SELLTICKETS.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/SellTicket.fxml"));
                        Parent root = loader.load();

                        // Get the controller of the SELLTICKETS.fxml file
                        SellTicketController sellTicketsController = loader.getController();

                        // Pass the selected match to the SellTicketsController
                        sellTicketsController.setSelectedMatch(selectedMatch);
                        sellTicketsController.setService(service);
                        // Show the SELLTICKETS.fxml file in a new stage
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 600, 400));
                        stage.setTitle("Sell Tickets");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initModel(){
        List<Match> matches = new ArrayList<>();
        for(Match match : matchRepo.findAll()){
            matches.add(match);
            //matchModel.add(match);
        }
        matchModel.setAll(matches);
        //setSoldOutMessageColumn();
//model---------------------------------------------------
//        sectiiTableView.setVisible(true);
//        List<Sectie> sectii = new ArrayList<>();
//
//        for (Sectie sectie : service.getAllSectie()) {
//            sectii.add(sectie);
//        }
//        //aici trebuie numele atributelor date in clasa
//        sectieModel.setAll(sectii);
    }

    public void onClickLogOut(ActionEvent actionEvent) throws IOException {
        this.loggedInUser = null;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/hello-view.fxml"));
//        Parent root = loader.load();
//        HelloController helloController = loader.getController();
//        helloController.setService(service);
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root, 600, 400));
//        stage.setTitle("Hello!");
//        stage.show();
        Stage thisStage = (Stage) LogOutBtn.getScene().getWindow();
        thisStage.close();
    }
//    public void refreshPage() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/user-view.fxml"));
//        Parent root = loader.getRoot();
//        UserController userController = loader.getController();
//        userController.setService(service);
//        userController.setLoggedInUser(loggedInUser);
//        Stage stage = (Stage) RefreshBtn.getScene().getWindow();
//        stage.setScene(new Scene(root, 600, 400));
//        stage.setTitle("Hello!");
//        stage.show();
//    }


    public void refreshPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/MatchesUI.fxml"));
        Parent root = loader.load();
        UserController userController = loader.getController();
        userController.setService(service);
        userController.setLoggedInUser(loggedInUser);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Hello, " + loggedInUser.getUsername() + "");
        stage.show();
        Stage thisStage = (Stage) RefreshBtn.getScene().getWindow();
        thisStage.close();
    }

    public void onClickRefresh(ActionEvent actionEvent) throws IOException {
        refreshPage();
    }


    @FXML
    private void onClickFilter(ActionEvent event) {
        // Filter the matches to only show matches with available tickets
        List<Match> filteredMatches = matchModel.stream()
                .filter(match -> match.getSoldSeats() > 0)
                .sorted(Comparator.comparingInt(Match::getSoldSeats).reversed())
                .collect(Collectors.toList());

        // Update the TableView with the filtered and sorted matches
        matchModel.setAll(filteredMatches);

    }

    public void undoFilter(ActionEvent actionEvent) {
        // Clear the current filter on the TableView
        MatchesTW.getItems().clear();
        // Reload the original data in the matchModel
        initModel();
        // Apply the cell factory for the soldOutMessageColumn
        soldOutMessageColumn.setCellFactory(column -> {
            return new TableCell<Match, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && getTableRow() != null && getTableRow().getItem() != null) {
                        Match match = getTableRow().getItem();
                        if (match.getSoldSeats() == 0) {
                            setText("SOLD OUT");
                            setTextFill(Color.RED);
                        } else {
                            setText("AVAILABLE");
                            setTextFill(Color.GREEN);
                        }
                    } else {
                        setText(null);
                    }
                }
            };
        });
    }

//    //open SellTickets.fxml when clicking on a table row (match)
//    public void onClickRow(MouseEvent mouseEvent) throws IOException {
//        if (mouseEvent.equals(2)) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo7/SellTickets.fxml"));
//            Parent root = loader.load();
//            SellTicketController sellTicketsController = loader.getController();
//            sellTicketsController.setService(service);
////            sellTicketsController.setLoggedInUser(loggedInUser);
//            sellTicketsController.setSelectedMatch(MatchesTW.getSelectionModel().getSelectedItem());
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root, 600, 400));
//            stage.setTitle("Hello!");
//            stage.show();
//            Stage thisStage = (Stage) MatchesTW.getScene().getWindow();
//            thisStage.close();
//        }
//    }

}

