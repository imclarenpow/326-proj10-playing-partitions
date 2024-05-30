# Playing Partitions
## Implementation
Reads stdin via method, checks that a scenario is valid (i.e. are there the same number of points for all the final positions and the initial one)<br>
then calls play() which checks the first moves back from the final points and adds to visited before calling the recursive method.<br>
processMove() is the recursive method that adds values to the points once it has been affirmed what type they are.<br>
utlilty methods include makeAllMoves which makes all moves for the current state<br>
makeAllReverseMoves() finds all possible earlier moves<br>
reverseMove() which is called by makeAllReverseMoves()<br>
makeMove() which is called by makeAllMoves()<br>
stdIn() and partitionHandler which make the data passed through readable to the program.<br>
finally, isValidScenario, which checks that a scenario is valid before running the main method.<br>
and this:<br>```if(!path.contains(rMove) && scen.initPosition.size() <= rMove.get(0) <br>
            <t>&& rMove.get(0) <= scen.initPosition.get(0) <br>
            <t>&& rMove.size() <= scen.initPosition.size()+scen.initPosition.get(scen.initPosition.size()-1))```
which is important in pruning / not following unecessary paths to the initial.
## Task
Write a program that takes input from stdin a data file formatted according to the rules
of the Parsing Partitions étude where each scenario in the file will consist of a starting
position, and a list of target positions. The starting position will never be one of the
target positions. The output (to stdout) should be in standard form. Each scenario
should be represented by the starting position, a blank line, the list of target positions,
and then a single comment line which is one of the following:<br>
`# WIN`<br>
`# LOSE`<br>
`# DRAW`<br>
Indicating the outcome of the game from the starting position for the first player assuming that both players are trying to win if possible.<br>
Example<br>
`Input:`<br>
`2 1`<br>
`3`<br>
`---`<br>
`3 2 1`<br>
`2 2 1 1`<br>
`Output:`<br>
`2 1`<br>
`3`<br>
`# DRAW`<br>
`---`<br>
`3 2 1`<br>
`2 2 1 1`<br>
`# WIN`<br>
In the first scenario, the legal moves are to (2, 1) (using the first column) or (1, 1, 1)
(using the second column). But, the second move is bad since it allows an immediate
win for the second player. So, the first player will “do nothing” and the game will be
drawn (since the second player will do the same, and so on until they get fed up and
quit).<br>
In the second scenario, the first player can win immediately by removing the second
column.<br>
## Standards
For an achieved standard, the program must work correctly on valid input representing
partitions of size 20 or less.<br>
Merit criteria include the ability to handle much larger partitions efficiently, handling
poorly-formatted input gracefully, and clearly written code.<br>
Excellence criteria include some significant extension to the functionality of the program, or an investigation of general properties of the problem. Pair or group submissions for excellence standard will be considered (but individual submissions of the
main part of the étude are still required).<br>
