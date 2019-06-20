package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.filters.Aquarelle;
import sample.filters.Filter;
import sample.filters.Stamping;
import sample.jpeg.JpegEncoder;
import sample.bmp.Bmp;
import sample.bmp.BMPDecoder;
import sample.bmp.BmpWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    private Bmp bmp = new Bmp();

    @FXML
    private Pane panel;

    @FXML
    private MenuBar mainMenu;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;


    @FXML
    void initialize() {
        Menu fileMenu = new Menu("Файл");
        Menu filterMenu = new Menu("Выбрать фильтр");

        MenuItem openFileItem = new MenuItem("Открыть файл");
        MenuItem saveBMPItem = new MenuItem("Сохранить в BMP");
        MenuItem saveJPEGItem = new MenuItem("Сохранить в JPEG");

        MenuItem aquarelleItem = new MenuItem("Акварелизация");
        MenuItem stampingItem = new MenuItem("Тиснение");
        MenuItem mosaicItem = new MenuItem("Мозаика");

        openFileItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            StringBuilder fileName = new StringBuilder();

            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("BMP images (*.bmp)", "*.bmp"));
            File file = fileChooser.showOpenDialog(panel.getScene().getWindow());
            if (file != null) {
                clearAll();
                fileName.append(file.getAbsolutePath());
                try {
                    decode(fileName.toString());
                    imageView1.setFitWidth(bmp.getWidth());
                    imageView1.setFitHeight(bmp.getHeight());

                    //printMatrix(bmp.getPixels());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        aquarelleItem.setOnAction(e->{
            Aquarelle aquarelle = new Aquarelle();
            Filter filter = new Filter();
            imageView2.setFitHeight(imageView1.getFitHeight());
            imageView2.setFitWidth(imageView1.getFitWidth());

            drawImage(filter.process(aquarelle.process(bmp.getPixels())), imageView2);
        });

        stampingItem.setOnAction(e->{
            Stamping stamping = new Stamping();
            stamping.process(bmp.getPixels());
            imageView2.setFitHeight(imageView1.getFitHeight());
            imageView2.setFitWidth(imageView1.getFitWidth());

            drawImage(stamping.process(bmp.getPixels()), imageView2);
        });

        saveBMPItem.setOnAction(e->{
            if(bmp.getSize() != 0) {
                FileChooser fileChooser = new FileChooser();

                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("BMP images (*.bmp)", "*.bmp"));
                File file = fileChooser.showSaveDialog(panel.getScene().getWindow());
                try {
                    BmpWriter.saveBMP(bmp.getPixels(), file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }else{
                showAlert("Картинки нет");
            }
        });

        saveJPEGItem.setOnAction(e->{
            if(bmp.getSize() != 0) {
                FileChooser fileChooser = new FileChooser();

                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("JPEG images (*.jpg)", "*.jpg"));
                File file = fileChooser.showSaveDialog(panel.getScene().getWindow());
                try {
                    JpegEncoder.saveJpeg(bmp.getPixels(), bmp.getWidth(), bmp.getHeight(), file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else{
                showAlert("Картинки нет");
            }
        });

        fileMenu.getItems().addAll(openFileItem, saveBMPItem, saveJPEGItem);
        filterMenu.getItems().addAll(aquarelleItem, stampingItem, mosaicItem);

        mainMenu.getMenus().addAll(fileMenu, filterMenu);
    }

    private void clearAll(){
        imageView1.setImage(null);
        imageView2.setImage(null);
    }

    public void decode(String fileName) throws Exception {
        File file = new File(fileName);
        BMPDecoder decoderBmp = new BMPDecoder();
        bmp = decoderBmp.loadBmp(file);
        drawImage(bmp.getPixels(), imageView1);
    }

    public void drawImage(int[][] pixels, ImageView imageView) {
        BufferedImage image = JpegEncoder.encode(pixels, pixels[0].length, pixels.length);
        imageView.setFitWidth(pixels[0].length);
        imageView.setFitHeight(pixels.length);
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Attention");
        alert.setContentText(text);

        alert.showAndWait();
    }

    public void printMatrix(int[][] matrix) {
        for(int[] line : matrix) {
            for(int i : line) {
                System.out.print(i);
            }
            System.out.println();
        }
    }
}
