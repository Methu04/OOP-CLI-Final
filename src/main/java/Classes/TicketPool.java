package Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    private final int maxCapacity;
    public final int totalTickets;

    public TicketPool(int maxCapacity, int totalTickets){
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }
    public synchronized void addTickets(String[] newTickets){
        while (tickets.size()+newTickets.length > maxCapacity){
            try{
                //System.out.println("Ticket pool exceeds maximum capacity.");
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        Collections.addAll(tickets, newTickets);
        System.out.println("Tickets are available !!!"+"New tickets are added: "+newTickets.length+"Current ticket pool size: "+tickets.size());
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
            System.out.println("No.of tickets remaining: "+tickets.size());
            notifyAll();
            return ticket;

        }
        return "";
    }
}
