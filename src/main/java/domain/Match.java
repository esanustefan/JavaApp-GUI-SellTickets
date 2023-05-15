package domain;

public class Match extends Entity<Integer> {
    private String teamA;
    private String teamB;
    private double ticketPrice;
//    private int totalSeats;
    private int soldSeats;

    public Match(Integer id, String teamA, String teamB, double ticketPrice, int soldSeats) {
        super(id);
        this.teamA = teamA;
        this.teamB = teamB;
        this.ticketPrice = ticketPrice;
//        this.totalSeats = totalSeats;
        this.soldSeats = soldSeats;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

//    public int getTotalSeats() {
//        return totalSeats;
//    }
//
//    public void setTotalSeats(int totalSeats) {
//        this.totalSeats = totalSeats;
//    }

    public int getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(int soldSeats) {
        this.soldSeats = soldSeats;
    }

    @Override
    public String toString() {
        return "Match{" +
                "teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", soldSeats=" + soldSeats +
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
