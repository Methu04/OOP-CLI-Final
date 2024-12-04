package Classes;

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
            while (!Thread.currentThread().isInterrupted()) {
                String[] tickets = new String[releaseRate];
                for (int i = 0; i < releaseRate; i++) {
                    tickets[i] = "Ticket - " + vendorID + " - " + System.currentTimeMillis() + " - " + i;

                }
                ticketPool.addTickets(tickets);
                System.out.println("Vendor " + vendorID + " added " + releaseRate + " tickets.");

                Thread.sleep(1000);
            }
        }catch(InterruptedException e){
            System.out.println("Vendor "+vendorID+" interrupted.");

        }

    }

}
