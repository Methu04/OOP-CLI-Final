import Classes.Configuration;
import Classes.Customer;
import Classes.TicketPool;
import Classes.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketingApp {
    public static void main(String[] args){
        Configuration config = new Configuration();

        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to create a new configuration file?(yes/no): ");
        String choice = input.next().toLowerCase();
        if(choice.equals("yes")){
            config = new Configuration();
            config.inputtotalTickets();
            config.inputreleaseRate();
            config.inputcustomerRetrievalRate();
            config.inputmaxTicketCapacity();
            config.saveToFile("config.json");
        }else if(choice.equals("no")){
            config = Configuration.loadFile("config.json");
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
        TicketPool ticketPool = new TicketPool(config.maxTicketCapacity);
        List<Thread> threads = new ArrayList<>();

        int noOfVendors = 10;
        for (int i = 1; i <= noOfVendors; i++) {
            threads.add(new Thread(new Vendor("Vendor"+i,ticketPool,config.releaseRate)));
        }
        int noOfCustomers = 10;
        for (int i = 1; i <= noOfCustomers; i++) {
            threads.add(new Thread(new Customer("Customer"+i,ticketPool,config.totalTickets)));
        }
        threads.forEach(Thread::start);

        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        threads.forEach(Thread::interrupt);


    }

}
