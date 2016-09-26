package ru.icc.cells.ssdc.evaluation;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * Created by Alexey Shigarov on 27.06.2016.
 */
public class Evaluator {
    public static void main(String[] args) throws IOException {
        File resultDirectory = new File(args[0]);
        File groundTruthDirectory = new File(args[1]);

        File[] listOfExcelFiles = resultDirectory.listFiles(e -> e.getName().endsWith(".xlsx"));
        Evaluator e = new Evaluator();

        for (File resultExcelFile : listOfExcelFiles) {
            if (resultExcelFile.isFile()) {
                System.out.println("FILE: " + resultExcelFile.getName());
                File gtExcelFile = new File(groundTruthDirectory.getAbsolutePath() + '\\' + resultExcelFile.getName());
                e.load(resultExcelFile, gtExcelFile);
                e.evaluate();
            }
        }

        System.out.println("RESULTS:");
        System.out.println(RESULT.trace());
    }

    private Workbook resWorkbook;
    private Workbook gtWorkbook;

    private Evaluator() {
    }

    private void load(File resultWorkbookFile, File groundTruthWorkbookFile) throws IOException {
        loadResultWorkbook(resultWorkbookFile);
        loadGroundTruthWorkbook(groundTruthWorkbookFile);
    }

    private void loadResultWorkbook(File resultWorkbookFile) throws IOException {
        resWorkbook = loadWorkbook(resultWorkbookFile);
    }

    private void loadGroundTruthWorkbook(File groundTruthWorkbookFile) throws IOException {
        gtWorkbook = loadWorkbook(groundTruthWorkbookFile);
    }

    private Workbook loadWorkbook(File excelFile) throws IOException {
        Workbook workbook = null;
        FileInputStream fin = new FileInputStream(excelFile);
        workbook = new XSSFWorkbook(fin);

        return workbook;
    }

    private static final EvalView RESULT = new EvalView();

    private void evaluate() throws IllegalStateException {
        if (null == resWorkbook)
            throw new IllegalStateException("The result workbook has not been loaded");

        if (null == gtWorkbook)
            throw new IllegalStateException("The ground truth workbook has not been loaded");

        EvalBox box = EvalBox.evaluate(gtWorkbook, resWorkbook);
        EvalView view = box.createEvalView();
        System.out.print(view.trace());
        System.out.println(box.traceErrors());
        RESULT.increment(view);
    }
}
