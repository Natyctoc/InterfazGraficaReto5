package co.edu.utp.misiontic2022.c2.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.utp.misiontic2022.c2.model.vo.ProyectoBancoVo;
import co.edu.utp.misiontic2022.c2.util.JDBCUtilities;

public class ProyectoBancoDao {
    
    public List<ProyectoBancoVo> listarProyectosPorBanco(String banco) throws SQLException {
        List<ProyectoBancoVo> lista = new ArrayList<>();

        var connection = JDBCUtilities.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            var query = " SELECT p.ID_Proyecto ID, p.Constructora, p.Ciudad , p.Clasificacion , t.Estrato ,"
            + " (l.Nombre || ' ' || l.Primer_Apellido || ' ' || l.Segundo_Apellido) AS LIDER"
            + " FROM Proyecto p"
            + " INNER JOIN Tipo t ON (t.ID_Tipo = p.ID_Tipo)"
            + " INNER JOIN Lider l ON (l.ID_Lider = p.ID_Lider)"
            + " WHERE p.Banco_Vinculado = ?"
            + " ORDER BY p.Fecha_Inicio DESC, p.Ciudad ASC, p.Constructora ;";

            statement = connection.prepareStatement(query);
            statement.setString(1, banco);
            result = statement.executeQuery();

            while (result.next()) {
                var vo = new ProyectoBancoVo();
                vo.setIdProyecto(result.getInt("ID"));
                vo.setConstructora(result.getString("Constructora"));
                vo.setCiudad(result.getString("Ciudad"));
                vo.setClasificacion(result.getString("Clasificacion"));
                vo.setEstrato(result.getInt("Estrato"));
                vo.setLider(result.getString("Lider"));
                lista.add(vo);
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return lista;
    } 
}
