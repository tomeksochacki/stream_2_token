package tockenexample;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Commodity[] commodity = new Commodity[3];

        commodity[0] = new Commodity();
        commodity[1] = new Commodity(29.0, "Video course Java");
        commodity[2] = new Commodity(39.0, "Video course Java Script", 2011, 11, 25);

        try {
        PrintWriter printWriter = new PrintWriter(new FileWriter("base.txt"));
        Commodity.saveToFile(commodity, printWriter);
        printWriter.close();

        BufferedReader reader = new BufferedReader(new FileReader("base.txt"));
        Commodity[] commodity2 = Commodity.getWithFile(reader);
        for (int i = 0; i<commodity2.length; i++)
            System.out.println(commodity2[i]);
        reader.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
