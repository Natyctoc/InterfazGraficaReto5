package co.edu.utp.misiontic2022.c2.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.utp.misiontic2022.c2.model.vo.PagadoPorProyectoVo;
import co.edu.utp.misiontic2022.c2.util.JDBCUtilities;

public class PagadoPorProyectoDao {
    
    public List<PagadoPorProyectoVo> listarPagadoPorProyecto(Double limiteInferior) throws SQLException {
        List<PagadoPorProyectoVo> lista = new ArrayList<>();

        var connection = JDBCUtilities.getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            var query = " SELECT p.ID_Proyecto ID, SUM(c.Cantidad*mc.Precio_Unidad) AS VALOR"
            + " FROM Proyecto p"
            + " INNER JOIN Compra c ON c.ID_Proyecto = p.ID_Proyecto"
            + " INNER JOIN MaterialConstruccion mc ON mc.ID_MaterialConstruccion = c.ID_MaterialConstruccion"
            + " WHERE c.Pagado = 'Si'"
            + " GROUP BY p.ID_Proyecto"
            + " HAVING SUM(c.Cantidad*mc.Precio_Unidad) > ?"
            + " ORDER BY VALOR DESC;";

            statement = connection.prepareStatement(query);
            statement.setDouble(1, limiteInferior);
            result = statement.executeQuery();

            while (result.next()) {
                var vo = new PagadoPorProyectoVo();
                vo.setId(result.getInt("ID"));
                vo.setValor(result.getDouble("VALOR"));
                lista.add(vo);
            }
        }finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
        }
        return lista;
    }
}
