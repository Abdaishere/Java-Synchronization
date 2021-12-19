import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Device implements Runnable {
    String name;
    String type;
    Router parent;
    int connection;

    public void setParent(Router parent) {
        this.parent = parent;
    }

    public void writeToFile(String str) throws IOException {
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log.txt", true));
        out.write(System.getProperty("line.separator"));
        out.write(str);
        out.close();
    }

    void login() throws IOException {
        String str = "Connection%d : %s login".formatted(this.connection, this.name);
        System.out.println(str);
        writeToFile(str);
    }

    void onlineActivity() throws IOException {
        String str = "Connection %d: %s performs online activity".formatted(this.connection, this.name);
        writeToFile(str);
        System.out.println(str);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void logout() throws IOException {
        String str = "Connection %d : %s logout".formatted(connection, name);
        writeToFile(str);
        System.out.println(str);

    }

    public void run() {
        parent.add(this);
        try {
            login();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            onlineActivity();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            logout();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parent.semaphore.Signal();
        parent.connectedDevices[this.connection - 1] = null;
    }

    public Device(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
