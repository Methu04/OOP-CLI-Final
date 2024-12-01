package Classes;

public class Vendor implements Runnable{
    private String vendorID;
    private TicketPool ticketPool;
    public int releaseRate;

    public Vendor(String vendorID, TicketPool ticketPool, int releaseRate){
        this.vendorID=vendorID;
        this.ticketPool=ticketPool;
        this.releaseRate=releaseRate;
    }
    @Override
    public void run(){
        try {
            String[] tickets = null;
            while (!Thread.currentThread().isInterrupted()) {
                tickets = new String[releaseRate];
                for (int i = 0; i < releaseRate; i++) {
                    tickets[i] = "Ticket - " + vendorID + " - " + System.currentTimeMillis() + " - " + i;

                }
            }
            ticketPool.addTickets(tickets);
            System.out.println("Vendor " + vendorID + " added " + releaseRate + " tickets.");
            Thread.sleep(1000);
        }catch(InterruptedException e){
            System.out.println("Vendor "+vendorID+" interrupted.");
        }catch(Exception e){
            System.out.println("Vendor "+vendorID+" error: "+e.getMessage());
        }

    }
}
