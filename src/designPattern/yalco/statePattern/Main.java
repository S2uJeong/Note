package designPattern.yalco.statePattern;

public class Main {
    public static void main(String[] args) {
        Door door = new Door();
        door.open();
        door.open();
        door.close();
        door.close();

        Player player = new Player();
        player.play();
        player.play();
        player.stop();
        player.stop();
        player.stop();
        player.play();
    }
}
