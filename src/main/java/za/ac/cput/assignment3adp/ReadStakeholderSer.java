/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3adp;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emileo willemse 219275874
 *         Assignment 3
 *         9 June 2021
 */
public class ReadStakeholderSer {

    ArrayList <Customer> customerList = new ArrayList();
    ArrayList <Supplier> supplierList = new ArrayList();
    
    ArrayList<LocalDate> customerDobList = new ArrayList();
    
    Customer cust = new Customer();
    Supplier supp = new Supplier();
    
    int canRent = 0;
    int cantRent = 0;
        
    public void readFromFile() {    
    
        int count = 0;
        
        String path = "stakeholder.ser";
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            /*String firstLine = reader.readLine();
            reader.close();
            System.out.println("First line printed: " + firstLine);*/
            ObjectInputStream objectReader = new ObjectInputStream(new FileInputStream(path));
            
            Object array = new Array[10];
            
            while (reader.readLine() != null){
                
                Stakeholder stakeholder = (Stakeholder) objectReader.readObject();
                
                if (stakeholder.getClass() == cust.getClass()) {
                    
                    customerList.add((Customer)stakeholder);
                    System.out.println("Added Customer");

                } else if (stakeholder.getClass() == supp.getClass()) {
                    
                    supplierList.add((Supplier)stakeholder);
                    System.out.println("Added Supplier");
                    
                }
                     
                count++;
            }

            System.out.println("Number of stakeholders = " + count +"\n");

            objectReader.close();  
            
        } catch (FileNotFoundException e) {
            System.out.println("404, file not found");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReadStakeholderSer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    
    public void convertToDate(){  
        
        for (int i = 0; i < customerList.size(); i++) {
            
            String cDate = customerList.get(i).getDateOfBirth();        
            DateTimeFormatter converter = DateTimeFormatter.ofPattern("yyyy-MM-d");
  
            LocalDate convertedDate = LocalDate.parse(cDate, converter);
            
            customerDobList.add(convertedDate);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-YYYY");
           
            String date = formatter.format(convertedDate);
            
            
            customerList.get(i).setDateOfBirth(date);
            
        }

    }
    
    public int calcAge(LocalDate birthDate){
    
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears(); 
        return age;
        
    }
    
    public void rentService(){
        
        for (int i = 0; i < customerDobList.size(); i++) {
            
            if (calcAge(customerDobList.get(i)) > 21){
                canRent++;
            }
            else {
                cantRent++;
            }
        }
        
        //System.out.println("Number of customers that can rent: " + canRent);
        //System.out.println("Number of customers that cannot rent: " + cantRent);
    }
    
    public void displayCustomerLists(){

        for (int i = 0; i < customerList.size(); i++) {
            
            System.out.println(String.format("%s\t%-10s\t%-10s\t%-10s\t%-10s", 
                        customerList.get(i).getStHolderId(),
                        customerList.get(i).getFirstName(),
                        customerList.get(i).getSurName(),
                        customerList.get(i).getDateOfBirth(),
                        calcAge(customerDobList.get(i)))
                    
                /*System.out.print(customerList.get(i).getStHolderId() + 
                " " +
                customerList.get(i).getFirstName() +
                "\t" +
                customerList.get(i).getSurName() +
                "\t\t" +
                customerList.get(i).getDateOfBirth() +
                "\t\t" +*/ 
            );              
        }
    }
    
    public void displaySupplierList() {
        
        for (int i = 0; i < supplierList.size(); i++) {

            System.out.println(supplierList.get(i).toString());
            
                /*System.out.println(
                supplierList.get(i).getStHolderId() +
                //" "+
                supplierList.get(i).getName() +
                //"\t\t"+
                supplierList.get(i).getProductType() +
                //"\t\t\t"+
                supplierList.get(i).getProductDescription()
            );*/
        }
    }
    
    public static Comparator<Customer> compareCustomer = new Comparator<Customer>(){
    
            @Override
            public int compare(Customer a, Customer b){
            
                return a.getFirstName().toUpperCase().compareTo(b.getFirstName().toUpperCase());
            }
    
    };
    
    public static Comparator<Supplier> compareSupplier = new Comparator<Supplier>(){
    
            @Override
            public int compare(Supplier x, Supplier y){
            
                return x.getName().toUpperCase().compareTo(y.getName().toUpperCase());
            }
    
    };
    
    public void sortLists() {
    
        Collections.sort(customerList, ReadStakeholderSer.compareCustomer);
            
        Collections.sort(supplierList, ReadStakeholderSer.compareSupplier);
        
    }
    
    public void writeToCustomerFile(){
        
        try {
            
            FileWriter fw = new FileWriter("Customer.txt");
            BufferedWriter bw = new BufferedWriter(fw);
     
            bw.write(String.format(
                "================================ CUSTOMERS ==============================="+
                "\n"+
                "%s\t%-10s\t%-10s\t%-10s\t%-10s%n","ID", "Name", "Surname", "Date of Birth", "Age")+
                "\n"+
                "=========================================================================="+"\n");
                
            for (int i = 0; i < customerList.size(); i++){     
                        
                bw.write(String.format("%s\t%-10s\t%-10s\t%-10s\t%-10s%n", 
                        customerList.get(i).getStHolderId(),
                        customerList.get(i).getFirstName(),
                        customerList.get(i).getSurName(),
                        customerList.get(i).getDateOfBirth(),
                        calcAge(customerDobList.get(i)))
                );                              
            }
            
            bw.write(String.format("\n" + "Number of customers who can rent:  %s %nNumber of Customers who cannot rent:  %s", canRent, cantRent));
            
            System.out.println("customer file successfully created");
            bw.close();

        } catch(IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);         
        }
    }
    
    public void writeToSupplierFile(){
        try {
            
            FileWriter fw = new FileWriter("Supplier.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(String.format(
                "================================ SUPPLIERS ==============================="+
                "\n"+
                "%-5s\t%-20s\t%-10s\t%-15s","ID","Name","Product Type","Description")+
                "\n"+
                "=========================================================================="+"\n");
            
            for (int i = 0; i < supplierList.size(); i++){              
                
                bw.write(supplierList.get(i).toString()+"\n");
            }

            System.out.println("supplier file successfully created");
            bw.close();
        
        } catch(IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);
        }
    }
 
    
    public static void main(String[] args) {
        ReadStakeholderSer a = new ReadStakeholderSer();
        
        a.readFromFile();
        a.convertToDate();
        a.sortLists();
        a.rentService();
        
        /*
        System.out.println("=============================================== Customers ==============================================");
        System.out.println("");
        System.out.println(String.format("%s\t%-10s\t%-10s\t%-10s\t%-10s", "ID", "Name", "Surname", "Date of Birth", "Age")) ;
        System.out.println("");
        System.out.println("========================================================================================================");
        a.displayCustomerLists();
        System.out.println("");
        a.rentService();
        System.out.println("\n");
        
        System.out.println("=============================================== Supplier ===============================================");
        System.out.println("");
        System.out.println(String.format("%-5s\t%-20s\t%-10s\t%-15s","ID","Name","Product Type","Description"));
        System.out.println("");
        System.out.println("========================================================================================================");
        a.displaySupplierList();
        */
        
        a.writeToCustomerFile();
        a.writeToSupplierFile();
        
       
    }
    
}
