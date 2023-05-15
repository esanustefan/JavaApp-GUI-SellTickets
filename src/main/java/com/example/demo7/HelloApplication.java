package com.example.demo7;

import domain.Match;
import domain.Ticket;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.MatchDBRepo;
import repo.TicketDBRepo;
import repo.UserDBRepo;
import service.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        System.out.println(props);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        HelloController helloController = fxmlLoader.getController();
        helloController.setService(new Service(new UserDBRepo(props), new MatchDBRepo(props), new TicketDBRepo(props)));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {



//        UserDBRepo userRepo = new UserDBRepo(props);
//        userRepo.add(new User(3, "user1", "pass1"));
//        System.out.println("Toate userii din db");
//        for (User user : userRepo.findAll())
//            System.out.println(user);
//
//        MatchDBRepo matchRepo = new MatchDBRepo(props);
//        matchRepo.add(new Match(1, "echipa1", "echipa2", 100, 200, 150));
//        System.out.println("Toate meciurile din db");
//        for (Match match : matchRepo.findAll())
//            System.out.println(match);
//
//        TicketDBRepo ticketRepo = new TicketDBRepo(props);
//        ticketRepo.add(new Ticket(1, "client2", 4));
//        System.out.println("Toate biletele din db");
//        for (Ticket ticket : ticketRepo.findAll())
//            System.out.println(ticket);
        launch();
    }

}