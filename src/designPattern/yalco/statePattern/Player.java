package designPattern.yalco.statePattern;

public class Player {
    private Statement statement;
    public Player() {
        statement = new StoppedState();
    }
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void play() {
        statement.play(this);
    }

    public void stop() {
        statement.stop(this);
    }
}

interface Statement {
    void play(Player player);
    void stop(Player player);
}

class PlayingState implements Statement {
    @Override
    public void play(Player player) {
        System.out.println("game is already started");
    }
    @Override
    public void stop(Player player) {
        System.out.println("Pausing game");
        player.setStatement(new PausedState());
    }
}

class PausedState implements Statement {
    @Override
    public void play(Player player) {
        player.setStatement(new PlayingState());
        System.out.println("Resuming the game");
    }
    @Override
    public void stop(Player player) {
        System.out.println("Stopping game");
        player.setStatement(new StoppedState());
    }
}

class StoppedState implements Statement {
    @Override
    public void play(Player player) {
        player.setStatement(new PlayingState());
        System.out.println("Starting the game");
    }
    @Override
    public void stop(Player player) {
        System.out.println("game is already stopped");
    }
}

