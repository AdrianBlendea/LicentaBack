package com.example.programingappapi.service;

import com.example.programingappapi.controller.SolutionController;
import com.example.programingappapi.entity.Problem;
import com.example.programingappapi.entity.Solution;
import com.example.programingappapi.exception.NotEnoughSolutionsForReportException;
import de.jplag.JPlag;
import de.jplag.JPlagResult;
import de.jplag.Language;
import de.jplag.exceptions.ExitException;
import de.jplag.java.JavaLanguage;
import de.jplag.options.JPlagOptions;
import de.jplag.python3.PythonLanguage;
import de.jplag.reporting.reportobject.ReportObjectFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import de.jplag.cpp.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlagiarismService {


    private final String folder = "C:/licentaback/plagiarism";


    public File getPlagiarismReport(Long problemId, String language) {
        File solutionFolder = new File(folder + "/" + problemId + "/" + language);

        if (solutionFolder.exists() && solutionFolder.isDirectory()) {
            // List all files in the directory
            File[] files = solutionFolder.listFiles();

            // Check if there are at least 2 files
            if (files == null || files.length < 2) {
                throw new NotEnoughSolutionsForReportException("not enough solutions to create report");
            }
        } else {
            throw new NotEnoughSolutionsForReportException("solutions folder not existent");
        }


        switch (language) {
            case "python":
                return createPythonProblemReport(problemId);

            case "java":
                return createJAVAProblemReport(problemId);

            case "cpp":
                return createCProblemReport(problemId);

            default:
                return null;

        }


    }

    public File createCProblemReport(Long problemId) {
        Language language = new CPPLanguage();
        Set<File> submissionDirectories = Set.of(
                new File(folder + "/" + problemId + "/cpp"));
        File baseCode = new File(folder + "/basecode.cpp");
        JPlagOptions options = new JPlagOptions(language,
                submissionDirectories, Set.of())
                .withBaseCodeSubmissionDirectory(baseCode)
                .withMinimumTokenMatch(1);
        try {
            JPlagResult result = JPlag.run(options);

            File outputDir = new File(folder + "/" + problemId + "/cpp"
                    + problemId + ".zip");

            ReportObjectFactory reportObjectFactory =
                    new ReportObjectFactory(outputDir);
            reportObjectFactory.createAndSaveReport(result);

            System.out.println("Report generated successfully!");
            return outputDir;
        } catch (ExitException e) {
            System.err.println("JPlag exit exception: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("File not found exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
        }

    public File createJAVAProblemReport(Long problemId) {
        Language language = new JavaLanguage();
        Set<File> submissionDirectories = Set.of(new File(folder + "/" + problemId + "/java"));
        File baseCode = new File(folder + "/basecode.java");
        JPlagOptions options = new JPlagOptions(language, submissionDirectories, Set.of()).withBaseCodeSubmissionDirectory(baseCode).withMinimumTokenMatch(1);

        try {
            JPlagResult result = JPlag.run(options);

            File outputDir = new File(folder + "/" + problemId + "/java" + problemId + ".zip");

            ReportObjectFactory reportObjectFactory = new ReportObjectFactory(outputDir);
            reportObjectFactory.createAndSaveReport(result);

            System.out.println("Report generated successfully!");
            return outputDir;
        } catch (ExitException e) {
            System.err.println("JPlag exit exception: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (FileNotFoundException e) {
            System.err.println("File not found exception: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
        return null;
    }

    public File createPythonProblemReport(Long problemId) {
        Language language = new PythonLanguage();
        Set<File> submissionDirectories = Set.of(new File(folder + "/" + problemId + "/python"));
        File baseCode = new File(folder + "/basecode.py");
        JPlagOptions options = new JPlagOptions(language, submissionDirectories, Set.of()).withBaseCodeSubmissionDirectory(baseCode).withMinimumTokenMatch(1);


        try {
            JPlagResult result = JPlag.run(options);

            File outputDir = new File(folder + "/" + problemId + "/python" + problemId + ".zip");

            ReportObjectFactory reportObjectFactory = new ReportObjectFactory(outputDir);
            reportObjectFactory.createAndSaveReport(result);

            System.out.println("Report generated successfully!");
            return outputDir;
        } catch (ExitException e) {
            System.err.println("JPlag exit exception: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (FileNotFoundException e) {
            System.err.println("File not found exception: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
        return null;
    }

    public void createNewFolderForProblem(Problem problem) {
        Long problemId = problem.getId();

        if (problemId == null) {
            throw new IllegalArgumentException("Problem ID cannot be null");
        }

        // Define the base folder path using the problem ID
        Path baseFolderPath = Paths.get(folder + "/" + problemId);

        // Define subfolder paths
        Path javaFolderPath = baseFolderPath.resolve("java");
        Path cppFolderPath = baseFolderPath.resolve("cpp");
        Path pythonFolderPath = baseFolderPath.resolve("python");

        try {
            // Create the base directory if it does not exist
            if (!Files.exists(baseFolderPath)) {
                Files.createDirectory(baseFolderPath);
                System.out.println("Base directory created: " + baseFolderPath.toAbsolutePath());
            } else {
                System.out.println("Base directory already exists: " + baseFolderPath.toAbsolutePath());
            }

            // Create the subdirectories if they do not exist
            if (!Files.exists(javaFolderPath)) {
                Files.createDirectory(javaFolderPath);
                System.out.println("Subdirectory created: " + javaFolderPath.toAbsolutePath());
            }

            if (!Files.exists(cppFolderPath)) {
                Files.createDirectory(cppFolderPath);
                System.out.println("Subdirectory created: " + cppFolderPath.toAbsolutePath());
            }

            if (!Files.exists(pythonFolderPath)) {
                Files.createDirectory(pythonFolderPath);
                System.out.println("Subdirectory created: " + pythonFolderPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to create directories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createNewFileForSubmission(Solution solution) {
        String problemFolder = folder + "/" + solution.getProblem().getId().toString() + "/" + solution.getLanguage();

        // Create the directory if it doesn't exist
        File directory = new File(problemFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Map the language to the correct file extension
        String fileExtension = "";
        switch (solution.getLanguage().toLowerCase()) {
            case "cpp":
                fileExtension = ".cpp";
                break;
            case "java":
                fileExtension = ".java";
                break;
            case "python":
                fileExtension = ".py";
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + solution.getLanguage());
        }

        // Create the file name
        String fileName = solution.getUser().getEmail() + fileExtension;
        File file = new File(problemFolder + "/" + fileName);

        // Write the solution content to the file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(solution.getSolution());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
