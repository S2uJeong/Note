package designPattern.yalco.templateMethod;

public class Main {
    public static void main(String[] args) {
        testBeverage();
        testDataProcessor();
    }

    private static void testBeverage() {
        Beverage tea = new Tea();
        Beverage coffee = new Coffee();
        tea.prepareRecipe();
        System.out.println("====================");
        coffee.prepareRecipe();
    }

    private static void testDataProcessor() {
        DataProcessor csvProcessor = new CSVDataProcessor();
        csvProcessor.process("CSV data");
        System.out.println("====================");
        DataProcessor jsonProcessor = new JSONDataProcessor();
        jsonProcessor.process("JSON data");

    }
}
