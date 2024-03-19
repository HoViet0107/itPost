package assets;

public class p {
    static void fun() throws IllegalAccessException, NullPointerException {
        System.out.println("Inside fun(). ");
        throw new NullPointerException("demo");
    }

    public static void main(String args[]) {
        try {
            fun();
        } catch (NullPointerException e) {
            System.out.println("null caught in main.");
        } catch (IllegalAccessException e) {
            System.out.println("ill caught in main.");
        }
    }
}
