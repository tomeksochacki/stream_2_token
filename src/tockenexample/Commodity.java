package tockenexample;

import sun.java2d.loops.GeneralRenderer;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Commodity {

    public Commodity() {
        this.price = 0.0;
        this.name = " ";
        this.dateRelease = new GregorianCalendar().getTime();
    }

    public Commodity(double price, String name) {
        this();
        this.price = price;
        this.name = name;
    }

    public Commodity(double price, String name, int year, int month, int day) {
        this(price, name);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, day);
        this.dateRelease = gregorianCalendar.getTime();
    }

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public Date getDateRelease() {
        return this.dateRelease;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateRelease(int year, int month, int day) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, day);
        this.dateRelease = gregorianCalendar.getTime();
    }

    public void setDateRelease(Date date) {
        this.dateRelease = date;
    }

    public String toString() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(this.dateRelease);
        return "price: " + this.price + " zł " + "name: " + this.name + gregorianCalendar.get(Calendar.YEAR) + "year: " + (gregorianCalendar.get(Calendar.MONTH) + 1) + "month: " + gregorianCalendar.get(Calendar.DAY_OF_MONTH) + " day: ";
    }

    public static void saveToFile(Commodity[] commodity, PrintWriter outS) {
        outS.println(commodity.length);
        GregorianCalendar calendar = new GregorianCalendar();

        for (int i = 0; i < commodity.length; i++) {
            calendar.setTime(commodity[i].getDateRelease());
            outS.println(commodity[i].getPrice() + "|" + commodity[i].getName() + "|" + calendar.get(Calendar.YEAR) + "|" + (calendar.get(Calendar.MONTH) + 1) + "|" + calendar.get(Calendar.DAY_OF_MONTH));
        }

    }

    public static Commodity[] getWithFile(BufferedReader inS) throws IOException {
        int le = Integer.parseInt(inS.readLine());
        Commodity[] commodity = new Commodity[le];
        for (int i = 0; i < le; i++) {

            String line = inS.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line, "|");

            double price = Double.parseDouble(tokenizer.nextToken());
            String name = tokenizer.nextToken();
            int year = Integer.parseInt(tokenizer.nextToken());
            int month = Integer.parseInt(tokenizer.nextToken());
            int day = Integer.parseInt(tokenizer.nextToken());
            commodity[i] = new Commodity(price, name, year, month, day);
        }
        return commodity;
    }

    private double price;
    private String name;
    private Date dateRelease;

}
