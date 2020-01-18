/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import entities.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Luana
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        
        List<Product> list = new ArrayList<>();
        
        System.out.print("Enter file path: ");
        String sourceFileStr = sc.nextLine();
        
        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();
        
        boolean success = new File(sourceFolderStr + "\\out").mkdir();
        
        String targetFileStr = sourceFolderStr + "\\out\\summary.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader (sourceFileStr))) {
            
            String itemTxt = br.readLine();
            while (itemTxt != null) {
                
                String[] fields = itemTxt.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                
                list.add(new Product(name, price, quantity));
                
                itemTxt = br.readLine();
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
                
                for (Product item : list) {
                    bw.write(item.getName() + "," + String.format("%.2f", item.total()));
                    bw.newLine();
                }
                System.out.println(targetFileStr + " CREATED!");
            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
        sc.close();
    }
    
}
