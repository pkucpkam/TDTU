//Dont modify this file

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class IDCardManagement {
    private ArrayList<IDCard> idCards;

    public IDCardManagement(String path) {
        idCards = new ArrayList<IDCard>();
        readIDCard(path);
    }

    public ArrayList<IDCard> getIDCards() {
        return this.idCards;
    }

    public void readIDCard(String path) {
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] components = line.split(",");
                idCards.add(new IDCard(Integer.parseInt(components[0]), components[1], components[2], components[3],
                        components[4], Integer.parseInt(components[5])));
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
