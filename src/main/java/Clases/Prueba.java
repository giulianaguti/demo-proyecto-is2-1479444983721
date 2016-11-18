/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author ronal
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("hola");
        DAO dao = new DAO();
       // dao.VerDisponibilidad(201);
       int ok=0;
//        System.out.println(dao.VerDisponibilidad(202));

        //String ok = dao.LoginAlumno(20121818, "123");
         ok=dao.AsignarCubiculo();
        
        System.out.println(ok);
    }
    
}
