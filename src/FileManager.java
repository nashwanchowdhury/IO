import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class FileManager {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the path of the directory you would like to see: ");
        String directorychoice = scan.nextLine();

        Path input = Paths.get(directorychoice);

        boolean x = true;
        while (x) {
            System.out.println("Choose an option: ");
            System.out.println("1. Display directory contents");
            System.out.println("2. Copy a file");
            System.out.println("3. Move a file");
            System.out.println("4. Delete a file");
            System.out.println("5. Create a directory");
            System.out.println("6. Delete a directory");
            System.out.println("7. Search for a specific file");
            System.out.println("8. Exit");

            int response = scan.nextInt();
            scan.nextLine();

            switch (response) {
                case 1:
                    displayDirectory(input);
                    break;
                case 2:
                    System.out.println("Enter the file name you want to copy: ");
                    String source = scan.nextLine();
                    System.out.println("Enter the name of the copied file: ");
                    String output = scan.nextLine();
                    copyFile(input.resolve(source), input.resolve(output));
                    break;
                case 3:
                    System.out.println("Enter the source file name: ");
                    String src = scan.nextLine();
                    System.out.println("Enter the target file name: ");
                    String target = scan.nextLine();
                    moveFile(input.resolve(src), input.resolve(target));
                    break;
                case 4:
                    System.out.println("Enter the file name to delete: ");
                    String deletedFile = scan.nextLine();
                    deleteFile(input.resolve(deletedFile));
                    break;
                case 5:
                    System.out.println("Enter the name of the new directory: ");
                    String newDirectory = scan.nextLine();
                    createDirectory(input.resolve(newDirectory));
                    break;
                case 6:
                    System.out.println("Enter the name of the directory you want to delete: ");
                    String deleteDirectory = scan.nextLine();
                    deleteDirectory(input.resolve(deleteDirectory));
                    break;
                case 7:
                    System.out.println("Enter your search keyword: ");
                    String keyword = scan.nextLine();
                    searchDirectories(input, keyword);
                case 8:
                    x = false;
                    break;
            }
        }
    }
    private static void displayDirectory(Path input) {
        try { DirectoryStream<Path> stream = Files.newDirectoryStream(input);
            System.out.println("Contents: ");
            for (Path files: stream) {
                long size = files.toFile().length();
                Date lastModified = new Date(files.toFile().lastModified());
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                System.out.println("File Name: " + files.getFileName());
                System.out.println("File Size: " + size);
                System.out.println("Last Modified: " + date.format(lastModified));
                System.out.println("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //function used to copy file
    private static void copyFile(Path input, Path output) {
        try {
            Files.copy(input, output, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The specified file was able to be copied");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function used to delete file
    private static void deleteFile(Path file) {
        try {
            Files.delete(file);
            System.out.println("The specified file was able to be deleted.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function used to move file
    private static void moveFile(Path input, Path output) {
        try {
            Files.move(input, output, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function used to create new directory
    private static void createDirectory (Path input) {
        try {
            Files.createDirectory(input);
            System.out.println("The specified directory was created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //function used to delete specified directory
    private static void deleteDirectory (Path input) {
        try {
            Files.delete(input);
            System.out.println("The specified directory was deleted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function used to search directories for a keyword
    private static void searchDirectories (Path input, String keyword){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(input, keyword)) {
            System.out.println("Your results: ");
            for (Path files : stream) {
                System.out.println(files.getFileName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

