from pyspark import SparkContext

sc = SparkContext.getOrCreate()

data = sc.textFile("hdfs://localhost:9000/lab04/input/WHO-COVID-19-20210601-213841.tsv")

header = data.first()

sea_cases = (
    data
    .filter(lambda line: line != header)  
    .map(lambda line: line.split('\t'))   
    .filter(lambda cols: len(cols) > 2 and cols[1].strip() == "South-East Asia")  
    .map(lambda cols: int(float(cols[2].replace(',', '').strip())) if cols[2].strip() else 0) 
    .sum()  
)

print(f"Total cumulative COVID-19 cases in South-East Asia: {sea_cases}")

sc.stop()
