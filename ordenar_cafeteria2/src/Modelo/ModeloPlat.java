package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ModeloPlat extends AbstractTableModel{
    private List<Platillo> platillos;
    
    private String encabezados[] = {"Id platillo", "Nombre", "Tiempo", "Precio", "Porción", "Unidad de medida", "Existencia"};
   
    public ModeloPlat (List<Platillo> plat){
        
        platillos = plat;
        
        
    }
    
    @Override
    public String getColumnName(int c){
        return encabezados[c];
    }
    
    @Override
    public int getRowCount() {
        return platillos.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch(c){
            case 0: return platillos.get(r).getIdplatillo();
            case 1: return platillos.get(r).getNombre();
            case 2: return platillos.get(r).getTiempo();
            case 3: return platillos.get(r).getPrecio();
            case 4: return platillos.get(r).getPorcion();
            case 5: return platillos.get(r).getUnidaddm();
            default: return platillos.get(r).getExistencia();
        }
        
    }
   /* @Override
    public void setValueAt(Object o, int r, int c){
        double valor = Double.parseDouble(o.toString());
        if (c==4) ;
        
        
        
    }
    
    
    @Override
    public boolean isCellEditable(int r, int c){
        return c != 4;
    } */
    
}
