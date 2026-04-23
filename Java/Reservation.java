import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private Customer customer;
    private Table table;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;

    public Reservation(int id, Customer customer, Table table, int durationMinutes) {
        this.reservationId = id;
        this.customer = customer;
        this.table = table;
        this.startTime = LocalDateTime.now();
        this.endTime = startTime.plusMinutes(durationMinutes);
        this.isActive = true;

        table.setAvailable(false); // จองแล้ว
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endTime);
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        this.isActive = false;
        table.setAvailable(true);
    }

    public Table getTable() {
        return table;
    }
}