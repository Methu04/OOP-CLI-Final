package Classes;

//import Gson library for JSON serialization and deserialization
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    private final int maxCapacity;
    public final int totalTickets;
    //a synchronized list of strings.
    //collections.synchronized makes the arraylist thread safety
    //it ensures only one thread can modify the list at a time
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());

    public TicketPool(int maxCapacity, int totalTickets){
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    //only one thread can be executed
    public synchronized void addTickets(String[] newTickets){
        //this loop runs until the addition of length of newTickets array and size of tickets list is greater than maximum capacity
        while (tickets.size()+newTickets.length > maxCapacity){
            try{
                //if new ticket exceeds capacity, the current thread enters the waiting state
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        //if the pool has enough capacity, new tickets are added to the tickets list
        Collections.addAll(tickets, newTickets);
        System.out.println("Tickets are available !!!"+" New tickets are added: "+newTickets.length+" Current ticket pool size: "+tickets.size());
        //the print statement is added to the logs list
        logs.add("Tickets are available !!!"+" New tickets are added: "+newTickets.length+" Current ticket pool size: "+tickets.size());
        //and saved to log.json file
        saveToLog("Log.json");
        //this is to wake up the threads that are waiting
        notifyAll();
    }

    //only one thread can be executed
    public synchronized String removeTicket(String customerID){
        //the loop runs until the size of the tickets list is less than totalTickets
        while(tickets.size()<totalTickets){
            //this loop runs until the tickets list is empty
            while(tickets.isEmpty()){
                try{
                    //if tickets are not available, the current thread enters the waiting state
                    System.out.println("Tickets are not available. Please wait....");
                    wait();
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Customer "+customerID+" interrupted.Exiting....");
                return null;
            }
            //once there are tickets available, it removes the first ticket from the list
            String ticket = tickets.remove(0);
            System.out.println("Customer: "+customerID+" purchased "+ticket);
            logs.add("Customer: "+customerID+" purchased "+ticket);
            System.out.println("No.of tickets remaining: "+tickets.size());
            logs.add("No.of tickets remaining: "+tickets.size());
            saveToLog("Log.json");
            //called to notify the waiting threads
            notifyAll();
            //return the ticket the customer purchased
            return ticket;

        }
        return "";
    }

    public void saveToLog(String filename){
        try(Writer writer = new FileWriter(filename)){
            //Created Gson object for Json serialization
            //Used GsonBuilder for pretty-printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //Converts the current object into a Json string and writes the Json representation
            gson.toJson(logs,writer);
        }catch(IOException e){
            System.out.println("Error saving to file: "+e.getMessage());
        }
    }
}
