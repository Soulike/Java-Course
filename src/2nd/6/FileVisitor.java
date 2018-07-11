import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class FileVisitor
{
    private final Path folderPath;

    public FileVisitor(String folderPathStr)
    {
        this.folderPath = Paths.get(folderPathStr);
    }

    public FileVisitor(Path folderPath)
    {
        this.folderPath = folderPath;
    }

    private void getAllFilesRecursive(Path folderPath, List<MyFile> fileList) throws IOException
    {
        // 得到文件夹下的列表
        Stream<Path> pathStream = Files.list(folderPath);

        pathStream.forEach((path ->
        {
            File file = path.toFile();
            if (file.isFile())
            {
                fileList.add(new MyFile(file.getName(), file.getAbsolutePath(), file.length()));
            }
            else if (file.isDirectory())
            {
                try
                {
                    getAllFilesRecursive(file.toPath(), fileList);
                }
                catch (IOException e)
                {
                    System.err.println("获取文件列表失败");
                    fileList.clear();
                }
            }
        }));
    }

    public List<MyFile> getAllFiles() throws IOException
    {
        List<MyFile> fileList = new ArrayList<>();
        getAllFilesRecursive(folderPath, fileList);
        return fileList;
    }
}
