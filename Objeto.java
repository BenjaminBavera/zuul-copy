
/**
 * Write a description of class Elemento here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Objeto
{
   private String nombre;
   private int peso;
   
   public Objeto(String nombre, int peso){
       this.nombre = nombre;
       this.peso = peso;
   } 
   
    public String getName(){
       return nombre; 
   }
}
