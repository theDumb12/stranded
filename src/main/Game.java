package main;

import gui.StrandedApplet;

import java.util.ArrayList;
import java.util.List;

import audio.Audio;
import processing.core.PApplet;
import tasks.BasicTaskCreator;
import tasks.Task;
import tasks.TaskCreator;
import tasks.TaskRunner;
import util.RandomPhraseAccessor;
import characters.BasicCharacterCreator;
import characters.Character;
import characters.CharacterCreator;
import characters.Skill;

public class Game {
    private StrandedApplet applet;
    public List<Task> tasks = new ArrayList<Task>();
    public List<Character> characters = new ArrayList<Character>();
    public Resources resources = new Resources();
    public TaskRunner taskRunner = new TaskRunner(this);
    
    
    public Game(StrandedApplet applet) {
        this.applet = applet;
    }

    public void start() {
        TaskCreator taskCreator = new BasicTaskCreator();
        CharacterCreator characterCreator = new BasicCharacterCreator();
        for (int i = 0; i < 4; i++) {
            tasks.add(taskCreator.createTask());
            characters.add(characterCreator.createCharacter());
        }
        promptNextCharacter();
    }
    
    public void promptNextCharacter() {
        Character c = characters.get(0);
        applet.consolePrinter.print(c.getFirstName() + " " + c.getLastName() + ": " + RandomPhraseAccessor.getRandomPhrase(), applet.color(0, 128, 0));
        applet.mainAudio.updateBeep();
    }
    
    public void assignTask(Task task, Character character) {
        applet.consolePrinter.print("Task: " + task.getName() + " assigned to character: " + character.getFirstName(), applet.color(255, 255, 0));
        task.setCharacter(character);
        taskRunner.startTask(task);
        tasks.remove(task);
        characters.remove(0);
        if(task.getPrimarySkill() == Skill.FIGHTING) {
            applet.mainAudio.fightSounds();
        }
        updateTasks();
        if (characters.size() > 0) {
            promptNextCharacter();
        }
    }
    
    public void updateTasks() {
        boolean shouldNotify = characters.size() == 0;
        for (Task t : taskRunner.getCompletedTasks()) {
            if (t.getSucceeded()) {
                applet.consolePrinter.print("Task succeeded: " + t.getName(), applet.color(0, 255, 0));
            } else {
                applet.consolePrinter.print("Task failed: " + t.getName(), applet.color(255, 0, 0));
            }
            characters.add(t.getCharacter());
        }
        long now = System.currentTimeMillis();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.isExpires() && t.getExpirationTime() < now) {
                t.setCompleted(true);
                t.setSucceeded(false);
                resources.subtract(t.getPenalty());
                applet.consolePrinter.print("Task " + t.getName() + " has expired", applet.color(128,0,0));
                applet.mainAudio.failSound();
                tasks.remove(i);
                i--;
            }
        }
        if (shouldNotify && characters.size() > 0) {
            promptNextCharacter();
        }
    }
}