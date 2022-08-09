package com.terbah.sketch.app.ui.controller.command;

import java.util.Stack;

/**
 * @author Dorian TERBAH
 *
 * Command history of the app.
 *
 * @version 1.0
 */
public final class CommandManager {

    private static final Stack<Command> history = new Stack<>();

    public static void executeCommand(Command command) {
        if (command.execute()) {
            history.push(command);
        }
    }

    public static void undo() {
        Command command = history.pop();
        if (command != null) {
            // command undo
        }
    }

    public boolean isEmpty() { return history.isEmpty(); }
}
