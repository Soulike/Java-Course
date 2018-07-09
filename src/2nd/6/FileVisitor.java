import java.nio.file.*;
import java.util.*;

public class FileVisitor
{
    private final Path folderPath;

    public FileVisitor(String folderPathStr)
    {
        this.folderPath = Paths.get(folderPathStr);
    }

    private void getAllFilesRecursive(Path folderPath, List<File> fileList)
    {
        java.io.File folder = folderPath.toFile();
        // 得到文件夹下的列表
        java.io.File[] files = folder.listFiles();
        // 遍历，如果是目录就递归下去。如果是文件就放进list
        for (java.io.File file : files)
        {
            if (file.isFile())
            {
                fileList.add(new File(file.getName(), file.getAbsolutePath(), file.length()));
            }
            else if (file.isDirectory())
            {
                getAllFilesRecursive(file.toPath(), fileList);
            }
        }
    }

    public List<File> getAllFiles()
    {
        List<File> fileList = new ArrayList<>();
        getAllFilesRecursive(folderPath, fileList);
        return fileList;
    }

}
