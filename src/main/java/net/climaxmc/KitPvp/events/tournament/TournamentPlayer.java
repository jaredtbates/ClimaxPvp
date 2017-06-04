package net.climaxmc.KitPvp.events.tournament;

public class TournamentPlayer {

    public TPState getState() {
        return state;
    }

    public void setState(TPState state) {
        this.state = state;
    }

    private TPState state;

    public TournamentPlayer() {}
}
