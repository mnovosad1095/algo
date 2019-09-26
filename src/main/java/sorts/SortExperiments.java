package sorts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;


public class SortExperiments {

    private PrintWriter fileWriter;

    public void makeExperiments(){
        createFileWriter();
        doExperiment("Random Array");
        doExperiment("Sorted Array");
        doExperiment("Descending Array");
        doExperiment("Array with same numbers");
        closeFile();
    }

    private void createFileWriter() {
        try {
            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("./results.txt")));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void closeFile() {
        fileWriter.close();
    }

    private void doExperiment(String experiment) {

        if (experiment.equals("Random Array")) {
            randomArraySort();
        } else if(experiment.equals("Sorted Array")) {
            sortSortedArray();
        } else if (experiment.equals("Descending Array")) {
            sortDescendingOrderArray();
        } else if (experiment.equals("Array with same numbers")) {
            sortSameNumbersArray();
        } else {
            System.out.println("No such experiment");
        }
    }

    private void randomArraySort(){
        long[][] timeRecords = new long[3][11];
        long[][] comparisons = new long[3][11];
        long started = System.nanoTime();

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] array = new int[n];
            randomizeArray(array, 1);
            doRandomSorts(timeRecords, comparisons, array, i);
        }
        writeResults(timeRecords, comparisons);
        System.out.println(System.nanoTime() - started);
    }

    private void sortSortedArray(){
        long[][] timeRecords = new long[3][11];
        long[][] comparisonsCounter = new long[3][11];
        long started = System.nanoTime();

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] array = new int[n];
            for (int j = 0; j < n; j++){
                array[j] = j;
            }
            doSorts(array, i, timeRecords, comparisonsCounter);
        }
        writeResults(timeRecords, comparisonsCounter);
        System.out.println(System.nanoTime() - started);
    }

    private void sortDescendingOrderArray(){
        long[][] timeRecords = new long[3][11];
        long[][] comparisonsCounter = new long[3][11];
        long started = System.nanoTime();

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] array = new int[n];
            for (int j = n - 1; j > 0; j--){
                array[j] = j;
            }
            doSorts(array, i, timeRecords, comparisonsCounter);
        }
        writeResults(timeRecords, comparisonsCounter);
        System.out.println(System.nanoTime() - started);
    }

    private  void sortSameNumbersArray(){
        long[][] timeRecords = new long[3][11];
        long[][] comparisons = new long[3][11];

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] arr = new int[n];
            randomizeArray(arr, 4);
            doRandomSorts(timeRecords, comparisons, arr, i);
        }
        writeResults(timeRecords, comparisons);
    }

    private static void doSorts(int[] array, int index, long[][] timeRecords, long[][] comparisonsCounter){
        long start = System.nanoTime();

        comparisonsCounter[0][index-7] =  shellSort(array.clone());
        timeRecords[0][index - 7] = System.nanoTime() - start;
        start = System.nanoTime();
        comparisonsCounter[1][index - 7] =  insertionSort(array.clone());
        timeRecords[1][index - 7] = System.nanoTime() - start;
        start = System.nanoTime();
        comparisonsCounter[2][index - 7] =  selectionSort(array.clone());
        timeRecords[2][index - 7] = System.nanoTime() - start;
    }

    private static void doRandomSorts(long[][] globalTimeRecords, long[][] globalComparisonsArray, int[] array, int i) {
        long[] localComparisonsArr = new long[10];
        long[] localTimeRecords = new long[10];

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            localComparisonsArr[j] = shellSort(array.clone());
            localTimeRecords[j] =(System.nanoTime() - startTime);
        }
        globalComparisonsArray[0][i-7] = getAverage(localComparisonsArr);
        globalTimeRecords[0][i-7] = getAverage(localTimeRecords);

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            localComparisonsArr[j] = insertionSort(array.clone());
            localTimeRecords[j] =(System.nanoTime() - startTime);
        }
        globalComparisonsArray[1][i-7] = getAverage(localComparisonsArr);
        globalTimeRecords[1][i-7] = getAverage(localTimeRecords);

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            localComparisonsArr[j] = selectionSort(array.clone());
            localTimeRecords[j] =(System.nanoTime() - startTime);
        }
        globalComparisonsArray[2][i-7] = getAverage(localComparisonsArr);
        globalTimeRecords[2][i-7] = getAverage(localTimeRecords);
    }

    private static long insertionSort(int[] array){
        long comparisonCounter = 0;

        for (int i = 0; i < array.length; i++){
            int currentItem = array[i];
            int j = i-1;

            while (j >= 0 && array[j] > currentItem){
                array[j+1] = array[j];
                j -= 1;
                comparisonCounter += 1;
            }
            array[j+1] = currentItem;
            comparisonCounter++;
        }
        return comparisonCounter;
    }

    private static long selectionSort(int[] array){
        long comparisonCounter = 0;

        for (int i = 0; i < array.length; i++){
            int minimum = i;

            for (int j = i+1; j < array.length; j++) {
                comparisonCounter++;
                if (array[j] < array[minimum]) {
                    minimum = j;
                } }

            int temp = array[minimum];
            array[minimum] = array[i];
            array[i] = temp;
        }

        return comparisonCounter;
    }

    private static long shellSort(int[] array) {
        int n = array.length;
        long comparisonCounter = 0;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j = i;
                comparisonCounter++;
                while (j >= gap && array[j - gap] > temp) {
                    comparisonCounter++;
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }

        return comparisonCounter + 1;
    }

    private static void randomizeArray(int[] array, int experiment){
        Random rd = new Random();
        int n = array.length;

        if (experiment == 1){
            for (int i = 0; i < n; i++){
                array[i] = rd.nextInt();
            }
        } else if (experiment == 4) {
            for (int i = 0; i < n; i++){
                array[i] = rd.nextInt(3) + 1;
            }
        }
    }

    private static long getAverage(long[] array){
        long sum = 0;

        for (long item: array
             ) {
            sum += item;
        }

        return sum/array.length;
    }

    private void writeResults(long[][] timeRes, long[][] comparisonRes) {
        fileWriter.println("Time:");
        writeArr(timeRes, fileWriter, 1);
        fileWriter.println("Comparisons:");
        writeArr(comparisonRes, fileWriter, 0);
        fileWriter.println();
    }

    private static void writeArr(long[][] array, PrintWriter fileWriter, int type){

        try {
            String[] sorts = {"Shell", "Insertion", "Selection"};
            for (int i = 0; i < 3; i++){
                fileWriter.print(sorts[i] + ":\t");
                for (int j = 0; j < array[i].length; j++){
                    if (type == 1){
                        double num = array[i][j] / Math.pow(10, 9);
                        String strNum = String.format("%.10f",num);
                        fileWriter.print( strNum + " ");
                    } else {
                        fileWriter.print(array[i][j] + " ");
                    }
                }
                fileWriter.print("\n");
            }

        }catch (Exception e) { System.out.println(e); }

        System.out.println("Success");
    }
}
