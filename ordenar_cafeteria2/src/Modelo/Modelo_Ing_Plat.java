/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author aimee
 */

public class Modelo_Ing_Plat extends AbstractTableModel{
    private List<Ingpla> IP;
    private String [] encabezados ={"IDIP","Existencia","IDING","IDPLA"}; 
    
    
    public Modelo_Ing_Plat(List<Ingpla> ip){
        this.IP = ip;
    }
    
    public String getColumnName(int c){
        return encabezados[c];
    }
    

    public int getRowCount() {
        return IP.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
    public void setValueAt(Object dato, int r, int c){
         
    }
   

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 ;//To change body of generated methods, choose Tools | Templates.
    }
    //public boolean isEditable(int r, int c){
      //  r
    //}

   
    public int getColumnCount() {
        return encabezados.length;
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return IP.get(rowIndex).getIdip(); 
            case 1: return IP.get(rowIndex).getExistencia();
            case 2: return IP.get(rowIndex).getIding().getNombre();//.getIding();
            case 3: return IP.get(rowIndex).getIdpla().getNombre();
            default:  return 0.0;
        }
        
        
//   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}