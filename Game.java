import java.util.Stack;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> habAnterior;
    private Personaje personaje;
        
    
    public Game() 
    {
        createRooms();
        parser = new Parser();
        habAnterior = new Stack<Room>();
    }

    
    private void createRooms()
    {
        Room recibidor, pasillo1, pasillo2, salon, cocina, armario, habDani, habAlex, habPadres, banho, terrazaExt, terrazaInt, portal;
      
        // create the rooms
        recibidor = new Room("la entrada de la casa.");
        pasillo1 = new Room("el pasillo1.");
        pasillo2 = new Room("el pasillo2");
        salon = new Room("el salon");
        cocina = new Room("la cocina");
        armario = new Room("un armario");
        habDani = new Room("la habitacion de mi hermano");
        habAlex = new Room("mi habitacion");
        habPadres = new Room("la habitacion de mis padres.");
        banho = new Room("el baño");
        terrazaExt = new Room("la terraza que da a la calle");
        terrazaInt = new Room("la terraza cerrada");
        portal = new Room("el portal");
        
        //Norte,Sur,Este,Oeste, NorEste,SurOeste
        //north, south, east1, west, northEast, southWest
        recibidor.setExits(null, null, portal, pasillo1, null, null);
        pasillo1.setExits(armario, null, recibidor, cocina, pasillo2, salon);
        pasillo2.setExits(banho, null, habDani, habPadres, habAlex, pasillo1);
        salon.setExits(null, null, null, null, pasillo1, null);
        cocina.setExits(null, null, pasillo1, terrazaExt, null, null);
        armario.setExits(null, pasillo1, null, null, null, null);
        habDani.setExits(null, null, terrazaInt, pasillo2, null, null);
        habAlex.setExits(null, null, null, null, null, pasillo2);
        habPadres.setExits(null, null, pasillo2, null, null, null);
        banho.setExits(null, pasillo2, null, null, null, null);
        terrazaExt.setExits(null, null, cocina, null, null, null);
        terrazaInt.setExits(null, null, null, habDani, null, null);
        portal.setExits(null, null, null, recibidor, null, null);

        currentRoom = recibidor;  // start game outside
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
        System.out.println("Gracias por jugar. Hasta la proxima.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a tu nueva aventura.");
        System.out.println(" ");
        System.out.println("Ecribe `ayuda´ si necesitas ayuda");
        System.out.println();
        System.out.println("Te encuentras en " + currentRoom.getDescription());
        System.out.print("Salidas: ");
        if(currentRoom.northExit != null) {
            System.out.print("norte ");
        }
        if(currentRoom.southExit != null) {
            System.out.print("sur ");
        }
        if(currentRoom.eastExit != null) {
            System.out.print("este ");
        }
        if(currentRoom.westExit != null) {
            System.out.print("oeste ");
        }
        if(currentRoom.northEastExit != null) {
            System.out.print("noreste ");
        }
        if(currentRoom.southWestExit != null) {
            System.out.print("suroeste ");
        }
        
        System.out.println();
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
            System.out.println("No entiendo lo que me quieres decir...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("ayuda")) {
            printHelp();
        }
        else if (commandWord.equals("ir")) {
            goRoom(command);
        }
        else if (commandWord.equals("quitar")) {
            wantToQuit = quit(command);
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
        System.out.println("Estas perdido? no sabes como seguir?");
        System.out.println();
        System.out.println("Los posibles comandos son:");
        System.out.println("   ir quitar ayuda");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("A donde quieres ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("norte")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("sur")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("este")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("oeste")) {
            nextRoom = currentRoom.westExit;
        }
        if(direction.equals("noreste")) {
            nextRoom = currentRoom.northEastExit;
        }
        if(direction.equals("suroeste")) {
            nextRoom = currentRoom.southWestExit;
        }

        if (nextRoom == null) {
            System.out.println("No hay salida en esa dirección!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println("Tu estas en " + currentRoom.getDescription());
            System.out.print("Salidas: ");
            if(currentRoom.northExit != null) {
                System.out.print("norte ");
            }
            if(currentRoom.southExit != null) {
                System.out.print("sur ");
            }
            if(currentRoom.eastExit != null) {
                System.out.print("este ");
            }
            if(currentRoom.westExit != null) {
                System.out.print("oeste ");
            }
            if(currentRoom.northEastExit != null) {
                System.out.print("noreste ");
            }
            if(currentRoom.southWestExit != null) {
                System.out.print("suroeste ");
            }
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Estas seguro?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
