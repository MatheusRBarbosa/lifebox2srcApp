/*
 * Classe que faz manipulação e controle dos DAO's
 */
package Control;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathe
 */
class DaoController {
    /*Classe responsavel por controlar o tempo e a frequencia em que os dados
    são salvos*/
    TempDAO dao = new TempDAO();
    static int daoTemp = 0;
    
    public DaoController(String id){
        try {
            dao.createTable();
        } catch (SQLException ex) {
            Logger.getLogger(DaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void timecontrol(String tempC, String tempD, String id) throws SQLException {

        float daoTempC;
        float daoTempD;
        int temp = 60; // Valor que mede o temp de cada ciclio, 60 é equivalente à 3 mins
        /* A cada 3 mins salva as temperaturas atuais no banco de dados*/
        if (daoTemp == temp) {
            daoTempC = Float.parseFloat(tempC); // COnverter string para float
            daoTempD = Float.parseFloat(tempD);
            dao.registerTemp(daoTempC, daoTempD, id); // Funcao de query para o banco
            daoTemp = 0; // Resetar ciclo
        }
        daoTemp++;

    }
}
