from Backend import movies

run = True

while(run):
    print("Cattolog: ")
    for i in range(3):
        print(movies[i]["Movie Name"])
    
    user_input = input("Enter movie number for full detail: ")
    print(movies[int(user_input)])

    exit_input = input("do you want to exit (type 'yes' to exit): ")
    if exit_input == "yes":
        run = False


