package org.example.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.example.Main;
import org.example.entity.CommandException;
import org.example.entity.CommandNode;
import org.example.quiz.QuizManager;

public class QuizCallbackCommand extends CommandNode {

    public QuizCallbackCommand() {
        super("qcb", "quiz.quiz", (sender, args) -> {
            if (!(sender instanceof Player)) {

                throw new CommandException("잘못된 접근");

            }

            QuizManager qm = Main.getInstance().getQuizManager();

            try {
                qm.startQuiz((Player) sender, qm.getQuest((Player) sender).getQuestionList().get(Integer.parseInt(args[0])));

            } catch (Exception e) {
                e.printStackTrace();
               throw new CommandException("잘못된 접근");

            }
            return true;
        });
    }
}
