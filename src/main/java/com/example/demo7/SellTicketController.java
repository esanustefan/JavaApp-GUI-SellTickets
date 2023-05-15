package com.example.demo7;


import domain.Match;
import domain.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class SellTicketController {

    @FXML
    private Button BuyTicketBtn;

    @FXML
    private Button EscWindowBTN;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField NumberOfTicketsTF;

    @FXML
    private Label errorLabel;

    private Service service;
    private Match selectedMatch;

    public void setService(Service service) throws IOException {
        this.service = service;
    }

    public void setSelectedMatch(Match selectedMatch) {
        this.selectedMatch = selectedMatch;
    }

    @FXML
    private void onClickBuyTicket(ActionEvent actionEvent) {
        String name = NameTF.getText();
        String numberOfTickets = NumberOfTicketsTF.getText();
        if (name.equals("") || numberOfTickets.equals("")) {
            errorLabel.setText("Please fill in all the fields!");
            return;
        }
        try {
            int numberOfTicketsInt = Integer.parseInt(numberOfTickets);
            if (numberOfTicketsInt <= 0) {
                errorLabel.setText("Number of tickets must be a positive number!");
                return;
            }
            if(!service.checkAvailableSeats(selectedMatch, numberOfTicketsInt)){
                errorLabel.setText("Not enough seats available!");
                return;
            }
            int randint = random.randint(1, 100000);
            Ticket newTicket = new Ticket(randint, name, numberOfTicketsInt);
            service.addTicket(newTicket);
            errorLabel.setText("Ticket(s) bought successfully!");
        } catch (NumberFormatException e) {
            errorLabel.setText("Number of tickets must be a number!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //generate random number
    public static class random {
        public static int randint(int min, int max) {
            return (int) (Math.random() * (max - min + 1) + min);
        }
    }

    @FXML
    private void onClickEscWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) EscWindowBTN.getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        errorLabel.setText("");
    }

    public void initModel() {
        errorLabel.setText("");
    }


}

