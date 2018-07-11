import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/*编写程序求出1万以内的所有素数，并将这些素数输出到一个文本文件中，每行文本只包含一个素数数据。
    该文本文件内容要求可以用记事本程序来查看。*/
public class PrimeNumberSaver
{
    private List<Integer> primeNumberList;
    private final int LIMIT;

    public PrimeNumberSaver(int limit)
    {
        LIMIT = limit;
        primeNumberList = new ArrayList<>();
        getPrimeNumbers();
    }

    private void getPrimeNumbers()
    {
        boolean isPrimeNumber;
        for (int i = 2; i < LIMIT; i++)
        {
            isPrimeNumber = true;
            for (int j = 2; j <= Math.sqrt(i); j++)
            {
                if (i % j == 0)
                {
                    isPrimeNumber = false;
                    break;
                }
            }
            if (isPrimeNumber)
            {
                primeNumberList.add(i);
            }
        }
    }

    public void savePrimeNumbers(String outputFilePathStr, Charset charset) throws IOException
    {
        final Path filePath = Paths.get(outputFilePathStr);
        if (Files.notExists(filePath))
        {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
        try (PrintWriter writer = new PrintWriter(filePath.toFile(), charset))
        {
            for (int num : primeNumberList)
            {
                writer.println(num);
            }
        }
        System.out.printf("共输出%d以下素数%d个", LIMIT, primeNumberList.size());
    }

    public static void main(String[] args)
    {
        try
        {
            PrimeNumberSaver primeNumberSaver = new PrimeNumberSaver(10000);
            primeNumberSaver.savePrimeNumbers("src/2nd/3/primeNumbers.txt", StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
