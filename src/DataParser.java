import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class DataParser {

    String a_pathDataPoint;
    String a_pathTarget;
    String b_pathDataPoint;
    String b_pathTarget;
    File TrainingFile;
    File TestFile;

    public DataParser(String a_pathDataPoint, String a_pathTarget, String b_pathDataPoint, String b_pathTarget) throws IOException {
        this.a_pathDataPoint = a_pathDataPoint;
        this.a_pathTarget = a_pathTarget;
        this.b_pathDataPoint = b_pathDataPoint;
        this.b_pathTarget = b_pathTarget;
        this.TrainingFile = null;
        this.TestFile = null;
    }
    public void generateTrainingTestFiles() throws IOException {
        File a_path = addTarget(this.a_pathDataPoint, this.a_pathTarget);
        File b_path = addTarget(this.b_pathDataPoint, this.b_pathTarget);
        this.concatenateFiles(a_path, b_path);
    }
    public File addTarget(String pathDataPoint, String pathTarget) throws IOException {
        File datapoint = new File(pathDataPoint);
        FileInputStream DataPointstream = new FileInputStream(datapoint);
        File targets = new File(pathTarget);
        FileInputStream Targetstream = new FileInputStream(targets);

        String[] name = datapoint.getName().split("_");
        String folderPath = new String(Arrays.copyOfRange(datapoint.getAbsolutePath().getBytes(), 0,
                datapoint.getAbsolutePath().lastIndexOf("\\")));

        Path pathToFile = Paths.get(folderPath + "\\" + name[0] + "_" + name[1] + ".txt");
        File outPutFile = new File(String.valueOf(pathToFile));
        FileOutputStream fos = new FileOutputStream(outPutFile);

        BufferedReader datapointStream = new BufferedReader(new InputStreamReader(DataPointstream));
        BufferedReader tagetStream = new BufferedReader(new InputStreamReader(Targetstream));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        String labelLine = datapointStream.readLine() + ";T;";
        bw.write(labelLine);
        bw.newLine();

        String targetLine, datapointLine;
        while (((datapointLine = datapointStream.readLine()) != null) &&
                ((targetLine = tagetStream.readLine()) != null)) {
            String str = (datapointLine + ";" + targetLine + ";");
            bw.write(str);
            bw.newLine();}

        datapointStream.close();
        tagetStream.close();
        bw.close();
        return outPutFile;
    }

    public void concatenateFiles(File file1, File file2) throws IOException {

        FileInputStream Expression1 = new FileInputStream(file1);
        FileInputStream Expression2 = new FileInputStream(file2);

        String[] name = file1.getName().split("_");
        String folderPath = new String(Arrays.copyOfRange(file1.getAbsolutePath().getBytes(), 0,
                file1.getAbsolutePath().lastIndexOf("\\")));

        Path pathToTrainFile = Paths.get(folderPath + "\\" + "TRAIN_" + name[1]);
        this.TrainingFile = new File(String.valueOf(pathToTrainFile));
        FileOutputStream training = new FileOutputStream(this.TrainingFile);

        Path pathToTestFile = Paths.get(folderPath + "\\" + "TEST_" + name[1]);
        this.TrainingFile = new File(String.valueOf(pathToTestFile));
        FileOutputStream test = new FileOutputStream(this.TrainingFile);

        BufferedReader Stream1 = new BufferedReader(new InputStreamReader(Expression1));
        BufferedReader Stream2 = new BufferedReader(new InputStreamReader(Expression2));
        BufferedWriter TrainingBuffer = new BufferedWriter(new OutputStreamWriter(training));
        BufferedWriter TestingBuffer = new BufferedWriter(new OutputStreamWriter(test));

        String labelLine1 = Stream1.readLine();
        String labelLine2 = Stream2.readLine();

        Random rn2 = new Random();
        Random rn1 = new Random();

        TrainingBuffer.write(labelLine1);
        TestingBuffer.write(labelLine2);
        TestingBuffer.newLine();
        TrainingBuffer.newLine();

        String line1, line2;
        while (((line1 = Stream2.readLine()) != null) && ((line2 = Stream2.readLine()) != null)) {
            int n1 = rn1.nextInt(10) + 1;
            if (n1 <= 7) {
                TrainingBuffer.write(line1);
                TrainingBuffer.newLine();
            } else {
                TestingBuffer.write(line1);
                TestingBuffer.newLine();
            }
            int n2 = rn2.nextInt(10) + 1;
            if (n2 <= 7) {
                TrainingBuffer.write(line2);
                TrainingBuffer.newLine();
            } else {
                TestingBuffer.write(line1);
                TestingBuffer.newLine();
            }
        }
        Stream1.close();
        Stream2.close();
        TestingBuffer.close();
        TrainingBuffer.close();
        file1.delete();
        file2.delete();
    }
}

