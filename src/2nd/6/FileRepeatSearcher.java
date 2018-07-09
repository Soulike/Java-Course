import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/*通过命令行参数输入一个文件夹的路径名称，然后编写程序找出该文件夹下文件名称重复并且文件大小也一样的文件，
    如果没有“重复文件”，则输出“没有重复文件”的提示，如果有，需要输出文件名称，和文件所在的文件夹路径（绝对路径）。
	提示，需要遍历该文件夹下所有子文件夹，设计一个文件类，属性包括文件名称，文件路径，文件大小，然后进行“重复”
	判断，如果文件重复，则需要记录并输出，有可能有文件名重复，但是文件大小不一样，重复的文件可能不止2个，可能
	在不同的子文件夹下有多个文件重复。*/
public class FileRepeatSearcher
{
    private final Path folderPath;

    public FileRepeatSearcher(String folderPath) throws IllegalArgumentException
    {
        this.folderPath = Paths.get(folderPath);
        if (Files.notExists(this.folderPath))
        {
            throw new IllegalArgumentException();
        }
    }

    private List<File> getAllFiles() throws IOException
    {
        List<File> fileList = new LinkedList<>();
        FileVisitor fileVisitor = new FileVisitor(fileList);
        Files.walkFileTree(folderPath, fileVisitor);
        return fileList;
    }

    public List<File> getRepeatedFiles() throws IOException
    {
        List<File> fileList = getAllFiles();
        List<File> fileListCopy = new ArrayList<>(fileList);
        List<File> repeatedFileList = new ArrayList<>();
        for (File file : fileList)
        {
            fileListCopy.remove(file);
            if (fileListCopy.contains(file) || repeatedFileList.contains(file))
            {
                repeatedFileList.add(file);
            }
        }
        return repeatedFileList;
    }

    public static void main(String[] args)
    {
        try
        {
            if (args.length != 1)
            {
                throw new IllegalArgumentException();
            }
            else
            {
                FileRepeatSearcher searcher = new FileRepeatSearcher(args[0]);
                List<File> repeatedFileList = searcher.getRepeatedFiles();
                if (repeatedFileList.size() == 0)
                {
                    System.out.println("没有重复文件");
                }
                else
                {
                    for (File file : repeatedFileList)
                    {
                        file.showFile();
                    }
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("参数无效。请指定有效且存在文件夹");
        }
        catch (IOException e)
        {
            System.out.println("IO 错误");
            e.printStackTrace();
        }
    }
}
