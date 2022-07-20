package com.terbah.sketch.app.ui;


import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.logging.Logger;

/**
 * @author Dorian Terbah
 *
 * @version 1.0
 *
 * This class represents the MenuBar located on the top of the application.
 */

@Service
public class SketchMenuBar extends MenuBar
{
    /**
     * Logger of the class
     */
    private Logger logger;

    @Autowired
    private void setLogger() {
        this.logger = SketchLoggerManager.getLogger(this.getClass());
    }

    /**
     * Default constructor of the <code>SketchMenuBar</code>.
     */
    public SketchMenuBar()
    {
        super();
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open (CTRL + O)");
        MenuItem closeFile = new MenuItem("Close");
        MenuItem openRecent = new MenuItem("Open Recent");
        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(openFile);
        fileMenu.getItems().add(closeFile);
        fileMenu.getItems().add(openRecent);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem saveFile = new MenuItem("Save (CTRL + S)");
        MenuItem saveAllFiles = new MenuItem("Save all");
        fileMenu.getItems().add(saveFile);
        fileMenu.getItems().add(saveAllFiles);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem settings = new MenuItem("Settings");
        fileMenu.getItems().add(settings);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitApp = new MenuItem("Exit app");
        fileMenu.getItems().add(exitApp);

        saveFile.setOnAction(event -> {
            // save the board
        });

        openFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File save = fileChooser.showOpenDialog(null);
            if (save != null)
            {
                // save the board
            }
        });

        /**
         * Run Menu
         */
        Menu runMenu = new Menu("Run");
        MenuItem run = new MenuItem("Run current Sketch");
        MenuItem runAll = new MenuItem("Run all Sketch");

        runMenu.getItems().add(run);
        runMenu.getItems().add(runAll);

        /**
         * Help menu
         */
        Menu helpMenu = new Menu("Help");

        this.getMenus().add(fileMenu);
        this.getMenus().add(runMenu);
        this.getMenus().add(helpMenu);
    }
}