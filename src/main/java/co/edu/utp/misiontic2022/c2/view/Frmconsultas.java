package co.edu.utp.misiontic2022.c2.view;

import java.util.List;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import co.edu.utp.misiontic2022.c2.controller.ReportesController;
import co.edu.utp.misiontic2022.c2.model.vo.ProyectoBancoVo;
import co.edu.utp.misiontic2022.c2.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.model.vo.PagadoPorProyectoVo;

public class Frmconsultas extends JFrame {

    private ReportesController controller;
    private JTable tabla, tabla2, tabla3;
    
    
    public Frmconsultas() {
        controller = new ReportesController();
        initUI();
        setLocationRelativeTo(null);
    }

    public void initUI() {
        setTitle("Consultas Reto 5");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        var tbd = new JTabbedPane();
        getContentPane().add(tbd, BorderLayout.CENTER);

        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        tbd.addTab("Consulta por Bancos", panel);
        var panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Banco"));
        var txt = new JTextField(20);
        panelEntrada.add(txt);
        var botonConsulta= new JButton("Proyectos por Banco");
        botonConsulta.addActionListener(e -> consultarProyectos(txt.getText().trim()));
        panelEntrada.add(botonConsulta);
        panel.add(panelEntrada, BorderLayout.PAGE_START);
        tabla = new JTable();
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelEntrada, BorderLayout.PAGE_START);        

        var panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        tbd.addTab("Consulta limite inferior", panel2);
        var panelEntrada2 = new JPanel();
        panelEntrada2.add(new JLabel("Proyectos"));
        var txt2 = new JTextField(20);
        panelEntrada2.add(txt2);
        var botonConsulta2= new JButton("limite inferior");
        botonConsulta2.addActionListener(e -> consultarPagadoPorProyectos(Double.parseDouble(txt2.getText().trim())));
        panelEntrada2.add(botonConsulta2);
        panel2.add(panelEntrada2, BorderLayout.PAGE_START);
        tabla2 = new JTable();
        panel2.add(new JScrollPane(tabla2), BorderLayout.CENTER);
        panel2.add(panelEntrada2, BorderLayout.PAGE_START);
        
        var panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        tbd.addTab("Consulta Lideres rentables", panel3);
        var panelEntrada3 = new JPanel();
        var botonConsulta3= new JButton("Lider 10 líderes más rentables");
        botonConsulta3.addActionListener(e -> consultarComprasDeLider(botonConsulta3.getText().trim()));
        panelEntrada3.add(botonConsulta3);
        panel3.add(panelEntrada3, BorderLayout.PAGE_START);
        tabla3 = new JTable();
        panel3.add(new JScrollPane(tabla3), BorderLayout.CENTER);
        panel3.add(panelEntrada3, BorderLayout.PAGE_START);

    }

    private void consultarProyectos(String banco) {
        try {
            var lista = controller.listarProyectoBanco(banco);
            var modelo = new ProyectosTableModel();
            modelo.setData(lista);
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ProyectosTableModel extends AbstractTableModel {
        private List<ProyectoBancoVo> datos;
        

        public void setData(List<ProyectoBancoVo> datos) {
            this.datos = datos;
        }


        public String getColumnName(int column) {
            switch (column) {
                case 0 :
                    return "ID";
                case 1 :
                    return "Constructora";
                case 2 :
                    return "Ciudad";
                case 3 :
                    return "Clasificacion";
                case 4 :
                    return "Estrato";
                case 5 :
                    return "Lider";
            }
            return super.getColumnName(column);
        }

        public int getRowCount() {
            return datos.size();
        }

        public int getColumnCount(){
            return 5;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            var tabla = datos.get(rowIndex);
            switch (columnIndex) {
                case 0 :
                    return tabla.getIdProyecto();
                case 1 :
                    return tabla.getConstructora();
                case 2 :
                    return tabla.getCiudad();
                case 3 :
                    return tabla.getClasificacion();
                case 4 :
                    return tabla.getEstrato();
                case 5 :
                    return tabla.getLider();
            }
            return null;
        }
    }

    private void consultarPagadoPorProyectos(Double limiteInferior) {
        try {
            var lista2 = controller.listarPagadoProyecto(limiteInferior);
            var modelo2 = new LimiteTableModel();
            modelo2.setDatos1(lista2);
            tabla2.setModel(modelo2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LimiteTableModel extends AbstractTableModel {
        private List<PagadoPorProyectoVo> datos1;
        

        public void setDatos1(List<PagadoPorProyectoVo> datos1) {
            this.datos1 = datos1;
        }


        public String getColumnName(int column) {
            switch (column) {
                case 0 :
                    return "LIDER";
                case 1 :
                    return "VALOR";
                
            }
            return super.getColumnName(column);
        }

        public int getRowCount() {
            return datos1.size();
        }

        public int getColumnCount(){
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            var tabla2 = datos1.get(rowIndex);
            switch (columnIndex) {
                case 0 :
                    return tabla2.getId();
                case 1 :
                    return tabla2.getValor();
            }
            return null;
        }
    }


    private void consultarComprasDeLider(String lider) {
        try {
            var lista3 = controller.listarComprasDeLider();
            var modelo3 = new LiderTableModel();
            modelo3.setDatos2(lista3);
            tabla3.setModel(modelo3);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LiderTableModel extends AbstractTableModel {
        private List<ComprasDeLiderVo> datos2;
        

        public void setDatos2(List<ComprasDeLiderVo> datos2) {
            this.datos2 = datos2;
        }

        public String getColumnName(int column) {
            switch (column) {
                case 0 :
                    return "LIDER";
                case 1 :
                    return "VALOR";
                
            }
            return super.getColumnName(column);
        }

        public int getRowCount() {
            return datos2.size();
        }

        public int getColumnCount(){
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            var tabla3 = datos2.get(rowIndex);
            switch (columnIndex) {
                case 0 :
                    return tabla3.getLider();
                case 1 :
                    return tabla3.getValor();
            }
            return null;
        }
    }
}
