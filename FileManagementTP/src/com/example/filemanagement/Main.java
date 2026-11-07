
package com.example.filemanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;

public class Main {

    public static void readFileWithBufferedReader(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
    }

    public static void writeFileWithBufferedWriter(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
            bw.newLine();
            bw.write("Ligne ajoutée avec succès.");
            System.out.println("Fichier écrit : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    public static void writeWithPrintWriter(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath))) {
            pw.println("Rapport des employés");
            pw.println("--------------------");
            pw.println("Alice : 25 ans - Engineer");
            pw.println("Bob : 30 ans - Manager");

            if (pw.checkError()) {
                System.err.println("Erreur pendant l'écriture.");
            } else {
                System.out.println("Rapport créé : " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public static void readWithFilesApi(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public static void writeWithFilesApi(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, content.getBytes());
            System.out.println("Fichier créé : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public static void copyFile(String source, String destination) {
        try {
            Files.copy(Paths.get(source), Paths.get(destination),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier copié avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur de copie : " + e.getMessage());
        }
    }

    public static void moveFile(String source, String destination) {
        try {
            Files.move(Paths.get(source), Paths.get(destination),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier déplacé avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur de déplacement : " + e.getMessage());
        }
    }

    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Fichier supprimé : " + filePath);
            } else {
                System.out.println("Fichier introuvable : " + filePath);
            }
        } catch (NoSuchFileException e) {
            System.err.println("Fichier introuvable.");
        } catch (IOException e) {
            System.err.println("Erreur de suppression : " + e.getMessage());
        }
    }

    public static void readBinaryFile(String filePath) {
        try (BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(filePath))) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = bis.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }

            System.out.println("Fichier binaire lu : " + filePath);
            System.out.println("Taille totale : " + totalBytes + " octets");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture binaire : " + e.getMessage());
        }
    }

    public static void writeBinaryFile(String filePath, byte[] data) {
        try (BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(filePath))) {

            bos.write(data);
            bos.flush();

            System.out.println("Fichier binaire écrit : " + filePath);
            System.out.println("Taille écrite : " + data.length + " octets");

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture binaire : " + e.getMessage());
        }
    }

    public static void getFileMetadata(String filePath) {
        try {
            Path path = Paths.get(filePath);

            BasicFileAttributes attrs =
                    Files.readAttributes(path, BasicFileAttributes.class);

            System.out.println("=== Métadonnées du fichier : " + filePath + " ===");
            System.out.println("Taille : " + attrs.size() + " octets");
            System.out.println("Dernière modification : "
                    + attrs.lastModifiedTime().toInstant());
            System.out.println("Est un répertoire : " + attrs.isDirectory());
            System.out.println("Est un fichier : " + attrs.isRegularFile());

        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération des métadonnées : "
                    + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println("TP 12 : Gestion des fichiers en Java");

        System.out.println("\n--- Lecture BufferedReader ---");
        readFileWithBufferedReader("data/employees.txt");

        System.out.println("\n--- Ecriture BufferedWriter ---");
        writeFileWithBufferedWriter(
                "data/output.txt",
                "Bonjour, ceci est un test d'écriture."
        );

        System.out.println("\n--- Ecriture PrintWriter ---");
        writeWithPrintWriter("data/report.txt");

        System.out.println("\n--- Lecture Files API ---");
        readWithFilesApi("data/employees.txt");

        System.out.println("\n--- Ecriture Files API ---");
        writeWithFilesApi(
                "data/nio_output.txt",
                "Alice,25,Engineer\nBob,30,Manager\nCharlie,28,Designer"
        );

        System.out.println("\n--- Copie du fichier ---");
        copyFile("data/employees.txt", "data/employees_backup.txt");

        System.out.println("\n--- Déplacement du fichier ---");
        moveFile("data/nio_output.txt", "data/moved_output.txt");

        System.out.println("\n--- Suppression du fichier ---");
        deleteFile("data/moved_output.txt");

        System.out.println("\n--- Test fichier inexistant ---");
        deleteFile("data/inexistant.txt");

        System.out.println("\n--- Lecture fichier binaire ---");
        readBinaryFile("data/test.png");

        System.out.println("\n--- Ecriture fichier binaire ---");

        try {
            byte[] imageData = Files.readAllBytes(Paths.get("data/test.png"));

            writeBinaryFile("data/copy_binary.bin", imageData);

            System.out.println("Taille originale : " + imageData.length + " octets");
            System.out.println("Taille de la copie : "
                    + Files.size(Paths.get("data/copy_binary.bin")) + " octets");

        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        System.out.println("\n--- Métadonnées du fichier ---");
        getFileMetadata("data/employees.txt");

        System.out.println("\nTraitement terminé.");
    }
}


