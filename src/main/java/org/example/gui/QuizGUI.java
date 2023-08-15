package org.example.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.example.Main;
import org.example.entity.Question;
import org.example.entity.GUI;
import org.example.entity.MenuItem;
import org.example.entity.RadioMenuItem;
import org.example.util.MenuBuilder;
import org.example.util.RadioMenuItemMenuBuilder;
import org.example.util.ItemStackBuilder;
import org.example.util.SlotBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class QuizGUI extends GUI {

    private Question question;
    private List<String> shuffledChoices;

    private static final int QUESTION_INDEX = 0;
    private static final int FIRST_CHOICE_INDEX = 3;
    private static final int LAST_CHOICE_INDEX = 6;

    private static final int NOT_SELECTED = -1;

    @Override
    protected void initialize() {
        MenuItem question = new MenuBuilder(this).id(QUESTION_INDEX).item(
                new ItemStackBuilder(Material.DIAMOND)
                        .name("&a질문: " + this.question.getQuestion())
                        .make()

        ).make();
        RadioMenuItemMenuBuilder radioMenuItemBuilder = new RadioMenuItemMenuBuilder(this);
        IntStream.range(0, shuffledChoices.size()).forEach(i -> {
            radioMenuItemBuilder.id(i + FIRST_CHOICE_INDEX)
                    .item(new ItemStackBuilder(Material.RED_WOOL).name(shuffledChoices.get(i)).make())
                    .deselectedItem(new ItemStackBuilder(Material.WHITE_WOOL).name(shuffledChoices.get(i)).make())
                    .onLeftClick(e ->{
                    })
                    .addItem();
        });

        RadioMenuItem choices = radioMenuItemBuilder.make();

        MenuItem submit = new MenuBuilder(this).id(8).item(
                        new ItemStackBuilder(Material.GREEN_WOOL).name("전송").make())
                .onLeftClick(gui -> {
                    if (selectedQuestion() == NOT_SELECTED){
                        getHandler().sendMessage("선택되지 않음");
                    } else {
                        if (shuffledChoices.get(selectedQuestion()).equals(this.question.getCorrect().orElse(""))) {
                            getHandler().sendMessage("정답!");
                            //todo sigreward API integration
                        }
                        else {
                            getHandler().sendMessage("실패!");
                        }
                        Main.getInstance().getGuiManager().closeGUI(getHandler());
                        getHandler().sendMessage("종료됨");

                    }


                }).make();

        SlotBuilder<MenuItem> slotBuilder = new SlotBuilder<MenuItem>(size, MenuItem.class);

        MenuItem[] items = slotBuilder.item(question)
                .skip(2)
                .items(new ArrayList<>(choices.getGroup()))
                .skip()
                .item(submit)
                .make();

        this.setItems(items);
        update();
    }

    public QuizGUI(Player handler, Question q) {
        super(Bukkit.createInventory(handler, 9, "Quiz"), handler, 9);
        this.question = q;
        shuffledChoices = this.question.shuffleChoices();
        initialize();
    }

    public int selectedQuestion() {
        RadioMenuItem item = (RadioMenuItem) getItems()[FIRST_CHOICE_INDEX];
        return item.selectedIndex().orElse(NOT_SELECTED);
    }



    public List<String> getShuffledChoices() {
        return shuffledChoices;
    }


}
