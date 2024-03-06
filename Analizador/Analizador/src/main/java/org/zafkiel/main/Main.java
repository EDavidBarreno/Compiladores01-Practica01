package org.zafkiel.main;

import org.zafkiel.frontend.HomePage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //LLamamos a Home Page
        HomePage homePage = new HomePage();
        homePage.setContentPane(homePage.panelHomePage);
        homePage.setExtendedState(JFrame.MAXIMIZED_BOTH);
        homePage.setUndecorated(true);
        homePage.setVisible(true);
    }
}
