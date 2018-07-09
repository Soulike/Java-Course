import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FileVisitor extends SimpleFileVisitor<Path>
{
    private List<File> fileList;

    public FileVisitor(List<File> fileList)
    {
        super();
        this.fileList = fileList;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
    {
        java.io.File fileObj = file.toFile();
        File customFile = new File(fileObj.getName(), fileObj.getAbsolutePath(), fileObj.length());
        fileList.add(customFile);
        return FileVisitResult.CONTINUE;
    }
}
