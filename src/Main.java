import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
        public static List<GameProgress> list = new ArrayList<>();
        public static List<File> files = new ArrayList<>();
    public static void main(String[] args) {

        GameProgress gp1 = new GameProgress(100, 150, 180, 100.2);
        GameProgress gp2 = new GameProgress(200, 250, 280, 200.2);
        GameProgress gp3 = new GameProgress(300, 350, 380, 300.2);
        list.add(gp1);
        list.add(gp2);
        list.add(gp3);
        for (int i = 0; i < list.size(); i++) {
            String s = "S:\\Games\\savegames\\save" + i + ".dat";
            saveGame(i, s);
        }


        String zipPath = "S:\\Games\\savegames\\zip_output.zip";
        zipFiles(zipPath, files);

    }

    private static void zipFiles(String zipPath, List<File> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath));
        ) {

            for (File item : files) {
                String path = "S:\\Games\\savegames\\" + item.getName();
                try (FileInputStream fis = new FileInputStream(path)) {
                    ZipEntry entry = new ZipEntry(item.getName());
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                }
                deleteFile(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteFile(File item) {
        item.delete();
    }

    private static void saveGame(int i, String s) {
        try (FileOutputStream fos = new FileOutputStream(s);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            files.add(new File(s));
            oos.writeObject(list.get(i));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}