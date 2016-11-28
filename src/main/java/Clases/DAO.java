/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author ronal
 */
public class DAO {
    private static Connection con;
  String url="jdbc:mysql://us-cdbr-iron-east-04.cleardb.net:3306/ad_c0fa135f852e7dd?user=b6c141d3953c89&password=0850ab00";
 

    public Connection getConexion() {
        //java.sql.Connection con = null;
        con=null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try {
                con = DriverManager.getConnection(url);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                
            }
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }  
     public String VerDisponibilidad() {
        
        Connection con = getConexion();
        String disponibilidad="";
        
        try {
            
            String strsql ="Select * from cubiculo where Disponibilidad='1'";
            PreparedStatement ps = con.prepareStatement(strsql);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                disponibilidad="Si hay disponibles";   
                
            }else{
                disponibilidad="No hay disponibles";   
                
            }
                
               
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return disponibilidad;
    }
     public String LoginAlumno(int u, String p) {
         System.out.println("LOGIN ALUMNOS OK");
        String ok = "";

        Connection con;
        con = getConexion();

        try {

            String strsql = "SELECT * FROM alumno where codigo ='" + u +"'";
            PreparedStatement pstm = con.prepareStatement(strsql);

            ResultSet rs = pstm.executeQuery();
            if (rs.next() ){   
            if (rs.getInt("codigo") == u && rs.getString("contraseña").equalsIgnoreCase(p)) {
                    System.out.println("USUARIO Y CONTRASEÑA CORRECTA");
                    ok = rs.getString("Nombre");
                }else{
                    System.out.println("USUARIO Y CONTRASEÑA INCORRECTA");
                    ok="Usuario no encontrado";
                }
            }
            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            ok = "Error Conexion";
        }
        return ok;
    }
     public int AsignarCubiculo() {
        
        Connection con = getConexion();
        int disponibilidad=1;
        
        try {
            
            String strsql ="Select  idCubiculo from cubiculo where disponibilidad='1'";
            PreparedStatement ps = con.prepareStatement(strsql);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                disponibilidad = rs.getInt("Idcubiculo");
                
            }else{
                disponibilidad=-1;   
                
            }
                
               
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
         System.out.println("asignar cubiculo ok!!!!");
        return disponibilidad;
    }

   
      public boolean reservarPC(int codigo, int pc){
        boolean ok=false;
        Connection con=getConexion();
        try{
            String strsql = "update pc set Disponibilidad=0, HoraInicio=current_time(), HoraFin=current_time()+Interval 1 hour where idPc="+pc;
            String sql="insert into reservapc (PC_idPC,alumno_codAlumno) values(?,?)";
            PreparedStatement pstm = con.prepareStatement(strsql);
            PreparedStatement pstm2 = con.prepareStatement(sql);
            pstm.executeUpdate();
            
            pstm2.setInt(1, pc);
            pstm2.setInt(2, codigo);
            pstm2.executeUpdate();
            pstm.close();
            pstm2.close();
            
            ok=true;
            con.close();
            
        }catch(Exception e){
            ok=false;
        }
        return ok;
    }
      
 
    

   
      public Date modificarDatos(int cod_al, String nombre) throws SQLException {
          Connection con;
          con = getConexion();
          PreparedStatement cnsltaReconocerLibro = con.prepareStatement("select * from libro where nombre='" + nombre + "'");
          ResultSet dato = cnsltaReconocerLibro.executeQuery();
          int codlibro = 0;
          while (dato.next()) {
              codlibro = dato.getInt("id");
          }
          System.out.println(codlibro);
          PreparedStatement cnsltaReconocerPedido = con.prepareStatement("select * from prestamolibro where codAlumno=" + cod_al + " and codlibro=" + codlibro);
          ResultSet pedido = cnsltaReconocerPedido.executeQuery();
          Date fechainicio = null;
          Date fechafin = null;
          int renovado = 0;
          Calendar calendar = Calendar.getInstance();
          while (pedido.next()) {
              fechainicio = pedido.getDate("fechaFin");
              System.out.println(fechainicio + "hola");
              renovado = pedido.getInt("renovado") + 1;
          }
          calendar.setTime(fechainicio);
          if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
              calendar.add(Calendar.DAY_OF_YEAR, 1);
          };

          fechainicio = new Date(calendar.getTime().getTime());
          calendar.add(Calendar.DAY_OF_YEAR, 3);
          if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
              calendar.add(Calendar.DAY_OF_WEEK, 1);
          };

          fechafin = new Date(calendar.getTime().getTime());
          PreparedStatement cnsltaModificar;
          cnsltaModificar = con.prepareStatement("update prestamolibro set fechaInicio = ?, fechaFin = ?, renovado = ? " + " where codAlumno = ? " + "and codlibro= ?");
          cnsltaModificar.setDate(1, fechainicio);
          cnsltaModificar.setDate(2, fechafin);
          cnsltaModificar.setInt(3, renovado);
          cnsltaModificar.setInt(4, cod_al);
          cnsltaModificar.setInt(5, codlibro);
          cnsltaModificar.executeUpdate();
          return fechafin;
      }

    
      public ArrayList<String> leerLibros(int id_al) throws SQLException {
          ArrayList<String> nombresLibros = new ArrayList();
          PreparedStatement cnsltaLeer;
          ResultSet datos;
          String qr = "";
          qr = "select lib.nombre, alu.nombre from libro lib \n"
                  + "inner join prestamolibro prest on prest.codLibro = lib.id \n"
                  + "inner join alumno alu on alu.codigo = prest.codAlumno \n"
                  + "where prest.codAlumno = " + id_al;
          Connection con;
          con = getConexion();
          cnsltaLeer = con.prepareStatement(qr);
          datos = cnsltaLeer.executeQuery();
          while (datos.next()) {
              nombresLibros.add(datos.getString("lib.nombre"));
          }
          return nombresLibros;
      }
      
      public ArrayList <Integer> listaPcDisponible() throws SQLException{
          ArrayList <Integer> listaPc= new ArrayList();
          PreparedStatement cnsltaLeer;
          ResultSet datos;
          String qr = "";
          qr = "select id from pc where ocupado = 0";
          Connection con;
          con = getConexion();
          cnsltaLeer = con.prepareStatement(qr);
          datos = cnsltaLeer.executeQuery();
          while (datos.next()) {
              listaPc.add(datos.getInt("id"));
          }
      return listaPc;
      }
      
      public StringTokenizer reservarPC(int id_pc, int cod_al, int cant_horas) throws SQLException{
          StringTokenizer hora_intervalo;
          
          PreparedStatement cnsltaModificar;
          ResultSet datos;
          String qr = ""; 
          Connection con;
          
          qr = "update pc set ocupado = ?, observacion = ? where id = " + id_pc;
          con = getConexion();
          cnsltaModificar = con.prepareStatement(qr);
          cnsltaModificar.setInt(1, 1);
          cnsltaModificar.setString(2, "Reservado por alumno de codigo - " + cod_al);
          cnsltaModificar.executeUpdate();
          
          Calendar calendario = new GregorianCalendar();
          int año = calendario.get(Calendar.YEAR);
          int mes = calendario.get(Calendar.MONTH);
          int dia = calendario.get(Calendar.DAY_OF_MONTH);
          int hora =calendario.get(Calendar.HOUR_OF_DAY);
          int minutos = calendario.get(Calendar.MINUTE) + 10;
          int segundos = calendario.get(Calendar.SECOND);
          
          String horaInicio = año + "-" + mes + "-" + dia + " " + hora + ":" + minutos + ":" + segundos;
          hora = hora + cant_horas;
          String horaFin = año + "-" + mes + "-" + dia + " " + hora + ":" + minutos + ":" + segundos;
          qr = "insert into reservapc(codPC, codAlumno, horaInicio, horaFin) " + 
                  "VALUES (" + id_pc + ", " + cod_al + ", '" + horaInicio + "', '" + horaFin + "')";     
          cnsltaModificar = con.prepareStatement(qr);
          cnsltaModificar.executeUpdate();
          
          hora_intervalo = new StringTokenizer(horaInicio + "/" + horaFin, "/");
          return hora_intervalo;
      }
}


