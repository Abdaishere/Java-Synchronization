import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
public class Network {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("What is the number of WI-FI Connections?");
 
        int N = input.nextInt();
        System.out.println("What is the number of devices Clients want to connect?");
        int TC = input.nextInt();
        
    
        input = new Scanner(System.in);
        Device[] TClines = new Device[TC];
        for (int i = 0; i < TC; i++) {
            String[] temp = input.nextLine().split(" ");
            TClines[i] = new Device(temp[0], temp[1]);
        }
        new Router(N, TC, TClines).connect();
    }
   
}
