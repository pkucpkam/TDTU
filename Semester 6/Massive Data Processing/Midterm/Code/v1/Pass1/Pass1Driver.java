

public static class Pass1Mapper extends Mapper<Object, Text, Text, Text> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();
        if (!line.contains("CustomerID")) {
            String[] fields = line.split(",");
            if (fields.length >= 2) {
                String customerID = fields[0].trim();
                String date = fields[1].trim();
                context.write(new Text(customerID), new Text(date));
            }
        }
    }
}