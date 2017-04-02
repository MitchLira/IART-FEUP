/**
 * Created by marga on 02/04/2017.
 */
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.io.File;
import java.io.IOException;

/**
 * This sample shows how to create, train, save and load simple Multi Layer Perceptron
 */
public class FacialExpressionNetwork {
    private static final int ENTRYNODES = 300;
    private static final int OUTPUTNODES = 18;
    private static final int INTERMEDIATENODES = 0;



    MultiLayerPerceptron neuroNetwork;
    DataSet trainingNet;


    public FacialExpressionNetwork(String[] args) {

    // create multi layer perceptron
    MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, ENTRYNODES, 3, OUTPUTNODES);



    /*
    // learn the training set
    myMlPerceptron.learn(trainingNet);

    // test perceptron
    System.out.println("Testing trained neural network");
    testNeuralNetwork(myMlPerceptron, trainingNet);

// save trained neural network
        myMlPerceptron.save("myMlPerceptron.nnet");

// load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

// test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(loadedMlPerceptron, trainingNet);
*/

    }
/*

    public DataSet getTrainingNet(String path)
    {
        DataSet data = DataSet.createFromFile(path,300,1," ",true);
        return data;
    }


    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

        for(DataSetRow dataRow : testSet.getRows()) {
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[ ] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
            System.out.println(" Output: " + Arrays.toString(networkOutput) );
        }

    }*/

    public void parseData() throws IOException {
        DataParser affirmative_expression = new DataParser("Expressions\\a_affirmative_datapoints.txt", "Expressions\\a_affirmative_targets.txt","Expressions\\b_affirmative_datapoints.txt", "Expressions\\b_affirmative_targets.txt");
        DataParser confitional_expression = new DataParser("Expressions\\a_conditional_datapoints.txt", "Expressions\\a_conditional_targets.txt", "Expressions\\b_conditional_datapoints.txt", "Expressions\\b_conditional_targets.txt");
        DataParser doubts_question_expression = new DataParser("Expressions\\a_doubt_question_datapoints.txt", "Expressions\\a_doubts_question_targets.txt", "Expressions\\b_doubt_question_datapoints.txt", "Expressions\\b_doubt_question_targets.txt");
        DataParser emphasis_expression = new DataParser("Expressions\\a_emphasis_datapoints.txt","Expressions\\a_emphasis_targets.txt", "Expressions\\b_emphasis_datapoints.txt","Expressions\\b_emphasis_targets.txt");
        DataParser negative_expression = new DataParser("Expressions\\a_negative_datapoints.txt","Expressions\\a_negative_targets.txt", "Expressions\\b_negative_datapoints.txt","Expressions\\b_negative_targets.txt");
        DataParser relative_expression = new DataParser("Expressions\\a_relative_datapoints.txt","Expressions\\a_relative_targets.txt","Expressions\\b_relative_datapoints.txt","Expressions\\b_relative_targets.txt");
        DataParser topics_expression = new DataParser("Expressions\\a_topics_datapoints.txt", "Expressions\\a_topics_targets.txt","Expressions\\b_topics_datapoints.txt", "Expressions\\b_topics_targets.txt");
        DataParser wh_question_expression = new DataParser("Expressions\\a_wh_question_datapoints.txt", "Expressions\\a_wh_question_targets.txt","Expressions\\b_wh_question_datapoints.txt", "Expressions\\b_wh_question_targets.txt");
        DataParser yn_question_expression = new DataParser("Expressions\\a_yn_question_datapoints.txt", "Expressions\\a_yn_question_targets.txt","Expressions\\b_yn_question_datapoints.txt", "Expressions\\b_yn_question_targets.txt");

    }


}