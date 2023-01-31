package com.virtusa.self.HealingProject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.virtusa.self.HealingProject.TestAutomationEnvironment.Action;
import com.virtusa.self.HealingProject.TestAutomationEnvironment.State;

public class QLearningAgent {

    // Define the Q-table
    private double[][] Q;

    // Define the learning rate
    private double alpha;

    // Define the discount factor
    private double gamma;


    
    
    private TestAutomationEnvironment environment;
    private double learningRate;
    private double discountFactor;
    private Map<State, Map<Action, Double>> qValues;
    
    public QLearningAgent(TestAutomationEnvironment environment, double learningRate, double discountFactor) {
        this.environment = environment;
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
        this.qValues = new HashMap<>();
    }

    public Action chooseAction(State currentState) {
        // Implement the Q-learning algorithm here
        // Choose the action with the highest Q-value for the current state
        // For example:
        Map<Action, Double> stateQValues = qValues.get(currentState);
        Action bestAction = null;
        double bestQValue = Double.NEGATIVE_INFINITY;
        for (Map.Entry<Action, Double> entry : stateQValues.entrySet()) {
            if (entry.getValue() > bestQValue) {
                bestAction = entry.getKey();
                bestQValue = entry.getValue();
            }
        }
        return bestAction;
    }

//    public void train(int numEpisodes) {
//        Random rand = new Random();
//        for (int episode = 0; episode < numEpisodes; episode++) {
//            // Set the initial state
//            environment.setCurrentState(environment.State.TEST_IN_PROGRESS);
//
//            // Run the episode
//            while (environment.getCurrentState() != environment.State.TEST_PASSED && environment.getCurrentState() != environment.State.TEST_FAILED) {
//                // Choose a random action
//                environment.Action action = environment.getPossibleActions().get(rand.nextInt(environment.getPossibleActions().size()));
//
//                // Take the action and observe the new state and reward
//                TestAutomationEnvironment.State newState = takeAction(action);
//                double reward = getReward(environment.getCurrentState(), action, newState);
//
//                // Update the Q-value
//                int currentStateIndex = environment.getCurrentState().ordinal();
//                int actionIndex = action.ordinal();
//                Q[currentStateIndex][actionIndex] = Q[currentStateIndex][actionIndex] + alpha * (reward + gamma * maxQ(newState) - Q[currentStateIndex][actionIndex]);
//
//                // Set the new state
//                environment.setCurrentState(newState);
//            }
//        }
//    }
//
//    public TestAutomationEnvironment.State takeAction(TestAutomationEnvironment.Action action) {
//        // Perform the action and return the new state
//        // This function would be specific to your test automation system
//        return newState;
//    }
//
//    public double getReward(TestAutomationEnvironment.State state, TestAutomationEnvironment.Action action, TestAutomationEnvironment.State newState) {
//        // Return the reward for taking the action in the given state
//        // This function would be specific to your test automation system
//        return reward;
//    }

//    public double maxQ(TestAutomationEnvironment.State state) {
//        // Return the maximum Q-value for the given state
//        int stateIndex = state.ordinal();
//        double maxQ = Double.NEGATIVE_INFINITY;
//        for (int actionIndex = 0; actionIndex < environment.Action.values().length; actionIndex++) {
//            maxQ = Math.max(maxQ, Q[stateIndex][actionIndex]);
//        }
//        return maxQ;
//    }
    public void updateQValues(State currentState, Action action, State newState, double reward) {
        // Implement the Q-learning update rule here
        // For example:
        double currentQValue = qValues.get(currentState).get(action);
        double maxNewQValue = Double.NEGATIVE_INFINITY;
        for (double qValue : qValues.get(newState).values()) {
            maxNewQValue = Math.max(maxNewQValue, qValue);
        }
        double newQValue = (1 - learningRate) * currentQValue + learningRate * (reward + discountFactor * maxNewQValue);
        qValues.get(currentState).put(action, newQValue);
    }
}

