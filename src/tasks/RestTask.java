package tasks;

import characters.Skill;

public class RestTask extends Task {
    public Task getFollowUpTask() {
        return new RestTask();
    }
    public String getName() {
        return "Skip";
    }
    public long getCompletionTime() {
        return 1;
    }
    public int getDifficulty() {
        return -10;
    }
    public Skill getPrimarySkill() {
        return Skill.SCAVENGING;
    }
}
