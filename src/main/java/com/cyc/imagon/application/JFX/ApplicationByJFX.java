package com.cyc.imagon.application.JFX;

import com.cyc.imagon.View.AbstractVScene;
import com.cyc.imagon.View.ImageScene;
import com.cyc.imagon.View.InitScene;
import com.cyc.imagon.main.MainModule;
import io.vproxy.vfx.control.globalscreen.GlobalScreenUtils;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.manager.task.TaskManager;
import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.button.FusionImageButton;
import io.vproxy.vfx.ui.layout.HPadding;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.loading.VProgressBar;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneGroup;
import io.vproxy.vfx.ui.scene.VSceneHideMethod;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.scene.VSceneShowMethod;
import io.vproxy.vfx.ui.stage.VStage;
import io.vproxy.vfx.util.FXUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.cyc.imagon.main.MainModule.getCount;
import static com.cyc.imagon.main.MainModule.loadFromHardDrive;

/**
 * ClassName: ApplicationByVFX
 * Package: com.cyc.imagon.application.VFX
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/17 12:00
 * @Version 1.0
 */
@Slf4j
@SpringBootApplication
public class ApplicationByJFX extends Application {

    private int totalCount = 0;
    private int curCount = 0;
    private final List<AbstractVScene> mainScenes = new ArrayList<>();
    private VSceneGroup sceneGroup;
    public static MainModule mainModule = new MainModule();

    @Override
    public void start(Stage primaryStage) throws Exception {
        val stage = new VStage(primaryStage) {
            @Override
            public void close() {
                super.close();
                TaskManager.get().terminate();
                GlobalScreenUtils.unregister();
            }
        };
        stage.getInitialScene().enableAutoContentWidthHeight();
        stage.setTitle("Imagon");
        primaryStage.getIcons().add(new Image("images/icon.jpg"));
        mainScenes.add(new InitScene());
        AbstractVScene initialScene = mainScenes.get(0);
        sceneGroup = new VSceneGroup(initialScene);
        for (AbstractVScene s : mainScenes) {
            if (s == initialScene) {
                continue;
            }
            sceneGroup.addScene(s);
        }
        val navigatePane = new FusionPane();

        navigatePane.getNode().setPrefHeight(60);

        FXUtils.observeHeight(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -10 - 60 - 5 - 10);
        FXUtils.observeWidth(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -20);
        FXUtils.observeWidth(stage.getInitialScene().getContentPane(), navigatePane.getNode(), -20);

        loadFromHardDrive();

        val prevButton = new FusionButton("<< Previous") {{
            setPrefWidth(150);
            setPrefHeight(navigatePane.getNode().getPrefHeight() - FusionPane.PADDING_V * 2);
            setOnlyAnimateWhenNotClicked(true);
            val current = sceneGroup.getCurrentMainScene();
            val index = mainScenes.indexOf(current);
            if (index == 0) {
                setDisable(true);
            }
        }};
        val nextButton = new FusionButton("Next >>") {{
            setPrefWidth(150);
            setPrefHeight(navigatePane.getNode().getPrefHeight() - FusionPane.PADDING_V * 2);
            setOnlyAnimateWhenNotClicked(true);
        }};
        prevButton.setOnAction(e -> {
            if (curCount > 0) {
                curCount--;
            }
            sceneGroup.show(mainScenes.get(curCount), VSceneShowMethod.FROM_LEFT);
        });
        nextButton.setOnAction(e -> {
            totalCount = getCount();
            if (curCount < totalCount) {
                curCount++;
            }
            while (curCount >= mainScenes.size()) {
                mainScenes.add(new ImageScene());
                Image image = SwingFXUtils.toFXImage(mainModule.getImageByCount(curCount), null);
                mainScenes.get(curCount).setBackgroundImage(image);
                sceneGroup.addScene(mainScenes.get(curCount));
            }

            val current = sceneGroup.getCurrentMainScene();
            val index = mainScenes.indexOf(current);
            if (index != 0) {
                prevButton.setDisable(false);
            }
            sceneGroup.show(mainScenes.get(curCount), VSceneShowMethod.FROM_RIGHT);
        });
        navigatePane.getContentPane().getChildren().add(prevButton);
        navigatePane.getContentPane().getChildren().add(nextButton);
        navigatePane.getContentPane().widthProperty().addListener((ob, old, now) -> {
            if (now == null) {
                return;
            }
            val v = now.doubleValue();
            nextButton.setLayoutX(v - nextButton.getPrefWidth());
        });

        val box = new HBox(
                new HPadding(10),
                new VBox(
                        new VPadding(10),
                        sceneGroup.getNode(),
                        new VPadding(5),
                        navigatePane.getNode()
                )
        );
        stage.getInitialScene().getContentPane().getChildren().add(box);

        //菜单
        val menuScene = new VScene(VSceneRole.DRAWER_VERTICAL);
        menuScene.getNode().setPrefWidth(450);
        menuScene.enableAutoContentWidth();
        menuScene.getNode().setBackground(new Background(new BackgroundFill(
                Theme.current().subSceneBackgroundColor(),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        stage.getRootSceneGroup().addScene(menuScene, VSceneHideMethod.TO_LEFT);

        val menuVBox = new VBox() {{
            setPadding(new Insets(0, 0, 0, 24));
            getChildren().add(new VPadding(20));
        }};
        menuScene.getContentPane().getChildren().add(menuVBox);

        val loadButton = new FusionButton("添加图片");
        loadButton.setDisableAnimation(true);
        loadButton.setOnAction(e -> {
            // 创建文件选择器
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择图片");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            // 添加文件过滤器，限定选择的文件类型为图片
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.bmp", "*.jpeg")
            );

            // 显示文件选择器对话框并获取选中的多个文件
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                Label label = new Label();
                label.setTextFill(Color.WHITE);
                VProgressBar progressBar = new VProgressBar();
                progressBar.setLength(400);
                VBox vBox = new VBox(
                        label,
                        new VPadding(10),
                        progressBar
                );

                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        int totalFiles = selectedFiles.size();
                        for (int i = 0; i < totalFiles; i++) {
                            // 处理每个选中的图片文件
                            File selectedFile = selectedFiles.get(i);
                            com.cyc.imagon.entity.Image image = new com.cyc.imagon.entity.Image();
                            mainModule.storeImageWithCount(image.loadImage(selectedFile));
                            // 更新进度
                            progressBar.setProgress((double) (i + 1) / (double) (totalFiles));
                        }
                        return null;
                    }
                };

                // 启动任务
                new Thread(task).start();
                VStage progressStage = new VStage();
                progressStage.getStage().setResizable(false);
                progressStage.getStage().setHeight(110);
                progressStage.getStage().setWidth(500);
                progressStage.getInitialScene().getContentPane().getChildren().add(vBox);
                vBox.setAlignment(Pos.CENTER);
                vBox.setLayoutX(50);
                vBox.setLayoutY(25);
                progressStage.show();
                // 设置任务完成时的回调
                task.setOnSucceeded(event -> {
                    // 在 JavaFX 线程上更新 UI
                    Platform.runLater(() -> {
                        log.info("图片全部传入成功");
                        progressStage.close();
                    });
                });
            }
        });
        loadButton.setPrefWidth(400);
        loadButton.setPrefHeight(40);
        menuVBox.getChildren().add(loadButton);
        menuVBox.getChildren().add(new VPadding(20));

        val storeButton = new FusionButton("持久化");
        storeButton.setDisableAnimation(true);
        storeButton.setOnAction(e -> {
            mainModule.storeToHardDrive();
        });
        storeButton.setPrefWidth(400);
        storeButton.setPrefHeight(40);
        menuVBox.getChildren().add(storeButton);
        menuVBox.getChildren().add(new VPadding(20));

        final String note = new String(Files.readAllBytes(Paths.get("README.md")));

        val noteButton = new FusionButton("说明");
        noteButton.setDisableAnimation(true);
        noteButton.setOnAction(e ->
                SimpleAlert.showAndWait(Alert.AlertType.INFORMATION, note)
        );
        noteButton.setPrefWidth(400);
        noteButton.setPrefHeight(40);
        menuVBox.getChildren().add(noteButton);
        menuVBox.getChildren().add(new VPadding(20));

        val menuBtn = new FusionImageButton(ImageManager.get().load("images/menu.png")) {{
            setPrefWidth(50);
            setPrefHeight(VStage.TITLE_BAR_HEIGHT + 10);
            getImageView().setFitHeight(15);
            setLayoutX(-2);
            setLayoutY(-1);
        }};
        menuBtn.setOnAction(e -> stage.getRootSceneGroup().show(menuScene, VSceneShowMethod.FROM_LEFT));
        stage.getRoot().getContentPane().getChildren().add(menuBtn);

        stage.getStage().setWidth(1200);
        stage.getStage().setHeight(800);
        stage.getStage().centerOnScreen();
        stage.getStage().show();
    }
}
