import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;


public class Main {

    public static void main(String[] args){
        try {
            PrintWriter fileWriter =new PrintWriter(new BufferedWriter(new FileWriter("./results.txt")));

            experiment1(fileWriter);
            fileWriter.print("\n");
            experiment2(fileWriter);
            fileWriter.print("\n");
            experiment3(fileWriter);
            fileWriter.print("\n");
            experiment4(fileWriter);
            fileWriter.close();
        } catch (Exception e) { System.out.println(e); }
    }

    private static void experiment1(PrintWriter fileWriter){
        long[][] allTimeRecords = new long[3][11];
        long[][] allComparisonsArray = new long[3][11];
        long started = System.nanoTime();

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] array = new int[n];
            randomizeArray(array, 1);
            doRandomSorts(allTimeRecords, allComparisonsArray, array, i);
        }
        writeResults(allTimeRecords, allComparisonsArray, fileWriter);
        System.out.println(System.nanoTime() - started);
    }

    private static void experiment2(PrintWriter fileWriter){
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
        writeResults(timeRecords, comparisonsCounter, fileWriter);
        System.out.println(System.nanoTime() - started);
    }

    private static void experiment3(PrintWriter fileWriter){
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
        writeResults(timeRecords, comparisonsCounter, fileWriter);
        System.out.println(System.nanoTime() - started);
    }

    private static void experiment4(PrintWriter fileWriter){
        long[][] allTimeRecords = new long[3][11];
        long[][] allComparisonsArray = new long[3][11];

        for (int i = 7; i < 18; i++){
            int n = (int) Math.pow(2, (double) i);
            int[] arr = new int[n];
            randomizeArray(arr, 4);
            doRandomSorts(allTimeRecords, allComparisonsArray, arr, i);
        }
        writeResults(allTimeRecords, allComparisonsArray, fileWriter);
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

    private static void doRandomSorts(long[][] allTimeRecords, long[][] allComparisonsArray, int[] array, int i){
        long[] comparisonsArr = new long[10];
        long[] timeRecords = new long[10];

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            comparisonsArr[j] = shellSort(array.clone());
            timeRecords[j] =(System.nanoTime() - startTime);
        }
        allComparisonsArray[0][i-7] = getAverage(comparisonsArr);
        allTimeRecords[0][i-7] = getAverage(timeRecords);

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            comparisonsArr[j] = insertionSort(array.clone());
            timeRecords[j] =(System.nanoTime() - startTime);
        }
        allComparisonsArray[1][i-7] = getAverage(comparisonsArr);
        allTimeRecords[1][i-7] = getAverage(timeRecords);

        for (int j = 0; j < 10; j++) {
            long startTime = System.nanoTime();
            comparisonsArr[j] = selectionSort(array.clone());
            timeRecords[j] =(System.nanoTime() - startTime);
        }
        allComparisonsArray[2][i-7] = getAverage(comparisonsArr);
        allTimeRecords[2][i-7] = getAverage(timeRecords);
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

    private static void writeResults(long[][] timeRes, long[][] comparisonRes, PrintWriter fileWriter){
        fileWriter.println("Time:");
        writeArr(timeRes, fileWriter);
        fileWriter.println("Comparisons:");
        writeArr(comparisonRes, fileWriter);
    }

    private static void writeArr(long[][] array, PrintWriter fileWriter){

        try {
            String[] sorts = {"Shell", "Insertion", "Selection"};
            for (int i = 0; i < 3; i++){
                fileWriter.print(sorts[i] + ":\t");
                for (int j = 0; j < array[i].length; j++){
                    fileWriter.print(array[i][j] + " ");
                }
                fileWriter.print("\n");
            }

        }catch (Exception e) { System.out.println(e); }

        System.out.println("Success");
    }
}
