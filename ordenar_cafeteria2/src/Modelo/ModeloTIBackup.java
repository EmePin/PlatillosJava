package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ModeloTIBackup extends AbstractTableModel{
    private List<Ingrediente> ingredientes;
    private String encabezados[] = {"Nombre de ingrediente", "Existencia", "Unidad de medida", "Tipo de ingrediente"};
    
    public ModeloTIBackup (List<Ingrediente> ing){
        ingredientes = ing;
    }
    
    @Override
    public String getColumnName(int c){
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

    @Override
    public Object getValueAt(int r, int c) {
        switch(c){
            case 0: return ingredientes.get(r).getNombre();
            case 1: return ingredientes.get(r).getCantidad();
            case 2: return ingredientes.get(r).getUnidad();
            default : return ingredientes.get(r).getTipoing();
        }
        
    }
    public void setValueAt(Object o, int r, int c){
        if(c == 1) ingredientes.get(r).setCantidad((Double) o);
    }
    
    public boolean isEditabled(int r, int c){
        return c == 1;
    }
    
}
