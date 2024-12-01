package Classes;

import java.io.*;
import java.util.Scanner;

public class Configuration {
    public int totalTickets;
    public int releaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;

    Scanner input = new Scanner(System.in);
    public void inputtotalTickets() {
        System.out.println("Enter the total number of tickets you want to purchase: ");
        totalTickets = input.nextInt();
    }
    public void inputreleaseRate(){
        System.out.println("Enter the ticket release rate: ");
        releaseRate = input.nextInt();
    }
    public void inputcustomerRetrievalRate(){
        System.out.println("Enter the customer retrieval rate: ");
        customerRetrievalRate = input.nextInt();
    }
    public void inputmaxTicketCapacity(){
        System.out.println("Enter the customer maxTicketCapacity: ");
        maxTicketCapacity = input.nextInt();
    }



//
//        System.out.println("Enter the maximum ticket capacity: ");
//        maxTicketCapacity = input.nextInt();
//
//        System.out.println("Configuration setup completed.");
//        System.out.println(this);


    public void saveToFile(String filename){
        try(BufferedWriter write = new BufferedWriter(new FileWriter(filename))){
            write.write("TotalTickets= "+totalTickets+"\n");
            write.write("TicketReleaseRate= "+releaseRate+"\n");
            write.write("CustomerRetrievalRate= "+customerRetrievalRate+"\n");
            write.write("MaximumTicketCapacity= "+maxTicketCapacity+"\n");
            System.out.println("Configuration saved to file: "+filename);
        }catch(IOException e){
            System.out.println("An error occured!!!");
        }
    }
    public static Configuration loadFile(String filename){
        Configuration config = new Configuration();
        try(BufferedReader read = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = read.readLine()) != null){
                String[] parts = line.split("=");
                if(parts.length == 2){
                    switch(parts[0]){
                        case "TotalTickets":
                            config.totalTickets=Integer.parseInt(parts[1]);
                            break;
                        case "TicketReleaseRate":
                            config.releaseRate=Integer.parseInt(parts[1]);
                            break;
                        case "CustomerRetrievalRate":
                            config.customerRetrievalRate=Integer.parseInt(parts[1]);
                            break;
                        case "MaximumTicketCapacity":
                            config.maxTicketCapacity=Integer.parseInt(parts[1]);
                            break;
                        default:
                            System.out.println("Configuration parameter does not exist!!!");

                    }
                }
            }
            System.out.println("Configuration loaded from: "+filename);
        }catch(IOException e){
            System.out.println("An error occured!!!");
        }
        return config;
    }

    @Override
    public String toString(){
        return "Total tickets= "+ totalTickets+
                ", Ticket Release Rate= "+ releaseRate+
                ", Customer Retrieval Rate= "+ customerRetrievalRate+
                ", Maximum Ticket Capacity= "+ maxTicketCapacity;
    }
}
