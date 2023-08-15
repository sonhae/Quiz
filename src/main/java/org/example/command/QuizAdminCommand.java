package org.example.command;

import org.bukkit.command.CommandExecutor;
import org.example.entity.CommandNode;

public class QuizAdminCommand extends CommandNode {
    public QuizAdminCommand() {
        super("quizadmin", "quiz.admin", (commandSender, args) -> {
            commandSender.sendMessage("Usage: quizadmin start/stop");
            return true;
        });
        this.addChild(new QuizAdminStart());
    }

    public class QuizAdminStart extends CommandNode {
        public QuizAdminStart() {
            super("start", "quiz.admin.start", (commandSender, args) -> {
                commandSender.sendMessage("quiz start!");
                return true;
            });
        }
    }
}
