package Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }
    public synchronized void addTickets(String[] newTickets){
        while (tickets.size()+newTickets.length>maxCapacity){
            try{
                System.out.println("Ticket pool exceeds maximum capacity.");
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        Collections.addAll(tickets, newTickets);
        System.out.println("Tickets are available");
        System.out.println("New tickets are added: "+newTickets.length);
        System.out.println("Current ticket pool size: "+tickets.size());
        notifyAll();
    }
    public synchronized String removeTicket(String customerID){
        while(tickets.isEmpty()){
            try{
                System.out.println("Tickets are not available. Please wait....");
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return null;
            }

        }
        String ticket = tickets.remove(0);
        System.out.println("Customer: "+customerID+" purchased "+ticket);
        System.out.println("No.of tickets remaining: "+tickets.size());
        notifyAll();
        return ticket;
    }
}
