import csv
from Backend import *

def display_catalog(catalog):
    print("\nCatalog:")
    if not catalog:
        print("No Drivers in the catalog.")
    else:
        for i, item in enumerate(catalog):
            print(f"Name: {item['name']}, Team:{item['team']}, Wins:{item['wins']}, Podiums:{item['podiums']}, Salary:{item['salary']}, Contract ends:{item['contract_ends']}, Career points:{item['career_points']}, Current_standing:{item['current_standing']}")

def main():
    catalog_file = 'Code/Drivers.csv'
    catalog = read_catalog(catalog_file)

    while True:
        print("\nOptions:")
        print("1. Add a new Driver")
        print("2. Edit an existing Driver")
        print("3. View all Drivers")
        print("4. Exit")
        choice = input("Enter your choice: ").strip()

        if choice == '1':
            new_item = {
                'name': input("Enter new Driver name: ").strip(),
                'team': input("Enter Driver Team: ").strip(),
                'wins': input("Enter Driver Wins: ").strip(),
                'podiums': input("Enter Driver Podiums: ").strip(),
                'salary': input("Enter Driver Salary: ").strip(),
                'contract_ends': input("Enter Driver Contract end: ").strip(),
                'career_points': input("Enter Driver Career points: ").strip(),
                'current_standing': input("Enter Driver current standing: ").strip(),
            }
            add_item(catalog, new_item)
            print("Driver added successfully.")

        elif choice == '2':
            display_catalog(catalog)
            index = int(input("Enter the Driver number to edit: ").strip()) - 1
            field = input("Enter the field to edit (name,team,wins,podiums,salary,contract_ends,career_points,current_standing): ").strip()
            new_value = input(f"Enter the new value for {field}: ").strip()
            edit_item(catalog, index, field, new_value)
            print("Movie updated successfully.")

        elif choice == '3':
            display_catalog(catalog)

        elif choice == '4':
            save_catalog(catalog_file, catalog)
            print("Changes saved. Exiting program.")
            break

if __name__ == "__main__":
    main()
