package tockenexample;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Commodity[] commodity = new Commodity[3];

        commodity[0] = new Commodity();
        commodity[1] = new Commodity(29.0, "Video course Java");
        commodity[2] = new Commodity(39.0, "Video course Java Script", 2011, 11, 25);

        try {
            RandomAccessFile RAF = new RandomAccessFile("newnextbase.txt", "rw");
            Commodity.saveToFile(commodity, RAF);
            RAF.seek(0);

            Commodity[] commodities = Commodity.getWithFile(RAF);
            for (int i = 0; i < commodities.length; i++) {
                System.out.println(commodities[i].getPrice());
                System.out.println(commodities[i].getName());
                System.out.println(commodities[i].getDateRelease());
                System.out.println("------------------------------");
            }
            /*
            int n = 3;
            RAF.seek((n-1)*Commodity.LENGTH_RECORD);
            Commodity a = new Commodity();
            a.readData(RAF);
            System.out.println(a);
            */

            try {

                Commodity b = new Commodity();
                b.readRecord(RAF, 233);
                System.out.println(b);
            }
            catch (AbsenceRecord err){
                System.out.println(err.getMessage());
            }

            RAF.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
