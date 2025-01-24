import csv
def read_catalog(file_path):
    catalog = []
    with open(file_path, 'r') as file:
        reader = csv.DictReader(file)
        for row in reader:
            catalog.append(row)
    return catalog

def save_catalog(file_path, catalog):
    with open(file_path, 'w', newline='') as file:
        fieldnames = catalog[0].keys()
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(catalog)

def edit_item(catalog, index, field, new_value):
    if field in catalog[index]:
        catalog[index][field] = new_value

def add_item(catalog, new_item):
    catalog.append(new_item)
