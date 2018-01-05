/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author mathe
 */
class TempDAO {
/*Classe responsavel por guardar os metodos que são enviar os comandos ao banco de dados*/
    Connection con;
    PreparedStatement stmt;

    public void createTable() throws SQLException {
        con = ConnectionFactory.getConnection();
        stmt = null;

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS templog (temp1 float, temp2 float, time timestamp, id varchar(255))");
        stmt.executeUpdate();
    }

    public void registerTemp(float temp1, float temp2, String id) throws SQLException {
        con = ConnectionFactory.getConnection();
        stmt = null;
        /*A tabela que guarda as temperaturas precisa de 4 campos.
        temp1 e temp2 para guardar as temperaturas.
        time para guardar a hora que foi registrado
        id para guardar a identificacao do teste realizado*/
        try {
            stmt = con.prepareStatement("INSERT INTO templog (temp1, temp2, time, id) VALUES (?, ?, ?, ?)");
            stmt.setFloat(1, temp1);
            stmt.setFloat(2, temp2);
            Timestamp data = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(3, data);
            stmt.setString(4, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
}
