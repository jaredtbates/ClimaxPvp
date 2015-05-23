package net.climaxmc.API;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sidebar {

    private Map<String, Integer> values = new ConcurrentHashMap<String, Integer>();
    private Scoreboard scoreboard;
    private Objective objective;

    public Sidebar(String objectiveName) {
        this(objectiveName, objectiveName);
    }

    public Sidebar(String objectiveName, String title) {
        this(objectiveName, title, Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public Sidebar(String objectiveName, Scoreboard existingScoreboard) {
        this(objectiveName, objectiveName, existingScoreboard);
    }

    public Sidebar(String objectiveName, String title, Scoreboard existingScoreboard) {
        this.scoreboard = existingScoreboard;
        if (scoreboard.getObjective(DisplaySlot.SIDEBAR) != null && objectiveName == null) {
            this.objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        } else {
            this.objective = scoreboard.registerNewObjective(objectiveName, "dummy");
        }
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(title);
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public String[] getValues(int key) {
        return Maps.filterValues(values, new FilterIntegers(key)).keySet().toArray(new String[0]);
    }

    public String getFirstValue(int key) {
        return Maps.filterValues(values, new FilterIntegers(key)).keySet().iterator().next();
    }

    public String getLastValue(int key) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setValue(int key, String value) {
        objective.getScore(value).setScore(key);
        this.values.put(value, key);

    }

    public String getTitle() {
        return objective.getDisplayName();
    }

    public void setTitle(String title) {
        this.objective.setDisplayName(title);
    }


    public void removeValues(int key) {
        for (String value : this.values.keySet()) {
            if (this.values.get(value) == key) {
                getScoreboard().resetScores(value);
                this.values.remove(value);
            }
        }
    }

    public void removeFirstValue(int key) {
        for (String value : this.values.keySet()) {
            if (this.values.get(value) == key) {
                getScoreboard().resetScores(value);
                this.values.remove(value);
                break;
            }
        }
    }

    public void removeValue(String value) {
        getScoreboard().resetScores(value);
        this.values.remove(value);
    }

    public void overrideValue(int key, String value) {
        this.removeValues(key);
        this.values.put(value, key);
        getObjective().getScore(value).setScore(key);
    }

    public int addValue(String value) {
        int key = values.size();
        objective.getScore(value).setScore(key);
        this.values.put(value, key);
        return key;
    }

    public int getKey(String value) {
        return this.values.get(value);
    }

    public void overrideFirstValue(int key, String value) {
        this.removeFirstValue(key);
        this.values.put(value, key);
        getObjective().getScore(value).setScore(key);
    }

    public class FilterIntegers implements Predicate<Integer> {

        private int number;

        public FilterIntegers(int number) {
            this.number = number;
        }

        public boolean apply(Integer arg0) {
            return (number == arg0);
        }


    }

}
