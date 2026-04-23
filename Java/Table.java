public class Table {
    private Canteen canteen;
    private int tableID;
    private int tableNo;
    private boolean isAvailable;

    public Table(Canteen canteenName, int tableID, int tableNo) {   
        this.canteen = canteenName;
        this.tableID = tableID;
        this.tableNo = tableNo;
        
    }

    public void setAvailable(boolean status) {
    this.isAvailable = status;
}

    public Canteen getCanteen() { return canteen;}
    public int getTableID() { return  tableID;}
    public int getTableNum() { return  tableNo; }
    public  boolean  isAvailable() { return  isAvailable;}
}