import java.util.Scanner;

public class FrontEnd {
    public static void main(String[] args) {

        boolean run = true;

        while(run == true) {

            String[] array = {"reng", "uvct", "guhv"};

            System.out.println("Cattolog: ");
            for (int i = 0; i < array.length; i++) {
                System.out.println("     " + i + ". " + array[i]);
            }

            Scanner obj = new Scanner(System.in);
            System.out.println("Enter movie line number: ");

            int user_input = obj.nextInt();
            System.out.println(array[user_input]);

            Scanner obj2 = new Scanner(System.in);
            System.out.println("Do you want to exit (type yes or no): ");
            String answer = obj2.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                run = false;
            }
        }
    }
}