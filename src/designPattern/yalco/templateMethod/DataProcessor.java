package designPattern.yalco.templateMethod;

abstract class DataProcessor {
    public final void process(String data) {
        loadData(data);
        if (isVaildData(data)) {
            processData(data);
            saveData(data);
        } else {
            System.out.println("Data is invaild, processing aborted");
        }

    }
    protected abstract void loadData(String data);
    protected abstract boolean isVaildData(String data);
    protected abstract void processData(String data);
    protected abstract void saveData(String data);
}

class CSVDataProcessor extends DataProcessor {
    @Override
    protected void loadData(String data) {
        System.out.println("Loading data from CSV file: " + data);
    }

    @Override
    protected boolean isVaildData(String data) {
        return data != null && data.contains("CSV");
    }

    @Override
    protected void processData(String data) {
        System.out.println("processing CSV data");
    }

    @Override
    protected void saveData(String data) {
        System.out.println("saving CSV data to database");
    }
}

class JSONDataProcessor extends DataProcessor {
    @Override
    protected void loadData(String data) {
        System.out.println("Loading data from JSON file: " + data);
    }

    @Override
    protected boolean isVaildData(String data) {
        return data != null && data.contains("JSON");
    }

    @Override
    protected void processData(String data) {
        System.out.println("processing JSON data");
    }

    @Override
    protected void saveData(String data) {
        System.out.println("saving JSON data to database");
    }
}

