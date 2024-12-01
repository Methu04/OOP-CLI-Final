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
                System.out.println("Customer "+customerID+" purchased "+ticket);
                if(ticket != null){
                    ticketsPurchased++;
                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException e){
            System.out.println("Customer "+customerID+" interrupted");
        }catch(Exception e){
            System.out.println("Customer "+customerID+" error: "+e.getMessage());
        }
    }

}
