package Code;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileInteraction{
    public static Movie[] movies;
    public static long lines;
    public static final String fileLocation = "Code/main.csv";

    public static Movie[] read() throws Exception{
        Scanner scan = new Scanner(new File(fileLocation));  // scans file
            Path path = Paths.get(fileLocation);    // path class object
            lines = Files.lines(path).count();      // number of lines
            Movie[] arr = new Movie[(int)lines];  // new player array that gets returned
            int c = 0;
            while(scan.hasNext()){
                String temp = scan.nextLine();
                String[] tempArray = temp.split(",");
                arr[c] = new Movie();
                arr[c].setTitle(tempArray[0]);
                arr[c].setGenre(tempArray[1]);                   // scan and splits each line into an array by its commas 
                c++;
            }
            scan.close();
            return arr;
        }

        public static void main(String[] args) throws Exception { 
        // Call the read() method in a static way
        Movie[] arr = FileInteraction.read();
        
        for (int i = 0; i < arr.length; i++) { 
            System.out.println(arr[i].getTitle() + ", " + arr[i].getGenre());
        }
    }
}
