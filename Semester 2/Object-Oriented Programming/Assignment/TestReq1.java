public class TestReq1 {
    public static void main(String[] args) {
        IDCard id = new IDCard(100012, "Alex", "male", "22/07/2015", "Boston", 8112345);
        IDCard id1 = new IDCard(100013, "Mary", "female", "22/07/2010", "New York", 2111235);
        try {
            ConvenientCard cc = new ConvenientCard(id);
            System.out.println("Type of the card: " + cc.getType());
        } catch (CannotCreateCard e) {
            System.out.println(e);
        }
        try {
            ConvenientCard cc = new ConvenientCard(id1);
            System.out.println("Type of the card: " + cc.getType());
        } catch (CannotCreateCard e) {
            System.out.println(e);
        }
    }
}
