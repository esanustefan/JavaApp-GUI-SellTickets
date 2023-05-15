package domain;

public class Ticket extends Entity<Integer> {
    private String clientName;
    private int numberOfSeats;

    public Ticket(Integer id, String clientName, int numberOfSeats) {
        super(id);
        this.clientName = clientName;
        this.numberOfSeats = numberOfSeats;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "clientName='" + clientName + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
