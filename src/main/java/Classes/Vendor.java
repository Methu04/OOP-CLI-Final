package Classes;

//implemented runnable to executed by a thread.
public class Vendor implements Runnable{
    private final String vendorID;
    private final TicketPool ticketPool;
    public final int releaseRate;


    public Vendor(String vendorID, TicketPool ticketPool, int releaseRate){
        this.vendorID=vendorID;
        this.ticketPool= ticketPool;
        this.releaseRate=releaseRate;
    }
    @Override
    public void run(){
        try {
            //loop runs until the thread is interrupted
            while (!Thread.currentThread().isInterrupted()) {
                //Array of string objects is created and the length of the array is determined by the releaseRate
                String[] tickets = new String[releaseRate];
                //generating the ticket String
                for (int i = 0; i < releaseRate; i++) {
                    //currentTimeMillis() is a timestamp representing current time to make the ticket unique
                    tickets[i] = "Ticket - " + vendorID + " - " + System.currentTimeMillis() + " - " + i;

                }
                //the array of tickets is passed to the ticketPool using addTickets() method
                ticketPool.addTickets(tickets);
                System.out.println("Vendor " + vendorID + " added " + releaseRate + " tickets.");

                //after releasing the tickets and logging, the thread sleeps for 1s
                Thread.sleep(1000);
            }
        }catch(InterruptedException e){
            System.out.println("Vendor "+vendorID+" interrupted.");

        }

    }

}
