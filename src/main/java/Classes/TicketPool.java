package Classes;

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
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());

    public TicketPool(int maxCapacity, int totalTickets){
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }
    public synchronized void addTickets(String[] newTickets){
        while (tickets.size()+newTickets.length > maxCapacity){
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        Collections.addAll(tickets, newTickets);
        System.out.println("Tickets are available !!!"+" New tickets are added: "+newTickets.length+" Current ticket pool size: "+tickets.size());
        logs.add("Tickets are available !!!"+" New tickets are added: "+newTickets.length+" Current ticket pool size: "+tickets.size());
        saveToLog("Log.json");
        notifyAll();
    }
    public synchronized String removeTicket(String customerID){
        while(tickets.size()<totalTickets){
            while(tickets.isEmpty()){
                try{
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
            String ticket = tickets.remove(0);
            System.out.println("Customer: "+customerID+" purchased "+ticket);
            logs.add("Customer: "+customerID+" purchased "+ticket);
            System.out.println("No.of tickets remaining: "+tickets.size());
            logs.add("No.of tickets remaining: "+tickets.size());
            saveToLog("Log.json");
            notifyAll();
            return ticket;

        }
        return "";
    }

    public void saveToLog(String filename){
        try(Writer writer = new FileWriter(filename)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(logs,writer);
        }catch(IOException e){
            System.out.println("Error saving to file: "+e.getMessage());
        }
    }
}
