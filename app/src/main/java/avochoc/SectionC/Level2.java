package avochoc.SectionC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level2 
{
    private static List<String> dictionary = new ArrayList<>();

    public static void main(String[] args) {
        String csvFilePath = "app/src/main/resources/researchlv2num1.csv";
        loadCSV(csvFilePath);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter word: ");
        String word = scanner.nextLine();
        String lowercaseWord = word.toLowerCase();
        // Perform fuzzy search
        List<String> matches = fuzzySearch(lowercaseWord, 0.7);

        // Print the matching words
        System.out.println("Matching words:");
        for (String match : matches) 
        {
            System.out.println(match);
        }
        scanner.close();
    }

    public static void loadCSV(String filePath) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] words = line.split(",");
                for (String word : words) 
                {
                    dictionary.add(word.trim());
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static List<String> fuzzySearch(String searchTerm, double similarityThreshold) 
    {
        List<String> matches = new ArrayList<>();
        
        //Going through each word in CSV file
        for (String word : dictionary) 
        {
            //Calculating how similar the word typed in is with the current word in CSV file
            double similarity = calculateSimilarity(searchTerm, word);
            //Checking to see if the similarity is above the set amount, if it is add word
            if (similarity >= similarityThreshold) 
            {
                matches.add(word);
            }
        }
        //Returning list of matching words based on Similarity
        return matches;
    }
    
    //Calculating how similar the 2 terms are using Levenshtein distance
    private static double calculateSimilarity(String term1, String term2) 
    {
        //Calculate distance
        int distance = levenshteinDistance(term1, term2);
        //Calculate max length
        int maxLength = Math.max(term1.length(), term2.length());
        //Calculate how similar based on a scale of 0 to 1
        return 1 - (double) distance / maxLength;
    }

    //Calculate
    private static int levenshteinDistance(String s1, String s2) 
    {
        int m = s1.length();
        int n = s2.length();
    
        //Declaring a dynamic table to store all the distances
        int[][] table = new int[m + 1][n + 1];
    
        //Setting base cases for the table
        for (int i = 0; i <= m; i++) 
        {
            table[i][0] = i;
        }
    
        for (int j = 0; j <= n; j++) 
        {
            table[0][j] = j;
        }

        //Calculating distances and filling the table
        for (int i = 1; i <= m; i++) 
        {
            for (int j = 1; j <= n; j++) 
            {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) 
                {
                    //No editing required characters the same
                    table[i][j] = table[i - 1][j - 1];
                } 
                else 
                {
                    //Characters are different, will calculate the minimum editing distance
                    int replace = table[i - 1][j - 1] + 1; //Replacing
                    int insert = table[i][j - 1] + 1; //Inserting
                    int delete = table[i - 1][j] + 1; //Deleting
                    table[i][j] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }
        //Return the 
        return table[m][n];
    }
}
