package solver;

import java.util.*;

public class DFSStrategy extends SearchMethod {

	public DFSStrategy() {
		code = "DFS";
		longName = "Depth-First Search";
		Frontier = new LinkedList<PuzzleState>();
		Searched = new LinkedList<PuzzleState>();
	}

	protected PuzzleState popFrontier() {
		// remove an item from the fringe to be searched
		final PuzzleState thisState = Frontier.pop();
		// Add it to the list of searched states, so that it isn't searched again
		Searched.add(thisState);

		return thisState;
	}

	@Override
	public direction[] Solve(final nPuzzle puzzle) {

		// put the start state in the Fringe to get explored.
		addToFrontier(puzzle.StartState);

		ArrayList<PuzzleState> newStates = new ArrayList<PuzzleState>();

		while (Frontier.size() > 0) {
			// get the next item off the fringe
			final PuzzleState thisState = popFrontier();

			// is it the goal item?
			if (thisState.equals(puzzle.GoalState)) {
				// We have found a solution! return it!
				return thisState.GetPathToState();

			} else {
				// This isn't the goal, just explore the node
				newStates = thisState.explore();

				// Reverse how the states are entered to search the correct node first
				//Since things are added to the frontier at the front the order must be reversed 
				// to allow the class to explore one node before backtracking through the tree.
				for (int i = newStates.size() - 1; i >= 0; i--) {
					addToFrontier(newStates.get(i));
					
				}
				// tells me the program is running :)
			//	System.out.println("Loading");
			}
		}

		// No solution found and we've run out of nodes to search
		// return null.
		return null;
	}

	public boolean addToFrontier(final PuzzleState aState) {
		// if this state has been found before,
		if (Searched.contains(aState) || Frontier.contains(aState)) {
			// discard it
			return false;
		} else {
			// else put this item on the start of the queue;
			Frontier.addFirst(aState);
			return true;
		}
	}

}