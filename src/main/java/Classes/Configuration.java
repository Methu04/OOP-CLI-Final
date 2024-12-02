package Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class Configuration {
    public int totalTickets;
    public int releaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;

    private transient Scanner input = new Scanner(System.in);

    public void inputtotalTickets() {
        while(true){
            System.out.println("Enter the total number of tickets you want to purchase: ");
            totalTickets = input.nextInt();
            if (totalTickets>0) break;
            System.out.println("Total tickets should be greater than zero!!!");
        }

    }
    public void inputreleaseRate(){
        while(true){
            System.out.println("Enter the ticket release rate: ");
            releaseRate = input.nextInt();
            if (releaseRate>0) break;
            System.out.println("Enter a value greater than zero!!!");
        }

    }
    public void inputcustomerRetrievalRate(){
        while(true){
            System.out.println("Enter the customer retrieval rate: ");
            customerRetrievalRate = input.nextInt();
            if(customerRetrievalRate>0) break;
            System.out.println("Enter a value greater than zero!!!");
        }

    }
    public void inputmaxTicketCapacity(){
        while(true){
            System.out.println("Enter the customer maxTicketCapacity: ");
            maxTicketCapacity = input.nextInt();
            if(maxTicketCapacity>=totalTickets) break;
            System.out.println("Max ticket capacity must be equal or greater than total number of tickets!!!");
        }

    }




    public void saveToFile(String filename){
        try(Writer writer = new FileWriter(filename)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this,writer);
            System.out.println("Configuration is saved to "+filename);
        }catch(IOException e){
            System.out.println("Error saving to file: "+e.getMessage());
        }
    }
    public static Configuration loadFile(String filename){
        Configuration config = new Configuration();
        try(Reader reader = new FileReader(filename)){
            Gson gson = new Gson();
            return gson.fromJson(reader,Configuration.class);
        }catch(IOException e){
            System.out.println("An error occured!!!");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String toString(){
        return "Total tickets= "+ totalTickets+
                ", Ticket Release Rate= "+ releaseRate+
                ", Customer Retrieval Rate= "+ customerRetrievalRate+
                ", Maximum Ticket Capacity= "+ maxTicketCapacity;
    }
}
