package org.example.quiz;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.example.Main;
import org.example.entity.Question;
import org.example.gui.GUIManager;
import org.example.gui.QuizGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizManager {
    private Map<Player, QuizExecutor> quest = new HashMap<>();

    public void startQuiz(Player p, Question q) {
        GUIManager gm = Main.getInstance().getGuiManager();
        gm.openGUI(p, new QuizGUI(p, q));
    }

    public void startScheduler(Player p){
        QuizExecutor executor = new QuizExecutor(p, new ArrayList<>());
        quest.put(p, executor);
                executor.startTask();
    }

    public void stopScheduler(Player p){
        quest.remove(p).stopTask();
    }

    public void stopQuiz(Player p) {
        GUIManager gm = Main.getInstance().getGuiManager();
        if (gm.isPlayerOpenGUI(p) && gm.getOpenedGUI(p) instanceof QuizGUI) {
            Main.getInstance().getGuiManager().closeGUI(p);
            p.sendMessage("중단됨");
        }
    }

    public QuizExecutor getExecutor(Player p){
        return quest.get(p);
    }

    public void resetQuiz(Player p, int n) {
        stopQuiz(p);

        List<Question> questions =  Main.getInstance().getConfigManager().getQuizConfig().getRandomQuestion(n);



        getExecutor(p).setQuestionList(questions);
    }

    public QuizExecutor getQuest(Player p) {
        return this.quest.get(p);
    }
}
