import java.time.LocalTime;

public class OpeningHours {
    private LocalTime openTime;
    private LocalTime closeTime;

    public OpeningHours(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public boolean isOpenNow() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(openTime) && !now.isAfter(closeTime);
    }


    public LocalTime getOpenTime() { return openTime; }
    public LocalTime getCloseTime() { return closeTime; }
}