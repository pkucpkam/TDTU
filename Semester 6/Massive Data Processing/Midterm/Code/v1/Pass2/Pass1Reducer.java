public static class Pass1Reducer extends Reducer<Text, Text, Text, Text> {
    private static final int SUPPORT_THRESHOLD = 2;

    @Override
    protected void reduce(Text customerID, Iterable<Text> dates, Context context) throws IOException, InterruptedException {
        Set<String> uniqueDates = new HashSet<>();
        for (Text date : dates) {
            uniqueDates.add(date.toString());
        }
        if (uniqueDates.size() >= SUPPORT_THRESHOLD) {
            context.write(customerID, new Text(String.valueOf(uniqueDates.size())));
        }
    }
}