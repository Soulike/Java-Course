import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*要求从控制台输入英语单词及单词解释两项数据，
    把录入的数据追加到文件中。要求提供单词查询功能。
    用户输入单词后，从单词库文件中查找，如果存在则输出
    该单词的解释。注意，单词不能有重复，如果重复则覆盖替换
    以前的解释数据。*/
public class Dictionary
{
    private final File file;
    private final String WORD_PREFIX = "%%~WORD||";
    private final String DESCRIPTION_PREFIX = "%%~DESCRIPTION||";
    private final String WORD_SEPARATOR = "--";

    public Dictionary(String filePath) throws IOException
    {
        this.file = new File(filePath);
        if (!file.exists())
        {
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
        }
    }

    /*
     * 文件结构
     * --
     * %%~WORD||balabala
     * %%~DESCRIPTION||balabala
     * --
     * %%~WORD||
     * %%~DESCRIPTION||
     * --
     * */
    public void add(String word, String description) throws IOException
    {
        int lineToDeleteNum = find(word);
        // 先将新的解释放到文件末尾
        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8))
        {
            writer.append(String.format("%s%s\n%s%s\n%s", WORD_PREFIX, word, DESCRIPTION_PREFIX, description, WORD_SEPARATOR));
        }
        // 如果这个单词已经存在
        if (lineToDeleteNum != 0)
        {
            // 创建一个临时文件
            File tempFile = new File(file.getParent() + "/~" + file.getName());
            if (!tempFile.exists())
            {
                File dir = new File(tempFile.getParent());
                dir.mkdirs();
                tempFile.createNewFile();
            }

            try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8); PrintWriter tempWritter = new PrintWriter(tempFile, StandardCharsets.UTF_8))
            {
                int nowLineNum = 0;//当前正在读取的行
                String buffer;
                while (scanner.hasNextLine())
                {
                    nowLineNum++;
                    // 如果到了该删除的行，就跳过
                    if (nowLineNum == lineToDeleteNum)
                    {
                        while (!scanner.nextLine().equals(WORD_SEPARATOR))
                        {
                            nowLineNum++;
                        }
                        scanner.nextLine();
                    }
                    // 如果不是该删除的，就读取并存进临时文件
                    else
                    {
                        buffer = scanner.nextLine();
                        tempWritter.append(buffer);
                    }
                }
            }
            if (!tempFile.renameTo(file))
            {
                throw new IOException("File rename failed");
            }
        }

    }

    // 寻找文件中是否存在这个单词的解释，返回其位置。如果不存在返回0
    // TODO: 检测有BUG
    private int find(String word) throws IOException
    {
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8))
        {
            String buffer;
            int lineNumber = 0;
            boolean find = true;
            while (scanner.hasNextLine())
            {
                find = false;
                buffer = scanner.nextLine();
                lineNumber++;
                if (buffer.substring(0, WORD_PREFIX.length()).equals(WORD_PREFIX))
                {
                    if (buffer.substring(WORD_PREFIX.length()).equals(word))
                    {
                        find = true;
                        break;
                    }
                }
            }
            if (!find)
            {
                lineNumber = 0;
            }
            return lineNumber;
        }
    }

    public void print(String word) throws IOException
    {
        int lineNumber = find(word);
        String buffer;
        if (lineNumber != 0)
        {
            try (Scanner scanner = new Scanner(file))
            {
                for (int i = 0; i < lineNumber - 1; i++)
                {
                    scanner.nextLine();
                }
                buffer = scanner.nextLine();
                System.out.printf("单词: %s\n", buffer.substring(WORD_PREFIX.length()));
                System.out.print("解释: ");
                buffer = scanner.nextLine();
                while (!buffer.equals(WORD_SEPARATOR))
                {
                    System.out.print(buffer.substring(DESCRIPTION_PREFIX.length()));
                    buffer = scanner.nextLine();
                }
                System.out.println();
            }
        }
        else
        {
            System.out.println("单词不存在");
        }
    }

    public static void main(String[] args)
    {
        try
        {
            Dictionary dictionary = new Dictionary("src/2nd/5/directory.dat");
            final int ADD_WORD = 0;
            final int FIND_WORD = 1;
            Scanner in = new Scanner(System.in);
            int operation;
            while (true)
            {
                System.out.printf("请输入要进行的操作\t%d-添加单词\t%d-查询单词\n", ADD_WORD, FIND_WORD);
                operation = in.nextInt();
                in.nextLine();
                if (operation != ADD_WORD && operation != FIND_WORD)
                {
                    System.out.println("操作无效。请重新输入");
                    continue;
                }
                else if (operation == ADD_WORD)
                {
                    String word, description;
                    System.out.print("请输入单词: ");
                    word = in.nextLine().toLowerCase();
                    System.out.print("请输入解释: ");
                    description = in.nextLine();
                    dictionary.add(word, description);
                    System.out.println("输入完毕。按任意键返回主菜单");
                    in.nextLine();
                    continue;
                }
                else if (operation == FIND_WORD)
                {
                    String word;
                    System.out.print("请输入单词: ");
                    word = in.nextLine().toLowerCase();
                    dictionary.print(word);
                    System.out.println("查询完毕。按任意键返回主菜单");
                    in.nextLine();
                    continue;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}