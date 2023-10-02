package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTI extends AbstractTableModel {

    private List<Ingrediente> ingredientes;
    private String encabezados[] = {"Nombre de ingrediente", "Existencia", "Unidad de medida", "Tipo de ingrediente", "Solicitar existencias"};
    private Double[] solExis;

    public ModeloTI(List<Ingrediente> ing) {

        ingredientes = ing;
        solExis = new Double[ingredientes.size()];
        llenado();
    }
    
    public void llenado(){
        for(int a = 0; a < solExis.length; a++){
            solExis[a] = 0.0;
        }
    }

    @Override
    public String getColumnName(int c) {
        return encabezados[c];
    }

    @Override
    public int getRowCount() {
        return ingredientes.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    public void actualizarTamaño(int a) {
        solExis = new Double[a];
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return ingredientes.get(r).getNombre();
            case 1:
                return ingredientes.get(r).getCantidad();
            case 2:
                return ingredientes.get(r).getUnidad();
            case 3:
                return ingredientes.get(r).getTipoing().getNombre();
            default:
                return solExis[r];
        }

    }

    @Override
    public void setValueAt(Object o, int r, int c) {
        double valor = Double.parseDouble(o.toString());
        if (c == 4 && valor >= 0.0 && ingredientes.get(r).getCantidad() >= valor) {
            solExis[r] = valor;
            ingredientes.get(r).setCantidad(ingredientes.get(r).getCantidad() - solExis[r]);
        }

    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return c == 4;
    }

}
