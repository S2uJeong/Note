package cleanArchitecture.srp.facade;

public class Facade {
    private Subsystem1 subsystem1;
    private Subsystem2 subsystem2;
    private Subsystem3 subsystem3;

    public Facade() {
        this.subsystem1 = new Subsystem1();
        this.subsystem2 = new Subsystem2();
        this.subsystem3 = new Subsystem3();
    }

     public void operation() {
        subsystem1.operation1();
        subsystem2.operation2();
        subsystem3.operation3();

    }
}
