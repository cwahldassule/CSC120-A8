import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Bumblebee implements Contract {
    private String name;
    private int size = 1;
    private int flight_power = 20;
    private int flight_cap = 20;
    private String[] allFlowers = {"Sunflower", "Tulip", "Rose", "Peony", "Lavender", "Tiger Lily", "Baby's-breath", "Forget Me Not", "Dahlia", "Magnolia", "Wisteria", "Lilac", "Petunia", "Morning Glory", "Lotus", "Daffodil", "Lily of the Valley", "Orchid", "Carnation", "Poppy"};
    private Random index = new Random();
    private int flower_size = 10;
    private ArrayList<String> myFlowers;

    /**
     * creates an instance of bumblebee
     * @param name name of the bee
     */
    public Bumblebee(String name){
        this.name = name;
        this.myFlowers = new ArrayList<String>();
    }

    /**
     * allows user to choose what method to call from the console
     * @return t/f based whether or not the user called "quit"
     */
    public boolean play(){
        System.out.println("\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("\nWhat would you like to do?");
        System.out.println("Options: \n+Fly \n+Walk \n+Snack \n+Sleep \n+Grow \n+Open Flower Bag \n+Empty Flower Bag \n+Stats \n+Help \n+Quit\n");
        Scanner in = new Scanner(System.in);
        String response = in.nextLine();
        response = response.toUpperCase();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n");
        if(response.equals("FLY")){
            System.out.println("Where would you like to fly to?");
            System.out.println("X coord:");
            int x = in.nextInt();
            System.out.println("Y coord:");
            int y = in.nextInt();
            this.fly(x, y);
            return true;
        }
        else if(response.equals("WALK")){
            System.out.println("What direction do you want to walk in?");
            String direction = in.nextLine();
            this.walk(direction);
            return true;
        }
        else if(response.equals("SNACK")){
            System.out.println("What flower do you want to munch on?");
            this.openBag();
            String res = in.nextLine();
            res = res.substring(0, 1).toUpperCase() + res.substring(1);
            this.use(res);
            return true;
        }
        else if(response.equals("SLEEP")){
            this.rest();
            return true;
        }
        else if(response.equals("GROW")){
            this.grow();
            return true;
        }
        else if(response.equals("OPEN FLOWER BAG")){
            this.openBag();
            return true;
        }
        else if(response.equals("QUIT")){
            return false;
        }
        
        else if(response.equals("STATS")){
            this.print_stats();
            return true;
        }
        else if(response.equals("EMPTY FLOWER BAG")){
            this.undo();
            return true;
        }
        
        else if(response.equals("HELP")){
            this.help();
            return true;
        }
        else{
            throw new RuntimeException("You didn't choose a valid option :0");}
        }
    
    /**
     * prints intructions on how each function works
     */
    public void help(){
        System.out.println("Fly:");
        System.out.println("\t+Choose the coords of where you want to go\n\t+Collect 2 flowers\n\t+possibility of loosing 1-4 flowers or flight power\n\t+loose 2 flight power");
        System.out.println("Walk:");
        System.out.println("\t+Choose where you want to go\n\t+Collect 1 flower\n\t+Lose 1 flight power");
        System.out.println("Snack:");
        System.out.println("\t+Eat 1 flower to gain 3 flight power");
        System.out.println("Sleep:");
        System.out.println("\t+Sleep for 20 seconds to reset flight power");
        System.out.println("Grow:");
        System.out.println("\t+Increase your size when you have the right number of identical flowers (your sze + 1)");
        System.out.println("\t\tEx) if size = 1, you need 2 identical flowers. if size = 2, you need 3 identical flowers");
        System.out.println("\t+for each size up, flight cap increases by 10 and bag size is increased by 5");
        System.out.println("Open Flower Bag:");
        System.out.println("\t+Opens flower bag");
        System.out.println("Empty Flower bag:");
        System.out.println("\t+Empties flower bag");
        System.out.println("Stats:");
        System.out.println("\tDisplays name, size, flight power, flight cap, bag size, number of flowers");
    }
    
    /**
     * prints the contents of the bag
     */
    public void openBag(){
        System.out.println("Opening bag...");
        myFlowers.sort(Comparator.naturalOrder());
        if(!myFlowers.isEmpty()){
        for(int i = 0; i<myFlowers.size(); i++){
            System.out.println("\t+"+myFlowers.get(i));
        }}
        else{
            throw new RuntimeException("You don't have any flowers lol");
        }
        System.out.println("");
    }
    /**
     * test to see if the user has the required number of flowers
     * @return whether or not they pass
     */
    private String upgrade(){
        for(int i=0; i<20; i++){
            int count = 0;
            for(int j=0; j<myFlowers.size(); j++){
                if(allFlowers[i] == myFlowers.get(j)){
                    count += 1;}
                if(count == this.size+1){
                    return allFlowers[i];}}}
        return "None";}
    
    /**
     * selects a random flower from predetermined list
     * @return the flower
     */
    private String randFlower(){
        Integer temp = index.nextInt(20);
        return allFlowers[temp];}

    /**
     * allows user to add a flower to bag
     * @param String the name of the flower
     */
    public void grab(String item) {
        if(myFlowers.size() < flower_size){
            this.myFlowers.add(item);
            System.out.println("Bumblebee " + this.name + " has picked up: "+item+"\n");}
        else{
            throw new RuntimeException("Your flower bag is full >_<");
        }
    }

    /**
     * allows user to not add to inventory
     * @param String the name of flower
     * @return name of the flower
     */
    public String drop(String item) {
        System.out.println("Bumblebee "+ this.name + " has dropped: "+item+"\n");
        return item;
    }

    /**
     * allows bumblebee to what flower they found and then grab/drop
     * @param String the name of flower
     */
    public void examine(String item) {
        System.out.println("\nyou've found a " +item+". What would you like to do?\n+Grab \n+Drop");
        try{Scanner in = new Scanner(System.in);
        String res = in.nextLine();
        res = res.toUpperCase();
        if(res.equals("GRAB")){
            System.out.println("Grabbing...");
            this.grab(item);}
        else if(res.equals("DROP")){
            System.out.println("Dropping...");
            this.drop(item);}
        else{
            throw new RuntimeException("You squished the flower :0");}}
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }


    /**
     * removes a flower from inventory and adds 3 to flight_power
     * @param String flower name
     */
    public void use(String item) {
        System.out.println("Munching on a yummy flower...");
        if(myFlowers.contains(item)){
            myFlowers.remove(item);
            if(flight_power <= flight_cap){
                flight_power += 3;}
            else{
                throw new RuntimeException("you've reached your flight power cap :0");
            }}
        else{
            throw new RuntimeException("You don't have that flower silly >_<");
        }
    }

    /**
     * walks to a place to get one flower
     * @param String direction they are walking in
     * @return boolean the action has been completed
     */
    public boolean walk(String direction) {
        if(flight_power >= 2){
        System.out.println("Walking to "+direction+"...");
        flight_power -= 2;
        this.examine(randFlower());}
        else{
            throw new RuntimeException("You are too tired to do that...");
        }
        return true;
    }

    /**
     * flies to a place to get two flowers
     * @param int x and y coords
     * @return action has been completed
     */
    public boolean fly(int x, int y) {
        if(flight_power >= 2){
        System.out.println("Flying to ("+x+","+y+")...");
        flight_power -= 2;
        Integer temp = index.nextInt(20);
        if(temp%3 == 0){
            this.shrink();
        }
        else{
            this.examine(randFlower());
            this.examine(randFlower());}}
        else{
            throw new RuntimeException("You are too tired to do that...");
        }
        return true;
        
        
    }


    /**
     * makes bumblebee drop up to 4 flowers
     * @return number of flowers dropped
     */
    public Number shrink() {
        System.out.println("You bumped into a tree...");
        Integer temp = index.nextInt(3);
        if(myFlowers.size() >= (temp+1)){
        System.out.println("Uh oh...dropping flowers...");
        int count = 0;
        for(int i = 0; i < temp+1; i++){
            Integer temp1 = index.nextInt(myFlowers.size());
            System.out.println("You dropped "+myFlowers.get(temp1));
            this.myFlowers.remove(myFlowers.get(temp1));
            count += 1;}
        return count;}
        else{
            System.out.println("Uh oh...losing flight power...");
            this.flight_power = this.flight_power/2;
            return 5;
        }
    }


    /**
     * when randomly selected flowers have been collected, bumblebee will grow in size and upgrade flight power
     * @return new size
     */
    public Number grow() {
        System.out.println("Growing...");

        String flower = this.upgrade();
        if(!flower.equals("None")){
            System.out.println("You successfully collected "+ (this.size+1)+ " "+ flower);
            System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~**");
            System.out.println("*Congrats! You're the biggest bumblebee on the block!*");
            System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~**");
            for(int i = 0; i<this.size+1;i++){
                myFlowers.remove(flower);
            }
            flight_cap += 10;
            size += 1;
            flower_size +=5;
        }
        else{
            System.out.println("You don't have the right flowers to grow :/");
        }
        return 1;
    }

    /**
     * reset flight power to max
     */
    public void rest() {
        System.out.println("Sleeping...please wait...");
        try {Thread.sleep(5000);
            System.out.println("Still sleeping...");
            Thread.sleep(5000);
            System.out.println("All rested :)");
        } catch (InterruptedException e) {
            System.out.println("You tried to cheat :0");
        }
        this.flight_power = flight_cap;
        this.print_stats();

    }

    /**
     *empties bag
     */
    public void undo() {
        System.out.println("Emptying bag...");
        myFlowers.clear();
        this.print_stats();
    }
    
    /**
     * prints out bee stats
     */
    public void print_stats(){
        System.out.println("\tName: " + this.name);
        System.out.println("\tFlight cap: "+this.flight_cap);
        System.out.println("\tFlight power: "+this.flight_power);
        System.out.println("\tSize: "+this.size);
        System.out.println("\tFlower bag size: "+flower_size);
        System.out.println("\tNumber of flowers: "+myFlowers.size());
    }

    public static void main(String[] args) {
        System.out.println("+Welcome to the Bumblebee game!");
        System.out.println("+Your goal is the become the biggest bumblebee on the block by collecting flowers");
        System.out.println("+Choose your name, then use the help option to see what you can do...");
        System.out.println("\nWhat would you like your name to be?");
        Scanner in = new Scanner(System.in);
        String res = in.nextLine();
        Bumblebee Bettie = new Bumblebee(res);
        while(true){
            try{Bettie.play();}
            catch(Exception e){
                System.out.println("----------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------");
            if(!Bettie.play()){
                break;
            }
            }
            }
        System.out.println("Bye bye!");
        System.out.println("Final Stats:");
        System.out.println("Size: " + Bettie.size);
        
    }
}
