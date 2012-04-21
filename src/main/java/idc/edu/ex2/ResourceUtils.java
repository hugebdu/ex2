package idc.edu.ex2;

import java.io.IOException;

import static com.google.common.base.Charsets.ISO_8859_1;
import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.readLines;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/21/12
 */
public abstract class ResourceUtils
{
    public static String[] listPointFiles()
    {
        try
        {
            return readLines(getResource("input/files_list.txt"), ISO_8859_1).toArray(new String[0]);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load files list", e);
        }
    }
}
