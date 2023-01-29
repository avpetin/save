import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress[] gp_save = new GameProgress[3];
        gp_save[0] = new GameProgress(100, 20, 3, 240.5);
        gp_save[1]= new GameProgress(80, 21, 4, 234.2);
        gp_save[2] = new GameProgress(70, 18, 5, 221.7);

        List<File> savesList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            File fileToSave = new File("D:/Games/savegames/save".concat(Integer.toString(i + 1)).concat(".dat"));
            savesList.add(fileToSave);
            saveGame(fileToSave.getPath(), gp_save[i]);
        }

        zipFiles("D:/Games/savegames", savesList);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        File file = new File(path);
        try {
            if(file.createNewFile() || (file.exists())) {
                try (FileOutputStream fos = new FileOutputStream(file.getPath());
                    ObjectOutputStream oos = new ObjectOutputStream(fos)){
                    oos.writeObject(gameProgress);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else{
                System.out.println("Невозможно создать файл");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, List<File> stringList){
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path.concat("/saves.zip")))){
            for(File filePath : stringList){
                ZipEntry enrty = new ZipEntry(filePath.getAbsolutePath());
                zos.putNextEntry(enrty);
                try (FileInputStream fis = new FileInputStream(filePath.getPath())) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);

                }
            }
            zos.closeEntry();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}