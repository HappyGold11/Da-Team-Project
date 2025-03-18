# F1 Database Project

## 📌 Overview
The **F1 Database** is a Java-based application that allows users to view and search Formula 1 drivers and teams. It includes a graphical user interface (GUI) built with **Swing** and leverages **Maven** for dependency management. The backend loads and processes CSV data, and JUnit tests ensure functionality.

## 📁 Project Structure
```
Da-Team-Project/
├── src/
│   ├── main/java/com/example/
│   │   ├── Backend.java  # Handles CSV data loading and search logic
│   │   ├── Frontend.java  # Creates the GUI for users
│   ├── main/resources/csv/
│   │   ├── Drivers.csv  # CSV file with F1 driver data
│   │   ├── Teams.csv  # CSV file with F1 team data
│   ├── test/java/
│   │   ├── TestingBackend.java  # JUnit tests for Backend
│   │   ├── TestingFrontend.java  # JUnit tests for Frontend
│   ├── test/resources/csv/
│   │   ├── test.csv  # Sample CSV for testing
├── pom.xml  # Maven project configuration
└── README.md  # Project documentation
```
*Subject to change over time.*
## 🔧 Installation & Setup
### **1. Prerequisites**
- Java **17+** installed ([Download JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html))
- **Apache Maven** installed ([Download Maven](https://maven.apache.org/download.cgi))

### **2. Clone the Repository**
```sh
git clone https://github.com/yourusername/Da-Team-Project.git
cd Da-Team-Project
```

### **3. Build the Project**
Run the following command to compile the project and install dependencies:
```sh
mvn clean install
```

### **4. Run the Application**
To start the GUI application.

## 🛠 Features
✅ **Search F1 drivers and teams**  
✅ **Bookmark favorite drivers and teams**  
✅ **JUnit tests for backend and frontend**  
✅ **CSV-based data management**  
✅ **Swing-based GUI**  

## 🧪 Running Tests
To execute unit tests, run:
```sh
mvn test
```
If you get **CSV loading errors**, ensure test CSVs are in `src/test/resources/csv/`.

## 📌 Troubleshooting
### **Error: Could not find or load main class Frontend**
✔ Ensure the project is built using:
```sh
mvn clean install
```