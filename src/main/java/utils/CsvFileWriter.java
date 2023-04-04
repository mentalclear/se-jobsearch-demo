package utils;

import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter {
    private String csvFileName;
    private String csvFilePath;
    public CsvFileWriter(String csvFileName) {
        this.csvFileName = csvFileName;
        csvFilePath = String.format("resources/data/companiesHiring_%s.csv", this.csvFileName);
    }
    public void writeDataToCSV(String companyName, String companyUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append(companyName).append(";").append(companyUrl).append("\n");
        try(FileWriter writer = new FileWriter(csvFilePath, true)) {
            writer.append(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
