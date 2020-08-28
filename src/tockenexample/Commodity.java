package tockenexample;

import sun.java2d.loops.GeneralRenderer;

import java.io.*;
import java.time.Year;
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

    public static void saveToFile(Commodity[] commodity, DataOutput outS) throws IOException {
        for (int i = 0; i < commodity.length; i++) {
        commodity[i].saveData(outS);
        }
    }

    public static Commodity[] getWithFile(RandomAccessFile raf) throws IOException {
        int numberOfRecords = (int) (raf.length()/LENGTH_RECORD);
        Commodity[] commodity = new Commodity[numberOfRecords];
        for (int i = 0; i < numberOfRecords; i++) {
        commodity[i] = new Commodity();
        commodity[i].readData(raf);
        }
        return commodity;

    }

    public void saveData(DataOutput outS) throws IOException {
        outS.writeDouble(this.price);
        StringBuffer stringB = new StringBuffer(Commodity.LENGTH_NAME);
        stringB.append(this.name);
        stringB.setLength(Commodity.LENGTH_NAME);

        outS.writeChars(stringB.toString());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.dateRelease);
        outS.writeInt(calendar.get(Calendar.YEAR));
        outS.writeInt((calendar.get(Calendar.MONTH)+1));
        outS.writeInt(calendar.get(Calendar.DAY_OF_MONTH));

    }

    public void readData(DataInput inS) throws IOException{
        this.price = inS.readDouble();

        StringBuffer tString = new StringBuffer(Commodity.LENGTH_NAME);
        for (int i = 0; i<Commodity.LENGTH_NAME; i++){
            char tCh = inS.readChar();
            if (tCh!='\0')
                tString.append(tCh);
        }

        this.name = tString.toString();

        int year = inS.readInt();
        int month = inS.readInt();
        int day = inS.readInt();

        GregorianCalendar calendar = new GregorianCalendar(year, month-1, day);
        this.dateRelease = calendar.getTime();

    }
    public void readRecord(RandomAccessFile RAF, int n) throws IOException, AbsenceRecord {
        if (n<=RAF.length()/Commodity.LENGTH_RECORD) {
            RAF.seek((n - 1) * Commodity.LENGTH_RECORD);
            this.readData(RAF);
        } else
            throw new AbsenceRecord("Unfortunately, there is no such record");
    }
    public static final int LENGTH_NAME = 30;
    public static final int LENGTH_RECORD = (Character.SIZE * LENGTH_NAME + Double.SIZE + 3*Integer.SIZE)/8;
    private double price; //8 bajtów
    private String name; // LENGTH_NAME *  = 60 bajtów
    private Date dateRelease; //4 bajty + 4 + 4 = 14 bajtów (jednej int = 4  bajty) = RAZEM 80 bajtów
}
