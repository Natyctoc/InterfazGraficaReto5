package co.edu.utp.misiontic2022.c2.controller;

import java.sql.SQLException;
import java.util.List;

import co.edu.utp.misiontic2022.c2.model.dao.ComprasDeLiderDao;
import co.edu.utp.misiontic2022.c2.model.dao.PagadoPorProyectoDao;
import co.edu.utp.misiontic2022.c2.model.dao.ProyectoBancoDao;
import co.edu.utp.misiontic2022.c2.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.model.vo.PagadoPorProyectoVo;
import co.edu.utp.misiontic2022.c2.model.vo.ProyectoBancoVo;

public class ReportesController {
    private ProyectoBancoDao proyectoBancoDao;
    private PagadoPorProyectoDao pagadoPorProyectoDao;
    private ComprasDeLiderDao comprasDeLiderDao;

    public ReportesController() {
        proyectoBancoDao = new ProyectoBancoDao();
        pagadoPorProyectoDao = new PagadoPorProyectoDao();
        comprasDeLiderDao = new ComprasDeLiderDao();
    }

    public List<ProyectoBancoVo> listarProyectoBanco(String banco) throws SQLException {
        return proyectoBancoDao.listarProyectosPorBanco(banco);
    }

    public List<PagadoPorProyectoVo> listarPagadoProyecto(Double limiteInferior) throws SQLException {
        return pagadoPorProyectoDao.listarPagadoPorProyecto(limiteInferior);
    }
    public List<ComprasDeLiderVo> listarComprasDeLider() throws SQLException {
        return comprasDeLiderDao.listarComprasDeLider();
    }
}
