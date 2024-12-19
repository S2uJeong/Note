package bookStrangeCode.strategyWithMap;

public interface Magic {
    String name();
    CostMagicPoint costMagicPoint();
    AttackPower attackPower();
    CostTechnicalPoint costTechnicalPoint();
}

class Fire implements Magic {
    private final Member member;

    public Fire(Member member) {
        this.member = member;
    }

    @Override
    public String name() {
        return "파이어";
    }

    @Override
    public CostMagicPoint costMagicPoint() {
        return new CostMagicPoint(2);
    }

    @Override
    public AttackPower attackPower() {
        return new AttackPower(20 + (int) (member.level() * 0.5));
    }

    @Override
    public CostTechnicalPoint costTechnicalPoint() {
        return new CostTechnicalPoint(0);
    }
}

class Lightning implements Magic {
    private final Member member;

    public Lightning(Member member) {
        this.member = member;
    }

    @Override
    public String name() {
        return "라이트닝";
    }

    @Override
    public CostMagicPoint costMagicPoint() {
        return new CostMagicPoint(5 + (int)(member.level() * 1.5));
    }

    @Override
    public AttackPower attackPower() {
        return new AttackPower(50);
    }

    @Override
    public CostTechnicalPoint costTechnicalPoint() {
        return new CostTechnicalPoint(5);
    }
}

