package Classes;

//import Gson library for JSON serialization and deserialization
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Configuration {
    public int totalTickets;
    public int releaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;

    //Used transient to prevent input from being serialized (It is not needed in JSON data)
    private transient Scanner input = new Scanner(System.in);

    public void inputtotalTickets() {
        // User input for the parameter totalTickets.
        //Used do-while to make sure the totalTicket is greater than 0
        do {
            try {
                System.out.print("Enter the total number of tickets you want to purchase: ");
                totalTickets = input.nextInt();
                if (totalTickets > 0) {
                    break;
                } else {
                    System.out.println("Total tickets should be greater than zero!!!");
                }
            //catches the error if the user input a letter
            } catch (InputMismatchException e) {
                System.out.println("Error: " + e.getMessage());
                input.next();
            }

        }while (totalTickets <= 0);
    }
    public void inputreleaseRate(){
        // User input for the parameter releaseRate.
        //Used do-while to make sure the releaseRate is greater than 0

        do {
            try {
                System.out.print("Enter the ticket release rate: ");
                releaseRate = input.nextInt();
                if (releaseRate>0) {
                    break;
                } else {
                    System.out.println("Enter a value greater than zero!!!");
                }
            //catches the error if the user input a letter
            } catch (InputMismatchException e) {
                System.out.println("Error: " + e.getMessage());
                input.next();
            }

        }while (releaseRate <= 0);


    }
    public void inputcustomerRetrievalRate(){
        // User input for the parameter customerRetrievalRate.
        //Used do-while to make sure the customerRetrievalRate is greater than 0

        do {
            try {
                System.out.print("Enter the customer retrieval rate: ");
                customerRetrievalRate = input.nextInt();
                if (customerRetrievalRate>0) {
                    break;
                } else {
                    System.out.println("Enter a value greater than zero!!!");
                }
            //catches the error if the user input a letter
            } catch (InputMismatchException e) {
                System.out.println("Error: " + e.getMessage());
                input.next();
            }

        }while (customerRetrievalRate <= 0);

    }
    public void inputmaxTicketCapacity(){
        // User input for the parameter maxTicketCapacity.
        //Used while(true) to make sure the maxTicketCapacity is totalTickets
        while(true){
            try {
                System.out.print("Enter the customer maximum capacity: ");
                maxTicketCapacity = input.nextInt();
                if (maxTicketCapacity>=totalTickets) {
                    return;
                } else {
                    System.out.println("Max ticket capacity must be equal or greater than total number of tickets!!!");
                }
            }catch (InputMismatchException e) {
                System.out.println("Error: " + e.getMessage());
                input.nextLine();
            }
        }
    }



    //this method saves the current configuration to the "config.json"
    public void saveToFile(String filename){
        try(Writer writer = new FileWriter(filename)){
            //Created Gson object for Json serialization
            //Used GsonBuilder for pretty-printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //Converts the current object into a Json string and writes the Json representation
            gson.toJson(this,writer);
            System.out.println("Configuration is saved to "+filename);
        //catches errors that occur in this file writing process
        }catch(IOException e){
            System.out.println("Error saving to file: "+e.getMessage());
        }
    }

    //This method returns a Configuration object
    public static Configuration loadFile(String filename){
        try(Reader reader = new FileReader(filename)){
            //created a new instance of Gson
            //Gson is used to deserialize the Json into Configuration object
            Gson gson = new Gson();
            //This converts the Json into an object of Configuration class
            return gson.fromJson(reader,Configuration.class);
        }catch(IOException e){
            System.out.println("An error occured!!!");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //@Override - this method overrides the toString() method from the object class
    @Override
    public String toString(){
        return "Total tickets= "+ totalTickets+
                ", Ticket Release Rate= "+ releaseRate+
                ", Customer Retrieval Rate= "+ customerRetrievalRate+
                ", Maximum Ticket Capacity= "+ maxTicketCapacity;
    }
}
