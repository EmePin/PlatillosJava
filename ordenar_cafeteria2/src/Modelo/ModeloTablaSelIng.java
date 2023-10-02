package Modelo;
//Se tienen que importar las librerias
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaSelIng extends AbstractTableModel {
    //Modelo de la tabla para selección de ingredientes donde se especificará la cantidad a utilizar y se calculará el porcentaje correspondiente a las existencia
private List<Ingrediente> datos;
private List<Integer> cantidad;
private List<Integer> tiempo; //NO se inicializa, se tiene que inicializar para que funcione 
private String encabezados[] = {"Nombre","Existencia","Uni.Med.","Cant.Requerida","% requerido"};
private Class tipoCol[] ={String.class, Integer.class, String.class, Integer.class,String.class,Integer.class};
private ArrayList <Integer> existeA = new ArrayList(); 

public ModeloTablaSelIng(List<Ingrediente> datos,List<Integer> cantidad, List<Integer> tiempo)
{   this.cantidad = cantidad;
    this.datos= datos;
    this.tiempo = tiempo;
     asignar();
} 
private void asignar()
{
  // for( Ingrediente ing : datos )
      // existeA.add((ing.getCantidad()));
}        
public void revertir()
{   
   for(int i=0; i< existeA.size(); i++ ){
       datos.get(i).setCantidad(existeA.get(i).doubleValue());
       tiempo.set(i,0);
       cantidad.set(i, 0);
   }       
   
}     
     @Override
     public Class getColumnClass(int c) { return tipoCol[c]; } 

    @Override
    public String getColumnName(int c) {
        return encabezados[c]; 
    }
     
    @Override
    public int getRowCount() {
       return datos.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch(c)
        { case 0: return datos.get(r).getNombre();
          case 1: return datos.get(r).getCantidad();
          case 2: return datos.get(r).getUnidad();
          case 3: return cantidad.get(r);
          case 4: return  cantidad.get(r)==0? 0 :100-(existeA.get(r)-cantidad.get(r))/(existeA.get(r))*100; //EL resultado del porcentaje sera entero
          default : return null; 
        }              
    }
    @Override
    public void setValueAt(Object valor, int r, int c) {
       if(c==3)
           {Integer cant = (Integer) valor; 
            if(cantidad.get(r)!=0) datos.get(r).setCantidad(existeA.get(r).doubleValue());
            if(cant>=0 && cant <= datos.get(r).getCantidad()){
              cantidad.set(r,cant);
              Ingrediente ings= datos.get(r);
                   ings.setCantidad(ings.getCantidad()-cant);
                   
              datos.set(r,ings);
            } 
           }
        if(c==5 && (Integer)valor>0 )
          tiempo.set(r,(Integer)valor);  
    }
   
}
