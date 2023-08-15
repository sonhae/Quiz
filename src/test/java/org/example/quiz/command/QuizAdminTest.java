package org.example.quiz.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.assertj.core.api.Assertions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.Main;
import org.example.config.QuizConfig;
import org.example.entity.CommandNode;
import org.example.entity.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class QuizAdminTest {
    private ServerMock server;

    private Main plugin;
    private PlayerMock player;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.getOrCreateMock();
        plugin = MockBukkit.load(Main.class);
        player = server.addPlayer("Tester");
        player.setOp(true);
    }

    @Test
    public void QuizAdminstartTest() {
        player.performCommand("quizadmin start");
        player.assertSaid("quiz start!");
    }

    @Test
    public void QuizAdmintest() {
        player.performCommand("quizadmin");
        player.assertSaid("Usage: quizadmin start/stop");
    }

    @Test
    public void quizadminTabTest() {
        CommandNode node = plugin.getCommandManager().getRootCommands().get("quizadmin");

        Assertions.assertThat(node.onTabComplete(player,null,"",new String[]{"start"})).contains("start");
    }
}
