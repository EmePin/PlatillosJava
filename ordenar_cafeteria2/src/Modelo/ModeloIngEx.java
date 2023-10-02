package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloIngEx extends AbstractTableModel {

    private List<Ingrediente> ingredientes;
    private String encabezados[] = {"Nombre de ingrediente", "Existencia", "Unidad de medida", "Cant. Requerida", "% Requerido", "Tiempo"};
    private Double[] solExis;
    private int[] tiempo;
    private double[] porCant;
    int b;
 
    public ModeloIngEx(List<Ingrediente> ing) {

        ingredientes = ing;
        solExis = new Double[ingredientes.size()];
        tiempo = new int[ingredientes.size()];
        porCant = new double[ingredientes.size()];

    }
    public void rellenar(){
        for(int a = 0; a < ingredientes.size(); a++){
            porCant [a] = 0.0;
            tiempo[a] = 0;
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
        porCant = new double[a];
        tiempo = new int[a];
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
                return solExis[r];
            case 4:
                return porCant[r];
            default: 
                return tiempo[r];    
        }

    }
    
    public void setTiempo(int a){
        b = a;
    }

    @Override
    public void setValueAt(Object o, int r, int c) {
        double valor = Double.parseDouble(o.toString());
        if (c == 3) {
            solExis[r] = valor;
            porCant[r] = (solExis[r] * 100) / ingredientes.get(r).getCantidad();
            if(b != 0 && ingredientes.get(r).getCantidad() > valor){
                tiempo[r] = b;
                //ingredientes.get(r).setCantidad(ingredientes.get(r).getCantidad()-valor);
                
            }else{
                tiempo[r] = 0;
            }
        }

    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return c == 3;
    }

}
