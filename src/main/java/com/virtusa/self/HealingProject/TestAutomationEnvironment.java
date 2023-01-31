package com.virtusa.self.HealingProject;

import java.util.*;

public class TestAutomationEnvironment {

    // Define the states of the environment
    public enum State {
    	 TEST_SETUP,
        TEST_PASSED,
        TEST_FAILED,
        TEST_IN_PROGRESS
    }

    // Define the actions that the agent can take
    public enum Action {
    	TEST_EXECUTION,
        RE_RUN_TEST,
        DEBUG_TEST,
        IGNORE_FAILURE,
        RESTART_SYSTEM
    }
    
 // Define the rewards
    private static final double REWARD_TEST_PASSED = 1.0;
    private static final double REWARD_TEST_FAILED = -1.0;
    private static final double REWARD_TEST_IN_PROGRESS = 0.0;

    // Define the current state
    private State currentState;
    
    
    

    // Define the list of possible actions in each state
    private List<List<Action>> possibleActions;

    public TestAutomationEnvironment() {
    	
    	// Initialize the environment in the "test setup" state
        this.currentState = State.TEST_SETUP;
        // Initialize the list of possible actions
        possibleActions = new ArrayList<List<Action>>();
        for (int i = 0; i < State.values().length; i++) {
            possibleActions.add(new ArrayList<>());
        }

        // Define the possible actions in each state
        possibleActions.get(State.TEST_PASSED.ordinal()).add(Action.RE_RUN_TEST);
        possibleActions.get(State.TEST_FAILED.ordinal()).add(Action.RE_RUN_TEST);
        possibleActions.get(State.TEST_FAILED.ordinal()).add(Action.DEBUG_TEST);
        possibleActions.get(State.TEST_FAILED.ordinal()).add(Action.IGNORE_FAILURE);
    }
    public double getReward(State currentState, Action action, State newState) {
        // Define the rewards for different state-action-new state combinations
        if (currentState == State.TEST_IN_PROGRESS && action == Action.TEST_EXECUTION && newState == State.TEST_PASSED) {
            return REWARD_TEST_PASSED;
        } else if (currentState == State.TEST_IN_PROGRESS && action == Action.TEST_EXECUTION && newState == State.TEST_FAILED) {
            return REWARD_TEST_FAILED;
        } else if (currentState == State.TEST_IN_PROGRESS && action == Action.DEBUG_TEST && newState == State.TEST_IN_PROGRESS) {
            return REWARD_TEST_IN_PROGRESS;
        } else if (currentState == State.TEST_FAILED && action == Action.RESTART_SYSTEM && newState == State.TEST_SETUP) {
            return REWARD_TEST_IN_PROGRESS;
        } else {
            return 0.0;
        }
    }
    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public List<Action> getPossibleActions() {
        return possibleActions.get(currentState.ordinal());
    }
}

