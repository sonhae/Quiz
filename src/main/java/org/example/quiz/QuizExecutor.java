package org.example.quiz;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.example.Main;
import org.example.entity.Question;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class QuizExecutor {

    private Player player;
   private List<Question> questionList ;
  private   BukkitRunnable task;


    public QuizExecutor(Player player, List<Question> questionList) {
        this.player = player;
        this.questionList = questionList;
    }

    public void startTask(){
        int interval = Main.getInstance().getConfigManager().getQuizSchedulerConfig().interval();

        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                int count = Main.getInstance().getConfigManager().getQuizSchedulerConfig().count();
                Main.getInstance().getQuizManager().resetQuiz(player, count);
                IntStream.range(0,count).forEach(i ->{
                    TextComponent normalText = new TextComponent("뉴퀴즈  - ");
                    TextComponent clickableText = new TextComponent("[퀴즈풀기]");
                    clickableText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/qcb " + i));
                    normalText.addExtra(clickableText);
                    player.sendMessage(normalText.toLegacyText());
                });

            }
        };
        task.runTaskTimer(Main.getInstance(),0,20L * interval);
    }

    public void stopTask(){
        if (task != null) task.cancel();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public BukkitRunnable getTask() {
        return task;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
