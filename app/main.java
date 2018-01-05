package app;

import View.View;
import java.io.IOException;

public class main {
 
    public static void main(String[] args) throws IOException {
        /*Chama a view, que é o nucleo da aplicacao*/
        View v = new View();
        v.setVisible(true);
        v.setSize(500,400);
 
    }
 
}