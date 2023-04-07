import com.digdes.school.JavaSchoolStarter;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String command = "";
        JavaSchoolStarter javaSchoolStarter = new JavaSchoolStarter();
        while (!command.equals("stop")){
            command = in.nextLine();
            javaSchoolStarter.execute(command);
        }
    }

}