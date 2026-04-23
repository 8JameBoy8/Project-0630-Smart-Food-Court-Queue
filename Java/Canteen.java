
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Canteen {
    private int canteenID;
    private String canteenName;
    private  List<Table> tables;
    private  List<Store> stores;
    private Map<DayOfWeek, OpeningHours> openingHoursMap = new HashMap<>();

    public Canteen(int canteenID, String canteenName) {
        this.canteenID = canteenID;
        this.canteenName = canteenName;
        this.tables = new ArrayList<>();
        this.stores = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void addStore(Store store) {
        stores.add(store);
    }

    public void setOpeningHours(DayOfWeek day, OpeningHours hours) {
        openingHoursMap.put(day, hours);
    }

    public boolean isOpen() {
         DayOfWeek today = LocalDate.now().getDayOfWeek();

         OpeningHours hours = openingHoursMap.get(today);

         if (hours == null) {
            return false; // ไม่มีข้อมูล = ปิด
         }

         return hours.isOpenNow();
    }

    //ตั้งค่าเวลาเปิดวันปกติทีเดียว
    public void setWeekdayHours(OpeningHours hours) {
    for (DayOfWeek day : DayOfWeek.values()) {
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            openingHoursMap.put(day, hours);
             }
        }
    }

    public int getCanteenID() {return  canteenID; }
    public List<Table> getTableNum() {return tables;}
    public List<Store> getStoreNum() {return stores;}

    
 }