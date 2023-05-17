package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler extends File {
    private String Path;

    public FileHandler(String Path) {
        super(Path);
        this.Path = Path;
    }

    /**
     * convert a file data into a byte array
     * 
     * @param filePath path to the file
     * @return file data in byte array
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if an I/O error occurs
     * @throws SecurityException     if application is not allowed to access the
     *                               file
     */
    public byte[] readFileToBytes() throws FileNotFoundException, SecurityException, IOException {

        byte[] bytes = new byte[(int) super.length()];

        FileInputStream fileReader = new FileInputStream(this);

        // read file into bytes[]
        fileReader.read(bytes);
        fileReader.close();

        return bytes;

    }

    /**
     * write a byte data into a file
     * 
     * @param bytes - byte array to write
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if an I/O error occurs
     * @throws SecurityException     if application is not allowed to access the
     *                               file
     */
    public void writeBytesToFile(byte[] bytes) throws FileNotFoundException, SecurityException, IOException {

        try (FileOutputStream fos = new FileOutputStream(this.Path)) {
            fos.write(bytes);
        }
    }

    /**
     * get file format like mp4, mp3, txt, ...
     * 
     * @return - file format without "."
     */
    public String getFormat() {
        String name = super.getName();
        String[] nameAndFormat = name.split(".");
        String format = nameAndFormat[nameAndFormat.length - 1];
        return format;
    }

    /**
     * delete file format ex: test.mp4 -> test
     * 
     * @return file name without format
     */
    public String getNameWithoutFormat() {
        String[] tempName = super.getName().split(".");
        String name = "";
        int i = 1;
        for (String word : tempName) {
            if (i == tempName.length) {
                break;
            }

            name = name + "." + word;
        }
        return name;
    }

}
