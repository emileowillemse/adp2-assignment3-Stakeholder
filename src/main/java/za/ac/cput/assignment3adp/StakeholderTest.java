/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3adp;

/**
 *
 * @author emileo willemse 219275874
 *         Assignment 3
 *         9 June 2021
 */

public class StakeholderTest {
    

    
    public static void main(String[] args) {
        
        ReadStakeholderSer stk = new ReadStakeholderSer();
        
        stk.readFromFile();
        stk.convertToDate();
        stk.sortLists();
        
        
        System.out.println("===================================== Customers ====================================");
        System.out.println("");
        System.out.println(String.format("%s\t%-10s\t%-10s\t%-10s\t%-10s", "ID", "Name", "Surname", "Date of Birth", "Age")) ;
        System.out.println("");
        System.out.println("====================================================================================");
        stk.displayCustomerLists();
        System.out.println("");
        stk.rentService();
        System.out.println("\n");
        
        System.out.println("===================================== Supplier =====================================");
        System.out.println("");
        System.out.println(String.format("%-5s\t%-20s\t%-10s\t%-15s","ID","Name","Product Type","Description"));
        System.out.println("");
        System.out.println("====================================================================================");
        stk.displaySupplierList();
        
    }
    
}
