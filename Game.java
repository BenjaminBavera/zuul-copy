import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;
         
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room inicio, uno, dos, tres, salida, win;
      
        // create the rooms
        inicio = new Room("sala donde el muñeco Pigsaw te explica el juego");
        uno = new Room("sala uno. Solo hay 3 objetos");
        dos = new Room("sala dos. Tienes que apagar el fuego para pasar");
        tres = new Room("sala tres. Tienes que encontrar una forma de derrotar al Indio");
        salida = new Room("la puerta de salida. Debes ingresar el codigo para poder pasar");
        win = new Room("el exterior. Ganaste");
        
        // initialise room exits
        inicio.setExit("east", uno);
        uno.setExit("north",dos);
        uno.setExit("south",tres);
        dos.setExit("east",salida);
        dos.setExit("south", uno);
        tres.setExit("north",uno);
        salida.setExit("east", win);
        win.setExit(null,null);
        
        // crear y agregar los objetos a los room
        inicio.addObjeto(new Objeto("control",200));
        uno.addObjeto(new Objeto("matafuegos",6000));
        uno.addObjeto(new Objeto("bomba",5000));
        uno.addObjeto(new Objeto("cinta",100));
        dos.addObjeto(new Objeto("maquillaje",500));
        dos.addObjeto(new Objeto("maniqui",2000));
        tres.addObjeto(new Objeto("acertijo",10));
        
        //crear el jugador y asignarle su currentRoom
        player = new Player(inicio);

        player.agregarAlRecorrido(inicio);
        // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(player.getCurrentRoom().getObjetos());
        }else if (commandWord.equals("take")) {
            agarrarObjeto(command);
        } else if (commandWord.equals("inventory")){
            System.out.println(getInventory());
        } else if (commandWord.equals("back")){
            goBack();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom =  player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.agregarAlRecorrido(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }
    
    private void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private void agarrarObjeto(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("take what?");
            return;
        }
        Objeto objeto = null;
        String secondWord = command.getSecondWord();
        ArrayList<Objeto>objetosRoom = player.getCurrentRoom().clonarListaObjetos();
        for (int i = 0; i < objetosRoom.size() && objeto == null; i++){
            if (secondWord.equals(objetosRoom.get(i).getName())){
                objeto = objetosRoom.get(i);    
            }
        }
        if (objeto == null) {
            System.out.println("No se encuentra ese objeto en la habitacion");
            return;
        }
        player.takeItem(objeto);
        player.getCurrentRoom().eliminarObjeto(objeto);
        System.out.println("Has agarrado: " + objeto.getName());
    }
    
    private String getInventory(){
        return player.getInventory();
    }
    
    private void goBack(){
        if (player.sizeRecorrido() == 1){ 
            System.out.println("no puedes volver atras");
        }
        else { 
            player.setCurrentRoom(player.peekRecorrido());
            player.eliminarDelRecorrido();
            printLocationInfo();
        }
    }
}
