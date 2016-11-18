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
import java.util.List;

/**
 *
 * @author ronal
 */
public class DAO {
    private static Connection con;
  String url="jdbc:mysql://localhost:3306/bd?user=root&password=root";
 

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
            
            String strsql ="Select * from cubiculo where disponibilidad='1'";
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

            String strsql = "SELECT * FROM alumno where codAlumno ='" + u +"'";
            PreparedStatement pstm = con.prepareStatement(strsql);

            ResultSet rs = pstm.executeQuery();
            if (rs.next() ){   
            if (rs.getInt("codAlumno") == u && rs.getString("Password").equalsIgnoreCase(p)) {
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
      public List<PC> getPCs(){
        List<PC> l=new ArrayList<>();
        Connection con=getConexion();
        PC p;
        try{
            String strsql = "SELECT * FROM pc where Disponibilidad=1";
            PreparedStatement pstm = con.prepareStatement(strsql);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                p=new PC();
                p.setId(rs.getInt("idPC"));
                p.setDispo(rs.getInt("Disponibilidad"));
                p.setFin(rs.getTime("HoraFin"));
                p.setInicio(rs.getTime("HoraInicio"));
                p.setUbicacion(rs.getString("Ubicación"));
                l.add(p);
            }
            rs.close();
            pstm.close();
            con.close();
        }catch(Exception e){
            l.add(null);
        }                
        
        return l;
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
      
      public List<PC> getTodosPCs(){
        List<PC> l=new ArrayList<>();
        Connection con=getConexion();
        PC p;
        try{
            String strsql = "SELECT * FROM pc";
            PreparedStatement pstm = con.prepareStatement(strsql);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                p=new PC();
                p.setId(rs.getInt("idPC"));
                p.setDispo(rs.getInt("Disponibilidad"));
                p.setFin(rs.getTime("HoraFin"));
                p.setInicio(rs.getTime("HoraInicio"));
                p.setUbicacion(rs.getString("Ubicación"));
                l.add(p);
            }
            rs.close();
            pstm.close();
            con.close();
        }catch(Exception e){
            l.add(null);
        }                
        
        return l;
    }
    

   
      public Date modificarDatos(int cod_al, String nombre) throws SQLException{ 
        PreparedStatement cnsltaReconocerLibro = con.prepareStatement("select * from libro where Nombre='" + nombre + "'");
        ResultSet dato = cnsltaReconocerLibro.executeQuery();
        int codlibro = 0;
        while (dato.next()){
           codlibro = dato.getInt("idLibro");
        }

        PreparedStatement cnsltaReconocerPedido = con.prepareStatement("select * from prestamoslibros where id_al=" + cod_al + " and Libro_idlibro=" + codlibro);
        ResultSet pedido = cnsltaReconocerPedido.executeQuery();
        Date fechainicio=null;
        Date fechafin = null;
        int renovado = 0;
        Calendar calendar = Calendar.getInstance();
        while (pedido.next()){
           fechainicio = pedido.getDate("FechaFin");
           renovado = pedido.getInt("Renovado")+1;
        }
        calendar.setTime(fechainicio);
        if (calendar.get(Calendar.DAY_OF_WEEK)==1){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        };
        
        fechainicio = new Date(calendar.getTime().getTime());
        calendar.add(Calendar.DAY_OF_YEAR,3);
        if (calendar.get(Calendar.DAY_OF_WEEK)==2){
            calendar.add(Calendar.DAY_OF_WEEK,1);
        };
 
        fechafin = new Date(calendar.getTime().getTime());
        PreparedStatement cnsltaModificar;
        cnsltaModificar = con.prepareStatement("update prestamoslibros set FechaInicio = ?,FechaFin = ?, Renovado = ?" + " where id_al = ? " + "and Libro_idlibro= ?");
        cnsltaModificar.setDate(1,fechainicio);
        cnsltaModificar.setDate(2,fechafin);
        cnsltaModificar.setInt(3,renovado);
        cnsltaModificar.setInt(4,cod_al);
        cnsltaModificar.setInt(5,codlibro);
        cnsltaModificar.executeUpdate();
        return fechafin;
    }
    
    public ResultSet leerLibros(int id_al) throws SQLException{
        
        
        PreparedStatement cnsltaLeer;
        ResultSet datos; 
        String qr = "";
        qr = "select lib.nombre, alu.nombre from libro lib \n" +
             "inner join prestamoslibros prest on prest.Libro_idLibro =lib.idLibro \n" +
             "inner join alumno alu on alu.codAlumno = prest.id_al \n" +
             "where prest.id_al =" + "20112449";
        cnsltaLeer= con.prepareStatement(qr);
        
        datos=cnsltaLeer.executeQuery();
        return datos;
    }
}


