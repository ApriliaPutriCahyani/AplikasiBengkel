/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibengkelaprilia;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Aprilia
 */
public class Koneksi {
    public Connection openConnect(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/UNIVERSITY_aplikasi_bengkel","root","");
            System.out.println("Success");
            return con;
        }catch(Exception e){
            System.out.println("Gagal :"+e.getMessage());
            return con;
        }
    }
}
