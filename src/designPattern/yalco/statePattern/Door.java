package designPattern.yalco.statePattern;

public class Door {
    private State state;
    public Door() {
        this.state = new ClosedState();
    }
    public void setState(State state) {
        this.state = state;
    }
    public void open() {
        state.open(this);
    }
    public void close() {
        state.close(this);
    }
}

interface State {
    void open(Door door);
    void close(Door door);
}

class ClosedState implements State {
    @Override
    public void open(Door door) {
        System.out.println("Door is now open");
        door.setState(new OpenState());
    }

    @Override
    public void close(Door door) {
        System.out.println("Door is already closed");
    }
}

class OpenState implements State {
    @Override
    public void open(Door door) {
        System.out.println("Door is already open");
    }

    @Override
    public void close(Door door) {
        System.out.println("Door is now close");
        door.setState(new ClosedState());
    }
}