package Classes;

public class Customer implements Runnable{
    private final String customerID;
    private final TicketPool ticketPool;
    private final int purchaseTicket;


    public Customer(String customerID, TicketPool ticketPool, int purchaseTicket){
        this.customerID=customerID;
        this.ticketPool=ticketPool;
        this.purchaseTicket=purchaseTicket;
    }

    @Override
    public void run(){
        int ticketsPurchased = 0;
        try{
            while(!Thread.currentThread().isInterrupted() && ticketsPurchased<purchaseTicket){
                String ticket = ticketPool.removeTicket(customerID);
                if(ticket != null){
                    ticketsPurchased++;
                }
                Thread.sleep(500);
            }
            System.out.println("Customer "+customerID+" purchased "+ticketsPurchased);
        }catch(InterruptedException e){
            System.out.println("Customer "+customerID+" interrupted");
            Thread.currentThread().interrupt();
        }catch(Exception e){
            System.out.println("Customer "+customerID+" error: "+e.getMessage());
        }
    }


}
