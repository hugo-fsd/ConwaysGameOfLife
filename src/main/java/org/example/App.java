package org.example;

import org.example.views.CLI;
import org.example.views.GUI;

public class App {
    public static void main(String[] args){
        /*new CLI();*/
        new GUI();
        GUI.main(args);
    }
}
