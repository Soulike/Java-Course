import java.nio.file.Path;
import java.nio.file.Paths;

//文件名称，文件路径，文件大小
public class File implements Cloneable
{
    private final String name;
    private final Path path;
    private final long size;

    public File(String name, String pathStr, long size)
    {
        this.name = name;
        this.path = Paths.get(pathStr);
        this.size = size;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        else if (this.getClass() != obj.getClass())
        {
            return false;
        }
        else
        {
            File file = (File) obj;
            return this.name.equals(file.name) && this.size == file.size;
        }
    }

    public String getName()
    {
        return name;
    }

    public long getSize()
    {
        return size;
    }

    public String getPath()
    {
        return path.toAbsolutePath().toString();
    }

    public void showFile()
    {
        System.out.printf("文件名: %s, 文件路径: %s, 文件大小: %d字节\n", getName(), getPath(), getSize());
    }
}
