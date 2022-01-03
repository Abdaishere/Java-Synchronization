import java.io.BufferedWriter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
public class Router {
    int N, TC;
    Device[] TClines;
    Semaphore semaphore;
    Device[] connectedDevices;

    public Router(int n, int TC, Device[] TClines) {
        N = n;
        this.TC = TC;
        this.TClines = TClines;
        this.semaphore = new Semaphore(N);
        this.connectedDevices = new Device[N];
    }
    public void writeToFile(String str) throws IOException
    {
    	BufferedWriter out = new BufferedWriter(
                new FileWriter("log.txt", true));
			out.write(System.getProperty("line.separator"));
			out.write(str);
			out.close();
    }
    public void connect() throws IOException {
    	String str = null;
        for (int i = 0; i < TC; i++) {
        
			if (semaphore.value <= 0) { 
				str= "(%s)(%s) arrived and waiting".formatted(TClines[i].name, TClines[i].type);
				writeToFile(str);
				System.out.println(str);}
            else {
            	str= "(%s)(%s) arrived".formatted(TClines[i].name, TClines[i].type);
            	writeToFile(str);
                System.out.println(str);
            }
            semaphore.Wait();
            TClines[i].setParent(this);
            Thread t = new Thread(TClines[i]);
            t.start();
        }
    }
    public void add(Device d){
        for (int j = 0; j < N; j++) {
            if (connectedDevices[j] == null) {
               d.connection = j + 1;
                connectedDevices[j] = d;
                String str1= "Connection %d : %s Occupied".formatted(this.connectedDevices[j].connection, this.connectedDevices[j].name);
                try {
                    writeToFile(str1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(str1);
                break;
            }
        }
    }
}
