package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler extends File {
    private String Path;
    private FileInputStream fileReader;

    public FileHandler(String Path) throws FileNotFoundException {
        super(Path);
        fileReader = new FileInputStream(this);
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
        fileReader.reset();
        fileReader.read(bytes);
        fileReader.reset();
        fileReader.close();

        return bytes;

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
    public byte[] readFileToBytes(long from) throws FileNotFoundException, SecurityException, IOException {

        byte[] bytes = new byte[(int) (super.length() - from)];
        setPointer(from);
        fileReader.read(bytes);
        fileReader.reset();
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

    /**
     * calculate slice number based on file length and config file
     * 
     * @return - number of slice
     */
    public int fileSliceNumber() {
        long byteLen = super.length();
        int slice = (int) byteLen / Config.MAX_FILE_SLICE_SIZE.getIntVal();
        return slice;
    }

    /**
     * slice file with read nBytes,
     * when call this method in several times its return next nBytes in file,
     * pointer will be not reset.
     * if you want to reset the file pointer call reset Method.
     * 
     * @return return nBytes of file
     * @throws IOException
     * @throws FileNotFoundException
     * @see #resetPointer()
     */
    public byte[] sliceFile() throws IOException, FileNotFoundException {
        byte[] slice = new byte[Config.MAX_FILE_SLICE_SIZE.getIntVal()];
        fileReader.read(slice);
        return slice;
    }

    /**
     * slice file with read nBytes,
     * when call this method in several times its return next nBytes in file,
     * pointer will be not reset.
     * if you want to reset the file pointer call reset Method.
     * 
     * @param from the starting position of the file pointer
     * @return return nBytes of file
     * @throws IOException
     * @throws FileNotFoundException
     * @see #resetPointer()
     */
    public byte[] sliceFile(long from) throws IOException, FileNotFoundException {
        byte[] slice = new byte[Config.MAX_FILE_SLICE_SIZE.getIntVal()];
        setPointer(from);
        fileReader.read(slice);
        return slice;
    }

    /**
     * reset file pointer into beginning of file
     * 
     * @throws IOException
     */
    public void resetPointer() throws IOException {
        fileReader.reset();
    }

    /**
     * set file pointer into specified position
     * @param position position of file pointer
     * @throws IOException
     */
    public void setPointer(long position) throws IOException{
        resetPointer();
        fileReader.skipNBytes(position);
    }

    /**
     * if there is any Byte in file to read return true otherwise return false
     * @return there is any Byte in file to read
     * @throws IOException
     */
    public Boolean hasNextByte() throws IOException{
        if(fileReader.available() != 0){
            return true;
        }
        else{
            return false;
        }
    }

}
