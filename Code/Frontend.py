import csv
from Backend import *

def display_catalog(catalog):
    print("\nCatalog:")
    if not catalog:
        print("No movies in the catalog.")
    else:
        for i, item in enumerate(catalog):
            genre = item.get('Genre', 'Unknown')
            print(f"{i + 1}. {item['Movie Name']} (Genre: {genre})")

def main():
    catalog_file = 'Code/main.csv'
    catalog = read_catalog(catalog_file)

    while True:
        print("\nOptions:")
        print("1. Add a new movie")
        print("2. Edit an existing movie")
        print("3. View all movies")
        print("4. Exit")
        choice = input("Enter your choice: ").strip()

        if choice == '1':
            new_item = {
                'Movie Name': input("Enter new movie name: ").strip(),
                'Genre': input("Enter movie genre: ").strip()
            }
            add_item(catalog, new_item)
            print("Movie added successfully.")

        elif choice == '2':
            display_catalog(catalog)
            index = int(input("Enter the movie number to edit: ").strip()) - 1
            field = input("Enter the field to edit (Movie Name/Genre): ").strip()
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
