package co.edu.utp.misiontic2022.c2.view;

import java.sql.SQLException;

import co.edu.utp.misiontic2022.c2.controller.ReportesController;
import co.edu.utp.misiontic2022.c2.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.model.vo.PagadoPorProyectoVo;
import co.edu.utp.misiontic2022.c2.model.vo.ProyectoBancoVo;

public class ReportesView {

    private ReportesController reportesController;

    public ReportesView() {
        reportesController = new ReportesController();
    }

    private String repitaCaracter(Character caracter, Integer veces) {
        var respuesta = "";
        for (int i = 0; i < veces; i++) {
            respuesta += caracter;
        }
        return respuesta;
    }

    public void ProyectosFinanciadosPorBanco(String banco) {
        try {
            System.out.println(repitaCaracter('=', 36) + "LISTADO DE PROYECTOS POR BANCO" + repitaCaracter('=', 37));
            if (banco != null && !banco.isBlank()) {
                System.out.println(String.format("%3s %-25s %-20s %-15s %-7s %-30s", "ID", "CONSTRUCTORA", "CIUDAD",
                        "CLASIFICACION", "ESTRATO", "LIDER"));
                System.out.println(repitaCaracter('-', 105));
            }
            var lista = reportesController.listarProyectoBanco(banco);
            for (ProyectoBancoVo proyecto : lista) {
                System.out.printf("%3d %-25s %-20s %-15s %7d %-30s %n", proyecto.getIdProyecto(),
                        proyecto.getConstructora(), proyecto.getCiudad(), proyecto.getClasificacion(),
                        proyecto.getEstrato(), proyecto.getLider());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public void totalPagadoPorProyectosSuperioresALimite(Double limiteInferior) {
        try {
            System.out.println(repitaCaracter('=', 1) + " TOTAL PAGADO POR PROYECTO " + repitaCaracter('=', 1));
            if (limiteInferior != null) {
            System.out.println(String.format("%3s %15s", "ID", "VALOR "));
            System.out.println(repitaCaracter('-', 29));
            }
            var lista = reportesController.listarPagadoProyecto(limiteInferior);
            for (PagadoPorProyectoVo proyecto : lista) {
            System.out.printf("%3d %,15.1f %n", proyecto.getId(), proyecto.getValor());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }
    public void lideresQueMenosGastan() {
        try {
            System.out.println(repitaCaracter('=', 5) + " 10 LIDERES MENOS COMPRADORES "
        + repitaCaracter('=', 6));
        System.out.println(String.format("%-25s %15s", "LIDER", "VALOR "));
        System.out.println(repitaCaracter('-', 41));
        var lista = reportesController.listarComprasDeLider();
        for (ComprasDeLiderVo proyecto : lista) {
            System.out.printf("%-25s %,15.1f %n", proyecto.getLider(), proyecto.getValor());
        }
        
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();    
        } 
    }
}
