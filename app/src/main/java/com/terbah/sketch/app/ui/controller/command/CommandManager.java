package com.terbah.sketch.app.ui.controller.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Dorian TERBAH
 *
 * Command history of the app.
 *
 * @version 1.0
 */
public final class CommandManager {

    private static final Deque<Command> history = new ArrayDeque<>();

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
