package com.terbah.sketch.api.ui;

import javafx.scene.control.Alert;

import static javafx.scene.control.Alert.AlertType;

/**
 * @author Dorian TERBAH
 *
 * This class has to build alert popup.
 *
 * @since 1.0
 */
public class SketchAlertBuilder
{
    /**
     * Header of the alert.
     */
    private String header;

    /**
     * Title of the alert.
     */
    private String title;

    /**
     * Content of the alert.
     */
    private String content;

    /**
     * Alert type of the alert.
     */
    private AlertType alertType;

    /**
     * Default constructor of the alert builder.
     */
    private SketchAlertBuilder()
    {
        // Default values.
        this.content = "Content";
        this.title = "Title";
        this.header = "Header";
        this.alertType = AlertType.NONE;
    }

    /**
     * @return A new builder.
     */
    public static SketchAlertBuilder builder()
    {
        return new SketchAlertBuilder();
    }

    /**
     * Set a new value for the content of the alert.
     * @param content The new content.
     * @return The current builder.
     */
    public SketchAlertBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    /**
     * Set a new value for the header of the alert.
     * @param header The new header.
     * @return The current builder.
     */
    public SketchAlertBuilder header(String header)
    {
        this.header = header;
        return this;
    }

    /**
     * Set a new value for the title of the alert.
     * @param title The new title.
     * @return The current builder.
     */
    public SketchAlertBuilder title(String title)
    {
        this.title = title;
        return this;
    }

    /**
     * Set a new value for the alert type.
     * @param alertType The new alert type.
     * @return The current builder.
     */
    public SketchAlertBuilder alertType(AlertType alertType)
    {
        this.alertType = alertType;
        return this;
    }

    /**
     * @return The alert associated to the builder.
     */
    public Alert build()
    {
        Alert alert = new Alert(this.alertType);
        alert.setContentText(this.content);
        alert.setTitle(this.title);
        alert.setHeaderText(this.header);
        return alert;
    }
}