package Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    public int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }
    public synchronized void addTickets(String[] newTickets) throws Exception{
        if (tickets.size()+newTickets.length>maxTicketCapacity){
            throw new Exception("Ticket pool exceeds maximum capacity.");
        }
        Collections.addAll(tickets, newTickets);
        System.out.println("Tickets are available");
        System.out.println("New tickets are added: "+newTickets.length);
        System.out.println("Current ticket pool size: "+tickets.size());
        notifyAll();
    }
    public synchronized String removeTicket() throws Exception{
        while(tickets.isEmpty()){
            System.out.println("Tickets are not available. Please wait....");
            wait();
        }
        String ticket = tickets.remove(0);
        System.out.println("Customer: "+customerID+" purchased "+ticket);
        System.out.println("No.of tickets remaining: "+tickets.size());
        notifyAll();
        return ticket;
    }
}
