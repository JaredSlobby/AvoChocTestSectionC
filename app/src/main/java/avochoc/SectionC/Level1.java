package avochoc.SectionC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Level1 
{
    public static void main(String[] args) 
    {
        String csvFilePath = "app/src/main/resources/researchlv1.csv";
        loadCSV(csvFilePath);
    }

    public static void loadCSV(String filePath) 
    {
        List<BigInteger> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                BigInteger result = new BigInteger(line);
                numbers.add(result);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger number : numbers) 
        {
            sum = sum.add(number);
        }
        //Printing sum of CSV values
        System.out.println("Sum of all numbers in CSV: " + sum);
    }
}