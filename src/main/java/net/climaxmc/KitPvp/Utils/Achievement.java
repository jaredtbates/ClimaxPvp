package net.climaxmc.KitPvp.Utils;
/* Created by GamerBah on 2/9/2016 */


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Achievement {
    FIRST_KILL("First KilL", "Get your first kill!");

    public String name;
    public String description;

}
