import csv

movies = []

with open("Code/main.csv",'r') as file:
    reader = csv.DictReader(file)
    for row in reader:
        movies.append(row)

