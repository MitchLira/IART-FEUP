import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.sample.SubSampling;

import java.io.*;
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
    public File generatingNetwork(String path, int maxIterations, double learningRate, double maxError) {

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

    SubSampling samples = new SubSampling(TRAINPERCENTAGE, 100 - TRAINPERCENTAGE);
    List<DataSet> dataSets = samples.sample(dataset);
    dataTraining = dataSets.get(0);
    dataTest = dataSets.get(1);


    neuroNetwork.learn(dataTraining, learningRule);
    String[] name = path.split("_");
    File finalFile = new File("Expressions\\Final_" + name[1]);
    if (!finalFile.exists()) {
        try {
                finalFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    int i=0;
    double error;
    double diff = 0.0;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(finalFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter line = new BufferedWriter(new OutputStreamWriter(fos));

    for(DataSetRow dataRow : this.dataTraining.getRows()) {
        neuroNetwork.setInput(dataRow.getInput());
        neuroNetwork.calculate();
        double[] networkOutput = neuroNetwork.getOutput();
        double[] networkDesiredOutput = dataRow.getDesiredOutput();
        diff += Math.pow(networkOutput[0] - networkDesiredOutput[0],2);




        i++;
        }
        error = diff/i;
        try {
            line.write("Train accuracy: " + error);
            line.newLine();
            line.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalFile;


    }

    public double testNeuralNetwork(File finalFile) {
        if (!finalFile.exists()) {
            try {
                finalFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
          fos = new FileOutputStream(finalFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter line = new BufferedWriter(new OutputStreamWriter(fos));

        int i = 0;
        double error;
        double diff = 0.0;


        for(DataSetRow dataRow : this.dataTest.getRows()) {
            neuroNetwork.setInput(dataRow.getInput());
            neuroNetwork.calculate();
            double[] networkOutput = neuroNetwork.getOutput();
            double[] networkDesiredOutput = dataRow.getDesiredOutput();
            diff += Math.pow(networkOutput[0] - networkDesiredOutput[0],2);


            System.out.println("Output: " + networkOutput[0] );
            System.out.println("Desired Output: " + networkDesiredOutput[0] );

            i++;
        }

        error = diff/i;

        try {
            line.write("Test accuracy: " + error);
            line.newLine();
            line.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return error;

    }

    public static void parseData() throws IOException {
        DataParser affirmative_expression = new DataParser("Expressions\\a_affirmative_datapoints.txt", "Expressions\\a_affirmative_targets.txt","Expressions\\b_affirmative_datapoints.txt", "Expressions\\b_affirmative_targets.txt");
        affirmative_expression.fileConcatenator();
        DataParser conditional_expression = new DataParser("Expressions\\a_conditional_datapoints.txt", "Expressions\\a_conditional_targets.txt", "Expressions\\b_conditional_datapoints.txt", "Expressions\\b_conditional_targets.txt");
        conditional_expression.fileConcatenator();
        DataParser doubts_question_expression = new DataParser("Expressions\\a_doubt_question_datapoints.txt", "Expressions\\a_doubts_question_targets.txt", "Expressions\\b_doubt_question_datapoints.txt", "Expressions\\b_doubt_question_targets.txt");
        doubts_question_expression.fileConcatenator();
        DataParser emphasis_expression = new DataParser("Expressions\\a_emphasis_datapoints.txt","Expressions\\a_emphasis_targets.txt", "Expressions\\b_emphasis_datapoints.txt","Expressions\\b_emphasis_targets.txt");
        emphasis_expression.fileConcatenator();
        DataParser negative_expression = new DataParser("Expressions\\a_negative_datapoints.txt","Expressions\\a_negative_targets.txt", "Expressions\\b_negative_datapoints.txt","Expressions\\b_negative_targets.txt");
        negative_expression.fileConcatenator();
        DataParser relative_expression = new DataParser("Expressions\\a_relative_datapoints.txt","Expressions\\a_relative_targets.txt","Expressions\\b_relative_datapoints.txt","Expressions\\b_relative_targets.txt");
        relative_expression.fileConcatenator();
        DataParser topics_expression = new DataParser("Expressions\\a_topics_datapoints.txt", "Expressions\\a_topics_targets.txt","Expressions\\b_topics_datapoints.txt", "Expressions\\b_topics_targets.txt");
        topics_expression.fileConcatenator();
        DataParser wh_question_expression = new DataParser("Expressions\\a_wh_question_datapoints.txt", "Expressions\\a_wh_question_targets.txt","Expressions\\b_wh_question_datapoints.txt", "Expressions\\b_wh_question_targets.txt");
        wh_question_expression.fileConcatenator();
        DataParser yn_question_expression = new DataParser("Expressions\\a_yn_question_datapoints.txt", "Expressions\\a_yn_question_targets.txt","Expressions\\b_yn_question_datapoints.txt", "Expressions\\b_yn_question_targets.txt");
        yn_question_expression.fileConcatenator();
    }

    public static void main(String []args)
    {
        try {
            FacialExpressionNetwork.parseData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FacialExpressionNetwork affirmativeNetwork = new FacialExpressionNetwork();
        File aff_file = affirmativeNetwork.generatingNetwork("Expressions\\SET_affirmative.txt", 1000, 0.1, 0.01);
        double aff_error = affirmativeNetwork.testNeuralNetwork(aff_file);

        FacialExpressionNetwork conditionalNetwork = new FacialExpressionNetwork();
        File cond_file = conditionalNetwork.generatingNetwork("Expressions\\SET_conditional.txt", 1000, 0.1, 0.01);
        double cond_error = conditionalNetwork.testNeuralNetwork( cond_file);


        FacialExpressionNetwork doubtNetwork = new FacialExpressionNetwork();
        File doubt_file =  doubtNetwork.generatingNetwork("Expressions\\SET_doubt.txt", 1000, 0.1, 0.01);
        double doubt_error =  doubtNetwork.testNeuralNetwork(doubt_file);

        FacialExpressionNetwork empashisNetwork = new FacialExpressionNetwork();
        File emph_file = empashisNetwork.generatingNetwork("Expressions\\SET_emphasis.txt", 1000, 0.1, 0.01);
        double emph_error = empashisNetwork.testNeuralNetwork(emph_file);

        FacialExpressionNetwork negativeNetwork = new FacialExpressionNetwork();
        File neg_file = negativeNetwork.generatingNetwork("Expressions\\SET_negative.txt", 1000, 0.1, 0.01);
        double neg_error =  negativeNetwork.testNeuralNetwork( neg_file);

        FacialExpressionNetwork relativeNetwork = new FacialExpressionNetwork();
        File rel_file = relativeNetwork.generatingNetwork("Expressions\\SET_relative.txt", 1000, 0.1, 0.01);
        double rel_error = relativeNetwork.testNeuralNetwork(rel_file );

        FacialExpressionNetwork topicsNetwork = new FacialExpressionNetwork();
        File top_file = topicsNetwork.generatingNetwork("Expressions\\SET_topics.txt", 1000, 0.1, 0.01);
        double top_error = topicsNetwork.testNeuralNetwork(top_file);

        FacialExpressionNetwork whNetwork = new FacialExpressionNetwork();
        File wh_file = whNetwork.generatingNetwork("Expressions\\SET_wh.txt", 1000, 0.1, 0.01);
        double wh_error = whNetwork.testNeuralNetwork(wh_file);

        FacialExpressionNetwork ynNetwork = new FacialExpressionNetwork();
        File yn_file  = ynNetwork.generatingNetwork("Expressions\\SET_yn.txt", 1000, 0.1, 0.01);
        double yn_error = ynNetwork.testNeuralNetwork(yn_file);


    }

}