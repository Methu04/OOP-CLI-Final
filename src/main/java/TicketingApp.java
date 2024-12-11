//imported the relevant classes
import Classes.Configuration;
import Classes.Customer;
import Classes.TicketPool;
import Classes.Vendor;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class TicketingApp {
    public static void main(String[] args){
        //created the config instance from Configuration class
        Configuration config = new Configuration();
        Scanner input;
        String choice = "";
        //loop will run until the user input correct inputs: "yes", "no'
        do{
            input = new Scanner(System.in);
            try {
                //asking the user to input either yes or no
                System.out.print("Do you want to create a new configuration file?(yes/no): ");
                choice = input.next().toLowerCase();
                //if the user input yes, a new configuration object will be created
                if(choice.equals("yes")){
                    config = new Configuration();
                    //methods are called to get the configuration
                    config.inputtotalTickets();
                    config.inputreleaseRate();
                    config.inputcustomerRetrievalRate();
                    config.inputmaxTicketCapacity();
                    //method is called to save the configuration to a file
                    config.saveToFile("config.json");
                    //if the user input "no"
                }else if(choice.equals("no")){
                    //the existing config.json file will be loaded
                    config = Configuration.loadFile("config.json");
                    //if the file is empty, again the program will ask the user to input the parameters
                    if(config == null){
                        config = new Configuration();
                        config.inputtotalTickets();
                        config.inputreleaseRate();
                        config.inputcustomerRetrievalRate();
                        config.inputmaxTicketCapacity();
                        config.saveToFile("config.json");
                    }
                }else{
                    System.out.println("Invalid input. exiting...");
                }
            }catch(InputMismatchException e){
                System.out.println("Error: "+ e.getMessage());
            }
        }while(!choice.equals("yes")&&!choice.equals("no"));

        //TicketPool instance is created by taking the values of maxTicketCapacity and totalTickets from the config
        TicketPool ticketPool = new TicketPool(config.maxTicketCapacity, config.totalTickets);
        //A list of thread objects is created to keep track of the threads
        List<Thread> threads = new ArrayList<>();

        int noOfVendors = 10;
        for (int i = 1; i <= noOfVendors; i++) {
            //a new thread is created with the vendor ID, ticketPool object and ticket releaseRate
            //this thread is added to the threads list
            threads.add(new Thread(new Vendor("Vendor"+i,ticketPool,config.releaseRate)));
        }

        int noOfCustomers = 10;
        for (int i = 1; i <= noOfCustomers; i++) {
            //a new thread is created with the customer ID, ticketPool object and totalTickets
            //this thread is added to the threads list
            threads.add(new Thread(new Customer("Customer"+i,ticketPool,config.totalTickets)));
        }

        //this iterates through the threads list and for each thread the start() method is called
        threads.forEach(Thread::start);

        //temporarily pause the execution for 10s
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            //stack trace of exception
            //throwable class - super class of exception
            e.printStackTrace();
        }

        //this iterates through the threads list and for each thread the interrupt() method is called
        threads.forEach(Thread::interrupt);

    }

}
