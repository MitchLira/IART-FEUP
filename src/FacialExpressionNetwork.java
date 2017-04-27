import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.sample.SubSampling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FacialExpressionNetwork {
    private static final int ENTRYNODES = 301;
    private static final int OUTPUTNODES = 1;
    private static final int INTERMEDIATENODES = 20;
    private static final int TRAINPERCENTAGE = 60;

    MultiLayerPerceptron neuroNetwork;
    DataSet dataset;
    DataSet dataTest;
    DataSet dataTraining;
    BackPropagation learningRule;

    public FacialExpressionNetwork() {};
    public DataSet generatingNetwork(String path, int maxIterations, double learningRate, double maxError) {

    // create multi layer perceptron
    neuroNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, ENTRYNODES, INTERMEDIATENODES, OUTPUTNODES);
    // create DataSet
    dataset = DataSet.createFromFile(path, ENTRYNODES, OUTPUTNODES, " ", true);

    //Normalizing data
    MaxMinNormalizer normalizing = new MaxMinNormalizer();
    normalizing.normalize(dataset);

    //Implementing Learning Rule
    learningRule = new BackPropagation();
    learningRule.setNeuralNetwork(neuroNetwork);
    learningRule.setLearningRate(learningRate);
    learningRule.setMaxError(maxError);
    learningRule.setMaxIterations(maxIterations);
    neuroNetwork.learn(dataset, learningRule);

    SubSampling samples = new SubSampling(TRAINPERCENTAGE, 100 - TRAINPERCENTAGE);
    List<DataSet> dataSets = samples.sample(dataset);
    dataTraining = dataSets.get(0);
    dataTest = dataSets.get(1);

    return dataTest;
    }

    public void testNeuralNetwork(DataSet testSet) {
        for(DataSetRow dataRow : testSet.getRows()) {
            neuroNetwork.setInput(dataRow.getInput());
            neuroNetwork.calculate();
            double[ ] networkOutput = neuroNetwork.getOutput();
            System.out.println("Output: " + Arrays.toString(networkOutput) );
            System.out.println("Desired Output: " + Arrays.toString(dataRow.getDesiredOutput()) );
        }

    }

    public static void parseData() throws IOException {
        DataParser affirmative_expression = new DataParser("Expressions\\a_affirmative_datapoints.txt", "Expressions\\a_affirmative_targets.txt","Expressions\\b_affirmative_datapoints.txt", "Expressions\\b_affirmative_targets.txt");
        affirmative_expression.generateTrainingTestFiles();
        DataParser conditional_expression = new DataParser("Expressions\\a_conditional_datapoints.txt", "Expressions\\a_conditional_targets.txt", "Expressions\\b_conditional_datapoints.txt", "Expressions\\b_conditional_targets.txt");
        conditional_expression.generateTrainingTestFiles();
        DataParser doubts_question_expression = new DataParser("Expressions\\a_doubt_question_datapoints.txt", "Expressions\\a_doubts_question_targets.txt", "Expressions\\b_doubt_question_datapoints.txt", "Expressions\\b_doubt_question_targets.txt");
        doubts_question_expression.generateTrainingTestFiles();
        DataParser emphasis_expression = new DataParser("Expressions\\a_emphasis_datapoints.txt","Expressions\\a_emphasis_targets.txt", "Expressions\\b_emphasis_datapoints.txt","Expressions\\b_emphasis_targets.txt");
        emphasis_expression.generateTrainingTestFiles();
        DataParser negative_expression = new DataParser("Expressions\\a_negative_datapoints.txt","Expressions\\a_negative_targets.txt", "Expressions\\b_negative_datapoints.txt","Expressions\\b_negative_targets.txt");
        negative_expression.generateTrainingTestFiles();
        DataParser relative_expression = new DataParser("Expressions\\a_relative_datapoints.txt","Expressions\\a_relative_targets.txt","Expressions\\b_relative_datapoints.txt","Expressions\\b_relative_targets.txt");
        relative_expression.generateTrainingTestFiles();
        DataParser topics_expression = new DataParser("Expressions\\a_topics_datapoints.txt", "Expressions\\a_topics_targets.txt","Expressions\\b_topics_datapoints.txt", "Expressions\\b_topics_targets.txt");
        topics_expression.generateTrainingTestFiles();
        DataParser wh_question_expression = new DataParser("Expressions\\a_wh_question_datapoints.txt", "Expressions\\a_wh_question_targets.txt","Expressions\\b_wh_question_datapoints.txt", "Expressions\\b_wh_question_targets.txt");
        wh_question_expression.generateTrainingTestFiles();
        DataParser yn_question_expression = new DataParser("Expressions\\a_yn_question_datapoints.txt", "Expressions\\a_yn_question_targets.txt","Expressions\\b_yn_question_datapoints.txt", "Expressions\\b_yn_question_targets.txt");
        yn_question_expression.generateTrainingTestFiles();
    }

    public static void main(String []args)
    {
        try {
            FacialExpressionNetwork.parseData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FacialExpressionNetwork affirmativeNetwork = new FacialExpressionNetwork();
        DataSet affirmativetest = affirmativeNetwork.generatingNetwork("Expressions\\SET_affirmative.txt", 1000, 0.1, 0.01);
        affirmativeNetwork.testNeuralNetwork(affirmativetest);

        FacialExpressionNetwork conditionalNetwork = new FacialExpressionNetwork();
        DataSet conditionaltest = conditionalNetwork.generatingNetwork("Expressions\\SET_conditional.txt", 1000, 0.1, 0.01);
        conditionalNetwork.testNeuralNetwork(conditionaltest);

        FacialExpressionNetwork doubtNetwork = new FacialExpressionNetwork();
        DataSet doubttest = doubtNetwork.generatingNetwork("Expressions\\SET_doubt.txt", 1000, 0.1, 0.01);
        doubtNetwork.testNeuralNetwork(doubttest);

        FacialExpressionNetwork empashisNetwork = new FacialExpressionNetwork();
        DataSet emphasisTest = empashisNetwork.generatingNetwork("Expressions\\SET_emphasis.txt", 1000, 0.1, 0.01);
        empashisNetwork.testNeuralNetwork(emphasisTest);

        FacialExpressionNetwork negativeNetwork = new FacialExpressionNetwork();
        DataSet negativetest = negativeNetwork.generatingNetwork("Expressions\\SET_negative.txt", 1000, 0.1, 0.01);
        negativeNetwork.testNeuralNetwork(negativetest);

        FacialExpressionNetwork relativeNetwork = new FacialExpressionNetwork();
        DataSet relativetest = relativeNetwork.generatingNetwork("Expressions\\SET_relative.txt", 1000, 0.1, 0.01);
        relativeNetwork.testNeuralNetwork(relativetest);

        FacialExpressionNetwork topicsNetwork = new FacialExpressionNetwork();
        DataSet topicstest = topicsNetwork.generatingNetwork("Expressions\\SET_topics.txt", 1000, 0.1, 0.01);
        topicsNetwork.testNeuralNetwork(topicstest);

        FacialExpressionNetwork whNetwork = new FacialExpressionNetwork();
        DataSet  whTest= whNetwork.generatingNetwork("Expressions\\SET_wh.txt", 1000, 0.1, 0.01);
        whNetwork.testNeuralNetwork(whTest);

        FacialExpressionNetwork ynNetwork = new FacialExpressionNetwork();
        DataSet  ynTest= ynNetwork.generatingNetwork("Expressions\\SET_yn.txt", 1000, 0.1, 0.01);
        ynNetwork.testNeuralNetwork(ynTest);


    }

}