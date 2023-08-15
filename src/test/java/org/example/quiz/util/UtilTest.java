package org.example.quiz.util;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.assertj.core.api.Assertions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.example.Main;
import org.example.util.ItemStackBuilder;
import org.junit.jupiter.api.*;

import java.util.List;

public class UtilTest {
    private ServerMock server;
    private Main plugin;

    @BeforeEach
    public void setUp(){
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Main.class);
    }

    @DisplayName("itemstack 생성 test")
    @Test
    public void UtilItemStackTest(){
        ItemStack item =  new ItemStackBuilder(Material.DIAMOND)
                .name("hi")
                .lore(List.of("a","b","c")).make();

        Assertions.assertThat(item.getItemMeta()).isNotNull();
        Assertions.assertThat(item.getItemMeta().getLore()).contains("b");

    }

    @AfterEach
    public void tearDown(){
        MockBukkit.unmock();
    }
}
