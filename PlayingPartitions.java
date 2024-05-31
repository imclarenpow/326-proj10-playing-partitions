import java.util.*;
import java.util.stream.Collectors;

public class PlayingPartitions{
    // indicates if this move is a move to a final state (1) (happy), or if all moves before it are a moves to a final states (2) (sad), otherwise 0 (meh)
    private static HashMap<ArrayList<Integer>, Integer> visited = new HashMap<>();
    private static int biggestFerrerLine = 0;
    public static void main(String[] args){
        System.setErr(System.out);
        ArrayList<Scenario> scenarios = stdin();
        for (Scenario scenario : scenarios) { 
            for(Integer i : scenario.initPosition){
                System.out.print(i + " ");
            }
            System.out.println(); System.out.println();
            for(ArrayList<Integer> finalPosition : scenario.finalPositions){
                for(Integer i : finalPosition){
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            if(isValidScenario(scenario)){
                biggestFerrerLineFinder(scenario);
                bfs(scenario);
            }else{
                System.out.println("# INVALID SCENARIO");
                if(scenarios.indexOf(scenario) < scenarios.size()-1){
                    System.out.println("--------");
                }
                continue;
            }
            if(visited.containsKey(scenario.initPosition)){
                if(visited.get(scenario.initPosition) == 1){
                    System.out.println("# WIN");
                }else if(visited.get(scenario.initPosition) == 2){
                    System.out.println("# LOSE");
                }else if(visited.get(scenario.initPosition) == 0){
                    System.out.println("# DRAW");
                }
            }else{
                System.out.println("# DRAW");
            }
            //for(ArrayList<Integer> s : visited.keySet()){
            //    System.out.println(s.toString() + " " + visited.get(s));
            //}
            visited = new HashMap<>();
            if(scenarios.indexOf(scenario) < scenarios.size()-1){
                System.out.println("--------");
            }
            
        }
    }
    public static void bfs(Scenario scen) {
        Queue<ArrayList<Integer>> queue = new LinkedList<>();
        for(ArrayList<Integer> finals : scen.finalPositions){
            visited.put(finals, 2);
            HashSet<ArrayList<Integer>> oneUp = makeAllReverseMoves(finals);
            for(ArrayList<Integer> oneUpMove : oneUp){
                queue.add(oneUpMove);
                visited.put(oneUpMove, 1);
            }
        }
        while(!queue.isEmpty()){   
            ArrayList<Integer> current = queue.poll();
            HashSet<ArrayList<Integer>> fMoves = makeAllMoves(current);
            int counter = 0;

            //System.out.println("Current: " + current.toString());
            for(ArrayList<Integer> fMove : fMoves){
                //System.out.println("-> " + fMove.toString());
                if(visited.containsKey(fMove)){
                    //System.out.println("  value: " + visited.get(fMove));
                    if(visited.get(fMove) == 1){
                        counter++;
                    }
                    else if(visited.get(fMove) == 2){
                        if((!visited.containsKey(current) || visited.get(current) == 0)){
                            visited.put(current, 1);
                        }  
                    }
                }else if(!queue.contains(fMove)){
                    queue.add(fMove);
                    //System.out.println("Added to queue: " + fMove.toString());
                }
            }
            HashSet<ArrayList<Integer>> rMoves = makeAllReverseMoves(current);
            boolean currSad = false;
            if(counter!=0 && counter == fMoves.size() && (!visited.containsKey(current) || visited.get(current) == 0)){
                visited.put(current, 2);
                currSad=true;
            }else if(!visited.containsKey(current)){
                visited.put(current, 0);
            }
            for(ArrayList<Integer> rMove : rMoves){
                if(!queue.contains(rMove) && (!visited.containsKey(rMove)) && !scen.finalPositions.contains(rMove)){
                    queue.add(rMove);
                }
                if(!scen.finalPositions.contains(rMove) && currSad){
                    visited.put(rMove, 1);
                }
            }
        }
    }
    public static void biggestFerrerLineFinder(Scenario sc){
        int temp = 0;
        if(sc.initPosition.size() > temp){
            temp = sc.initPosition.size();
        }
        if(sc.initPosition.get(0) > temp){
            temp = sc.initPosition.get(0);
        }
        for(ArrayList<Integer> arr : sc.finalPositions){
            if(arr.size() > temp){
                temp = arr.size();
            }
            if(arr.get(0) > temp){
                temp = arr.get(0);
            }
        }
        biggestFerrerLine = temp;
    }
    public static HashSet<ArrayList<Integer>> makeAllMoves(ArrayList<Integer> current){
        HashSet<ArrayList<Integer>> output = new HashSet<>();
            for(int j = 0; j < current.get(0); j++){
                ArrayList<Integer> move = makeMove(current, j);
                if(move.get(0) <= biggestFerrerLine && move.size() <= biggestFerrerLine){
                    output.add(move);
                }
            }
        return output;
    }
    public static HashSet<ArrayList<Integer>> makeAllReverseMoves(ArrayList<Integer> current){
        HashSet<ArrayList<Integer>> output = new HashSet<>();
        for(int i = 0; i < current.size(); i++){
            ArrayList<Integer> move = reverseMove(current, i);
            if(move.get(0) <= biggestFerrerLine && move.size() <= biggestFerrerLine){
                output.add(move);
            }
        }
        //System.out.println(output.size() + " amt of r moves");
        return output;
    }
    public static ArrayList<Integer> reverseMove(ArrayList<Integer> current,
            int index) {
        
        ArrayList<Integer> newConfiguration = new ArrayList<>(current);
        int adding = newConfiguration.get(index);
        //System.out.println(current.toString() + " removing: " + newConfiguration.get(index));
        newConfiguration.remove(index);
        for(int i = 0; i < adding; i++){
            if(i >= newConfiguration.size()){
                newConfiguration.add(1);
            }else{
                newConfiguration.set(i, newConfiguration.get(i) + 1);
            }
        }
        //System.out.println(newConfiguration.toString());
        return newConfiguration;
    }
    /* method that makes the specified move and returns an array */
    public static ArrayList<Integer> makeMove(ArrayList<Integer> current, 
            int index) {
        //System.out.println(current.toString() + " " + index);
        ArrayList<Integer> newConfiguration = new ArrayList<>(current);
        int newRow = 0;
        for (int i = 0; i < newConfiguration.size(); i++) {
            if (newConfiguration.get(i) > index) {
                int temp = newConfiguration.get(i);
        //        System.out.println("index passed through - " + index + " temp - " + temp);
                if (temp - 1 > 0) { newConfiguration.set(i, temp - 1); newRow++; }
                else { newConfiguration.remove(i); newRow++; i--; }
            }
        }
        newConfiguration.add(newRow);
        Collections.sort(newConfiguration, Collections.reverseOrder());
        //System.out.println("result: "+ newConfiguration.toString());
        return newConfiguration;
    }
/* The following code handles input and scenarios */
    /** handles stdin and returns as expected input to algorithm */
    public static ArrayList<Scenario> stdin() {
        // instantiate variables needed
        Scanner sc = new Scanner(System.in);
        ArrayList<Scenario> output = new ArrayList<>();
        ArrayList<String> rawInput = new ArrayList<>();
        while (sc.hasNextLine()) { rawInput.add(sc.nextLine()); } // reads raw input
        sc.close(); // memory management (doesn't really matter just good practice)
        // instantiate individual scenario variables
        ArrayList<Integer> initPosition = new ArrayList<>();
        HashSet<ArrayList<Integer>> finalPositions = new HashSet<>();
        // for loop to handle raw input + 1 so we can add the last scenario
        for (int i = 0; i < rawInput.size()+1; i ++){
            // if for adding last scenario must be before any get
            if(i == rawInput.size()){
                output.add(new Scenario(initPosition, finalPositions));
                continue; } // continue to avoid other operations
            // temp for partition handling
            ArrayList<Integer> temp = partitionHandler(rawInput.get(i));
            // ifs for first line
            if(i==0 && rawInput.get(i).trim().isEmpty()){ 
                rawInput.remove(i); i--; continue; }
            else if(i==0){
                if(!temp.isEmpty()){ initPosition = temp; }
                else{ rawInput.remove(i); i--; continue; }
                continue;
            }
            // ifs for seperators
            if(rawInput.get(i).trim().isEmpty()){ continue; }
            // if for new scenarios
            else if(rawInput.get(i).trim().startsWith("-")){
                output.add(new Scenario(initPosition, finalPositions));
                initPosition = new ArrayList<>();
                finalPositions = new HashSet<>();
            }else if(rawInput.get(i).trim().startsWith("#")){ continue; }
            // ifs for first positions
            if(initPosition.isEmpty()){
                if(!temp.isEmpty()){ initPosition = temp; }
                else{ rawInput.remove(i); i--; continue; }
            // else for final positions
            }else{
                if(!temp.isEmpty()){ finalPositions.add(temp); }
                else{ continue; }
            }   
        }
        return output;
    }
    /** this method makes the method above smaller by making this logic seperate */
    public static ArrayList<Integer> partitionHandler(String input) {
        try { return Arrays.stream(input.trim().split("\\s+"))
            .map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        } catch (NumberFormatException e) { return new ArrayList<>(); }
    }
    /** object for scenarios */
    static class Scenario {
        ArrayList<Integer> initPosition;
        HashSet<ArrayList<Integer>> finalPositions;
        // construtor
        Scenario(ArrayList<Integer> initPosition, HashSet<ArrayList<Integer>> finalPositions) {
            // sorting the arraylists in descending order
            Collections.sort(initPosition, Collections.reverseOrder());
            HashSet<ArrayList<Integer>> sortedFinalPositions = new HashSet<>();
            for (ArrayList<Integer> position : finalPositions) {
                Collections.sort(position, Collections.reverseOrder());
                sortedFinalPositions.add(position); }
            this.initPosition = initPosition;  // assign the sorted list
            this.finalPositions = sortedFinalPositions;  // assign the sorted set
        }
    }
    /** simple method that checks if a scenario is valid or not */
    public static boolean isValidScenario(Scenario scenario) {
        // now accounts for empty scenarios
        if(scenario.initPosition.isEmpty()){ return false; }
        int length = scenario.initPosition.stream().mapToInt(Integer::intValue).sum();
        for (ArrayList<Integer> finalPosition : scenario.finalPositions) {
            if (length != finalPosition.stream().mapToInt(Integer::intValue).sum()) {
                return false; }
        }
        return true;
    }
}