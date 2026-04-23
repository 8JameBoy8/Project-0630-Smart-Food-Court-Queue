// คลาสแม่ (Superclass) สำหรับผู้ใช้ และต้องเปลี่ยนเป็น abstract ด้วย
public abstract  class User {
    protected int userID;
    protected String userName;
    protected String email;
    protected String password;

    public User(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

   
    public int getuserID() {return  userID;}
    public String getName() { return userName; }
    public String getEmail() { return  email; }

    
}