package Classes;

//implemented runnable to executed by a thread.
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
            //the loop runs until the customer purchase tickets equal to the number in purchaseTicket
            while(ticketsPurchased<purchaseTicket){
                //This calls the removeTicket() method from the ticketPool object
                //it passes the customerID
                String ticket = ticketPool.removeTicket(customerID);
                //if the ticket is not null, the ticketPurchased increases
                if(ticket != null){
                    ticketsPurchased++;
                }
                //each purchase will have a delay of 1 second between them.
                Thread.sleep(1000);

            }

        }catch(InterruptedException e){
            System.out.println("Customer "+customerID+" interrupted");
            //this is called to re interrupt the thread after catching the exception
            Thread.currentThread().interrupt();
        }
    }


}
