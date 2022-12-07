/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otlob;

/**
 *
 * @author hesha_000
 */
public class OTLOB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Order o=new Order();
       
        double x =o.Buy(o);
        System.out.print(x);
    }
    
}
