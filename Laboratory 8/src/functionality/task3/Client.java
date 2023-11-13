package task3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static ObjectInputStream in;
    static ObjectOutputStream out;

    static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Connection to the socket...");
        socket = new Socket("localhost", 8888);
        System.out.println("Connected!!!");
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        String commands = """
                1. Create new group
                2. Delete group
                3. Create student
                4. Delete student
                5. Update student's group
                6. Update student
                7. Get all students with group id
                8. Get all groups
                9. Get all students
                10. Exit
                """;

        System.out.println(commands);

        Integer operation = null;

        Scanner scanner = new Scanner(System.in);

        while (operation == null || operation != 10) {
            System.out.println("Input operation");
            operation = Integer.parseInt(scanner.nextLine());
            sendMessage(operation);

            Object obj;
            do {
                obj = in.readObject();
                if (!obj.equals("stop")) {

                    if (!(obj instanceof String)) {
                        System.out.println(obj);
                    } else {
                        System.out.println(obj);
                        String message = scanner.nextLine();
                        sendMessage(message);
                    }
                }

            } while (!obj.equals("stop"));
        }
        scanner.close();
        socket.close();
    }

    public static void sendMessage(Object msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean readMessage(Object msg) {
        if (msg == null) {
            return false;
        }
        System.out.println(msg);
        return true;
    }

}
