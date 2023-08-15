package org.example.quiz.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.assertj.core.api.Assertions;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.example.Main;
import org.example.config.QuizConfig;
import org.example.config.QuizSchedulerConfig;
import org.example.entity.Quiz;
import org.example.gui.GUIManager;
import org.example.gui.QuizGUI;
import org.example.entity.RadioMenuItem;
import org.example.quiz.QuizManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class QuizGUITest {
    private ServerMock server;

    private Main plugin;
    private PlayerMock player;
    InputStream ymlStream;
    YamlConfiguration config;

    InputStream ymlStream1;
    YamlConfiguration config1;
    QuizConfig quizConfig;
    QuizSchedulerConfig quizSchedulerConfig;
    Quiz testQuiz;
    QuizManager qm ;
    GUIManager gm ;

    @BeforeEach
    public void setUp() {
        ymlStream = getClass().getClassLoader().getResourceAsStream("quiz.yml");
        config = YamlConfiguration.loadConfiguration(new InputStreamReader(ymlStream));
        ymlStream1 = getClass().getClassLoader().getResourceAsStream("quiz_scheduler.yml");
        config1 = YamlConfiguration.loadConfiguration(new InputStreamReader(ymlStream1));

        server = MockBukkit.getOrCreateMock();
        plugin = MockBukkit.load(Main.class);
        plugin.onEnable();
        player = server.addPlayer("Tester");
        quizConfig = new QuizConfig(config);
        quizSchedulerConfig = new QuizSchedulerConfig(config1);
        testQuiz = quizConfig.getQuiz("vanilla");
        gm = plugin.getGuiManager();
        qm = plugin.getQuizManager();
        player.setOp(true);

        plugin.getConfigManager().setQuizConfig(quizConfig);
        plugin.getConfigManager().setQuizSchedulerConfig(quizSchedulerConfig);
    }


    @Test
    public void testQuizScheduler(){
        TextComponent normalText = new TextComponent("뉴퀴즈  - ");
        TextComponent clickableText = new TextComponent("[퀴즈풀기]");
        clickableText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/qcb " + 0));
        normalText.addExtra(clickableText);

        server.getScheduler().performTicks(20L * quizSchedulerConfig.interval() * 10);
        System.out.println(server.getScheduler().getCurrentTick());
        player.assertSaid(normalText.toLegacyText());

        player.performCommand("qcb 0");

        player.simulateInventoryClick(3);



        player.simulateInventoryClick(8);
        Assertions.assertThat(gm.isPlayerOpenGUI(player)).isFalse();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.nextMessage();
        player.assertSaid("종료됨");
    }
    @Test
    public void testQuizStop(){

        qm.stopQuiz(player);

        qm.startQuiz(player, testQuiz.getQuestionMap().values().stream().findFirst().get());
        Assertions.assertThat(gm.isPlayerOpenGUI(player)).isTrue();
        Assertions.assertThat(gm.getOpenedGUI(player)).isInstanceOf(QuizGUI.class);
        qm.stopQuiz(player);

        Assertions.assertThat(gm.isPlayerOpenGUI(player)).isFalse();

        player.assertSaid("중단됨");
    }

    @Test
    public void testQuiz(){
        qm.startQuiz(player, testQuiz.getQuestionMap().values().stream().findFirst().get());


        Assertions.assertThat(gm.getOpenedGUI(player)).isInstanceOf(QuizGUI.class);

        player.simulateInventoryClick(3);



        player.simulateInventoryClick(8);
        Assertions.assertThat(gm.isPlayerOpenGUI(player)).isFalse();
        player.nextMessage();
        player.assertSaid("종료됨");
    }

    @Test
    public void testQuizGUI() {

        QuizGUI gui = new QuizGUI(player,testQuiz.getQuestionMap().values().stream().findFirst().get());
        Assertions.assertThat(gui.selectedQuestion()).isEqualTo(-1);
        Assertions.assertThat(gui.getInv().getItem(0).getItemMeta().displayName().toString()).contains("질문");
        Assertions.assertThat(gui.getShuffledChoices().size()).isEqualTo(4);
        Assertions.assertThat(gui.getItems().length).isEqualTo(9);

        plugin.getGuiManager().openGUI(player,gui);


        InventoryClickEvent e = player.simulateInventoryClick(3);
        Inventory inv  = e.getClickedInventory();

        RadioMenuItem radio = (RadioMenuItem) gui.getItems()[3];

        //Assertions.assertThat(radio.getItem()).isEqualTo(inv.getItem(3));


        //Assertions.assertThat(radio.isSelected()).isTrue();
    //    player.assertSaid("정답!");
        Assertions.assertThat(gui.getInv().getItem(3).getType()).isEqualTo(Material.RED_WOOL);
        Assertions.assertThat(gui.getInv().getItem(8).getItemMeta().displayName().toString()).contains("전송");

    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
