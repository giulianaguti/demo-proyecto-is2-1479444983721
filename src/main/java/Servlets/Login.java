/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Clases.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               System.out.println("Inicio Servlet OK");
        response.setContentType("text/html;charset=UTF-8");
        int usuario =   Integer.parseInt(request.getParameter("codigo"));
        String contrasena = request.getParameter("contrasena");
        System.out.println(usuario);
        System.out.println(contrasena);
        DAO dao = new DAO();
        
        String nombre = dao.LoginAlumno(usuario, contrasena);
        if(nombre != "Usuario no encontrado"){
        request.getSession().setAttribute("Nombre", nombre);
        request.getSession().setAttribute("Codigo", usuario);
 
        response.sendRedirect("bienvenida.jsp");    
        }else{
        response.sendRedirect("NoEncontrado.jsp");    
        }
        
    }


}
