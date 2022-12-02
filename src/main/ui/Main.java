package ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

// initiates the GUI program
public class Main {
    public static void main(String[] args) {
        try {
            new PathToProgress();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}
